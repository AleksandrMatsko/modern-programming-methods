(ns modern-programming-method-labs.lab2.core-test
  (:require [clojure.test :refer :all]))

(defn integral-with-given-x-returns-expected
  [integral
   h
   x
   expected]
  (let [actual (integral x)
        expected-left (- expected h)
        expected-right (+ expected h)
        ]
    (and (< actual expected-right) (> actual expected-left))))

