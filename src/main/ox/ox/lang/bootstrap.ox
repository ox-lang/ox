(ns ox.lang.bootstrap
  (require ox.lang.booleans))

;; FIXME: this one is hard, IBoolean?

(def map nil
  (λ (((f ← ((x ← a) → b), xs ← nil) → nil)
      nil)

     (((f ← ((x ← a) → b), xs ← (Seq a)) → (Seq b))
      (let* ((x   (first xs))
             (xs' (rest xs)))
            (cons (f x)
                  (map-1 f xs'))))))

(def foldr nil
  (λ (((f ← ((x ← a, y ← b) → a), i ← a, xs ← nil) → a)
      i)

     (((f ← ((x ← a, y ← b) → a), i ← a, xs ← (Seq b)) → a)
      (foldr (f i) (first xs) (rest xs)))))
