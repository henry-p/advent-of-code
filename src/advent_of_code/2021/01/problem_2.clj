(ns advent-of-code.2021.01.problem-2)

(require '[clojure.string :as str])

(def depths
  (->> (slurp "./resources/advent_of_code/2021/day_01.txt")
       str/split-lines
       (map #(Integer/parseInt %))))

(->> depths
     (partition 3 1)
     (map #(apply + %))
     (partition 2 1)
     (filter (fn [[a b]] (> b a)))
     count)
