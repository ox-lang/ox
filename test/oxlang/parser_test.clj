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

;; Test :conc
;;--------------------------------------------------------------------
(defspec conc-matches
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)]
    (let [g {:a     {:op  :term,
                     :val a}
             :b     {:op  :term,
                     :val b}
             :entry {:op   :conc,
                     :body [:a, :b]}}
          p (partial parse g :entry)]
      (success? (p [a b])))))

(defspec conc-transform-ok
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)]
    (let [t  (fn [x] (/ (max (long x) 1) 2))
          t' (partial map t)
          g  {:a     {:op  :term,
                      :val a}
              :b     {:op  :term,
                      :val b}
              :entry {:op   :conc,
                      :body [:a, :b]
                      :transform t'}}
          r  (parse g :entry [a b])]
      (and (success? r)
           (= (:dat r)
              (t' [a b]))))))

;; Test :succeed
;;--------------------------------------------------------------------
(defspec succeed-matches
  (prop/for-all [a gen/char]
    (let [g {:entry {:op :succeed}}
          r (parse g :entry [a])]
      (and (success? r)
           (= [a] (:buff r))))))

;; Test :opt
;;--------------------------------------------------------------------
(defspec opt-matches
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)]
    (let [g {:a     {:op  :term,
                     :val a},
             :b     {:op  :term,
                     :val b},
             :o     {:op   :opt,
                     :body :a},
             :entry {:op   :conc,
                     :body [:o :b]}}
          p (partial parse g :entry)]
      (and (success? (p [a b]))
           (success? (p [b]))
           (failure? (p [a]))))))

(defspec opt-transform-ok
  (prop/for-all [a gen/char]
    (let [t (fn [x] (/ (max (long x) 1) 2))
          g {:a     {:op  :term,
                     :val a},
             :entry {:op        :opt,
                     :body      :a,
                     :transform t}}
          r (parse g :entry [a])]
      (= (:dat r) (t a)))))

;; Test :rep*
;;--------------------------------------------------------------------
(defspec rep*-matches-self
  (prop/for-all [a  gen/char]
    (prop/for-all [as (gen/vector (gen/return a))]
      (let [g {:a     {:op  :term,
                       :val a},
               :entry {:op   :rep*,
                       :body :a}}
            p (partial parse g :entry)]
        (success? (p as))))))

(defspec rep*-matches-zero
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)]
    (prop/for-all [bs (gen/vector (gen/return b))]
      (let [g {:a     {:op  :term,
                       :val a},
               :entry {:op   :rep*,
                       :body :a}}
            p (partial parse g :entry)]
        (success? (p bs))))))

(defspec rep*-transform-ok
  (prop/for-all [a  gen/char]
    (prop/for-all [as (gen/vector (gen/return a))]
      (let [t (partial map (fn [x] (/ (max (long x) 1) 2)))
            g {:a     {:op  :term,
                       :val a},
               :entry {:op        :rep*,
                       :body      :a,
                       :transform t}}
            r (parse g :entry as)]
        (= (:dat r) (t as))))))

;; Test :rep+
;;--------------------------------------------------------------------
(defspec rep+-matches-self
  (prop/for-all [a  gen/char]
    (prop/for-all [as (gen/not-empty (gen/vector (gen/return a)))]
      (let [g {:a     {:op  :term,
                       :val a},
               :entry {:op   :rep+,
                       :body :a}}
            p (partial parse g :entry)]
        (success? (p as))))))

(defspec rep+-rejects-zero
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)]
    (prop/for-all [bs (gen/vector (gen/return b))]
      (let [g {:a     {:op  :term,
                       :val a},
               :entry {:op   :rep+,
                       :body :a}}
            p (partial parse g :entry)]
        (failure? (p bs))))))

(defspec rep+-transform-ok
  (prop/for-all [a  gen/char]
    (prop/for-all [as (gen/not-empty (gen/vector (gen/return a)))]
      (let [t (partial map (fn [x] (/ (max (long x) 1) 2)))
            g {:a     {:op  :term,
                       :val a},
               :entry {:op        :rep+,
                       :body      :a,
                       :transform t}}
            r (parse g :entry as)]
        (= (:dat r) (t as))))))
