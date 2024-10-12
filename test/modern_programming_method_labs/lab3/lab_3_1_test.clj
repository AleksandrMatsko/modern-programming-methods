(ns modern-programming-method-labs.lab3.lab-3-1-test
  (:require [clojure.test :refer :all]
            [modern-programming-method-labs.lab3.lab-3-1 :refer :all]))

(deftest thread-filter-is-same-as-filter
  (testing "with empty coll"
    (let [coll (list)]
      (is (= (filter even? coll) (thread-filter even? 5 coll)))
      )
    )
  (testing "with not empty coll"
    (let [coll (take 100 (range))]
      (is (= (filter even? coll) (thread-filter even? 5 coll)))
      )
    )
  (testing "with not empty coll and collection size is NOT divisible by num threads"
    (let [coll (take 103 (range))]
      (is (= (filter even? coll) (thread-filter even? 5 coll)))
      )
    )
  )
