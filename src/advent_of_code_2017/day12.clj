(ns advent-of-code-2017.day12
  (:require [clojure.set :as set]
            [clojure.java.io :as io]))

(defn format-row [row]
  (let [[first & rest] (clojure.string/split row #" <-> |, ")]
    {first (set rest)}))

(def input (->> (io/resource "day12.txt")
                (slurp)
                (clojure.string/split-lines)
                (map format-row)
                (into {})))

(defn group-containing [pipes init-program]
  (loop [result #{}
         programs [init-program]]
    (if (empty? programs)
      result
      (let [[first & rest] programs]
        (recur (conj result first)
               (->> (get pipes first)
                    (filter #(not (contains? result %)))
                    (into rest)))))))

(defn groups [pipes]
  (reduce
    #(conj %1 (group-containing pipes %2))
    #{}
    (keys pipes)))

(defn solve-part-one [input]
  (->> (groups input)
       (filter #(contains? % "0"))
       (first)
       (vals)
       (count)))

(defn solve-part-two [input]
  (-> (groups input)
      (count)))