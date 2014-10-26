(ns oxlang.parser-test
  (:require [oxlang.parser :refer :all]
            [oxlang.test :refer :all]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

;; Test :term
;;--------------------------------------------------------------------
(defspec terminal-matches-only
  (prop/for-all [[m n] (distinct-n-tuple gen/char 2)]
    (let [g {:entry {:op :term :val m}}
          r (parse g :entry [n])]
      (failure? r))))

(defspec terminal-buff-ok
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)
                 n gen/nat
                 m gen/nat]
    (let [n  (max n 1)
          m  (max m 1)
          as (repeat n a)
          bs (repeat m b)
          g  {:entry {:op :term :val a}}
          r  (parse g :entry (concat as bs))]
      (and (success? r)
           (= (count (:buff r))
              (dec (+ m n)))))))

;; Test :pred
;;--------------------------------------------------------------------
(defspec predicate-matches-only
  (prop/for-all [[m n] (distinct-n-tuple gen/char 2)]
    (let [g {:entry {:op :pred :body #(= m %1)}}
          r (parse g :entry [n])]
      (failure? r))))

(defspec predicate-matches-every
  (prop/for-all [m gen/char]
    (let [g {:entry {:op :pred :body #(= m %1)}}
          r (parse g :entry [m])]
      (success? r))))

(defspec predicate-buff-ok
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)
                 n gen/nat
                 m gen/nat]
    (let [n  (max n 1)
          m  (max m 1)
          as (repeat n a)
          bs (repeat m b)
          g {:entry {:op :pred :body #(= a %1)}}
          r  (parse g :entry (concat as bs))]
      (and (success? r)
           (= (count (:buff r))
              (dec (+ m n)))))))

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

(defspec alt-buff-ok
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)
                 n gen/nat
                 m gen/nat]
    (let [n   (max n 1)
          m   (max m 1)
          as  (repeat n a)
          bs  (repeat m b)
          g   {:a     {:op  :term,
                       :val a}
               :b     {:op  :term,
                       :val b}
               :entry {:op   :alt,
                       :body [:a, :b]}}
          r_a (parse g :entry (concat as bs))
          r_b (parse g :entry (concat bs as))]
      (and (success? r_a)
           (= (count (:buff r_a))
              (dec (+ m n)))
           (success? r_b)
           (= (count (:buff r_b))
              (dec (+ m n)))))))

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

(defspec conc-buff-ok
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)
                 c (gen/vector gen/char)]
    (let [g  {:a     {:op  :term,
                      :val a}
              :b     {:op  :term,
                      :val b}
              :entry {:op   :conc,
                      :body [:a, :b]}}
          r  (parse g :entry (concat [a b] c))]
      (and (success? r)
           (= (count (:buff r)) (count c))))))

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

(defspec opt-buff-ok
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)
                 cs    (gen/not-empty (gen/vector gen/char))]
    (let [g {:a     {:op  :term,
                     :val a},
             :b     {:op  :term,
                     :val b},
             :o     {:op   :opt,
                     :body :a},
             :entry {:op   :conc,
                     :body [:o :b]}}
          p (partial parse g :entry)]
      (and (let [r (p [a b])]
             (and (success? r)
                  (empty? (:buff r))))
           
           (let [r (p [b])]
             (and (success? r)
                  (empty? (:buff r))))

           (let [r (p (cons b cs))]
             (and (success? r)
                  (= cs (:buff r))))))))

;; Test :rep*
;;--------------------------------------------------------------------
(defspec rep*-matches-self
  (prop/for-all [a gen/char
                 n gen/int]
    (let [g {:a     {:op  :term,
                     :val a},
             :entry {:op   :rep*,
                     :body :a}}
          p (partial parse g :entry)
          s (repeat n a)]
      (success? (p s)))))

(defspec rep*-matches-zero
  (prop/for-all [a gen/char]
    (let [g {:a     {:op  :term,
                     :val a},
             :entry {:op   :rep*,
                     :body :a}}
          p (partial parse g :entry)]
      (success? (p [])))))

(defspec rep*-matches-none
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)]
    (prop/for-all [bs (gen/vector (gen/return b))]
      (let [g {:a     {:op  :term,
                       :val a},
               :entry {:op   :rep*,
                       :body :a}}
            p (partial parse g :entry)]
        (success? (p bs))))))

(defspec rep*-matches-trailing
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)]
    (prop/for-all [as (gen/vector (gen/return a))
                   bs (gen/vector (gen/return b))]
      (let [g {:a     {:op  :term,
                       :val a},
               :entry {:op   :rep*,
                       :body :a}}
            p (partial parse g :entry)]
        (success? (p (concat as bs)))))))

;; Test :rep+
;;--------------------------------------------------------------------
(defspec rep+-matches-self
  (prop/for-all [a gen/char
                 n gen/nat]
    (let [g {:a     {:op  :term,
                     :val a},
             :entry {:op   :rep+,
                     :body :a}}
          p (partial parse g :entry)
          as (repeat (inc n) a)]
      (success? (p as)))))

(defspec rep+-rejects-zero
  (prop/for-all [a gen/char]
    (let [g {:a     {:op  :term,
                     :val a},
             :entry {:op   :rep+,
                     :body :a}}
          p (partial parse g :entry)]
      (failure? (p [])))))

(defspec rep+-matches-trailing
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)
                 n gen/nat
                 m gen/nat]
    (let [as (repeat (inc n) a)
          bs (repeat m b)
          g {:a     {:op  :term,
                     :val a},
             :entry {:op   :rep+,
                     :body :a}}
          p (partial parse g :entry)]
      (and (success? (p as))
           (success? (p (concat as bs)))))))

;; Test :transform
;;--------------------------------------------------------------------
(defspec transform-fails-when-child-fails
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)]
    (let [g {:a     {:op  :term,
                     :val a},
             :entry {:op   :transform,
                     :body :a
                     :fn   str}}]
      (and (failure? (parse g :a [b]))
           (failure? (parse g :entry [b]))))))

(defspec transform-runs-when-child-succeeds
  (prop/for-all [a gen/char]
    (let [g {:a     {:op  :term,
                     :val a},
             :entry {:op   :transform,
                     :body :a
                     :fn   (constantly 3)}}
          r_a (parse g :a [a])
          r_e (parse g :entry [a])]
      (and (success? r_a)
           (success? r_e)
           (= 3 (:dat r_e))))))
