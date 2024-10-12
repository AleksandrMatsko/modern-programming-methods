(ns modern-programming-method-labs.lab3.core)

(defn heavy-even?
  [x]
  (Thread/sleep 10)
  (even? x))

(defn get-split-coll-f
  "Returns function that returns part of given coll. Valid parts are from 1 to num-threads. The 0 part is full coll"
  [num-threads coll]
  (let [coll-size (count coll)
        general-part-size (quot coll-size num-threads)
        rest-size (mod coll-size num-threads)
        parts (->>
                (iterate
                  (fn [[res-coll iterated-coll thread-idx]]
                    (if (< thread-idx rest-size)
                      [(take (inc general-part-size) iterated-coll)
                       (drop (inc general-part-size) iterated-coll)
                       (inc thread-idx)]
                      [(take general-part-size iterated-coll)
                       (drop general-part-size iterated-coll)
                       (inc thread-idx)]))
                  [coll coll 0])
                (map first))]
    (fn [thread-number]
      (nth parts thread-number))))

(defn split-coll
  [num-threads coll]
  (let [split-coll-f (get-split-coll-f num-threads coll)]
    (->>
      (range 1 (inc num-threads) 1)
      (map split-coll-f)
      )
    )
  )
