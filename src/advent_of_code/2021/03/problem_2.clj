(ns advent-of-code.2021.03.problem-2)

(require '[clojure.string :as str])

(def input (slurp "./resources/advent_of_code/2021/day_03.txt"))

(defn string-to-number-vec [s]
  (map #(Integer/parseInt %) (map str (seq s))))

(defn transpose [vv]
  (apply mapv vector vv))

(defn most-common-bit [v]
  (let [freqs (frequencies v)
        zero-count (get freqs 0)
        one-count (get freqs 1)]
    (cond
      (> zero-count one-count) 0
      (< zero-count one-count) 1
      :else 1)))

(defn least-common-bit [v]
  ({0 1 1 0} (most-common-bit v)))

(defn binary-to-decimal [v]
  (let [vec-as-str (apply str v)]
    (Integer/parseInt vec-as-str 2)))

(def parsed
  (->> input
       str/split-lines
       (map string-to-number-vec)))

(defn find-needle [haystack f]
  (loop [rows haystack
         columns (transpose haystack)
         n 0]
    (let [polarizing-bit (f (first columns))
          matching-rows (filter #(= (nth % n) polarizing-bit) rows)
          next-n (inc n)]
      (if (or (empty? columns) (<= (count matching-rows) 1))
        (first matching-rows)
        (recur matching-rows
               (subvec (transpose matching-rows) next-n)
               next-n)))))

(apply * (map binary-to-decimal
              [(find-needle parsed most-common-bit)
               (find-needle parsed least-common-bit)]))
