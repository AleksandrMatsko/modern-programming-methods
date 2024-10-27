(ns modern-programming-method-labs.lab4.dnf
  (:require [modern-programming-method-labs.lab4.operations :refer :all]))

(defn convert-logic-ops-factory
  "Accepts map of expression constructors, expression recognising cond? and
  conversion function (convert-f) which should take 2 arguments:
  function to convert nested expressions and expression.
  See the usage on convert-implications."
  [constructors cond? convert-f]
  (fn convert-logic-op [expr]
    (cond
      (cond? expr) (convert-f convert-logic-op expr)
      (constant? expr) expr
      (variable? expr) expr
      :else (let [expr-type (get-type expr)
                  no-proper-constructor "no constructor for such expression type"
                  constructor (get constructors expr-type no-proper-constructor)
                  converted-expressions (map
                                          convert-logic-op
                                          (args expr))
                  ]
              (if (= constructor no-proper-constructor)
                (throw (Exception. no-proper-constructor))
                (apply constructor converted-expressions)
                ))
      )
    )
  )

(defn convert-implications
  "Convert implications (x -> y = !x || y) also goes deep.
  Based on convert-logic-ops-factory"
  [constructors]
  (convert-logic-ops-factory
    constructors
    logic-impl?
    (fn [convert-logic-op expr]
      (logic-or
        (logic-not (convert-logic-op (first-arg expr)))
        (convert-logic-op (second-arg expr))))))

(defn convert-rules
  [constructors]
  (list
    (convert-implications constructors)))

(defn negation-equivalencies
  "!(x || y) = !x && !y
   !(x && y) = !x || !y
   !(!x) = x
   !0 = 1; !1 = 0"
  [expr]
  (let [negation-equivalencies-with-not (fn [expression]
                                          (negation-equivalencies (logic-not expression)))]
    (cond
      (logic-not? expr) (let [sub-expr (first-arg expr)]
                          (cond
                            ; !(expr || sub-expr) = !expr && !sub-expr
                            (logic-or? sub-expr) (apply logic-and (map negation-equivalencies-with-not (args sub-expr)))
                            ; !(expr && sub-expr) = !expr || !sub-expr
                            (logic-and? sub-expr) (apply logic-or (map negation-equivalencies-with-not (args sub-expr)))
                            ; !(!sub-expr) = sub-expr
                            (logic-not? sub-expr) (negation-equivalencies (first-arg sub-expr))
                            ; !0 = 1; !1 = 0
                            (constant? sub-expr) (if (= sub-expr (constant 0))
                                                   (constant 1)
                                                   (constant 0))
                            ; we can't do anything return y
                            :else expr))
      ; going inside disjunction and conjunction
      (logic-or? expr) (apply logic-or (map negation-equivalencies (args expr)))
      (logic-and? expr) (apply logic-and (map negation-equivalencies (args expr)))
      :else expr)
    ))

