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

;; FIXME: :DecimalIntegerLiteral should yeield a num, not just chars
(def int-number
  (->> {:NonZeroDigit          [:pred #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9}]
        :Digit                 [:alt :NonZeroDigit [:term \0]]

        :DecimalIntegerLiteral [:conc :DecimalNumeral [:opt :IntegerTypeSuffix]]
        :IntegerTypeSuffix     [:alt [:term \l] [:term \L]]

        ;; FIXME: Rewrite number patterns non-recursively for efficiency
        :DecimalNumeral        [:alt
                                [:term \0]
                                [:conc :NonZeroDigit [:opt :Digits]]
                                [:conc :NonZeroDigit [:rep* [:term \_]] :Digits]]

        :Digits                [:alt :Digit
                                [:conc :Digit [:opt :DigitsAndUnderscores] :Digit]]


        :DigitsAndUnderscores  [:alt :DigitOrUnderscore
                                [:conc :DigitsAndUnderscores :DigitOrUnderscore]]

        :DigitOrUnderscore     [:alt :Digit [:term \_]]}
       (compile-grammar)))

;; FIXME: :HexIntegerLiteral should yield a num, not just chars
(def hex-number
  (->> {:HexIntegerLiteral       [:conc :HexNumeral [:opt :IntegerTypeSuffix]]

        :HexNumeral              [:conc [:term \0]
                                  [:alt [:term \x] [:term \X]]
                                  :HexDigits]

        :HexDigits               [:alt
                                  :HexDigit
                                  [:conc :HexDigit [:opt :HexDigitsAndUnderscores] :HexDigit]]

        :HexDigit                [:pred #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \a \b \c \d \e \f \A \B \C \D \E \F}]

        :HexDigitsAndUnderscores [:alt
                                  :HexDigitOrUnderscore
                                  [:conc :HexDigitsAndUnderscores :HexDigitOrUnderscore]]

        :HexDigitOrUnderscore    [:alt :HexDigit [:term \_]]}
       (merge int-number)
       (compile-grammar)))
