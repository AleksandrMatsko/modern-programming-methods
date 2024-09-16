(ns modern-programming-method-labs.lab1.lab-1-3)

(defn my-map-list
  "Accepts f and coll and returns new collection,
  each element of new collection is a result of applying f to corresponding element in coll.
  f should accept one argument and return one argument."
  [f
   coll]
  (seq (reduce
         (fn [accumulated
              new-val]
           (concat accumulated (list (f new-val))))
         (list)
         coll)))

(println "map:" (map inc (list 1 2 3 4 5)))
(println "my-map-list" (my-map-list inc (list 1 2 3 4 5)))

(defn my-filter-list
  "Accepts pred and coll and returns new collection,
  consisted of elements from coll which satisfied pred.
  pred should accept one argument and return true of false."
  [predicate
   coll]
  (seq (reduce
         (fn [accumulated
              new-val]
           (if (predicate new-val)
             (concat accumulated (list new-val))
             accumulated))
         (list)
         coll)))

(println "filter:" (filter even? (list 1 2 3 4 5 6)))
(println "my-filter-list:" (my-filter-list even? (list 1 2 3 4 5 6)))
