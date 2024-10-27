(ns modern-programming-method-labs.lab4.core
  (:require [modern-programming-method-labs.lab4.operations :refer :all]
            [modern-programming-method-labs.lab4.dnf :refer :all]))

(defn logic-eq
  "a == b"
  [expr1 expr2]
  (list ::eq expr1 expr2))

(defn logic-eq?
  "Check if eq"
  [expr]
  (= (first expr) ::eq))

(defn logic-eq-to-str
  "Converts eq to string"
  [rules-map expr]
  {:pre [(logic-eq? expr)]}
  (str
    "("
    (expr-to-str rules-map (first-arg expr))
    " == "
    (expr-to-str rules-map (second-arg expr))
    ")"
    ))

(defn convert-eq
  [constructors]
  (convert-logic-ops-factory
    constructors
    logic-eq?
    (fn [convert-logic-op expr]
      (logic-or
        (logic-and
          (logic-not (convert-logic-op (first-arg expr)))
          (logic-not (convert-logic-op (second-arg expr))))
        (logic-and
          (convert-logic-op (first-arg expr))
          (convert-logic-op (second-arg expr)))
        ))))

(defn -main []
  (println (expr-to-str
             (conj expr-to-string-rules {::eq logic-eq-to-str})
             ; (x -> y) == 1
             (logic-eq (logic-impl (variable :x) (variable :y)) (constant 1))))
  (println (expr-to-str
             expr-to-string-rules
             (logic-or
               (variable :x)
               (logic-or
                 (variable :y)
                 (variable :z)))))
  (println (expr-to-str
             expr-to-string-rules
             ((convert-implications expr-constructors)
              ; (x -> y) -> (0 -> z) = !(!x || y) || (!0 || z)
              (logic-impl
                (logic-impl (variable :x) (variable :y))
                (logic-impl (constant 0) (variable :z))))))
  (println (expr-to-str
             expr-to-string-rules
             (use-constant-laws
               ; (x && 0) || (y && 0)
               (logic-or
                 (logic-and
                   (variable :x)
                   (constant 0))
                 (logic-and
                   (variable :y)
                   (constant 0))))))
  (println (expr-to-str
             expr-to-string-rules
             (dnf
               (convert-rules expr-constructors)
               ; x -> y
               (logic-impl
                 (variable :x)
                 (variable :y)))))
  (println (expr-to-str
             expr-to-string-rules
             (dnf
               (convert-rules expr-constructors)
               ; (x && (x || y))
               (logic-and
                 (variable :x)
                 (logic-or
                   (variable :x)
                   (variable :y))))))
  (println (substitute-vals
             expr-constructors
             ; (x -> y) [y = 0]
             (logic-impl
               (variable :x)
               (variable :y))
             (hash-map
               :y 0)))
  (let [constructors (conj expr-constructors {::eq logic-eq})
        convert-rules-with-eq (list
                                (convert-implications constructors)
                                (convert-eq constructors))]
    (println (expr-to-str
               expr-to-string-rules
               (dnf
                 convert-rules-with-eq
                 (substitute-vals
                   constructors
                   ; (x == y) -> z [x = 1]
                   (logic-impl
                     (logic-eq
                       (variable :x)
                       (variable :y))
                     (variable :z))
                   (hash-map :x 1)))))
    )
  )

