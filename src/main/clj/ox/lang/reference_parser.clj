(ns ox.lang.reference-parser
  (:require [clojure.java.io :as io]
            [instaparse.core :as insta]))

(def parser
  (let [mg        (io/resource "ox.bnf")
        wg        (io/resource "ox-ws.bnf")
        ws-parser (insta/parser wg)]
    (insta/parser mg
                  :output-format   :enlive
                  :auto-whitespace ws-parser)))
