(ns advent-of-code.2021.02.problem-2)

(require '[clojure.string :as str])

(def input (slurp "./resources/advent_of_code/2021/day_02.txt"))

(def parsed
  (->> input
       str/split-lines
       (map #(str/split % #" "))
       (map #(vector (first %) (Integer/parseInt (second %))))))

(defn calc-vals [params command value]
  (let [[aim depth horizontal] (vals params)]
    (case command
      "down" {:aim (+ aim value)}
      "up" {:aim (- aim value)}
      "forward" {:depth      (+ depth (* aim value))
                 :horizontal (+ horizontal value)})))

(loop [l parsed
       params {:aim 0 :depth 0 :horizontal 0}]
  (let [[command value] (first l)]
    (if (seq l)
      (recur (rest l)
             (merge params (calc-vals params command value)))
      (* (params :horizontal) (params :depth)))))
