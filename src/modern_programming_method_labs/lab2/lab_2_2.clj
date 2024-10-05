(ns modern-programming-method-labs.lab2.lab-2-2
  (:require [modern-programming-method-labs.lab2.core :refer :all]
            [clojure.math :as math]))

(defn integral-inf
  "Returns function, which calculates integral for 'f' from 0 to x
  by using trapezoid method with fixed step h. Optimized by using lazy sequences"
  [f h]
  (let
    [seq (map
           first
           (iterate
             (fn [[prev-integral-val index]]
               [(+
                  prev-integral-val
                  (trapezoid f (* h (- index 1)) (* h index)))
                (inc index)])
             [0 1]))]
    (fn [x]
      (let [n (math/round (/ x h))]
        (+
          (nth seq n)
          (trapezoid f (* h n) x)))
      )
    )
  )

