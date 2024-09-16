(ns modern-programming-method-labs.lab1.lab-1-3)

(defn my-map-list
  [f
   coll]
  (seq (reduce
         (fn [arg1
              arg2]
           (concat arg1 (list (f arg2))))
         (list)
         coll)))

(println "map:" (map inc (list 1 2 3 4 5)))
(println "my-map-list" (my-map-list inc (list 1 2 3 4 5)))
