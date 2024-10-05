(ns modern-programming-method-labs.lab2.lab-2-2
  (:require [modern-programming-method-labs.lab2.core :refer :all]))

(defn integral-inf
  [f h]
  (let
    [seq (map
           first
           (iterate
             (fn [[prev-integral-val index]]
               [(+ prev-integral-val (trapezoid f (* h (- index 1)) (* h index)))
                (inc index)])
             [0 1]))]
    (fn [x]
      (let [n (int (/ x h))]
        (+
          (nth seq n)
          (trapezoid f (* h n) x)))
      )
    )
  )

(defn -main
  [& args]
  (let [h 0.01
        integrals (list
                    (simple-integral long-func h)
                    (integral-inf long-func h)
                    )
        vals (list 10 20 15)
        ]
    (measure-integrals integrals vals)
    ))
