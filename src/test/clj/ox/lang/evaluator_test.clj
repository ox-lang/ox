(ns ox.lang.evaluator-test
  (:refer-clojure :exclude [eval apply macroexpand-1 macroexpand compile])
  (:require [ox.lang.evaluator :refer :all]
            [ox.lang.environment :as env]
            [clojure.test :refer :all]))

(deftest fix-test
  (is (= 6 (fix #(min 6 (inc %1)) 0))))
