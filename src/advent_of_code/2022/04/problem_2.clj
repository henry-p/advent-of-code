(ns advent-of-code.2022.04.problem-2)

(require '[clojure.string :as str])
(require '[clojure.set :refer [intersection]])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_04.txt")
       str/split-lines
       (mapcat #(str/split % #","))
       (mapcat #(str/split % #"-"))
       (map #(Integer/parseInt %))
       (partition 4)))

(defn intersects? [row]
  (let [[a b c d] row
        fr (set (range a (inc b)))
        lr (set (range c (inc d)))]
    (some? (seq (intersection fr lr)))))

(count (filter intersects? parsed))
