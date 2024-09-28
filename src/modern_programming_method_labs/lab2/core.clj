(ns modern-programming-method-labs.lab2.core
  (:require [clojure.math :refer :all] ))

(defn polynomial
  "Construct polynomial function with given coefficients.
  First coefficient is before (pow x 0)"
  [coefficients]
  (fn
    [x]
    (reduce
      +
      (map-indexed
        (fn [idx item]
          (* item (pow x idx)))
        coefficients))))

(println ((polynomial (list 1 2 3)) 3))
