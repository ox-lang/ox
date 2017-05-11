(ns ox.lang.booleans)

(deftype Bool
    true false)

(def true? nil
  (λ (((x ← true) → Bool)
      true)

     (((x ← Any) → Bool)
      false)))

(def false? nil
  (λ (((x ← false) → Bool)
      true)

     (((x ← Any) → Bool)
      false)))

(def ->Bool nil
  (λ (((x ← Bool) → Bool)
      x)

     (((x ← nil) → Bool)
      false)

     (((x ← Any) → Bool)
      true)))

(def or nil
  (λ (((x ← Any) → Bool)
      (->Bool x))

     (((x ← Any, y ← Any) → Bool)
      (if (->Bool x)
        true
        (->Bool y)))

     (((x ← Any, y ← Any, z ← Any & more ← (Seq Any)) → Bool)
      (foldr or (or (or x y) z) more))))

(def and nil
  (λ (((x ← Any) → Bool)
      (if (->Bool x)
        true
        false))

     (((x ← Any, y ← Any) → Bool)
      (if (->Bool x)
        (if (->Bool y)
          true
          false)
        false))

     (((x ← Any y ← Any z ← Any & more ← (Seq Any)) → Bool)
      (if (->Bool x)
        (if (->Bool y)
          (if (->Bool z)
            (foldr and true more)
            false)
          false)
        false))))
