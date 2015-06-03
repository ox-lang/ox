(ns ox.lang.evaluator-test
  (:refer-clojure :exclude [eval apply macroexpand-1 macroexpand compile])
  (:require [ox.lang.evaluator :refer :all]
            [ox.lang.environment :as env]
            [clojure.test :refer :all]))