(defn distributivity-law
  "(x || y) && (z || w) = ((x || y) && z) || ((x || y) && w) =
  (x && z) || (y && z) || (x && w) || (y && w)"
  [expr]
  (cond
    (logic-and? expr) (let [disjunction-exprs (filter
                                                logic-or?
                                                (args expr))
                            not-disjunction-exprs (filter
                                                    (fn [e] (not (logic-or? e)))
                                                    (args expr))]
                        (if (empty? disjunction-exprs)
                          (apply logic-and (map distributivity-law (args expr)))
                          (let [; get first disjunction
                                disjunction (first disjunction-exprs)
                                ; other disjunctions goes back to huge conjunction
                                ; example:
                                ; if expr = (a || b || c) && (d || e || f) || g
                                ; disjunction = (a || b || c)
                                ; huge-conjunction = (g && (d || e || f))
                                huge-conjunction (apply logic-and (concat
                                                                    not-disjunction-exprs
                                                                    (drop 1 disjunction-exprs)))
                                ]
                            ; result will be
                            ; (a && (g && (d || e || f)) || (b && (g && (d || e || f))) || (c && (g && (d || e || f)))
                            (apply logic-or (map
                                              (fn [e]
                                                (distributivity-law (logic-and e huge-conjunction)))
                                              (args disjunction))))
                          ))
    (logic-or? expr) (apply logic-or (map distributivity-law (args expr)))
    :else expr                                              ; 'logic-not' also here because '!' is in front of variables and constants
    )
  )

(defn combine-same-args
  "Combine arguments of the same type in one operation"
  [expr]
  (if (or (constant? expr) (variable? expr))
    (list expr)
    (mapcat
      (fn [inner-expr]
        (if (same-type? expr inner-expr)
          (combine-same-args inner-expr)
          (list inner-expr)
          ))
      (args expr)
      )))

(defn decompose
  "x && (y && z) = x && y && z"
  [expr]
  (if (or (logic-or? expr) (logic-and? expr))
    (cons (get-type expr) (map decompose (combine-same-args expr)))
    expr))

(defn idempotent-law
  "x && x = x
   x || x = x"
  [expr]
  (cond
    (logic-and? expr) (apply logic-and (map idempotent-law (distinct (args (decompose expr)))))
    (logic-or? expr) (apply logic-or (map idempotent-law (distinct (args (decompose expr)))))
    (logic-not? expr) (logic-not (idempotent-law (first-arg expr)))
    :else expr))

(defn use-constant-laws
  "x && 1 = x
   x && 0 = 0
   x || 1 = 1
   x || 0 = x"
  [expr]
  (let [expr-args-has-constant (fn [expr c]
                            (some
                              (fn [e]
                                (and
                                  (constant? e)
                                  (= e c)))
                              (args expr)))
        ]
    (cond
      (logic-and? expr) (cond
                          (expr-args-has-constant expr (constant 0)) (constant 0)
                          (expr-args-has-constant expr (constant 1)) (let [expr (idempotent-law expr)]
                                                                       (if (logic-and? expr)
                                                                         (use-constant-laws
                                                                           (apply
                                                                             logic-and
                                                                             (filter
                                                                               (fn [e]
                                                                                 (not (and
                                                                                        (constant? e)
                                                                                        (= e (constant 1)))))
                                                                               (args expr))
                                                                             ))
                                                                         expr
                                                                         ))
                          :else (apply logic-and (map use-constant-laws (args expr)))
                          )
      (logic-or? expr) (cond
                          (expr-args-has-constant expr (constant 1)) (constant 1)
                          (expr-args-has-constant expr (constant 0)) (let [expr (idempotent-law expr)]
                                                                       (if (logic-or? expr)
                                                                         (use-constant-laws
                                                                           (apply
                                                                             logic-or
                                                                             (filter
                                                                               (fn [e]
                                                                                 (not (and
                                                                                        (constant? e)
                                                                                        (= e (constant 0)))))
                                                                               (args expr))
                                                                             ))
                                                                         expr
                                                                         ))
                          :else (apply logic-or (map use-constant-laws (args expr)))
                          )
      :else expr
      )
    )
  )

(defn print-expr
  [expr]
  (println (expr-to-str
             expr-to-string-rules
             expr))
  expr
  )

(defn dnf
  "Convert logic expression to DNF format"
  [converting-rules expr]
  (->>
    (reduce
      (fn [expr rule]
        (rule expr))
      expr
      converting-rules)
    ;(print-expr)
    (negation-equivalencies)
    ;(print-expr)
    (distributivity-law)
    ;(print-expr)
    (idempotent-law)
    ;(print-expr)
    (use-constant-laws)
    ;(print-expr)
    (idempotent-law)
    ;(print-expr)
    (use-constant-laws)
    )
  )

(defn substitute-vals
  "Substitute values to variables in expression."
  [constructors expr vars-map ]
  (cond
    (variable? expr) (if (contains? vars-map (first-arg expr))
                       (constant (get vars-map (first-arg expr)))
                       expr)
    (constant? expr) expr
    :else (let [expr-type (get-type expr)
                no-proper-constructor "no constructor for such expression type"
                constructor (get constructors expr-type no-proper-constructor)
                substituted-expressions (map
                                          (fn [e]
                                            (substitute-vals constructors e vars-map))
                                          (args expr))
                ]
            (if (= constructor no-proper-constructor)
              (throw (Exception. no-proper-constructor))
              (apply constructor substituted-expressions)
              ))
    )
  )
