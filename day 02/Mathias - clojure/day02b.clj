(ns aoc-2022.day02b
  (:require [util :as util]
            [clojure.string :as str]))

(def example-input (list "A Y"
                     "B X"
                     "C Z"))

(def score-round {"C A" 7
                  "A B" 8
                  "B C" 9
                  "A A" 4
                  "B B" 5
                  "C C" 6
                  "B A" 1
                  "C B" 2
                  "A C" 3})

(def simple-play-strategy {"C X" "C A"
                           "A Y" "A B"
                           "B Z" "B C"
                           "A X" "A A"
                           "B Y" "B B"
                           "C Z" "C C"
                           "B X" "B A"
                           "C Y" "C B"
                           "A Z" "A C"})

(def result-based-play-strategy {"C X" "C B"
                                 "A Y" "A A"
                                 "B Z" "B C"
                                 "A X" "A C"
                                 "B Y" "B B"
                                 "C Z" "C A"
                                 "B X" "B A"
                                 "C Y" "C C"
                                 "A Z" "A B"})

(defn simulate-strategy-guide [input used-strategy]
  (reduce (fn [total round-instruction]
            (let [played-round (used-strategy round-instruction)]
              (+ total (score-round played-round))))
    0
    input))

(comment
  ;; verify example input
  ;; A=rock B=paper C=scissors
  ;; X=rock Y=paper Z=scissors
  (score-round (simple-play-strategy "A X"))

  (= 15 (simulate-strategy-guide example-input simple-play-strategy))
  (= 12 (simulate-strategy-guide example-input result-based-play-strategy))

  ,,,)

(defn -main
  "Main function"
  []
  (let [input (util/file->seq "2022/d02a.txt")]
    (simulate-strategy-guide input result-based-play-strategy)))