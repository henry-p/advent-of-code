(ns advent-of-code.2021.05.problem-1)

(defn filter-lines [lines]
  (filter
    (fn [line]
      (or (= (line :x1) (line :x2))
          (= (line :y1) (line :y2))))
    lines))

(defn line-range [n1 n2]
  (cond
    (< n1 n2) (range n1 (inc n2) 1)
    (= n1 n2) '()
    :else (range n1 (dec n2) -1)))

(defn touched-points [line]
  (let [x-range (line-range (line :x1) (line :x2))
        y-range (line-range (line :y1) (line :y2))]
    (cond
      (seq x-range) (map vector x-range (cycle [(line :y1)]))
      (seq y-range) (map vector (cycle [(line :x1)]) y-range))))

(->> (slurp "./resources/advent_of_code/2021/day_05.txt")
     clojure.string/split-lines
     (mapcat #(clojure.string/split % #" -> "))
     (mapcat #(clojure.string/split % #","))
     (map #(Integer/parseInt %))
     (partition 4)
     (map #(zipmap [:x1 :y1 :x2 :y2] %))
     filter-lines
     (mapcat touched-points)
     frequencies
     (filter #(>= (second %) 2))
     count)
