(ns ox.lang.test
  (:require [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

(defmacro distinct-n-tuple [a n]
  `(gen/such-that
       (fn [s#]
         (= (count s#)
            (count (into #{} s#))))
     (gen/tuple ~@(repeat n a))))
