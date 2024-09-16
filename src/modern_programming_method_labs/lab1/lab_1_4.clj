(ns modern-programming-method-labs.lab1.lab-1-4
  (:require [modern-programming-method-labs.lab1.core :refer :all]
            [modern-programming-method-labs.lab1.lab-1-3 :refer :all]))

(defn add-symbol-to-words
  [words
   sym
   n]
  (if (empty? words)
    (list sym)
    (my-filter-list
      (fn [val]
        (not (nil? val))
        )
      (my-map-list
        (fn [word] (add-char-to-word word sym n))
        words)))
  )

(defn inc-words
  [words
   alphabet
   n]
  (reduce
    (fn [accumulated
         sym]
      (concat accumulated (add-symbol-to-words words sym n)))
    (list)
    alphabet))

(defn repeat-alphabet
  [alphabet
   n]
  (my-map-list
    (fn [val] (seq alphabet))
    (take n (range))))


(defn gen-permutations
  [alphabet
   n]
  (reduce
    (fn [words
         a]
      (inc-words words a n))
    (list)
    (repeat-alphabet alphabet n)))

(println (gen-permutations (list "a" "b" "c") 3))
