(ns ox.lang.reader-test
  (:require [clojure.test :refer :all]

            [clojure.test.check :as tc]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :refer :all]
            
            [ox.lang.reader :as r]
            [ox.lang.reference-reader :as rr]))

(deftest EOF-tests
  (is (= (r/read-string "") nil))
  (is (= (r/read-string (apply str (take 200 (cycle [\newline \tab \space])))) nil))
  (is (= (r/read-string ";foo\n") nil)))

(defspec sym-read-equality
  (prop/for-all [sym gen/symbol]
    (let [s (pr-str sym)]
      (= (r/read-string s)
         (rr/read s)
         (read-string s)))))

(defspec nsym-read-equality
  (prop/for-all [sym gen/symbol-ns]
    (let [s (pr-str sym)]
      (= (r/read-string s)
         (rr/read s)
         (read-string s)))))

(defspec l-sym-read-equality
  (prop/for-all [l (gen/list gen/symbol-ns)]
    (let [s (pr-str l)]
      (= (r/read-string s)
         (rr/read s)
         (read-string s)))))
