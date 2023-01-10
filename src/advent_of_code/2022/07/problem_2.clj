(ns advent-of-code.2022.07.problem-2)

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
    (str/starts-with? arg "/") (into [:/] (->> (str/split arg #"/")
                                               (remove str/blank?)
                                               (map keyword)))
    (= arg "..") (pop current-dir)
    :else (conj current-dir (keyword arg))))

(def filemap
  (loop [command-list (parse-log parsed)
         current-dir [:/]
         filemap {}]
    (let [[line & list-rest] command-list]
      (if-not line
        filemap
        (let [{:keys [command args output]} line]
          (cond (= command "cd") (recur list-rest
                                        (chdir current-dir (first args))
                                        filemap)
                (= command "ls") (recur list-rest
                                        current-dir
                                        (update filemap current-dir concat output))))))))

(defn get-sizemap [path filemap]
  (let [content (get filemap path)]
    (for [entry content
          :let [type (entry :type)]]
      (cond (= type :file) (entry :size)
            (= type :dir) (let [new-path (into path [(keyword (entry :name))])]
                            (get-sizemap new-path filemap))))))

(def disk-size 70000000)
(def minimum-required-free-space 30000000)
(def size-per-dir
  (->> (keys filemap)
       (map (fn [path]
              (->> (get-sizemap path filemap)
                   flatten
                   (apply +))))
       (zipmap (keys filemap))))
(def currently-occupied-space (size-per-dir [:/]))

(->> (vals size-per-dir)
     (filter
       (fn [dir-size]
         (->> dir-size
              (- currently-occupied-space)
              (- disk-size)
              (< minimum-required-free-space))))
     (apply min))
