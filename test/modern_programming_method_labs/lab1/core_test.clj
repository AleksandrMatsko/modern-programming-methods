(ns modern-programming-method-labs.lab1.core-test
  (:require [clojure.test :refer :all]))

(defn contains-in-expected
  [actual
   expected]
  (if (empty? actual)
    true
    (and
      (contains? expected (first actual))
      (contains-in-expected (rest actual) expected))))

(defn contains-all-expected
  [actual
   expected]
  (if (= (count actual) (count expected))
    ()
    false))

(def one-sym-alphabet (list "a"))
(def alphabet (list "a" "b" "c"))
(def expected-words-with-len-2 (list "ab" "ac" "ba" "bc" "ca" "cb"))
(def expected-words-with-len-3 (list "aba" "abc" "aca" "acb" "bab" "bac" "bca" "bcb" "cab" "cac" "cba" "cbc"))
