(ns advent-of-code.2022.08.problem-1)

(require '[clojure.string :as str])

(def rows
  (->> (slurp "./resources/advent_of_code/2022/day_08.txt")
       str/split-lines
       (map (fn [line] (map (fn [char] (parse-long (str char))) line)))))

(defn transpose [matrix]
  (apply mapv vector matrix))

(def cols
  (transpose rows))

(defn tallest? [pos coll]
  (let [[left [el & right]] (split-at pos coll)]
    (or (every? #(> el %) left)
        (every? #(> el %) right))))

(->>
  (for [row-pos (range (count cols))]
    (for [col-pos (range (count rows))]
      (let [row (nth rows col-pos)
            col (nth cols row-pos)]
        (or (tallest? row-pos row)
            (tallest? col-pos col)))))
  flatten
  (filter true?)
  count)
