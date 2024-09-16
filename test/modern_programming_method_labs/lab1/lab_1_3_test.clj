(ns modern-programming-method-labs.lab1.lab-1-3-test
  (:require [clojure.test :refer :all]
            [modern-programming-method-labs.lab1.lab-1-3 :refer :all]))

(defn maps-equal
  "Calls my-map-list and map with f and coll and compares results"
  [f
   coll]
  (= (my-map-list f coll) (map f coll)))

(defn filters-equal
  "Calls my-filter-list and filter with pred and coll and compares results"
  [pred
   coll]
  (= (my-filter-list pred coll) (filter pred coll)))

(deftest my-map-list-equals-map-test
  (testing "my-map-list and map res is equal with same function and collection"
    (is (maps-equal inc (list 1 2 3 4 5)))
    (is (maps-equal dec (list 1 2 3 4 5)))))

(deftest my-filter-list-equals-filter-test
  (testing "my-filter-list and filter res is equal with same predicate and collection"
    (is (filters-equal even? (list 1 2 3 4 5 6)))))
