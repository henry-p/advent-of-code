(ns advent-of-code.2022.08.problem-2)

(require '[clojure.string :as str])

(def rows
  (->> (slurp "./resources/advent_of_code/2022/day_08.txt")
       str/split-lines
       (map (fn [line] (map (fn [char] (parse-long (str char))) line)))))

(defn transpose [matrix]
  (apply mapv vector matrix))

(def cols
  (transpose rows))

(defn n-viewable [pos coll]
  (let [[left [el & right]] (split-at pos coll)
        how-many (fn [el coll]
                   (let [n-trees (count (take-while #(> el %) coll))]
                     (if (= n-trees (count coll)) n-trees (inc n-trees))))]
    [(how-many el (reverse left))
     (how-many el right)]))

(->>
  (for [row-pos (range (count cols))]
    (for [col-pos (range (count rows))]
      (let [row (nth rows col-pos)
            col (nth cols row-pos)]
        (into (n-viewable row-pos row)
              (n-viewable col-pos col)))))
  (apply concat)
  (map #(apply * %))
  (apply max))
