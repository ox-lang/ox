(ns oxlang.reader-test
  (:require [oxlang.reader :refer :all]
            [instaparse.core :as insta]
            [clojure.test :refer :all]))

(deftest whitespace-tests
  (let [w whitespace-parser]
    (testing "Whitespace consumer"
      (are [expected got] (= expected got)
           (w " ")  [:trash [:whitespace " "]]
           (w "\t") [:trash [:whitespace "\t"]]
           (w "\n") [:trash [:whitespace "\n"]]
           (w "\r") [:trash [:whitespace "\r"]]))

    (testing "Comment consumer"
      (are [expected got] (= expected got)
           (w ";\n")      [:trash [:comment ";\n"]]
           (w ";; a b\n") [:trash [:comment ";; a b\n"]]))))

(deftest symbol-tests
  (testing "Symbol consumer alone"
    (let [p (insta/parser grammar :start :symbol)]
      (are [expected got] (= expected got)
           (insta/parse p "a")     [:symbol "a"]
           (insta/parse p "a.b")   [:symbol "a.b"]
           (insta/parse p "a.b/c") [:symbol "a.b/c"]
           (insta/parse p "a.b//") [:symbol "a.b//"]
           (insta/parse p "/")     [:symbol "/"])))

  (testing "Symbol consumer in whole grammar"
    (let [p           parser
          with-prefix (fn [x] [:file [:form [:atom x]]])]
      (are [expected got] (= expected (with-prefix got))
           (p "a")     [:symbol "a"]
           (p "a.b")   [:symbol "a.b"]
           (p "a.b/c") [:symbol "a.b/c"]
           (p "a.b//") [:symbol "a.b//"]
           (p "/")     [:symbol "/"]))))

(deftest vector-test
  (testing "Vector consumer alone"
    )
  (testing "Vector consumer in whole grammar"
    ))

(deftest map-test
  (testing "Map consumer alone"
    )
  (testing "Map consumer in whole grammar"
    ))
