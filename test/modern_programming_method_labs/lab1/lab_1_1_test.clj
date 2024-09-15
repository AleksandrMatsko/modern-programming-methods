(ns modern-programming-method-labs.lab1.lab_1_1_test
  (:require [clojure.test :refer :all]
            [modern-programming-method-labs.lab1.lab-1-1 :refer :all]
            [modern-programming-method-labs.lab1.core-test :refer :all]))

(deftest negative-n-test
  (testing "Test gen-permutations with n < 0"
    (is (empty? (gen-permutations alphabet -1)))))

(deftest zero-n-test
  (testing "Test gen-permutations with n = 0"
    (is (empty? (gen-permutations alphabet 0)))))

(deftest empty-alphabet-test
  (testing "Test gen-permutations with n > 0 and empty alphabet"
    (is (empty? (gen-permutations (list) 2)))))

(deftest one-symbol-alphabet-test
  (testing "Test gen-permutations with alphabet of 1 symbol"
    (is (empty? (gen-permutations one-sym-alphabet 2)))
    (is (contains-all-expected (gen-permutations one-sym-alphabet 1) one-sym-alphabet))))

(deftest words-of-1-test
  (testing "Test gen-permutations with n = 1 and normal alphabet"
    (is (contains-all-expected (gen-permutations alphabet 1) alphabet))))

(deftest words-of-2-test
  (testing "Test gen-permutations with n = 2 and normal alphabet"
    (is (contains-all-expected (gen-permutations alphabet 2) expected-words-with-len-2))))

(deftest words-of-3-test
  (testing "Test gen-permutations with n = 3 and normal alphabet"
    (is (contains-all-expected (gen-permutations alphabet 3) expected-words-with-len-3))))
