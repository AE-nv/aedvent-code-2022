(ns aoc-2022.day02
  (:require [util :as util]
            [clojure.string :as str]))

(def example-input (list "A Y"
                     "B X"
                     "C Z"))

(def score-round {"C X" 7
                  "A Y" 8
                  "B Z" 9
                  "A X" 4
                  "B Y" 5
                  "C Z" 6
                  "B X" 1
                  "C Y" 2
                  "A Z" 3})

(defn simulate-strategy-guide [input]
  (reduce (fn [total round] (+ total (score-round round)))
    0
    input))

(comment
  ;; verify example input
  (= 15 (simulate-strategy-guide example-input))

  ,,,)

(defn -main
  "Main function"
  []
  (let [input (util/file->seq "2022/d02a.txt")]
    (simulate-strategy-guide input)))