(ns advent-of-code.2021.24.problem-1)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2021/day_24.txt")
       str/split-lines))



