(ns advent-of-code-2017.day14
  (:require [advent-of-code-2017.day10 :as day10]))

(defn input-fixer [input]
  (-> (map int (map char input))
      (concat [17, 31, 73, 47, 23])))

(defn knot-hash-of [input]
  (let [iterator (iterate (partial day10/algorithm input) day10/init-acc)
        prepend-zero #(if (= 1 (count %)) (str "0" %) %)]
    (->> (nth iterator 64)
         (:list)
         (partition 16)
         (map #(apply bit-xor %))
         (map #(format "%02x" %))
         (map prepend-zero)
         (apply str))))

(defn hex->binary [hex]
  (map #(->> (Character/digit % 16)
             (Integer/toBinaryString)
             (Integer/parseInt)
             (format "%04d")) hex))

(defn parse-row [input n]
  (->> (str input "-" n)
       (input-fixer)
       (knot-hash-of)
       (hex->binary)
       (clojure.string/join)))

(defn parse-rows [input]
  (reduce
   (fn [acc curr]
     (-> (parse-row input curr)
         (frequencies)
         (get \1)
         (+ acc)))
   0
   (range 0 128)))

(defn solve-part-one [input] (parse-rows input))
;(solve-part-one "ljoxqyyw")

