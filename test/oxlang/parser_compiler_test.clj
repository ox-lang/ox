(ns oxlang.parser-compiler-test
  (:require [oxlang.parser-compiler :refer :all]
            [oxlang.parser :refer [parse success? failure?]]
            [oxlang.test :refer :all]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

(defspec term-compiles-ok
  (prop/for-all [a gen/char]
    (let [g (compile-grammar
             {:g [:term a]})
          p (partial parse g :g)]
      (success? (p [a])))))

(defspec alt-compiles-ok
  (prop/for-all [a gen/char
                 b gen/char]
    (let [g (compile-grammar
             {:g [:alt [:term a] [:term b]]})
          p (partial parse g :g)]
      (and (success? (p [a]))
           (success? (p [b]))))))

(defspec conc-compiles-ok
  (prop/for-all [[a b] (distinct-n-tuple gen/char 2)]
    (let [g (compile-grammar
             {:g [:conc [:term a] [:term b]]})
          p (partial parse g :g)]
      (and (success? (p [a b]))
           (failure? (p [a]))
           (failure? (p [a a]))
           (failure? (p [b]))
           (failure? (p [b b]))
           (failure? (p [b a]))))))

(defspec opt-compiles-ok
  (prop/for-all [a gen/char
                 b gen/char]
    (let [g (compile-grammar
             {:g [:opt [:term a]]})
          p (partial parse g :g)]
      (and (success? (p [a b]))
           (success? (p [b]))))))

(defspec rep*-compiles-ok
  (prop/for-all [a gen/char]
    (prop/for-all [as (gen/vector (gen/return a))]
      (let [g (compile-grammar
               {:g [:opt [:term a]]})
            p (partial parse g :g)]
        (success? (p as))))))

(defspec rep+-compiles-ok
  (prop/for-all [a gen/char]
    (prop/for-all [as (gen/not-empty (gen/vector (gen/return a)))]
      (let [g (compile-grammar
               {:g [:opt [:term a]]})
            p (partial parse g :g)]
        (success? (p as))))))
