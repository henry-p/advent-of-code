(ns advent-of-code.2021.21.problem-1)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2021/day_21.txt")
       str/split-lines))



