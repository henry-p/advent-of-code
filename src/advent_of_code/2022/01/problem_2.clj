(ns advent-of-code.2022.01.problem-2)

(require '[clojure.string :as str])

(->> (slurp "./resources/advent_of_code/2022/day_01.txt")
     str/split-lines
     (map #(when-not (str/blank? %) (Integer/parseInt %)))
     (partition-by nil?)
     (remove #(-> % first nil?))
     (map #(apply + %))
     sort
     (take-last 3)
     (apply +))
