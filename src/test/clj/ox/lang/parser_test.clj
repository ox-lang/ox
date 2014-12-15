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
    (= x (parse-string (format "0x%X" x)))))

(def gen-double
  (gen/fmap double gen/ratio))

(defspec parses-raw-double
  (prop/for-all [x gen-double]
    (= x (parse-string (pr-str x)))))

(defspec parses-exp-double
  (prop/for-all [x gen/int]
    (= (-> x (* 1000) double)
       (parse-string (str x "e3")))))

(deftest parses-nan
  (is (. (parse-string "NaN")
         isNaN))

  (is (. (parse-string "-NaN")
         isNaN)))

(deftest parses-inf
  (is (= Double/POSITIVE_INFINITY
         (parse-string "Infinity")))
  
  (is (= Double/NEGATIVE_INFINITY
         (parse-string "-Infinity"))))


(defspec parses-symbol
  (prop/for-all [x (gen/one-of [gen/symbol gen/symbol-ns])]
    (= x (parse-string (pr-str x)))))

#_(defspec parses-vector
    (prop/for-all [l (gen/recursive-gen
                      gen/vector
                      gen/int)]
      (= l (parse-string (pr-str l)))))

#_(defspec parses-list
    (prop/for-all [l (gen/recursive-gen
                      gen/list
                      gen/symbol)]
      (= l (parse-string (pr-str l)))))
