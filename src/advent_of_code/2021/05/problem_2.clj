(ns advent-of-code.2021.05.problem-2)

(defn diagonal? [line]
  (let [{x1 :x1 y1 :y1 x2 :x2 y2 :y2} line]
    (= (abs (- x1 x2)) (abs (- y1 y2)))))

(defn filter-lines [lines]
  (filter
    (fn [line]
      (or (= (line :x1) (line :x2))
          (= (line :y1) (line :y2))
          (diagonal? line)))
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
      (and (seq x-range) (empty? y-range)) (map vector x-range (cycle [(line :y1)]))
      (and (seq y-range) (empty? x-range)) (map vector (cycle [(line :x1)]) y-range)
      (and (seq x-range) (seq y-range)) (map vector x-range y-range))))

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
