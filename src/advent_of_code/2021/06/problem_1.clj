(ns advent-of-code.2021.06.problem-1)

(def parsed
  (as-> (slurp "./resources/advent_of_code/2021/day_06.txt") x
        (clojure.string/split x #",")
        (map #(Integer/parseInt %) x)))

(loop [fishs parsed
       days-left 80]
  (if (= days-left 0)
    (count fishs)
    (recur (flatten
             (for [fish fishs]
               (let [new-fish (dec fish)]
                 (if (< new-fish 0)
                   [6 8]
                   [new-fish]))))
           (dec days-left))))
