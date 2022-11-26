(ns advent-of-code.2022.17.problem-2)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_17.txt")
       str/split-lines))



