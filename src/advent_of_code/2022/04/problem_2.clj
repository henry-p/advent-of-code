(ns advent-of-code.2022.04.problem-2)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_04.txt")
       str/split-lines))



