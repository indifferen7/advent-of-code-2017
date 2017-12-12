(ns advent-of-code-2017.day7
  (:require [clojure.java.io :as io]))

(defn parse-row [row]
  (-> (clojure.string/replace row #" " "")
      (clojure.string/replace #"\(" "," )
      (clojure.string/replace #"\)->|\)" "," )
      (clojure.string/split #",")))

(def input
  (->> (io/resource "day7.txt")
       (slurp)
       (clojure.string/split-lines)
       (map parse-row)))

(defn solve-part-one
  [input]
  (let [nodes (map first input)
        children (flatten (map #(drop 2 %) input))]
    (-> (set nodes)
        (clojure.set/difference (set children))
        (first))))