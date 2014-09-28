(ns oxlang.evaluator
  )

;; This program provides an whole program AST analyzer for a very
;; simple lisp having only lists, symbols and atoms built in.

(defn analyze
  "Function of a form, an environment and a transient map representing
  the whole loaded program in SSA form. Walks the form, analyzing any
  subforms, creating a new entry in the global AST.

  Returns a tuple being the ID of the analyzed node in the global AST,
  the updated or unchanged environment, and the updated global AST."
  ([form env gast]
     (cond
      ;; List (general expression case)
      (list? form)
      ,,(loop [forms   form
               env     env
               gast    gast
               results []]
          (if forms
            (let [[f & forms] forms
                  [res env gast] (analyze f env gast)]
              (recur forms env gast (conj results res)))
            (let [id (uuid)])
            )

          )

      ;; Symbol, terminal case for all tokens
      (symbol? form)
      ,,()

      ;; Else case, handles inline constants
      true
      ,,()))
