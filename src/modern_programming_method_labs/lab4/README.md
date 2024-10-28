# Lab 4 

Code to convert logical formulas to DNF.

Now implemented the following operations:
- `constant`: 0 (false) or 1 (true)
- `variable` - a keyword
- `logic-not` - negation (!)
- `logic-and` - conjunction (&&)
- `logic-or` - disjunction (||)
- `logic-impl` - implication (->)

## Adding new operations

Do the following steps:

1. Create constructor for operation:
```clojure
; example constructor for implication
(defn your-logic-impl
  "Constructor for your implication"
  [expr1 expr2]
  (list ::your-impl expr1 expr2))
```
2. Create recognizing function that returns true if it is your operation and false otherwise:
```clojure
; example recognising function for implication
(defn your-logic-impl?
  "Check if expression is your implication"
  [expr]
  (= (first expr) ::your-impl))
```
3. If you want to convert expression with your operation to string you need to write `-to-str` function, add it to rules map and use it. Example:
```clojure
(defn your-logic-impl-to-str
  "Converts implication to string by using rules map and expr-to-str function"
  [rules-map  ; map of converting to string rules
   expr       ; your expression 
   ]
  {:pre [your-logic-impl? expr]}
  (str "("
       ; expr-to-str is a function that calls suitable converting rule for expression
       (expr-to-str rules-map (first (args expr))) 
       " -->> "
       (expr-to-str rules-map (second (args expr)))
       ")"))

; in your code add your function to `expr-to-str-rules` - rules map
(def your-rules-map
  (conj expr-to-str-rules {::your-impl your-logic-impl-to-str}))

; use your rules while printing expressions
(expr-to-str
  your-rules-map
  ; x -->> y
  (your-logic-impl
    (variable :x)
    (variable :y)))
```
4. If you want to use your operation in dnf you need to implement converting function, add it to converting rules and use new rules. Example:
```clojure
(defn convert-your-impl
  "Convert your-impl (x -->> y = !x || y) also goes deep.
  Based on convert-logic-ops-factory"
  [constructors]
  (convert-logic-ops-factory    ; function for creating convertors
    constructors                ; map of rules for constructing expression
    your-logic-impl?            ; recognising function from step 2
    (fn [convert-logic-op       ; function that recursively converts operations (got from convert-logic-ops-factory)
         expr                   ; your-impl expression to convert
         ]
      (logic-or
        (logic-not (convert-logic-op (first-arg expr)))
        (convert-logic-op (second-arg expr))))))

; add constructor of your operation to constructors map
(def your-expr-constructors
  (conj expr-constructors {::your-impl your-logic-impl}))

; create (or add to existing) converting rules list
; default converting rules may be obtained by calling (converting-rules your-constructors)
(defn your-converting-rules
  [constructors]
  (list
    (convert-your-impl constructors)
    ))

; call dnf with your converting rules
(dnf
  (your-converting-rules your-expr-constructors)
  (your-logic-impl
    (variable :x)
    (variable :y)))
```
5. Also variable substitution is provided. Example:
```clojure
(substitute-vals
  your-expr-constructors
  ; x -->> y
  (your-logic-impl
    (variable :x)
    (variable :y))
  (hash-map
    :y 0))
  ; will result in x -->> 0
```

