(ns advent-of-code.2021.07.problem-2)

(def parsed
  (as-> (slurp "./resources/advent_of_code/2021/day_07.txt") x
        (clojure.string/split x #",")
        (map #(Integer/parseInt %) x)))

(def pos-range
  (range (apply min parsed) (+ (apply max parsed) 1)))

(defn gauss-sum [n]
  (-> (* n n) (+ n) (/ 2)))

(defn fuel-cost [pos nums]
  (map #(gauss-sum (abs (- % pos))) nums))

(apply min (map #(apply + (fuel-cost % parsed)) pos-range))
