(ns advent-of-code.2021.10.problem-2)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2021/day_10.txt")
       str/split-lines))



