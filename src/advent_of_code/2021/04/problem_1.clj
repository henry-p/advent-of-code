(ns advent-of-code.2021.04.problem-1)

(require '[clojure.string :as str])
(use 'clojure.tools.trace)

(def input (slurp "./resources/advent_of_code/2021/day_04.txt"))

(def parsed (-> input str/split-lines))

(def random-seq
  (map #(Integer/parseInt %) (str/split (first parsed) #",")))

(defn parse-bingo-row [s]
  (map #(Integer/parseInt %) (re-seq #"\d+" s)))

(defn parse-bingo-board [v]
  (map parse-bingo-row v))

(def raw-bingo-boards
  (map parse-bingo-board (partition 5 6 (drop 2 parsed))))

(defn add-checked-flag [bingo-board]
  (map (fn [row]
         (map (fn [number]
                (hash-map :number number
                          :checked? false))
              row))
       bingo-board))

(def bingo-boards
  (map add-checked-flag raw-bingo-boards))

(defn all-true? [v]
  (every? true? v))

(defn get-checks [board-with-checks]
  (map
    (fn [row] (map #(% :checked?) row))
    board-with-checks))

(defn transpose [board]
  (apply map vector board))

(defn check-off-number [board n]
  (for [row board]
    (map
      (fn [m]
        (if (= (m :number) n)
          (assoc m :checked? true)
          m))
      row)))

(defn bingo? [board-with-checks]
  (let [row-checks (get-checks board-with-checks)
        column-checks (get-checks (transpose board-with-checks))]
    (or (some all-true? row-checks)
        (some all-true? column-checks))))

(defn find-bingo [boards numbers]
  (loop [ns numbers
         boards boards]
    (let [n (first ns)
          checked-off-boards (map #(check-off-number % n) boards)
          board-with-bingo (some #(when (bingo? %) %) checked-off-boards)]
      (cond
        (empty? ns) nil
        board-with-bingo {:board        board-with-bingo
                          :bingo-number n}
        :else (recur (rest ns)
                     checked-off-boards)))))

(defn non-checked-numbers [row]
  (remove nil?
          (map (fn [m]
                 (when (false? (m :checked?))
                   (m :number)))
               row)))

(let [{board :board num :bingo-number} (find-bingo bingo-boards random-seq)]
  (->> board
       (mapcat non-checked-numbers)
       (apply +)
       (* num)))
