(ns advent-of-code.2022.13.problem-1)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_13.txt")
       str/split-lines))



