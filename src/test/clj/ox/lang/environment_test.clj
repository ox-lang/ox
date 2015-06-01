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
  (let [e0 (env.t/->ns 'test)]
    (prop/for-all [ks (gen/not-empty (gen/list gen/symbol))]
      (let [kvs          (map vector ks (range))
            expected-env (into {} kvs)
            env          (add-inters e0 expected-env)]
        (and (every? (partial env/resolve env) ks)
             (every? #(= (get expected-env %)
                         (env/get-value env %)) ks))))))

(defspec push-locals-tests
  (let [e0 (env.t/->ns 'test)]
    (prop/for-all [cks (gen/not-empty (gen/list gen/symbol))
                   pks (gen/not-empty (gen/list gen/symbol))]
      (let [pkvs         (map vector pks (range))
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
                     (env/get-value env (symbol "test" (name %)))) pks)

         ;; Are unqualified unaliased globals handled correctly. We know that
         ;; locals cover globals just fine from the first every?
         (let [visible-globals (set/difference (set pks) (set cks))]
           (every? #(= (get pinters %)
                       (env/get-value env %)) visible-globals)))))))

(deftest push-dynamics-test
  (let [env  (-> (env.t/->ns 'test)
                 (env/inter '*foo* 1)
                 (env/alter-meta 'test/*foo* #(assoc % :dynamic true)))
        env1 (env/push-dynamics env {'test/*foo* 2})
        env2 (env/push-dynamics env1 {'test/*foo* 3})]
    (is (env/dynamic? env 'test/*foo*))
    (is (= 1 (env/get-value env 'test/*foo*)))
    (is (= 2 (env/get-value env1 'test/*foo*)))
    (is (= 3 (env/get-value env2 'test/*foo*)))))
