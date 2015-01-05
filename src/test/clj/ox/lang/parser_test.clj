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

(defspec parses-decimal-bigints
  (prop/for-all [x gen/int]
    (let [x (*' x 9999999)]
      (parse-string (str x "n")))))

(defspec parses-hex-ints
  (prop/for-all [x gen/s-pos-int]
    (= x (parse-string (format "0x%X" x)))))

(defspec parses-radix-ints
  (prop/for-all [x gen/s-pos-int
                 r (gen/elements (range 2 (inc Character/MAX_RADIX)))]
    (let [x (biginteger x)]
      (parse-string (str r "r" (.toString x r))))))

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

(deftest parses-bool
  (is (true? (parse-string "true")))

  (is (false? (parse-string "false"))))

(deftest parses-inf
  (is (= Double/POSITIVE_INFINITY
         (parse-string "Infinity")))
  
  (is (= Double/NEGATIVE_INFINITY
         (parse-string "-Infinity"))))

(defspec parses-symbol
  (prop/for-all [x (gen/one-of [gen/symbol gen/symbol-ns])]
    (= x (parse-string (pr-str x)))))

(defspec parses-vector
  (prop/for-all [l (gen/vector
                    gen/int)]
    (= (list 'vector l) (parse-string (pr-str l)))))

(defspec parses-list
  (prop/for-all [l (gen/recursive-gen
                    gen/list
                    gen/symbol)]
    (= l (parse-string (pr-str l)))))

(defspec parses-maps
  (prop/for-all [m (gen/map gen/keyword gen/int)]
    (parse-string (pr-str m))))

(defspec parses-sets
  (prop/for-all [m (gen/vector gen/int)]
    (let [s (into #{} m)]
      (parse-string (pr-str s)))))

(defspec parses-char
  (prop/for-all [c gen/char]
    (= c (parse-string (pr-str c)))))

(deftest parses-utf8-character
  (prop/for-all [c gen/char]
    (= c (parse-string (format "\\u%04X" (.charValue c))))))

(deftest parses-dispatch
  (is (= (parse-string "#foo bar")
         '(read-eval ((resolve-reader-macro
                       (this-ns) (quote foo))
                      bar)))))

(deftest parse-deref
  (is (= (parse-string "@foo")
         '(deref foo))))

(deftest parses-host-expr
  (is (parse-string "#+cljs foo")))

(defspec parses-keyword
  (prop/for-all [k (gen/one-of
                    [gen/keyword
                     gen/keyword-ns])]
    (parse-string (pr-str k))))

(defspec parses-macro-keyword
  (prop/for-all [k (gen/one-of
                    [gen/keyword
                     gen/keyword-ns])]
    (parse-string (str ":" (pr-str k)))))

(deftest parses-quote-macros
  (is (parse-string "(`(~'foo ~@abar))")))

(deftest parse-tags
  (is (parse-string "#^{:foo :bar} foo"))
  (is (parse-string "^foo bar")))
