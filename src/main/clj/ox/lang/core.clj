(ns ox.lang.core
  (:refer-clojure :only [defn defmethod let cond instance? or format
                         print-method nil? reduce if-not reduced
                         boolean empty? assert ->> for])
  (:require clojure.string)
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
  ([x]
   true)
  
  ([x y]
   (if-not (nil? x)
     (.equals x y)
     (if-not (nil? y)
       (.equals y x)
       true)))

  ([x y & [head & more' :as more]]
   (let [x (unbox x)
         y (unbox y)]
     (if (= x y)
       (do (assert (= y x) "Transitive equality violation!")
           (if-not (empty? more)
             (recur y head more')
             true))
       false))))

(defn eq-table
  "FIXME: this will _not_ stay in core"
  [col]
  (->> (for [i col]
         (format "%5s %5s"
                 (= i j)
                 (= j i)))
       (clojure.string/join "|")
       (for [j col])
       (clojure.string/join "\n")))
