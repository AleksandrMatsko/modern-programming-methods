(ns modern-programming-method-labs.lab1.lab-1-4
  (:require [modern-programming-method-labs.lab1.core :refer :all]))

(defn add-symbol-to-words
  [words
   sym
   n]
  (if (empty? words)
    (list sym)
    (remove
      nil?
      (map
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
  (map
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
