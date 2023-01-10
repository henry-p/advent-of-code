(ns advent-of-code.2022.07.problem-1)

(use 'clojure.tools.trace)

(require '[clojure.string :as str])

(def parsed
  (->> (slurp "./resources/advent_of_code/2022/day_07.txt")
       str/split-lines))

(def test-parsed
  (-> "$ cd /\n$ ls\ndir a\n14848514 b.txt\n8504156 c.dat\ndir d\n$ cd a\n$ ls\ndir e\n29116 f\n2557 g\n62596 h.lst\n$ cd e\n$ ls\n584 i\n$ cd ..\n$ cd ..\n$ cd d\n$ ls\n4060174 j\n8033020 d.log\n5626152 d.ext\n7214296 k"
      str/split-lines))

(defn parse-command [s]
  (cond (str/starts-with? s "cd") (zipmap [:command :args]
                                          (let [[command & args] (str/split s #" ")]
                                            [command args]))
        (str/starts-with? s "ls") {:command "ls"}))

(defn parse-output [s]
  (cond (str/starts-with? s "dir") {:type :dir :name (subs s 4)}
        :else (into {:type :file}
                    (zipmap [:size :name]
                            (let [[size name] (str/split s #" ")]
                              [(parse-long size) name])))))

(defn parse-log [lines]
  (loop [partitioned (partition-by #(re-find #"\$" %) lines)
         parsed []]
    (if-let [group (first partitioned)]
      (let [commands (map #(second (re-find #"\$ (.*)" %)) group)]
        (recur (rest partitioned)
               (if (first commands)
                 (into parsed (map parse-command commands))
                 (conj (pop parsed)
                       (assoc (peek parsed) :output (map parse-output group))))))
      parsed)))

(defn chdir [current-dir arg]
  (cond
    (str/starts-with? arg "/") (into [:/] (map keyword (remove str/blank? (str/split arg #"/"))))
    (= arg "..") (pop current-dir)
    :else (conj current-dir (keyword arg))))

(defn dir-size [output]
  (apply + (for [entry output :when (= (entry :type) :file)]
             (entry :size))))

(defn flat-tree-from-log [log]
  (loop [log log
         current-dir []
         directories {}]
    (if-let [line (first log)]
      (let [{:keys [command args output]} line]
        (case command
          "cd" (recur (rest log)
                      (chdir current-dir (first args))
                      directories)
          "ls" (recur (rest log)
                      current-dir
                      (assoc directories current-dir (dir-size output)))))
      directories)))

(defn tree-from-flat-tree [flat-tree]
  (let [directories flat-tree]
    (loop [directories directories
           tree {}]
      (if-let [dir (first directories)]
        (let [[dir-path
               dir-contents] dir]
          (recur (rest directories)
                 (update-in tree dir-path
                            (fnil into {})
                            {:__dir-size dir-contents})))
        tree))))

(def t
  (->>
    test-parsed
    parse-log
    flat-tree-from-log
    tree-from-flat-tree
    ))

(def t2
  (->>
    test-parsed
    parse-log
    flat-tree-from-log
    ))

(defn sum-tree [tree]
  (for [[path subtree :as t] tree]
    (if (number? subtree)
      subtree
      {path (into [] (sum-tree subtree))})))
(def t3 (sum-tree t))

(defn xxx [l]
  (for [t l]
    (let []
      ;{path (apply + (trace (first subtree)) (xxx subtree))}
      subtree
      )))
(xxx t3)


(->>
  test-parsed
  parse-log
  flat-tree-from-log
  ;tree-from-flat-tree
  ;vals
  ;(map :dir-size)
  ;(filter #(<= % 100000))
  ;(apply +)
  )





