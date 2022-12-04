(ns advent-of-code.2022.04.problem-1)

(require '[clojure.string :as str])
(require '[clojure.set :refer [superset?]])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_04.txt")
       str/split-lines
       (mapcat #(str/split % #","))
       (mapcat #(str/split % #"-"))
       (map #(Integer/parseInt %))
       (partition 4)))

(defn fully-contained? [row]
  (let [[a b c d] row
        fr (set (range a (inc b)))
        lr (set (range c (inc d)))]
    (or (superset? fr lr)
        (superset? lr fr))))

(count (filter fully-contained? parsed))