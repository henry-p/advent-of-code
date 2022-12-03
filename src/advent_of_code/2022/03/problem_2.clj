(ns advent-of-code.2022.03.problem-2)

(require '[clojure.string :as str])
(require '[clojure.set :refer [intersection]])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_03.txt")
       str/split-lines
       (partition-all 3)))

(defn char-range [start end]
  (map char (range (int start) (inc (int end)))))

(def prios
  (let [chars (-> (char-range \a \z)
                  (concat (char-range \A \Z)))]
    (zipmap chars
            (->> (iterate inc 1)
                 (take (count chars))))))

(defn find-common [group]
  (->> group
       (map set)
       (apply intersection)
       first))

(->> parsed
     (map #(prios (find-common %)))
     (apply +))
