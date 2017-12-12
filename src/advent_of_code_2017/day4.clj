(ns advent-of-code-2017.day4
  (:require [clojure.java.io :as io]))

(def input
  (->> (io/resource "day4.txt")
       (io/file)
       (slurp)
       (clojure.string/split-lines)
       (map #(clojure.string/split % #" "))))

(defn secure?
  "Determines if a seq of strings are secure."
  [words]
  (let [words-set (into #{} words)]
    (= (count words) (count words-set))))

(defn word->chars
  "Chops up a string to chars and inserts them into a set."
  [word]
  (set (chars (char-array word))))

(defn extra-secure?
  "Determines if a seq of strings are extra super secure."
  [words]
  (let [words-chars-set (set (map word->chars words))]
    (and (secure? words)
         (= (count words-chars-set) (count words)))))

(defn solve-part-one
  "Solve part one!"
  [input]
  (count (filter secure? input)))

(defn solve-part-two
  "Solve part two!"
  [input]
  (count (filter extra-secure? input)))