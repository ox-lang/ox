(ns oxlang.parser-test
  (:require [oxlang.parser :refer :all]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

(defmacro distinct-n-tuple [a n]
  `(gen/such-that
       (fn [s#]
         (= (count s#)
            (count (into #{} s#))))
     (gen/tuple ~@(repeat n a))))

;; Test :term
;;--------------------------------------------------------------------
(defspec terminal-matches-only
  (prop/for-all [[m n] (distinct-n-tuple gen/char 2)]
    (let [g {:entry {:op :term :val m}}
          r (parse g :entry [n])]
      (failure? r))))

(defspec terminal-transform-ok
  (prop/for-all [m gen/char]
    (let [t (fn [x] (/ (max (long x) 1) 2))
          g {:entry {:op :term :val m :transform t}}
          r (parse g :entry [m])]
      (and (success? r)
           (= (:dat r)
              (t m))))))

;; Test :alt
;;--------------------------------------------------------------------
(defspec alt-matches-only
  (prop/for-all [[a b c] (distinct-n-tuple gen/char 3)],
    (let [g {:a     {:op  :term,
                     :val a}
             :b     {:op  :term,
                     :val b}
             :entry {:op   :alt,
                     :body [:a, :b]}}
          p (partial parse g :entry)]
      (and (success? (p [a]))
           (success? (p [b]))
           (failure? (p [c]))))))

(defspec alt-transform-ok
  (prop/for-all [a gen/char]
    (let [t (fn [x] (/ (max (long x) 1) 2))
          g {:a     {:op  :term,
                     :val a}
             :entry {:op        :alt,
                     :body      [:a],
                     :transform t}}
          r (parse g :entry [a])]
      (and (success? r)
           (= (:dat r)
              (t a))))))
