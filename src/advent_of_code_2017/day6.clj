(ns advent-of-code-2017.day6)

(def input
  (let [string "0\t5\t10\t0\t11\t14\t13\t4\t11\t8\t8\t7\t1\t4\t12\t11"]
    (->> (clojure.string/split string #"\t")
         (map read-string)
         (map vector (range))
         (into (sorted-map)))))

(defn distribute-blocks [memory k]
  (let [v (get memory k)]
    (->> (keys memory)
         (cycle)
         (drop (inc k))
         (take v)
         (frequencies)
         (merge-with + (assoc memory k 0)))))

(defn next-key [memory]
  (key (apply max-key val (reverse memory))))

(defn solve-part-one [input]
  (loop [current-memory input
         past-memories #{}
         cycles 0]
    (if (contains? past-memories current-memory)
      cycles
      (let [next-memory (distribute-blocks current-memory (next-key current-memory))]
        (recur next-memory (conj past-memories current-memory) (inc cycles))))))

(defn solve-part-two [input]
  (loop [current-memory input
         past-memories #{}
         cycles 0
         illegal-memory nil]

    (if (= current-memory illegal-memory)
      cycles
      (let [next-memory (distribute-blocks current-memory (next-key current-memory))
            loop-detected? (and (nil? illegal-memory) (contains? past-memories current-memory))
            next-cycle (if loop-detected? 0 cycles)
            next-illegal-memory (if loop-detected? current-memory illegal-memory)]

        (recur next-memory
               (conj past-memories current-memory)
               (inc next-cycle)
               next-illegal-memory)))))
