(ns ox.lang.reference-reader-test
  (:refer-clojure :exclude [read])
  (:require [ox.lang.reference-reader :refer [read]]
            [clojure.test :refer :all]))

(deftest symbol-tests
  (is (= (read "foo") 'foo))
  (is (= (read "foo/bar") 'foo/bar)))

(deftest keyword-tests
  (is (= (read ":foo") :foo))
  (is (= (read ":foo/bar") :foo/bar)))

(deftest vector-tests
  (is (= (read "[foo bar]") '[foo bar])))
