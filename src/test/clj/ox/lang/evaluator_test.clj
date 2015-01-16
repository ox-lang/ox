(ns ox.lang.evaluator-test
  (:refer-clojure :exclude [eval apply macroexpand-1 macroexpand compile])
  (:require [ox.lang.evaluator :refer :all]
            [ox.lang.environment :as env]
            [clojure.test :refer :all]))

(deftest eval-if-test
  (let [env (env/make-environment 'user)]
    (is (= [env 2]
           (interpreting-eval
            env '(if* false 1 2))))

    (is (= [env 1]
           (interpreting-eval
            env '(if* '() 1 2))))

    (is (= [env 1]
           (interpreting-eval
            env '(if* true 1 2))))))
