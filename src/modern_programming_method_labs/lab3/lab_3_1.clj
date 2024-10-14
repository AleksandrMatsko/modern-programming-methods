(ns modern-programming-method-labs.lab3.lab-3-1
  (:require [modern-programming-method-labs.lab3.core :refer :all]))

(defn thread-filter
  [cond? num-threads coll]
  (->>
    (split-coll num-threads coll)
    (map (fn [coll-part]
           (future (->>
                     (filter cond? coll-part)
                     (doall)))))
    (mapcat deref)))

(defn -main
  [& args]
  (->>
    (thread-filter odd? 5 (range 0 100 1))
    (println))
  (shutdown-agents)
  )
