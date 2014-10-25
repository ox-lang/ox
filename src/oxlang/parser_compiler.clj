(ns oxlang.parser-compiler)

;; Grammar compiler DSL
;;--------------------------------------------------------------------
(defmulti -compile-grammar-rule (fn [_ [_ [op & body]]] op))

(defmethod -compile-grammar-rule :term [acc [name [_ c]]]
  (->> {:op :term,
        :val c}
       (assoc acc name)))

(defmethod -compile-grammar-rule :pred [acc [name [_ p]]]
  (->> {:op :pred,
        :body p}
       (assoc acc name)))

(defn -compile-children [acc forms]
  (reduce (fn [[acc names] form]
            (if (keyword? form)
              [acc (conj names form)]
              (let [kn  (keyword (str "k_" (::c acc)))
                    acc (assoc acc ::c (inc (::c acc)))]
                [(-compile-grammar-rule acc [kn form])
                 (conj names kn)])))
          [acc []]
          forms))

(defmethod -compile-grammar-rule :alt [acc [name [_ & forms]]]
  (let [[acc names] (-compile-children acc forms)]
    (->> {:op   :alt,
          :body names}
         (assoc acc name))))

(defmethod -compile-grammar-rule :conc [acc [name [_ & forms]]]
  (let [[acc names] (-compile-children acc forms)]
    (->> {:op   :conc,
          :body names}
         (assoc acc name))))

(defmethod -compile-grammar-rule :opt [acc [name [_ form]]]
  (let [[acc [name']] (-compile-children acc [form])]
    (->> {:op   :opt,
          :body name'}
         (assoc acc name))))

(defmethod -compile-grammar-rule :rep* [acc [name [_ form]]]
  (let [[acc [name']] (-compile-children acc [form])]
    (->> {:op   :rep*,
          :body name'}
         (assoc acc name))))

(defmethod -compile-grammar-rule :rep+ [acc [name [_ form]]]
  (let [[acc [name']] (-compile-children acc [form])]
    (->> {:op   :rep+,
          :body name'}
         (assoc acc name))))

;; Compiler invocation interface
;;--------------------------------------------------------------------
(defn compile-grammar [grammar-map]
  (as-> grammar-map g
        (reduce -compile-grammar-rule {::c 0} g)
        (dissoc g ::c)))