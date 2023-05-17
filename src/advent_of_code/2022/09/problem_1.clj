(ns advent-of-code.2022.09.problem-1)

(require '[clojure.string :as str])

(defn parse-raw [s]
  (->> s
       str/split-lines
       (map #(str/split % #" "))
       (map (fn [[dir steps]] [(keyword dir) (parse-long steps)]))
       (mapcat (fn [[dir steps]] (repeat steps dir)))))

(defn move-head-once [dir current-pos]
  (let [[head-x head-y] current-pos]
    (case dir
      :R [(inc head-x) head-y]
      :U [head-x (inc head-y)]
      :L [(dec head-x) head-y]
      :D [head-x (dec head-y)])))

(defn distances [head tail]
  (map - tail head))

(defn correction [num]
  (-> (cond
        (> num 1) (dec num)
        (< num -1) (inc num)
        :else 0)
      (* -1)))

(defn tail-correction [distances]
  (let [[x-dist y-dist] distances
        [abs-x-dist abs-y-dist] (map abs distances)]
    (cond
      (> abs-x-dist abs-y-dist) [(correction x-dist) (* y-dist -1)]
      (< abs-x-dist abs-y-dist) [(* x-dist -1) (correction y-dist)]
      :else (map correction distances))))

(defn trace-dirs [dirs]
  (loop [dirs dirs
         head [0 0]
         tail [0 0]
         trace {:heads [head] :tails [tail]}]
    (if-let [next-dir (first dirs)]
      (let [new-head (move-head-once next-dir head)
            new-tail (->> tail
                          (distances new-head)
                          tail-correction
                          (map + tail)
                          vec)
            new-trace (-> trace
                          (update :heads conj new-head)
                          (update :tails conj new-tail))]
        (recur (rest dirs)
               new-head
               new-tail
               new-trace))
      trace)))

(-> "./resources/advent_of_code/2022/day_09.txt"
    slurp
    parse-raw
    trace-dirs
    :tails
    distinct
    count)
