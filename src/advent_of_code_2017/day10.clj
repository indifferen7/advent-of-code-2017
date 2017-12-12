(ns advent-of-code-2017.day10)

(def input "187,254,0,81,169,219,1,190,19,102,255,56,46,32,2,216")

(def part-one-input
  (->> (clojure.string/split input #",")
       (map read-string)))

(def part-two-input
  (-> (map int (map char input))
      (concat [17, 31, 73, 47, 23])))

(defn rotate [list n]
  "Rotates the list n steps, cycling it if needed."
  (take (count list) (drop n (cycle list))))

(defn rotate-back [list n]
  "Rotates the list n steps in reverse order."
  (-> (reverse list)
      (rotate n)
      (reverse)))

(defn perform-reverse-step [list length pos]
  (let [rotated (rotate list pos)]
    (-> (take length rotated)
        (reverse)
        (concat (drop length rotated))
        (rotate-back pos))))

; the initial accumulator
(def init-acc {:skip-size 0 :pos 0 :list (range 0 256)})

(defn algorithm [input init-acc]
  "Da algorithm."
  (reduce
    (fn [acc length]
      (let [{:keys [list pos skip-size]} acc
            next-list (perform-reverse-step list length pos)
            next-pos (mod (+ pos length skip-size) (count next-list))]
        (assoc acc :list next-list
                   :pos next-pos
                   :skip-size (inc skip-size))))
    init-acc
    input))

(defn solve-part-one []
  (->> (algorithm part-one-input init-acc)
       (:list)
       (take 2)
       (apply *)))

(defn solve-part-two []
  (let [iterator (iterate (partial algorithm part-two-input) init-acc)
        prepend-zero #(if (= 1 (count %)) (str "0" %) %)]
    (->> (nth iterator 64)
         (:list)
         (partition 16)
         (map #(apply bit-xor %))
         (map #(format "%02x" %))
         (map prepend-zero)
         (apply str))))