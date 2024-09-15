(ns modern-programming-method-labs.lab1.lab-1-2
  (:require [modern-programming-method-labs.lab1.core :refer :all]))

(defn inc-word
  "Generate from given word new words by adding symbol to the end using function add-char-to-word"
  [word
   n
   alphabet
   ready-words]
  (if (empty? alphabet)
    ready-words
    (let [new-word (add-char-to-word word (first alphabet) n)
          new-alphabet (rest alphabet)]
      (if (nil? new-word)
        (recur word n new-alphabet ready-words)
        (recur word n new-alphabet (concat ready-words (list new-word)))))))

(defn gen-words
  "Generate new words all of len n by applying inc-word to each element in words"
  [alphabet
   n
   words
   ready-words]
  (if (empty? words)
    (if (< (count (first ready-words)) n)
      (recur alphabet n ready-words (list))             ; if we already generated words of previous len, start generating words with len+1
      ready-words)
    (let [new-words (inc-word (first words) n alphabet (list))]
      (recur alphabet n (rest words) (concat ready-words new-words)))))

(defn gen-permutations
  "Generate permutations with length n, consisted of symbols from alphabet.
  Each permutation doesn't have 2 same symbols in a row."
  [alphabet
   n]
  (if (<= n 0)
    (list)
    (if (= n 1)
      alphabet
      (if (= (count alphabet) 1)                            ; if alphabet contains only 1 symbol we can't construct words of len > 1
        (list)
        (gen-words alphabet n alphabet (list)))
      ))
  )

(println (gen-permutations (list "a" "b" "c" "d") 3))

