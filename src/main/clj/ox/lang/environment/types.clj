(ns ox.lang.environment.types)

(defn env?
  "FIXME: quick and dirty predicate"
  [x]
  (and (vector? x)
       (#{:env/local :env/ns} (first x))))

(defn ns?
  "FIXME: quick and dirty predicate"
  [x]
  (and (env? x)
       (= :env/ns (first x))))

(defn alias?
  "FIXME: quick an dirty predicate"
  [x]
  (and (vector? x)
       (#{:binding/alias} (first x))))

(defn value?
  "FIXME: quick and dirty predicate"
  [x]
  (and (vector? x)
       (#{:binding/value} (first x))))
