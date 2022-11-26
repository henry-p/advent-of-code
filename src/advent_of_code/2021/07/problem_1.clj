(ns advent-of-code.2021.07.problem-1)

(def parsed
  (as-> (slurp "./resources/advent_of_code/2021/day_07.txt") x
        (clojure.string/split x #",")
        (map #(Integer/parseInt %) x)))

(def pos-range
  (range (apply min parsed) (+ (apply max parsed) 1)))

(defn abs-diffs [pos nums]
  (for [n nums]
    (abs (- n pos))))

(apply min (for [pos pos-range]
             (apply + (abs-diffs pos parsed))))
