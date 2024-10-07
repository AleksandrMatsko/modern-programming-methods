(ns modern-programming-method-labs.lab2.core-test
  (:require [clojure.test :refer :all]
            [modern-programming-method-labs.lab2.core :refer :all]
            [modern-programming-method-labs.lab2.lab-2-1 :refer :all]
            [modern-programming-method-labs.lab2.lab-2-2 :refer :all]))

(defn long-func
  [x]
  (Thread/sleep 1)
  x)

(defn measure-integral
  [integral-f
   vals]
  (doall
    (map
      (fn [val]
        (time (integral-f val)))
      vals)))

(deftest integral-benchmark
  (let [h 0.01
        f long-func
        integral-f (simple-integral f h)
        integral-mem-f (integral-mem f h)
        integral-inf-f (integral-inf f h)
        vals (list 10 20 30 15)]
    (println "No mem integral:")
    (measure-integral integral-f vals)
    (println "Mem integral:")
    (measure-integral integral-mem-f vals)
    (println "Inf sequence integral:")
    (measure-integral integral-inf-f vals)
    ))

(def test-func
  (polynomial (list 1 2 3)))

(defn integral-has-expected-val
  [integral-f
   h
   arg
   expected]
  (<= (abs (- (integral-f arg) expected)) (* h h arg)))

(deftest integral-calc
  (let [h 0.01
        f test-func
        integral-f (simple-integral f h)
        integral-mem-f (integral-mem f h)
        integral-inf-f (integral-inf f h)
        ]
    (testing "no mem integral"
      (is (integral-has-expected-val integral-f h 1 3))
      (is (integral-has-expected-val integral-f h 1.5 7.125))
      )
    (testing "mem integral"
      (is (integral-has-expected-val integral-mem-f h 1 3))
      (is (integral-has-expected-val integral-mem-f h 1.5 7.125))
      )
    (testing "inf sequence integral"
      (is (integral-has-expected-val integral-inf-f h 1 3))
      (is (integral-has-expected-val integral-inf-f h 1.5 7.125))
      )
    ))
