(ns modern-programming-method-labs.lab2.lab-2-1
  (:require [modern-programming-method-labs.lab2.core :refer :all]
            [clojure.math :as math]))

(def indexed-trapezoid-mem
  (memoize indexed-trapezoid))

(defn integral-mem
  "Returns function, which calculates integral for 'f' from 0 to x
  by using trapezoid method with fixed step h. Optimized with memoization of trapezoids"
  [f h]
  (memoize (fn [x]
             (let [n (math/round (/ x h))]
               (reduce
                 (fn [acc idx]
                   (+
                     acc
                     (indexed-trapezoid-mem f h idx)))
                 (trapezoid f (* h n) x)
                 (range (dec n) -1 -1)))))
  )

