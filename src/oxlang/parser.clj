(ns oxlang.parser
  (:require [clj-antlr.core :as antlr]))

(def parser (antlr/parser "grammar/Oxlang.g4"))
