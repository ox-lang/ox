(ns ox.lang.environment.types
  (:require [guten-tag.core :refer [deftag]]))

(deftag env
  [e])

(deftag ns
  [n])

(deftag alias
  [sym])

(deftag value
  [v])
