(ns ox.lang.core
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
