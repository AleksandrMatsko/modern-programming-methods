(ns modern-programming-method-labs.lab4.dnf-test
  (:require [clojure.test :refer :all]
            [modern-programming-method-labs.lab4.dnf :refer :all]
            [modern-programming-method-labs.lab4.operations :refer :all]))

(deftest test-distributivity-law
  (testing "(x || y) && z"
    (is (= (distributivity-law
             (logic-and
               (logic-or
                 (variable :x)
                 (variable :y))
               (variable :z)))
           (logic-or
             (logic-and
               (variable :x)
               (variable :z))
             (logic-and
               (variable :y)
               (variable :z)))
           ))
    )
  )

(deftest test-use-constant-laws
  (testing "0 || x = x"
    (is (= (use-constant-laws
             (logic-or
               (constant 0)
               (variable :x)))
           (variable :x)))
    )
  (testing "x || 0 = x"
    (is (= (use-constant-laws
             (logic-or
               (variable :x)
               (constant 0)))
           (variable :x)))
    )
  (testing "1 || x = 1"
    (is (= (use-constant-laws
             (logic-or
               (constant 1)
               (variable :x)))
           (constant 1)))
    )
  (testing "x || 1 = 1"
    (is (= (use-constant-laws
             (logic-or
               (variable :x)
               (constant 1)))
           (constant 1)))
    )
  (testing "1 && x = x"
    (is (= (use-constant-laws
             (logic-and
               (constant 1)
               (variable :x)))
           (variable :x)))
    )
  (testing "x && 1 = x"
    (is (= (use-constant-laws
             (logic-and
               (variable :x)
               (constant 1)))
           (variable :x)))
    )
  (testing "0 && x = 0"
    (is (= (use-constant-laws
             (logic-and
               (constant 0)
               (variable :x)))
           (constant 0)))
    )
  (testing "x && 0 = 0"
    (is (= (use-constant-laws
             (logic-and
               (variable :x)
               (constant 0)))
           (constant 0)))
    )
  )

(deftest test-dnf
  (testing "x -> y = (!x || y)"
    (is (= (dnf
             (convert-rules expr-constructors)
             (logic-impl
               (variable :x)
               (variable :y)))
           (logic-or
             (logic-not
               (variable :x))
             (variable :y)))))
  (testing "!((x -> y) || !(y -> z)) = (x && !y) || (x && !y && z)"
    (is (= (dnf
             (convert-rules expr-constructors)
             (logic-not
               (logic-or
                 (logic-impl
                   (variable :x)
                   (variable :y))
                 (logic-not
                   (logic-impl
                     (variable :y)
                     (variable :z))))))
           (logic-or
             (logic-and
               (logic-not (variable :y))
               (variable :x)
               )
             (logic-and
               (variable :z)
               (variable :x)
               (logic-not (variable :y))))))
    )
  )

(deftest test-substitute-vals
  (testing "(x -> y) [y = 0] = (x -> 0)"
    (is (= (substitute-vals
             expr-constructors
             (logic-impl
               (variable :x)
               (variable :y))
             (hash-map
               :y 0))
           (logic-impl
             (variable :x)
             (constant 0))
           ))
    )
  )
