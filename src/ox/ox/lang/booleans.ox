(ns ox.lang.booleans)

(deftype Bool
    (| true
       false))

(define-syntax (and)
  true)

(define-syntax (and clause &more clauses)
  (list 'ox.lang.core/if
