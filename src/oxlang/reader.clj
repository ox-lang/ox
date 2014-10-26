(ns oxlang.reader
  (:require [oxlang.parser :as p]
            [clojure.set :refer [union]]
            [oxlang.parser-compiler :refer [compile-grammar]]))

(def whitespace #{\space \newline \tab})
(def syntax #{\: \;  \( \) \{ \} \" \' \/})
(def numeric #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9})

(def symbol
  (-> {:prefix-char [:pred (complement (union whitespace syntax numeric))]
       :body-char   [:pred (complement (union whitespace syntax))]
       :slash       [:term \/]
       :body        [:alt
                     [:transform
                      [:rep+ :slash]
                      (partial apply str)]
                     [:transform
                      [:conc :prefix-char [:rep* :body-char]]
                      (fn [[x xs]] (apply str x xs))]]
       :prefix      [:transform [:conc :body :slash]
                     (partial apply str)]
       :symbol      [:transform [:alt
                                 [:rep+ :slash]
                                 [:conc [:opt :prefix] :body]]
                     (partial apply str)]}
      (compile-grammar)))

(def keyword
  (->> {:keyword [:alt
                  [:transform [:conc [:term \:] [:term \:] :body]
                   (fn [[_ _ b]] [:unqualified b])]

                  [:transform [:conc [:term \:] :prefix :symbol]
                   (fn [[_ p b]] [:qualified (str p b)])]
                  
                  [:transform [:conc [:term \:] :body]
                   (fn [[_ b]] [:unqualified b])]]}
       (merge symbol)
       (compile-grammar)))

(def number
  (->> {:digit  [:pred #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9}]
        :ratio  [:conc [:rep+ :digit] [:term \/] [:rep+ :digit]]
        :float  [:conc [:rep+ :digit] [:term \.] [:rep+ :digit]]
        :long   [:rep+ :digit]
        :number [:alt :ratio :float :long]}
       (compile-grammar)))
