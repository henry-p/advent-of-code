(ns advent-of-code.2021.03.problem-1)

(require '[clojure.string :as str])

(def input (slurp "./resources/advent_of_code/2021/day_03.txt"))

(defn string-to-number-vec [s]
  (map #(Integer/parseInt %) (map str (seq s))))

(defn transpose [m]
  (apply mapv vector m))

(def freqs
  (->> input
       str/split-lines
       (map string-to-number-vec)
       transpose
       (map frequencies)))

(let [key-through-fn (fn [f] (Integer/parseInt (apply str (map #(key (apply f val %)) freqs)) 2))
      gamma-rate (key-through-fn max-key)
      epsilon-rate (key-through-fn min-key)]
  (* gamma-rate epsilon-rate))
