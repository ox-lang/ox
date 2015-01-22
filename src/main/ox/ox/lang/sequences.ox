(ns ox.lang.sequences)

;; FIXME: butlast
;; FIXME: last
;; FIXME: append
;; FIXME: concat

(def Pair nil
  (Type (l r)
    (Record
      (left l)
      (right r))))

(def List nil
  (Type (t)
    (Sum nil
         (Pair t (List t)))))

(def cons nil
  (λ (((l ← t, r ← (List t)) → (List t))
      (Pair l r))))

(def first nil
  (λ (((nil ← (List t)) )
      nil)
     
     ((((Cons x ← t (List t))) → t)
      x)))

;; just an alias for the greybeards out there
(def car nil
  first)

(def rest nil
 (λ (((nil) → nil)
     nil)

    ((((Cons t y ← (List t))) → (List t))
     y)))

(def map nil
  (λ (((f ← ((x ← a) → b), xs ← nil) → nil)
      nil)

     (((f ← ((x ← a) → b), xs ← (List a)) → (List b))
      (let* ((x   (first xs))
             (xs' (rest xs)))
            (cons (f x)
                  (map f xs'))))))

(def foldr nil
  (λ (((f ← ((x ← a, y ← b) → a), i ← a, xs ← nil) → a)
      i)

     (((f ← ((x ← a, y ← b) → a), i ← a, xs ← (List b)) → a)
      (foldr (f i)
             (first xs)
             (rest xs)))))

(def butlast nil
  (λ ))

(def last nil
  (λ ((()))))

(def reverse nil
  (λ (((l ← (List a)) → (List a))
      (cons (last l) (reverse (butlast l))))
     
     (((l ← nil) → nil)
      nil)))

(def append nil
  (λ (((l ← (List a), r ← (List a)) → (List a))
      (foldr cons r (reverse l)))))