(ns advent-of-code.2022.14.problem-2)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_14.txt")
       str/split-lines))



