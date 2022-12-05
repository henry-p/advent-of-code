(ns advent-of-code.2022.05.problem-1)

(require '[clojure.string :as str])

(defn parse-raw [s]
  (let [[stacks-strs move-strs] (->> s
                                     str/split-lines
                                     (partition-by str/blank?)
                                     (remove (comp str/blank? first)))]
    {:stacks-strs stacks-strs
     :move-strs   move-strs}))

(def parsed
  (parse-raw (slurp "./resources/advent_of_code/2022/day_05.txt")))

(def parsed-test
  (parse-raw "    [D]    \n[N] [C]    \n[Z] [M] [P]\n 1   2   3 \n\nmove 1 from 2 to 1\nmove 3 from 1 to 3\nmove 2 from 2 to 1\nmove 1 from 1 to 2"))

(defn transpose [matrix]
  (apply map vector matrix))

(defn parse-stacks [stacks-strs]
  (into {}
        (map #(hash-map
                (Integer/parseInt (str (last %)))
                (remove (fn [c] (= c \space)) (drop-last %)))
             (filter #(some (fn [c] (re-find #"\d" (str c))) %)
                     (transpose stacks-strs)))))

(defn parse-moves [move-strs]
  (for [move-str move-strs]
    (let [[a b c] (->> move-str
                       (re-seq #"\d+")
                       (map #(Integer/parseInt %)))]
      {:n a :from b :to c})))

(defn crate-mover-9000 [items]
  (reverse items))

(defn move-crates-once [move stacks crate-mover-fn]
  (let [{from :from
         to   :to
         n    :n} move
        from-stack (stacks from)
        to-stack (stacks to)
        [taken-off-from-stack new-from-stack] (split-at n from-stack)
        new-to-stack (concat (crate-mover-fn taken-off-from-stack) to-stack)]
    (assoc stacks from new-from-stack
                  to new-to-stack)))

(defn move-all-crates [stacks moves crate-mover-fn]
  (loop [stacks stacks
         moves moves]
    (if (seq moves)
      (recur (move-crates-once (first moves) stacks crate-mover-fn)
             (rest moves))
      stacks)))

(defn get-solution [stacks moves crate-mover-fn]
  (->> (move-all-crates stacks moves crate-mover-fn)
       (into (sorted-map))
       vals
       (map first)
       (apply str)))

;; test
(get-solution (parse-stacks (parsed-test :stacks-strs))
              (parse-moves (parsed-test :move-strs))
              crate-mover-9000)

;; solution
(get-solution (parse-stacks (parsed :stacks-strs))
              (parse-moves (parsed :move-strs))
              crate-mover-9000)
