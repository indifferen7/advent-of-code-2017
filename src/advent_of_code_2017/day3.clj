(ns advent-of-code-2017.day3)

(def input 265149)

(defn manhattan-distance
  "Calculates the manhattan distance between two points."
  [[start-x start-y] [end-x end-y]]
  (+ (Math/abs (long (- start-x end-x)))
     (Math/abs (long (- start-y end-y)))))

(defn next-xy-dxy
  "Returns the next point and delta in a spiral motion."
  [xy dxy]
  (let [[x y] xy
        [dx dy] dxy]
    (if (or (= x y)
            (and (< x 0) (= x (- y)) )
            (and (> x 0) (= x (- 1 y))))
      (conj [] [(+ x (- dy)) (+ y dx)] [(- dy) dx])
      (conj [] [(+ x dx) (+ y dy)] dxy))))

(defn solve-part-one [limit]
  "This solves part one."
  (loop [step 1
         xy [0 0]
         dxy [0 -1]]
    (if (= step limit)
      (manhattan-distance [0 0] xy)
      (let [[next-xy next-dxy] (next-xy-dxy xy dxy)]
        (recur (inc step) next-xy next-dxy)))))

(def deltas #{[1 1] [1 0] [1 -1] [0 1] [0 -1] [-1 -1] [-1 0] [-1 1]})

(defn neighbours
  "Returns all neighbouring points to xy."
  [xy]
  (let [[x y] xy]
    (map
      (fn [dxy]
        (let [[dx dy] dxy]
          [(+ x dx) (+ y dy)]))
      deltas)))

(defn sum-neighbours [xy values]
  "Sums all neighbouring points to xy present in values."
  (if (empty? values)
    1
    (->> (map #(get values % 0) (neighbours xy))
         (apply +))))

(defn solve-part-two [limit]
  "This solves part two."
  (loop [values {}
         xy [0 0]
         dxy [0 -1]]
    (let [new-values (assoc values xy (sum-neighbours xy values))
          [next-xy next-dxy] (next-xy-dxy xy dxy)]
      (if (> (get new-values xy) limit)
        (get new-values xy)
        (recur new-values next-xy next-dxy)))))