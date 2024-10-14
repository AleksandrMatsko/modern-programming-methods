(ns modern-programming-method-labs.lab3.lab-3-2-test
  (:require [clojure.test :refer :all]
            [modern-programming-method-labs.lab3.lab-3-2 :refer :all]))

(deftest lazy-thread-filter-is-same-as-filter
  (testing "with empty coll"
    (let [coll (list)]
      (is (= (filter even? coll) (lazy-thread-filter even? 5 17 coll)))
      )
    )
  (testing "with not empty coll"
    (let [coll (take 100 (range))]
      (is (= (filter even? coll) (lazy-thread-filter even? 5 17 coll)))
      )
    )
  (testing "with not empty coll and collection size is NOT divisible by num threads"
    (let [coll (take 103 (range))]
      (is (= (filter even? coll) (lazy-thread-filter even? 5 17 coll)))
      )
    )
  )

(deftest test-lazy-thread-filter-has-no-stack-overflow
  (doall (lazy-thread-filter even? 1 1 (take 1000000 (range)))))
