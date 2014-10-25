(ns oxlang.reader
  (:require [oxlang.parser :as p]
            [oxlang.parser-compiler :refer [compile-grammar]]))

(def symbol
  (-> {:prefix-char [:pred (fn [c] (not (#{\: \; \space \newline \( \) \" \' \/
                                        \0 \1 \2 \3 \4 \5 \6 \7 \8 \9}
                                      c)))]
       :body-char   [:pred (fn [c] (not (#{\space \newline \( \) \/} c)))]
       :body        [:conc :prefix-char
                     [:rep* :body-char]]
       :symbol      [:alt [:term \/]
                     [:conc
                      [:opt [:conc :body [:term \/]]]
                      :body]]}
      (compile-grammar)))

(def keyword
  (->> {:keyword [:conc [:term \:] :symbol]}
       (merge symbol)
       (compile-grammar)))

(def number
  (->> {:digit  [:pred #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9}]
        :ratio  [:conc [:rep+ :digit] [:term \/] [:rep+ :digit]]
        :float  [:conc [:rep+ :digit] [:term \.] [:rep+ :digit]]
        :long   [:rep+ :digit]
        :number [:alt :ratio :float :long]}
       (compile-grammar)))
