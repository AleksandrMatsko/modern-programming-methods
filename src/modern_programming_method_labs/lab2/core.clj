(ns modern-programming-method-labs.lab2.core
  (:require [clojure.math :as math]))

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
          (* item (math/pow x idx)))
        coefficients))))

(defn trapezoid-numerator
  [f a b]
  (+ (f a) (f b)))

(defn trapezoid
  [f a b]
  (let [res (*
              (trapezoid-numerator f a b)
              (- b a)
              0.5)]
    res)
  )

(defn indexed-trapezoid
  [f h i]
  (trapezoid f (* h i) (* h (inc i))))

(defn simple-integral
  "Returns function, which calculates integral for 'f' from 0 to x
  by using trapezoid method with fixed step h"
  [f h]
  (fn [x]
    (let [n (math/round (/ x h))]
      (+
        (*
          (reduce
            +
            0
            (map
              (fn
                [val]
                (trapezoid-numerator f val (+ val h)))
              (range 0 x h)))
          h
          0.5)
        (trapezoid f (* n h) x)))
    ))

