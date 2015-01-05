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
      (= (list 'big-integer (str x) 10)
         (parse-string (str x "n"))))))

(defspec parses-hex-ints
  (prop/for-all [x gen/s-pos-int]
    (= x (parse-string (format "0x%X" x)))))

(defspec parses-radix-ints
  (prop/for-all [x gen/s-pos-int
                 r (gen/elements (range 2 (inc Character/MAX_RADIX)))]
    (let [x (biginteger x)
          x (.toString x r)]
      (= (list 'big-integer (str x)
               (list 'integer (str r)))
         (parse-string (str r "r" x))))))

(def gen-double
  (gen/fmap double gen/ratio))

(defspec parses-raw-double
  (prop/for-all [x gen-double]
    (let [s (pr-str x)]
      (= (list 'read-eval (list 'float s))
         (parse-string s)))))

(defspec parses-exp-double
  (prop/for-all [x gen/int]
    (let [s (str x "e3")]
      (= (list 'read-eval (list 'float s))
         (parse-string s)))))

(deftest parses-nan
  (is (= (list 'read-eval (list 'float "NaN"))
         (parse-string "NaN")))

  (is (= (list 'read-eval (list 'float "-NaN"))
         (parse-string "-NaN"))))

(deftest parses-inf
  (is (= (list 'read-eval (list 'float "Infinity"))
         (parse-string "Infinity")))
  
  (is (= (list 'read-eval (list 'float "-Infinity"))
         (parse-string "-Infinity"))))

(deftest parses-bool
  (is (true? (parse-string "true")))

  (is (false? (parse-string "false"))))

(defspec parses-symbol
  (prop/for-all [x (gen/one-of [gen/symbol gen/symbol-ns])]
    (= x (parse-string (pr-str x)))))

(defspec parses-vector
  (prop/for-all [l (gen/vector
                    gen/int)]
    (= (list 'read-eval
             (list 'vector l))
       (parse-string (pr-str l)))))

(defspec parses-list
  (prop/for-all [l (gen/recursive-gen
                    gen/list
                    gen/symbol)]
    (= l (parse-string (pr-str l)))))

(defspec parses-maps
  (prop/for-all [m (gen/map gen/int gen/int)]
    (= `(~'read-eval
         ~(list 'hash-map
                (or (map seq (seq m)) '())))
       (parse-string (pr-str m)))))

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
    (= (list 'read-eval
             (if (namespace k)
               (list 'qualified-keyword (namespace k) (name k))
               (list 'keyword (name k))))
       (parse-string (pr-str k)))))

(defspec parses-macro-keyword
  (prop/for-all [k (gen/one-of
                    [gen/keyword
                     gen/keyword-ns])]
    (= (if (namespace k)
         `(~'read-eval (~'qualified-keyword
                        (~'name (~'resolve-ns-alias (~'this-ns) ~(namespace k)))
                        ~(name k)))
         `(~'read-eval (~'qualified-keyword
                        (~'name (~'this-ns))
                        ~(name k))))
       (parse-string (str ":" (pr-str k))))))

(deftest parses-quote-macros
  (is (parse-string "(`(~'foo ~@abar))")))

(deftest parse-tags
  (is (parse-string "#^{:foo :bar} foo"))
  (is (parse-string "^foo bar")))

(defspec parses-string
  (prop/for-all [s gen/string]
    (let [s (pr-str s)]
      (= `(~'read-eval (~'unquote-string ~s))
         (parse-string s)))))

(defspec parses-regex
  (prop/for-all [s gen/string]
    (let [s (pr-str s)]
      (= `(~'read-eval (~'re-compile (~'read-eval (~'unquote-string ~s))))
         (parse-string (str "#" s))))))
