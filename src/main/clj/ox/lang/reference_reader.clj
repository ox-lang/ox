(ns ox.lang.reference-reader
  (:require [ox.lang.reference-parser :refer [parser]]
            [instaparse.core :as insta]))

(defn punt [& content]
  content)

(defn punt1 [& content]
  (first content))

(defn map-reader [& content]
  (println content)
  (->> content
       (map (comp vec
                  :content))
       (into {})))

(defn set-reader [& content]
  (into #{} content))

(defn map-meta-reader [m e]
  [:with-meta e m])

(defn tag-meta-reader [m e]
  [:with-meta e {:tag m}])

(defn keyword-reader [sym]
  (keyword (namespace sym) (name sym)))

(defn quote-reader [expr]
  [:quote expr])

(def reader-dispatch
  {:forms    punt
   :form     punt1
   :atom     punt1

   :map      map-reader
   :list     list
   :vector   vector

   :meta     punt1
   :map-meta map-meta-reader
   :tag-meta tag-meta-reader

   :quote    quote-reader
   
   :keyword  keyword-reader
   :symbol   punt1
   :ns       punt1
   :name     punt1
   :sym      symbol
   :qsym     symbol})

(defn reader [source]
  (->> (parser source)
       (insta/transform reader-dispatch)))

(defn read [source]
  (first (reader source)))
