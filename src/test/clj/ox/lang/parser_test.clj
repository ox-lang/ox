(ns ox.lang.parser-test
  (:require
   [ox.lang.test :refer :all]
   [clojure.test.check :as tc]
   [clojure.test.check.clojure-test :refer [defspec]]
   [clojure.test.check.generators :as gen]
   [clojure.test.check.properties :as prop]
   [clojure.test :refer :all]
   [ox.lang.parser :refer :all]))

(defspec parses-decimal-ints
  (prop/for-all [x gen/int]
    (is (= x (parse-string (str x))))))

(defspec parses-hex-ints
  (prop/for-all [x gen/s-pos-int]
    (is (= x (parse-string (format "0x%X" x))))))

(def gen-float (gen/fmap float gen/ratio))

(defspec parses-float
  (prop/for-all [x gen-float]
    (is (= x
           (parse-string (str x))))

    (is (= (* x 1000)
           (parse-string (str x "e3"))))))

(defspec parses-symbol
  (prop/for-all [x (gen/one-of [gen/symbol gen/symbol-ns])]
    (is (= x
           (parse-string (pr-str x))))))

(deftest parses-nan
  (is (= Double/NaN (parse-string "NaN"))))

(deftest parses-inf
  (is (= Double/POSITIVE_INFINITY (parse-string "Infinity")))
  (is (= Double/NEGATIVE_INFINITY (parse-string "-Infinity"))))

(defspec parses-vector
  (prop/for-all [l (gen/recursive-gen
                    gen/vector
                    gen/int)]
    (is (= l
           (parse-string (pr-str l))))))

(defspec parses-list
  (prop/for-all [l (gen/recursive-gen
                    gen/list
                    gen/symbol)]
    (is (= l
           (parse-string (pr-str l))))))
