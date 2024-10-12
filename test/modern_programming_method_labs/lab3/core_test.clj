(ns modern-programming-method-labs.lab3.core-test
  (:require [clojure.test :refer :all]
            [modern-programming-method-labs.lab3.core :refer :all]
            [modern-programming-method-labs.lab3.lab-3-1 :refer :all]))

(deftest get-split-coll-f-test
  (testing "collection is divided correctly by returned func when collection size is divisible by num-threads"
    (let [coll (take 100 (range))
          coll-part-f (get-split-coll-f 5 coll)]
      (is (= (coll-part-f 1) (range 0 20 1)))
      (is (= (coll-part-f 2) (range 20 40 1)))
      (is (= (coll-part-f 3) (range 40 60 1)))
      (is (= (coll-part-f 4) (range 60 80 1)))
      (is (= (coll-part-f 5) (range 80 100 1)))
      ))
  (testing "collection is divided correctly by returned func when collection size is NOT divisible by num-threads"
    (let [coll (take 103 (range))
          coll-part-f (get-split-coll-f 5 coll)]
      (is (= (coll-part-f 1) (range 0 21 1)))
      (is (= (coll-part-f 2) (range 21 42 1)))
      (is (= (coll-part-f 3) (range 42 63 1)))
      (is (= (coll-part-f 4) (range 63 83 1)))
      (is (= (coll-part-f 5) (range 83 103 1)))
      ))
  )

(deftest split-coll-test
  (testing "collection is divided correctly when collection size is divisible by num-threads"
    (is (=
          (split-coll 5 (take 100 (range)))
          (list
            (range 0 20 1)
            (range 20 40 1)
            (range 40 60 1)
            (range 60 80 1)
            (range 80 100 1)
            )))
    )
  (testing "collection is divided correctly when collection size is NOT divisible by num-threads"
    (is (=
          (split-coll 5 (take 103 (range)))
          (list
            (range 0 21 1)
            (range 21 42 1)
            (range 42 63 1)
            (range 63 83 1)
            (range 83 103 1)
            )))
    )
  )

(deftest filters-benchmark
  (let [coll (take 100 (range))]
    (println "Simple filter")
    (time (->>
            (filter heavy-even? coll)
            (doall)))
    (println "Thread filter")
    (let [threads-counts (range 1 11 1)]
      (->>
        (map
          (fn [thread-count]
            (println "with" thread-count "threads")
            (time (->>
                    (thread-filter heavy-even? thread-count coll)
                    (doall)))
            )
          threads-counts)
        (doall)
        ))
    )
  )
