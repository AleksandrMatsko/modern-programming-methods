(ns modern-programming-method-labs.lab4.to-str-test
  (:require [clojure.test :refer :all]
            [modern-programming-method-labs.lab4.operations :refer :all]))

(deftest test-constant-to-str
  (testing "0 and 1 to str"
    (is (= (constant-to-str {} (constant 1)) "1"))
    (is (= (constant-to-str {} (constant 0)) "0"))
    )
  )

(deftest test-variable-to-str
  (testing "x to str"
    (is (= (variable-to-str {} (variable :x)) "x"))
    )
  )

(deftest test-variable-to-str
  (testing "x to str"
    (is (= (variable-to-str {} (variable :x)) "x"))
    )
  )

(deftest test-expr-to-str
  (testing "x || 1 || y"
    (is (= (expr-to-str
             expr-to-string-rules
             (logic-or
               (variable :x)
               (constant 1)
               (variable :y)))
           "(x || 1 || y)"))
    )
  (testing "x && 1 && y"
    (is (= (expr-to-str
             expr-to-string-rules
             (logic-and
               (variable :x)
               (constant 1)
               (variable :y)))
           "(x && 1 && y)"))
    )
  (testing "z -> y"
    (is (= (expr-to-str
             expr-to-string-rules
             (logic-impl
               (variable :z)
               (variable :y)))
           "(z -> y)"))
    )
  (testing "!(a -> b)"
    (is (= (expr-to-str
             expr-to-string-rules
             (logic-not (logic-impl
                          (variable :a)
                          (variable :b))))
           "(!(a -> b))"))
    )
  )

