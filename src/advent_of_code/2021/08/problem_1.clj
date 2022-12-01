(ns advent-of-code.2021.08.problem-1)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2021/day_08.txt")
       str/split-lines
       (map #(str/split % #" \| "))
       (map #(hash-map :input (first %) :output (second %)))))

(def outs
  (map #(% :output) parsed))

(defn output-lengths [output]
  (map count (str/split output #" ")))

(defn count-easy-digits [output]
  (let [easy-digits {2 1
                     4 4
                     3 7
                     7 8}]
    (->> output
         output-lengths
         (map #(easy-digits %))
         (filter some?)
         count)))

(apply + (map count-easy-digits outs))
