(ns advent-of-code.2021.08.problem-2)

(require '[clojure.string :as str])
(require '[clojure.set :refer [intersection rename-keys]])

(def parsed
  (->> (slurp "./resources/advent_of_code/2021/day_08.txt")
       str/split-lines
       (map #(str/split % #" \| "))
       (map #(hash-map :input (first %) :output (second %)))))

(defn tokenize [s]
  (map #(hash-map :token (set %)) (str/split s #" ")))

(defn easy-num [token-length]
  (let [mapping {2 1, 4 4, 3 7, 7 8}]
    (mapping token-length)))

(defn assoc-easy-num [m]
  (if-some [num (easy-num (m :length))]
    (assoc m :num num)
    m))

(def number-mapping
  {0 #{:top-left :top :top-right :bottom-right :bottom :bottom-left}
   1 #{:top-right :bottom-right}
   2 #{:top :top-right :middle :bottom-left :bottom}
   3 #{:top :top-right :middle :bottom-right :bottom}
   4 #{:top-left :top-right :middle :bottom-right}
   5 #{:top-left :top :middle :bottom-right :bottom}
   6 #{:top-left :top :middle :bottom-right :bottom :bottom-left}
   7 #{:top :top-right :bottom-right}
   8 #{:top-left :top :top-right :middle :bottom-right :bottom :bottom-left}
   9 #{:top-left :top :top-right :middle :bottom-right :bottom}})

(defn n-matching-segments [m mapping]
  (let [[outer-num outer-dirs] m]
    (for [matched-m mapping
          :let [[inner-num inner-dirs] matched-m]
          :when (not= outer-num inner-num)]
      {:num                    outer-num
       :n-num-segments         (count outer-dirs)
       :n-matched-num-segments (count inner-dirs)
       :matched-num            inner-num
       :n-matching-segments    (count (intersection outer-dirs inner-dirs))})))

(def puzzle-lookup
  (->> number-mapping
       (mapcat #(n-matching-segments % number-mapping))
       (group-by (juxt :num :n-matching-segments :n-matched-num-segments))
       (filter #(= (count (second %)) 1))
       (mapcat second)
       (sort-by (juxt :num :n-matching-segments :n-matched-num-segments :matched-num))))

(defn solve-unknown [unknown
                     knowns
                     lookup]
  (-> (->> (for [known knowns]
             (filter
               #(= ((juxt :num
                          :n-matching-segments
                          :n-matched-num-segments) %)
                   [(known :num)
                    (count (intersection (known :token) (unknown :token)))
                    (unknown :length)])
               lookup))
           (apply concat)
           first)
      (select-keys [:matched-num])
      (assoc :solved-token (unknown :token))))

(defn solve-trivials [input]
  (rename-keys
    (->> input
         tokenize
         (map #(assoc % :length (count (% :token))))
         (map assoc-easy-num)
         (group-by #(some? (% :num))))
    {true  :known
     false :unknown}))

(defn solve-input [input]
  (let [trivials (solve-trivials input)]
    (loop [unknowns (trivials :unknown)
           knowns (trivials :known)]
      (let [solutions (for [unknown unknowns
                            :let [solution (solve-unknown unknown knowns puzzle-lookup)]
                            :when (some? (solution :matched-num))] solution)
            solved-tokens (map :solved-token solutions)
            left-unknowns (remove #(.contains solved-tokens (% :token)) unknowns)
            new-knowns (concat knowns
                               (map #(hash-map :num (% :matched-num)
                                               :token (% :solved-token)
                                               :length (count (% :solved-token))) solutions))]
        (if (empty? unknowns)
          new-knowns
          (recur left-unknowns
                 new-knowns))))))

(defn solve-output [solved-input
                    output-tokens]
  (->> (for [token output-tokens]
         (first (filter #(= (% :token) token) solved-input)))
       (map :num)
       (map str)
       (apply str)
       Integer/parseInt))

(defn solve-puzzle [puzzle]
     (let [solved-input (solve-input (:input puzzle))]
       (solve-output solved-input
                     (map :token (tokenize (:output puzzle))))))

(apply + (map solve-puzzle parsed))
