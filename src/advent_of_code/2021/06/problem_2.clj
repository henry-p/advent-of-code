(ns advent-of-code.2021.06.problem-2)

(def parsed
  (as-> (slurp "./resources/advent_of_code/2021/day_06.txt") x
        (clojure.string/split x #",")
        (map #(Integer/parseInt %) x)))

(def initial-state
  (merge (into (sorted-map) (zipmap (range 0 9) (cycle [0])))
         (frequencies parsed)))

(defn grow-fish [fish-freqs]
  (let [nums (vals fish-freqs)
        first-num (first nums)
        rotated (zipmap (range 0 9) (concat (rest nums) [first-num]))]
    (into (sorted-map) (update-in rotated [6] #(+ % first-num)))))

(apply + (vals (nth (iterate grow-fish initial-state) 256)))
