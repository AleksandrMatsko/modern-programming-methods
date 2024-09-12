(ns modern-programming-method-labs.lab1.core)

(defn add-char-to-word
  "Add single character to word if it doesn't end on the same char"
  [word
   char
   n]
  (if (>= (count (str word)) n)
    nil
    (if (= (str (last word))  char)
      nil
      (str word char)
      )))

(defn inc-word
  [word
   n
   alphabet
   ready-words]
  (if (empty? alphabet)
    ready-words
    (let [new-word (add-char-to-word word (first alphabet) n)
          new-alphabet (rest alphabet)]
      (if (nil? new-word)
        (inc-word word n new-alphabet ready-words)
        (inc-word word n new-alphabet (into ready-words (list new-word)))))))

(defn gen-words
  [alphabet
   n
   words
   ready-words]
  (if (empty? words)
    (if (< (count (first ready-words)) n)
      (gen-words alphabet n ready-words (list))             ; if we already generated words of previous len, start generating words with len+1
      ready-words)
    (let [new-words (inc-word (first words) n alphabet (list))]
      (gen-words alphabet n (rest words) (into ready-words new-words)))))

(defn gen-permutations
  "Generate permutations with length n, consisted of symbols from alphabet
  and each permutation doesn't have 2 same symbols in a row."
  [alphabet
   n]
  (if (<= n 0)
    (list)
    (if (= n 1)
      alphabet
      (gen-words alphabet n alphabet (list))))
  )

(println (gen-permutations (list "a" "b" "c") 4))