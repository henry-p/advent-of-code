(ns advent-of-code.2022.20.problem-1)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_20.txt")
       str/split-lines))



