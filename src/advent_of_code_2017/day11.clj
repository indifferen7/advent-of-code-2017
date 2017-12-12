(ns advent-of-code-2017.day11
  (:require [clojure.java.io :as io]))

(def input (-> (io/resource "day11.txt")
               (slurp)
               (clojure.string/split #",")))

(defn dxyz [d]
  (case d
    "n"  [0 1 -1]
    "nw" [-1 1 0]
    "ne" [1 0 -1]
    "s"  [0 -1 1]
    "sw" [-1 0 1]
    "se" [1 -1 0]))

(defn move [xyz d]
  (let [[x y z] xyz
        [dx dy dz] (dxyz d)]
    [(+ x dx) (+ y dy) (+ z dz)]))

(def start [0 0 0])
(defn end [input] (reduce move start input))

(defn distance
  [[start-x start-y start-z] [end-x end-y end-z]]
  (-> (+ (Math/abs (long (- start-x end-x)))
         (Math/abs (long (- start-y end-y)))
         (Math/abs (long (- start-z end-z))))
      (/ 2)))

(defn solve-part-one [input]
  (distance start (end input)))

(defn solve-part-two [input]
  (->> input
       (reduce
         #(conj %1 (move (last %1) %2))
         [start])
       (reduce
         #(max %1 (distance start %2))
         0)))