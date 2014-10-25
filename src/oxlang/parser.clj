(ns oxlang.parser)

;; My Little Parser
;;--------------------------------------------------------------------
;; The following is a datastructure description and interpreter for an
;; obvious recursive decent state monad parser combinator supporting
;; only a few classical EBNF operations.
;;
;; The interpretation of a production returns either
;;   Succeed (dat . rest)
;;   Fail (dat)
;;
;; Parser progress is implemented by continuing forwards on the rest
;; of a Succeed, being the sequence of remaining tokens. Parser
;; failure propagates back up the stack to a production which
;; tolerates failure or to the top level. As the token sequence is
;; immutable, the value of a Fail need not be the token sequence at
;; which the failure occured, it can be debugginf information or any
;; other value.
;;
;; At present parser failures produce no debugging information. This
;; is a flaw which will eventually be remedied to provide column/line
;; information.
;;
;; Grammar
;;  | {[Keyword        | (Σ Terminal Alternation Concatination
;;  |                       Succeed Fail Option Rep* Rep+)]}
;;  |
;;  |  Terminal
;;  |   | {[:op        | :term]
;;  |   |  [:val       | Character]
;;  |   |  [:transform | (Option Fn | Identity)]}
;;  | 
;;  |  Alternation
;;  |   | {[:op        | :alt]
;;  |   |  [:body      | [& Keyword]]
;;  |   |  [:transform | (Option Fn | Identity)]}
;;  | 
;;  |  Concatination
;;  |   | {[:op        | :conc]
;;  |   |  [:body      | [& Keyword]]
;;  |   |  [:transform | (Option Fn | Identity)]}
;;  |
;;  |  Succeed
;;  |   | {[:op        | :succ]}
;;  |
;;  |  Fail
;;  |   | {[:op        | :fail]}
;;  |
;;  |  Option
;;  |   | {[:op        | :opt]
;;  |   |  [:body      | Keyword]
;;  |   |  [:transform | (Option Fn | Identity)]}
;;  |
;;  |  Rep*
;;  |   | {[:op        | :rep*]
;;  |   |  [:body      | Keyword]
;;  |   |  [:transform | (Option Fn | Identity)]}
;;  |
;;  |  Rep+
;;  |   | {[:op        | :rep+]
;;  |   |  [:body      | Keyword]
;;  |   |  [:transform | (Option Fn | Identity)]}


(defn succeed
  [datastructure buff]
  {:result :success
   :dat    datastructure
   :buff   buff})


(defn fail
  [buff]
  {:result :failure
   :buff   buff})


(defn -parse-dispatch
  [grammar {op :op} _tokens]
  op)


(defmulti -parse -parse-dispatch)


(defmethod -parse :term
  [grammar
   {val :val
    tfn :transform
    :or {tfn identity}
    :as op}
   [t & tokens' :as tokens]]
  (if (= val t)
    (succeed (tfn val) tokens')
    (fail nil)))


(defmethod -parse :alt
  [grammar
   {terms :body
    tfn :transform
    :or {tfn identity}
    :as op}
   tokens]
  (loop [[t & terms] terms]
    (if t
      (let [{r :result
             :as res}
            (-parse grammar
                    (get grammar t)
                    tokens)]
        (if (= :success r)
          res
          (recur terms)))
      (fail nil))))


(defmethod -parse :conc
  [grammar
   {terms :body
    tfn :transform
    :or {tfn identity}
    :as op}
   tokens]
  (loop [[t & terms] terms
         tokens'     tokens
         results     nil]
    (if t
      (let [{r :result
             buff :buff
             :as res}
            (-parse grammar
                    (get grammar t)
                    tokens')]
        (if (= :success r)
            (recur terms
                   buff
                   (cons (:dat res)
                         results))
            (fail nil)))
      (succeed (tfn (reverse results))
               tokens'))))


(defmethod -parse :succeed
  [grammar _op tokens]
  (succeed nil tokens))


(defmethod -parse :opt
  [grammar
   {t :body
    tfn :transform
    :or {tfn identity}}
   tokens]
  (let [{r :result
         buff :buff
         :as res}
        (-parse grammar
                (get grammar t)
                tokens)]
    (if (= :success r)
      (succeed (tfn r) buff)
      (succeed nil tokens))))


(defmethod -parse :rep*
  [grammar
   {t :body
    tfn :transform
    :or {tfn identity}}
   tokens]
  (loop [tokens tokens
         results nil]
    (let [{r :result
           buff :buff
           :as res}
          (-parse grammar
                  (get grammar t)
                  tokens)]
      (if (= :success r)
        (recur buff
               (cons (:dat res) ;; note cons preppends
                     results))
        (succeed (tfn (reverse results)) tokens)))))


(defmethod -parse :rep+
  [grammar
   {t :body
    tfn :transform
    :or {tfn identity}}
   tokens]
  (loop [tokens tokens
         results nil]
    (let [{r :result
           buff :buff
           :as res}
          (-parse grammar (get grammar t) tokens)]
      (if (= :success r)
        (recur buff (cons (:dat res) results))
        (if (>= 1 (count results))
          (succeed (tfn (reverse results)) tokens)
          (fail nil))))))


(defn parse
  [grammar entry token-seq]
  (-parse grammar (get grammar entry) token-seq))
