(ns modern-programming-method-labs.lab1.lab-1-3-test
  (:require [clojure.test :refer :all]
            [modern-programming-method-labs.lab1.lab-1-3 :refer :all]))

(defn maps-equal
  [f
   coll]
  (= (my-map-list f coll) (map f coll)))

(deftest my-map-with-inc-test
  (testing "my-map and map res is equal with same function and collection"
    (is (maps-equal inc (list 1 2 3 4 5)))
    (is (maps-equal dec (list 1 2 3 4 5)))))

