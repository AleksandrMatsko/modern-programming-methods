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

(defn long-func
  [x]
  (Thread/sleep 1)
  x)

(defn trapezoid-numerator
  [f a b]
  (+ (f a) (f b)))

(defn trapezoid
  [f a b]
  (*
    (trapezoid-numerator f a b)
    (- b a)
    0.5))

(defn indexed-trapezoid
  [f h i]
  (trapezoid f (* h i) (* h (inc i))))

(defn simple-integral
  [f
   h]
  (fn [x]
    (*
      h
      (reduce
        +
        (trapezoid f (* h (int (/ x h))) x)
        (map
          (fn
            [val]
            (+ (f val) (f (+ val h))))
          (range 0 x h)))
      0.5)))

(defn measure-integrals
  [integrals vals]
  (doall
    (map-indexed
      (fn [idx integral-f]
        (println "\nIntegral: " (inc idx))
        (doall (map
                 (fn [val]
                   (time (integral-f val))
                   )
                 vals))
        )
      integrals)))
