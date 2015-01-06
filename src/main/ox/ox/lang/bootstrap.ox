; ns ox.lang.bootstrap

(def* list nil
  (fn* ((& xs)
        (list* xs))))

(def* first nil
  (fn* ((x)
	(apply* (fn* ((x & more) x)) x))))

(def* second nil
  (fn* ((x)
        (apply* (fn* ((x y & more) y)) x))))

(def* rest nil
  (fn* ((x)
        (apply* (fn* ((x & more) more)) x))))

(def* keyword nil
  (fn* ((n)
        (list 'keyword/unqualified n))
       ((ns n)
        (list 'keyword/qualified ns n))))
