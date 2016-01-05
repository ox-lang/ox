(ns ox.lang.core
  (:refer-clojure :only [defn defmethod let cond instance? or print-method nil? reduce if-not reduced boolean empty?])
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

(defn unbox [o]
  (if (instance? ox.lang.IRef o)
    (.deref o) o))

(defn =
  ([x] true)
  ([x y]
   (if-not (nil? x)
     (.equals x y)
     (if-not (nil? y)
       (.equals y x)
       true)))
  ([x y & [head & more' :as more]]
   (if (= x y)
     (if-not (empty? more)
       (recur y head more')
       true)
     false)))
