(ns advent-of-code-2017.day2
  (:require [clojure.math.combinatorics :as combo]
            [clojure.java.io :as io]))

(def input
  "Read and parse input lines of strings."
  (->> (io/resource "day2.txt")
       (io/file)
       (slurp)))

(defn parse-row [row]
  (->> (clojure.string/split row #"\t")
       (map read-string)))

(defn part-1-algorithm [nums] (- (apply max nums) (apply min nums)))

(defn part-2-algorithm [nums]
  (let [tuples (apply concat (map combo/permutations (combo/combinations nums 2)))]
    (reduce
      (fn [acc curr]
        (if (= 0 (mod (first curr) (second curr)))
          (reduced (/ (first curr) (second curr)))))
      0
      tuples)))

(defn solver [algorithm]
  (fn [input]
    (->> (clojure.string/split-lines input)
         (map parse-row)
         (map algorithm)
         (apply +))))

(def solver-part-1 (solver part-1-algorithm))               ; solves part 1
(def solver-part-2 (solver part-2-algorithm))               ; solves part 2