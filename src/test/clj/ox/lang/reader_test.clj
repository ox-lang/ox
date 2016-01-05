(ns ox.lang.reader-test
  (:require [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer :all]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test :refer :all]
            [ox.lang.core :as ox]
            [ox.lang.reader :as r]
            [ox.lang.reference-reader :as rr]))

(deftest EOF-tests
  (is (= (r/read-string "") nil))
  (is (= (r/read-string (apply str (take 200 (cycle [\newline \tab \space])))) nil))
  (is (= (r/read-string ";foo\n") nil)))

(defn all-read-ok? [s]
  (let  [mine     (r/read-string s)
         check    (rr/read s)
         official (read-string s)]
    #_(printf "%s %s\n%s %s\n%s %s\n%s"
              mine (type mine)
              check (type check)
              official (type official)
              (ox/eq-table [mine check official]))
    (ox/= mine check official)))

(defspec sym-read-equality
  (prop/for-all [sym gen/symbol]
    (let [s (pr-str sym)]
      (all-read-ok? s))))

(defspec nsym-read-equality
  (prop/for-all [sym gen/symbol-ns]
    (let [s (pr-str sym)]
      (all-read-ok? s))))

(defspec l-sym-read-equality
  (prop/for-all [l (gen/list gen/symbol-ns)]
    (let [s (pr-str l)]
      (all-read-ok? s))))
