(ns modern-programming-method-labs.lab1.core)

(defn add-char-to-word
  "Add single character to word if it doesn't end on the same char and has len < n"
  [word
   char
   n]
  (if (>= (count (str word)) n)
    nil
    (if (empty? word)
      (str char)
      (if (= (str (last word))  char)
        nil
        (str word char)
        ))
    ))
