(ns ox.lang.util)

(defn fix [f x]
  (loop [x x]
    (let [x' (f x)]
      (if-not (= x x')
        (recur x')
        x))))

(defn update-vals
  [m f & args]
  (->> (for [[k v] m]
         [k (apply f v args)])
       (into {})))
