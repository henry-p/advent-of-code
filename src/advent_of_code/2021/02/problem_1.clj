(ns advent-of-code.2021.02.problem-1)

(require '[clojure.string :as str])

(def input (slurp "./resources/advent_of_code/2021/day_02.txt"))

(def grouped (->> input
                  str/split-lines
                  (map #(str/split % #" "))
                  (group-by first)))

(defn get-second-parse-int [l] (Integer/parseInt (second l)))

(def result
  (zipmap (keys grouped)
          (map (fn [l] (apply + (map get-second-parse-int l)))
               (vals grouped))))

(* (result "forward") (- (result "down") (result "up")))
