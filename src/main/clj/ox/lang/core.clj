(ns ox.lang.core
  (:refer-clojure :only [defn defmethod let cond instance? or])
  (:import [ox.lang
            ,,AObj
            ,,Box]

           [ox.lang.parser
            ,,Position]

           [java.io Writer]))

(defmethod print-method Box [b ^Writer w]
  (.write ^Writer w (.toString b)))

(defmethod print-method Position [b ^Writer w]
  (.write ^Writer w (.toString b)))

(defn with-meta [o m]
  (. Box of o ^Map (or m {})))

(defn meta [o]
  (cond
    (instance? ox.lang.IMeta o)
    ,,(.getMeta ^ox.lang.IMeta o)
    
    (instance? clojure.lang.IMeta o)
    ,,(clojure.core/meta o)
    
    :else {}))
