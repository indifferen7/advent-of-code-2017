(ns advent-of-code-2017.day8
  (:require [clojure.java.io :as io]))

(def input (->> (io/resource "day8.txt")
                (slurp)
                (clojure.string/split-lines)))

(defn modify [registers parts]
  (let [op (if (= "inc" (second parts)) + -)]
    (->> (nth parts 2)
         (read-string)
         (op (get registers (nth parts 0) 0)))))

(defn str->op [str]
  "Translates the string to an operator."
  (-> (clojure.string/replace str #"==" "=")
      (clojure.string/replace #"!=" "not=")
      (symbol)
      (resolve)))

(defn should-update [registers parts]
  (let [key (nth parts 4)
        val (read-string (nth parts 6))
        op (str->op (nth parts 5))]
    (op (get registers key 0) val)))

(defn do-update [registers parts]
  (let [register (nth parts 0)]
    (assoc registers register (modify registers parts))))

(defn update-register [registers line]
  (let [parts (clojure.string/split line #" ")]
    (if (should-update registers parts)
      (do-update registers parts)
      registers)))

(defn update-registers [input]
  (reduce
    (fn [acc curr]
      (let [registers (update-register (:registers acc) curr)]
        {:registers registers
         :max (max (:max acc) (apply max (vals registers)))}))
    {:registers {} :max 0}
    input))

(defn solve-part-one [input]
  (->> (update-registers input)
       (:registers)
       (apply max-key val)
       (val)))

(defn solve-part-two [input]
  (->> (update-registers input)
       (:max)))