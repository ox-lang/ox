(ns oxlang.reader-test
  (:refer-clojure :exclude [symbol keyword])
  (:require [oxlang.reader :refer :all]
            [oxlang.parser :refer [parse success? failure?]]
            [clojure.test :refer :all]))

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
