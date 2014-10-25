(ns oxlang.parser-test
  (:require [oxlang.parser :refer :all]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

(defspec terminal-matches-only
  (prop/for-all [m gen/char
                 n gen/char]
      (let [g {:c {:op :term :val m}}
            r (parse g :c [n])]
        (if (= m n)
          (= (:result r) :success)
          (= (:result r) :failure)))))

(defspec terminal-transform-ok
  (prop/for-all [m gen/char]
      (let [t (fn [x] (/ (max (long x) 1) 2))
            g {:c {:op :term :val m :transform t}}
            r (parse g :c [m])]
        (= (:dat r) (t m)))))




