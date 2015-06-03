(ns ox.lang.util)

(defn update-vals
  [m f & args]
  (->> (for [[k v] m]
         [k (apply f v args)])
       (into {})))
