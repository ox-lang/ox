(ns oxlang.reader-test
  (:refer-clojure :exclude [symbol keyword])
  (:require [oxlang.reader :refer :all]
            [oxlang.parser :refer [parse success? failure?]]
            [clojure.test :refer :all]
            [clojure.test.check :as tc]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

(deftest symbol-tests
  (are [s] (let [r (parse symbol :symbol s)]
             (and (success? r)
                  (= s (:dat r))))
       "a" "b" "牛"
       "ox.core/conj"
       "ox.core/->"
       "ox.core/->>"
       "ox.core/牛"

       "/" "//" "///"
       "ox.core//"
       "ox.core///"
       "ox.core////")

  (are [s] (let [r (parse symbol :symbol s)]
             (failure? r))
       "0" ":" ":a" "::a" "{" "}" "(" ")" " " "\n" "\t"))

(deftest keyword-tests
  (are [s v] (let [r (parse keyword :keyword s)]
               (and (success? r)
                    (= v (:dat r))))
       
       ":a"            [:unqualified "a"]
       "::b"           [:unqualified "b"]
       ":牛"           [:unqualified "牛"]
       "://"           [:unqualified "//"]
       ":ox.core/conj" [:qualified   "ox.core/conj"]
       ":ox.core/->"   [:qualified   "ox.core/->"]
       ":ox.core/->>"  [:qualified   "ox.core/->>"]
       ":ox.core/牛"   [:qualified   "ox.core/牛"])

  (are [s] (let [r (parse keyword :keyword s)]
             (failure? r))
       "0" "{" "}" "(" ")" " " "\n" "\t" "a" "a/b" "//"))

(defspec decimal-tests
  (prop/for-all [i gen/nat]
    (let [s (str i)
          r (parse int-number :DecimalIntegerLiteral s)]
      (success? r))))

(defspec hex-tests
  (prop/for-all [i gen/nat]
    (let [s (format "0x%X" i)
          r (parse hex-number :HexIntegerLiteral s)]
      (success? r))))
