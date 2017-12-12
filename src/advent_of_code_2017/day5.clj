(ns advent-of-code-2017.day5
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def init
  (->> (io/resource "day5.txt")
       (slurp)
       (str/split-lines)
       (map #(Integer/parseInt %))
       (map vector (range))
       (into {})))

(defn part-one-algoritm [val] (inc val))
(defn part-two-algoritm [val] (if (< val 3) (inc val) (dec val)))

(defn solver [algorithm]
  (loop [instructions init
         curr 0
         steps 0]
    (if-let [val (get instructions curr)]
      (recur (assoc instructions curr (algorithm val))
             (+ curr val)
             (inc steps))
      steps)))

(defn solve-part-one [] (solver part-one-algoritm))
(defn solve-part-two [] (solver part-two-algoritm))