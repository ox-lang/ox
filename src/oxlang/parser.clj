(ns oxlang.parser
  "My Little Parser
  --------------------------------------------------------------------
  The following is a datastructure description and interpreter for an
  obvious recursive decent state monad parser combinator supporting
  only a few classical EBNF operations.

  The interpretation of a production returns either
  Succeed (dat . rest)
  Fail (dat)

  Parser progress is implemented by continuing forwards on the rest
  of a Succeed, being the sequence of remaining tokens. Parser
  failure propagates back up the stack to a production which
  tolerates failure or to the top level. As the token sequence is
  immutable, the value of a Fail need not be the token sequence at
  which the failure occured, it can be debugginf information or any
  other value.

  At present parser failures produce no debugging information.

  Grammar
  | {[Keyword        | (Σ Terminal Predicate Transform
  |                       Alternation Concatination
  |                       Succeed Fail Option Rep* Rep+)]}
  |
  |  Terminal
  |   | {[:op        | :term]
  |   |  [:val       | Character]
  |   |  [:transform | (Option Fn | Identity)]}
  |
  |  Predicate
  |   | {[:op        | :pred]
  |   |  [:body      | (Fn $ [Character] → Bool)]
  |   |  [:transform | (Option Fn | Identity)]}
  |
  |  Transform
  |   | {[:op        | :transform]
  |   |  [:body      | Keyword]
  |   |  [:fn        | (Option Fn | Identity)]}
  |
  |  Alternation
  |   | {[:op        | :alt]
  |   |  [:body      | [& Keyword]]
  |   |  [:transform | (Option Fn | Identity)]}
  |
  |  Concatination
  |   | {[:op        | :conc]
  |   |  [:body      | [& Keyword]]
  |   |  [:transform | (Option Fn | Identity)]}
  |
  |  Succeed
  |   | {[:op        | :succeed]}
  |
  |  Fail
  |   | {[:op        | :fail]}
  |
  |  Option
  |   | {[:op        | :opt]
  |   |  [:body      | Keyword]
  |   |  [:transform | (Option Fn | Identity)]}
  |
  |  Rep*
  |   | {[:op        | :rep*]
  |   |  [:body      | Keyword]
  |   |  [:transform | (Option Fn | Identity)]}
  |
  |  Rep+
  |   | {[:op        | :rep+]
  |   |  [:body      | Keyword]
  |   |  [:transform | (Option Fn | Identity)]}")

(defn succeed
  [datastructure buff]
  {:result :success
   :dat    datastructure
   :buff   buff})

(defn success? [res]
  (= :success (:result res)))

(defn failure
  [buff]
  {:result :failure
   :buff   buff})

(defn failure? [res]
  (= :failure (:result res)))

;; Parser interpreter
;;--------------------------------------------------------------------
(defn -parse-dispatch
  [grammar {op :op} _tokens]
  op)

(defmulti -parse -parse-dispatch)

(defmethod -parse :term
  [grammar {val :val} [t & tokens']]
  (if (= val t)
    (succeed val tokens')
    (failure nil)))

(defmethod -parse :pred
  [grammar {f :body} [t & tokens']]
  (if (f t)
    (succeed t tokens')
    (failure nil)))

(defmethod -parse :alt
  [grammar {terms :body} tokens]
  (loop [[t & terms] terms]
    (if t
      (let [res (-parse grammar (get grammar t) tokens)]
        (if (success? res)
          (let [{:keys [dat buff]} res]
            (succeed dat buff))
          (recur terms)))
      (failure nil))))

(defmethod -parse :conc
  [grammar {terms :body} tokens]
  (loop [[t & terms] terms
         tokens'     tokens
         results     nil]
    (if t
      (let [{:keys [buff dat] :as res} (-parse grammar (get grammar t) tokens')
            results'                   (cons dat results)]
        (if (success? res)
          (recur terms buff results')
          (failure nil)))
      (succeed (reverse results) tokens'))))

(defmethod -parse :succeed
  [grammar _op tokens]
  (succeed nil tokens))

(defmethod -parse :opt
  [grammar {t :body} tokens]
  (let [{:keys [buff dat] :as res}
        (-parse grammar
                (get grammar t)
                tokens)]
    (if (success? res)
      (succeed dat buff)
      (succeed nil tokens))))

(defmethod -parse :rep*
  [grammar {t :body} tokens]
  (loop [tokens  tokens
         results nil]
    (let [{:keys [dat buff] :as res}
          (-parse grammar (get grammar t) tokens)]
      (if (and (success? res) (not-empty tokens))
        (recur buff (cons dat results))
        (succeed (reverse results) tokens)))))

(defmethod -parse :rep+
  [grammar {t :body} tokens]
  (loop [tokens tokens
         results nil]
    (let [{:keys [buff dat] :as res} (-parse grammar (get grammar t) tokens)
          results'                   (cons dat results)]
      (if (and (success? res)
               (not-empty tokens))
        (recur buff results')
        (if results
          (succeed (reverse results') buff)
          (failure nil))))))

(defmethod -parse :transform
  [grammar {t :body f :fn} tokens]
  (let [res (-parse grammar (get grammar t) tokens)]
    (if (success? res)
      (succeed (f (:dat res)) (:buff res))
      (failure nil))))

;; Parser invocation interface
;;--------------------------------------------------------------------
(defn parse
  [grammar entry token-seq]
  (-parse grammar (get grammar entry) token-seq))
