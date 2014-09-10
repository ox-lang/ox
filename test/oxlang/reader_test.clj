(ns oxlang.reader-test
  (:require [oxlang.reader :refer :all]
            [instaparse.core :as insta]
            [clojure.test :refer :all]))

(deftest whitespace-tests
  (let [w whitespace-parser]
    (testing "Whitespace consumer"
      (doseq [c [" " "\t" "\n" "\r"]]
        (= (w c) [:trash [:whitespace c]])))

    (testing "Comment consumer"
      (are [expected got] (= expected got)
           (w ";\n")      [:trash [:comment ";\n"]]
           (w ";; a b\n") [:trash [:comment ";; a b\n"]]))))

(deftest symbol-tests
  (testing "Symbol consumer alone"
    (let [p (insta/parser grammar :start :symbol)]
      (doseq [i ["a" "a.b" "a.b/c" "a.b//" "/"]]
        (is (= (insta/parse p i)
               [:symbol i])))))

  (testing "Symbol consumer in whole grammar"
    (let [p           parser
          with-prefix (fn [x] [:file [:form [:atom [:symbol x]]]])]
      (doseq [i ["a" "a.b" "a.b/c" "a.b//" "/"]]
        (is (= (p i) (with-prefix i)))))))

(deftest character-tests
  (let [chars (map (partial str "\\")
                   (concat (map char (range 97 123))
                           ["newline" "space" "tab" "backspace" "formfeed" "return"]
                           ["u000" "u00" "u0"]))]
    (testing "Character consumer alone"
      (let [p (insta/parser grammar :start :character)]
        (doseq [c chars]
          (is (p c)))))))

(deftest keyword-tests)

(deftest vector-tests
  (testing "Vector consumer alone"
    )
  (testing "Vector consumer in whole grammar"
    ))

(deftest map-tests
  (testing "Map consumer alone"
    )
  (testing "Map consumer in whole grammar"
    ))
