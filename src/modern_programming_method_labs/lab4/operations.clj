(ns modern-programming-method-labs.lab4.operations)

(defn expr-to-str [expr])

(defn get-type
  "Returns the type of expression"
  [expr]
  (keyword (first expr)))

(defn same-type?
  "Check that expr1 and expr2 are of the same type"
  [expr1 expr2]
  (= (first expr1) (first expr2)))

(defn args
  "Get arguments of operation"
  [expr]
  (rest expr))

(defn first-arg
  "Get arguments of operation"
  [expr]
  (first (rest expr)))

(defn second-arg
  "Get arguments of operation"
  [expr]
  (second (rest expr)))

; constant expression

(defn constant
  "Constructor for constant"
  [value]
  {:pre [(or (= value 0) (= value 1))]}
  (list ::const value))

(defn constant?
  "Check if expr is constant"
  [expr]
  (= (first expr) ::const))

(defn constant-value
  "Get constant value"
  [expr]
  (second expr))

(defn constant-to-str
  "Converts constant to string (rules-map is unused)"
  [rules-map expr]
  {:pre [(constant? expr)]}
  (str (constant-value expr))
  )

; variable expression

(defn variable
  "Constructor for variable"
  [name]
  {:pre [(keyword? name)]}
  (list ::var name))

(defn variable?
  "Check if expr is variable"
  [expr]
  (= (first expr) ::var))

(defn variable-name
  "Get variable name"
  [var]
  (name (second var)))

(defn same-variables?
  "Check if two variable are the same"
  [var1 var2]
  (and
    (variable? var1)
    (variable? var2)
    (= (variable-name var1)
       (variable-name var2))))

(defn variable-to-str
  "Converts variable to string (rules-map is unused)"
  [rules-map expr]
  {:pre [(variable? expr)]}
  (variable-name expr)
  )

; negation expression

(defn logic-not
  "Constructor for negation"
  [expr]
  (list ::not expr))

(defn logic-not?
  "Check if expression is negation"
  [expr]
  (= (first expr) ::not))

(defn logic-not-to-str
  "Converts negation to string by using rules map and expr-to-str function"
  [rules-map expr]
  {:pre [(logic-not? expr)]}
  (str "!"
       (expr-to-str rules-map (first (args expr)))
       )
  )

; disjunction expression

(defn logic-or
  "Constructor for disjunction"
  [expr & rest]
  (if (empty? rest)
    expr
    (cons ::or (cons expr rest))))

(defn logic-or?
  "Check if expression is disjunction"
  [expr]
  (= (first expr) ::or))

(defn logic-or-to-str
  "Converts disjunction to string by using rules map and expr-to-str function"
  [rules-map expr]
  {:pre [logic-or? expr]}
  (str "("
       (reduce
         (fn [exprs-str another-expr-str]
           (str exprs-str " || " another-expr-str))
         (expr-to-str rules-map (first (args expr)))
         (map
           (fn [expr]
             (expr-to-str rules-map expr))
           (rest (args expr))))
       ")"))

; conjunction expression

(defn logic-and
  "Constructor for conjunction"
  [expr & rest]
  (if (empty? rest)
    expr
    (cons ::and (cons expr rest))))

(defn logic-and?
  "Check if expression is conjunction"
  [expr]
  (= (first expr) ::and))

(defn logic-and-to-str
  "Converts conjunction to string by using rules map and expr-to-str function"
  [rules-map expr]
  {:pre [logic-and? expr]}
  (str "("
       (reduce
         (fn [exprs-str another-expr-str]
           (str exprs-str " && " another-expr-str))
         (expr-to-str rules-map (first (args expr)))
         (map
           (fn [expr]
             (expr-to-str rules-map expr))
           (rest (args expr))))
       ")"))

; implication expression

(defn logic-impl
  "Constructor for implication"
  [expr1 expr2]
  (list ::impl expr1 expr2))

(defn logic-impl?
  "Check if expression is implication"
  [expr]
  (= (first expr) ::impl))

(defn logic-impl-to-str
  "Converts implication to string by using rules map and expr-to-str function"
  [rules-map expr]
  {:pre [logic-impl? expr]}
  (str "("
       (expr-to-str rules-map (first (args expr)))
       " -> "
       (expr-to-str rules-map (second (args expr)))
       ")"))

(def expr-constructors
  "Map of constructors, there are
  keys: operation types (keywords) (example: ::and)
  value: operation constructor (function) (example: logic-and)"
  (hash-map
    ::not logic-not
    ::or logic-or
    ::and logic-and
    ::impl logic-impl))

(def expr-to-str-rules
  "Map of rules for converting expression to string
  Example {::constant constant-to-str}, there ::constant is the type of expression and
  constant-to-str is a function that consumes rules-map and expression and returns string"
  (hash-map
    ::const constant-to-str
    ::var variable-to-str
    ::not logic-not-to-str
    ::or logic-or-to-str
    ::and logic-and-to-str
    ::impl logic-impl-to-str
    ))

(defn expr-to-str
  "Converting expression to string by using given rules"
  [rules-map expr]
  (let
    [bad-expr-type "bad expression type"
     handler (get rules-map (get-type expr) bad-expr-type)]
    (if (= handler bad-expr-type)
      (throw (Exception. bad-expr-type))
      (handler rules-map expr)
      )
    )
  )
