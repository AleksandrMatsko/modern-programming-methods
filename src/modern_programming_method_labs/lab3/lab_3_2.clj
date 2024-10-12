(ns modern-programming-method-labs.lab3.lab-3-2
  (:require [modern-programming-method-labs.lab3.lab-3-1 :refer :all]))

(defn lazy-thread-filter
  [cond? thread-count take-count coll]
  (lazy-cat
    (thread-filter cond? thread-count (take take-count coll))
    (if (empty? coll)
      coll
      (lazy-thread-filter cond? thread-count take-count (drop take-count coll)))))

(println (lazy-thread-filter odd? 5 17 (range 0 100 1)))

(defn printable-even?
  [x]
  (if (= (mod x 1000) 0)
    (println x)
    nil)
  (even? x))

(defn -main
  [& args]
  (->>
    (lazy-thread-filter printable-even? 5 17 (iterate inc 1))
    (doall)))
