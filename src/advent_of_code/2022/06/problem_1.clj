(ns advent-of-code.2022.06.problem-1)

(defn find-marker [l payload]
  (->> payload
       (partition l 1)
       (map-indexed vector)
       (filter #(= (count (set (second %))) l))
       ffirst
       (+ l)))

(find-marker 4 (slurp "./resources/advent_of_code/2022/day_06.txt"))
