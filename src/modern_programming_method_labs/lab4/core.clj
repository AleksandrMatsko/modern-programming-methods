(ns modern-programming-method-labs.lab4.core
  (:require [modern-programming-method-labs.lab4.operations :refer :all]))

(defn logic-eq
  [expr1 expr2]
  (list ::eq expr1 expr2))

(defn logic-eq?
  [expr]
  (= (first expr) ::eq))

(defn logic-eq-to-str
  [rules-map expr]
  {:pre [(logic-eq? expr)]}
  (str
    "("
    (expr-to-str rules-map (first-arg expr))
    " == "
    (expr-to-str rules-map (second-arg expr))
    ")"
    ))

(defn -main []
  (println (expr-to-str
             (conj expr-to-string-rules {::eq logic-eq-to-str})
             (logic-eq (logic-impl (variable :x) (variable :y)) (constant 1)))))

