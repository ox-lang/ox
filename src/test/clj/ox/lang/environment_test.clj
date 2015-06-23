(ns ox.lang.environment-test
  (:require [ox.lang.environment :as env]
            [ox.lang.environment.types :as env.t]
            [clojure.set :as set]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

(defn add-inters [env kvs]
  (reduce (fn [e [s v]]
            (env/inter e s v))
          env kvs))

(defspec inter-resolve-tests
  (let [e0 env.t/bootstrap-env]
    (prop/for-all [ks (gen/not-empty (gen/list gen/symbol))]
      (let [ks (map (comp (partial symbol "user") name) ks)
            kvs          (map vector ks (range))
            
            expected-env (into {} kvs)
            env          (add-inters e0 expected-env)]
        (and (every? (partial env/resolve env) ks)
             (every? #(= (get expected-env %)
                         (env/get-value env %)) ks))))))

(defspec push-locals-tests
  (let [e0 env.t/bootstrap-env]
    (prop/for-all [cks (gen/not-empty (gen/list gen/symbol))
                   pks (gen/not-empty (gen/list gen/symbol))]
      (let [pks          (map (comp (partial symbol "user") name) pks)
            pkvs         (map vector pks (range))
            pinters      (into {} pkvs)

            ckvs         (map vector cks (range (count pks) (+ (count pks) (count cks))))
            expected-env (into {} ckvs)

            env          (-> e0
                             (add-inters pinters)
                             (env/push-locals expected-env))]
        (and
         ;; Are locals handled correctly
         (every? #(= (get expected-env %)
                     (env/get-value env %)) cks)

         ;; Are qualified globals handled correctly
         (every? #(= (get pinters %)
                     (->>  %
                           name
                           (symbol "user")
                           (env/get-value env)))
                 pks)

         ;; Are unqualified unaliased globals handled correctly. We know that
         ;; locals cover globals just fine from the first every?
         (let [visible-globals (set/difference (set pks) (set cks))]
           (every? #(= (get pinters %)
                       (env/get-value env %)) visible-globals)))))))

(deftest push-dynamics-test
  (let [k    'user/*foo*
        env  (-> env.t/bootstrap-env
                 (env/inter k {:dynamic true} 1))
        env1 (env/push-dynamics env {k 2})
        env2 (env/push-dynamics env {k 3})]
    ;; basic sanity checks
    (is (env/dynamic? env k))
    (is (env/dynamic? env1 k))
    (is (env/dynamic? env2 k))

    ;; value checks
    (is (= 1 (env/get-value env k)))
    (is (= 2 (env/get-value env1 k)))
    (is (= 3 (env/get-value env2 k)))))

(deftest alter-meta-test
  (let [env (-> env.t/bootstrap-env
                (env/inter 'user/foo {:a true} 4))]
    (->> 'user/foo
         (env/get-meta env)
         :a
         true?
         is)
    
    (-> env
        (env/alter-meta 'user/foo assoc :a false)
        (env/get-meta 'user/foo)
        :a
        false?
        is)))

(deftest get-global-env-test
  (-> env.t/bootstrap-env
      (env/push-locals {'a 3
                        'b 4})
      (env/push-dynamics {})
      (env/get-global-env)
      (env.t/global?)))

(deftest resolve-test
  (let [env (-> env.t/bootstrap-env
                (env/inter 'user/foo 3)
                (env/alias 'user/bar  'user/foo)
                (env/alias 'user/qux  'user/bar)
                (env/alias 'user/boom 'user/qux))]
    (doseq [var ['user/foo 'user/bar 'user/qux]]
      (is (= 3 (env/get-value env var)))
      (is (env/get-entry env var))
      (is (env/resolve env var)))

    (is (thrown? Exception (env/get-entry env 'user/boom)))
    (is (thrown? Exception (env/get-value env 'user/boom)))
    (is (thrown? Exception (env/resolve env   'user/boom)))

    (is (env/get-entry env 'user/boom 4))
    (is (= 3 (env/get-value env 'user/boom 4)))
    (is (env/resolve env 'user/boom 4))))
