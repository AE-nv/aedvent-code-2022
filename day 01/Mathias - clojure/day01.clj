(ns aoc-2022.day01
  (:require [util :as util]
            [clojure.string :as str]))

(def example-input (list "1000"
                     "2000"
                     "3000"
                     ""
                     "4000"
                     ""
                     "5000"
                     "6000"
                     ""
                     "7000"
                     "8000"
                     "9000"
                     ""
                     "10000"))

(defn input->calories-per-elf [input]
  (->> (util/split-using str/blank? input)
       (map (fn [xs] (map util/to-int xs)))))

(defn colories-list->total-calories [colories-list]
  (map (fn [xs] (reduce + 0 xs)) colories-list))

(defn aggregate-elves-and-select-max-calories [input]
  (->> (input->calories-per-elf input)
       colories-list->total-calories
       (apply max)))

(defn keep-top-3 [totals]
  (reduce (fn [[third second first :as top3] x]
            (condp < x
              first [second first x]
              second [second x first]
              third [x second first]
              top3))
    [0 0 0]
    totals))

(defn aggregate-elves-and-calculate-top-3-total [input]
  (->> input
       input->calories-per-elf
       colories-list->total-calories
       keep-top-3
       (reduce + 0)))

(comment
  ;; verify example input
  (= 24000 (aggregate-elves-and-select-max-calories example-input))
  (= 45000 (aggregate-elves-and-calculate-top-3-total example-input))
  ,,,)

(defn -main
  "Main function"
  []
  (let [input (util/file->seq "2022/d01a.txt")]
    (println (aggregate-elves-and-select-max-calories input))
    (println (aggregate-elves-and-calculate-top-3-total input))))