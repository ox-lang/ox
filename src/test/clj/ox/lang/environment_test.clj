(ns ox.lang.environment-test
  (:require [ox.lang.environment :as env]
            [ox.lang.environment.types :as env.t]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

(defspec inter-resolve-tests
  (let [e0 (env.t/->ns 'test)]
    (prop/for-all [ks (gen/not-empty (gen/list gen/symbol))]
      (let [e (reduce (fn [e [s v]] (env/inter e s v))
                      e0 (map list ks (range)))]
        (every? (partial env/resolve e) ks)))))
