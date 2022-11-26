(ns advent-of-code.2021.12.problem-1)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2021/day_12.txt")
       str/split-lines))



