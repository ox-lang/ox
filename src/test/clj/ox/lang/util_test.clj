(ns ox.lang.util-test
  (:require [ox.lang.util :refer :all]
            [clojure.test :refer :all]))

(deftest fix-test
  (is (= 6 (fix #(min 6 (inc %1)) 0))))
