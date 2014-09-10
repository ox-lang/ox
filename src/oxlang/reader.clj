(ns oxlang.reader
  (:require [instaparse.core :as insta]
            [clojure.java.io :as io]))

(def whitespace-parser
  (-> "whitespace.ebnf" io/resource slurp insta/parser))

(def grammar
  (-> "oxlang.ebnf" io/resource slurp))

(def parser
  (insta/parser grammar
                :start :file
                :auto-whitespace whitespace-parser))

