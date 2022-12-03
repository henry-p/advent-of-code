(ns advent-of-code.2022.03.problem-1)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_03.txt")
       str/split-lines))

(defn split-in-half [s]
  (->> s
       (partition-all (-> (count s) (/ 2)))
       (map #(apply str %))))

(defn find-item-in-both-comps [s]
  (let [[a b] (split-in-half s)]
    (->> b
         (filter #(.contains a (str %)))
         first)))

(defn char-range [start end]
  (map char (range (int start) (inc (int end)))))

(def prios
  (let [chars (-> (char-range \a \z)
                  (concat (char-range \A \Z)))
        chars-len (count chars)]
    (zipmap chars
            (->> (iterate inc 1)
                 (take chars-len)))))

(->> parsed
     (map #(prios (find-item-in-both-comps %)))
     (apply +))
