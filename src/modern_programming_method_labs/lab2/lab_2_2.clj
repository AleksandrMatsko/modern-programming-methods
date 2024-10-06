(ns modern-programming-method-labs.lab2.lab-2-2
  (:require [modern-programming-method-labs.lab2.core :refer :all]
            [clojure.math :as math]))

(defrecord integral-iter-data
  [prev-integral-val
   index])

(defn new-integral-from-prev
  [f h]
  (fn [integral-iter-data-example]
    (let [idx (:index integral-iter-data-example)]
      (integral-iter-data.
        (+
          (:prev-integral-val integral-iter-data-example)
          (trapezoid
            f
            (* h (dec idx)) (* h idx)))
        (inc idx)))
    ))

(defn integral-inf
  "Returns function, which calculates integral for 'f' from 0 to x
  by using trapezoid method with fixed step h. Optimized by using lazy sequences"
  [f h]
  (let
    [iter-f (new-integral-from-prev f h)
     seq (map
           :prev-integral-val
           (iterate
             iter-f
             (integral-iter-data. 0 1)))]
    (fn [x]
      (let [n (math/round (/ x h))]
        (+
          (nth seq n)
          (trapezoid f (* h n) x)))
      )
    )
  )

