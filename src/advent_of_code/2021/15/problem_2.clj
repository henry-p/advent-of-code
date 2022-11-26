(ns advent-of-code.2021.15.problem-2)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2021/day_15.txt")
       str/split-lines))



