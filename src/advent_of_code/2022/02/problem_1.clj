(ns advent-of-code.2022.02.problem-1)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_02.txt")
       str/split-lines
       (map #(str/split % #" "))))

(def opp-lookup
  {"A" :rock
   "B" :paper
   "C" :scissors})

(def me-lookup
  {"X" :rock
   "Y" :paper
   "Z" :scissors})

(def points-outcome
  {[:rock :rock] 3
   [:rock :paper] 6
   [:rock :scissors] 0
   [:paper :rock] 0
   [:paper :paper] 3
   [:paper :scissors] 6
   [:scissors :rock] 6
   [:scissors :paper] 0
   [:scissors :scissors] 3})

(def points-per-type
  {:rock 1
   :paper 2
   :scissors 3})

(defn calc-outcome [input]
  (let [[opp me] input
        opp-type (opp-lookup opp)
        me-type (me-lookup me)
        battle [opp-type me-type]
        battle-points (points-outcome battle)
        me-points (points-per-type me-type)]
    (+ battle-points me-points)))

(apply + (map calc-outcome parsed))
