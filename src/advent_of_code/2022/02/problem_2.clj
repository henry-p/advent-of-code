(ns advent-of-code.2022.02.problem-2)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_02.txt")
       str/split-lines
       (map #(str/split % #" "))))

(def opp-lookup
  {"A" :rock
   "B" :paper
   "C" :scissors})

(def need-to
  {"X" :loose
   "Y" :draw
   "Z" :win})

(def me-chooser
  {[:rock :loose] :scissors
   [:rock :draw] :rock
   [:rock :win] :paper
   [:paper :loose] :rock
   [:paper :draw] :paper
   [:paper :win] :scissors
   [:scissors :loose] :paper
   [:scissors :draw] :scissors
   [:scissors :win] :rock})

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
        me-outcome (need-to me)
        me-should-choose (me-chooser [opp-type me-outcome])
        battle [opp-type me-should-choose]
        battle-points (points-outcome battle)
        me-points (points-per-type me-should-choose)]
    (+ battle-points me-points)))

(->> parsed
     (map calc-outcome)
     (apply +))
