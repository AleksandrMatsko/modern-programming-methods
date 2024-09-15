(ns modern-programming-method-labs.lab1.core-test
  (:require [clojure.test :refer :all]))

(defn contains-in-list
  [_list
   elem]
  (if (empty? _list)
    false
    (if (= (first _list) elem)
      true
      (recur (rest _list) elem)))
  )

(defn contains-in-expected
  [actual
   expected]
  (if (empty? actual)
    true
    (and
      (contains-in-list expected (first actual))
      (recur (rest actual) expected))))

(defn contains-all-expected
  [actual
   expected]
  (if (= (count actual) (count expected))
    (contains-in-expected actual expected)
    false))

(def one-sym-alphabet (list "a"))
(def alphabet (list "a" "b" "c"))
(def expected-words-with-len-2 (list "ab" "ac" "ba" "bc" "ca" "cb"))
(def expected-words-with-len-3 (list "aba" "abc" "aca" "acb" "bab" "bac" "bca" "bcb" "cab" "cac" "cba" "cbc"))
