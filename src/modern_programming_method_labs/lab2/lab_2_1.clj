(ns modern-programming-method-labs.lab2.lab-2-1
  (:require [modern-programming-method-labs.lab2.core :refer :all]))

(def indexed-trapezoid-mem
  (memoize indexed-trapezoid))

(defn integral-mem
  [f h]
  (memoize (fn [x]
             (let [n (int (/ x h))]
               (reduce
                 (fn [acc idx]
                   (+
                     acc
                     (indexed-trapezoid-mem f h idx)))
                 (trapezoid f (* h n) x)
                 (range (dec n) -1 -1)))))
  )

(defn long-func
  [x]
  (Thread/sleep 1)
  x)


(defn -main
  [& args]
  (let [h 0.01
        integrals (list
                    (simple-integral long-func h)
                    (integral-mem long-func h)
                    )
        vals (list 10 20 15)
        ]
    (measure-integrals integrals vals)
  ))
