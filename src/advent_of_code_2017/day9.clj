(ns advent-of-code-2017.day9
  (:require [clojure.java.io :as io]))

(def input
  (-> (io/resource "day9.txt")
      (slurp)))

(defn garbage-free [str]
  (reduce
    (fn [acc c]
      (let [{:keys [skip-next
                    in-garbage
                    score
                    level
                    garbage-count]} acc]
        (if (and in-garbage skip-next)
          (assoc acc :skip-next false)
          (if in-garbage
            (case c
              \! (assoc acc :skip-next true)
              \> (assoc acc :in-garbage false)
              (assoc acc :garbage-count (inc garbage-count)))
            (case c
              \< (assoc acc :in-garbage true)
              \} (assoc acc :level (dec level) :score (+ score level))
              \{ (assoc acc :level (inc level))
              acc))
          )))
    {:skip-next false
     :in-garbage false
     :level 0
     :garbage-count 0
     :score 0}
    str))

(defn solve-part-one [input]
  (-> (garbage-free input)
      (:score)))

(defn solve-part-two [input]
  (-> (garbage-free input)
      (:garbage-count)))