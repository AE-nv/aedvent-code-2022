(ns aoc-2022.day04
  (:require [util :as util]
            [clojure.string :as str]
            [clojure.set :as s]))

(def example-input (list
                     "2-4,6-8"
                     "2-3,4-5"
                     "5-7,7-9"
                     "2-8,3-7"
                     "6-6,4-6"
                     "2-6,4-8"))

(defn range->set-of-sections [start-str end-str]
  (let [start (util/to-int start-str)
        end (util/to-int end-str)] (->> (iterate inc start)
        (take-while (fn [x] (<= x end)))
        set)))

(defn line->data [line]
  (let [[_ start-1 end-1 start-2 end-2] (re-matches #"(.+)-(.+),(.+)-(.+)" line)]
    [(range->set-of-sections start-1 end-1) (range->set-of-sections start-2 end-2)]))

(defn any-subsumes-the-other [[sections-one sections-two]]
  (or (s/subset? sections-one sections-two) (s/subset? sections-two sections-one)))

(defn count-full-overlap-pairs [input]
  (->> (map line->data input)
       (filter any-subsumes-the-other)
       count))

(defn any-overlap-with-other [[sections-one sections-two]]
  (not= (s/intersection sections-one sections-two) #{}))

(defn count-any-overlap-pairs [input]
  (->> (map line->data input)
       (filter any-overlap-with-other)
       count))

(comment
  (re-matches #"(.+)-(.+),(.+)-(.+)" (first example-input))
  (line->data (first example-input))

  (set (take-while (fn [x] (<= x 5)) (iterate inc 5)))
  ;; verify example input
  (= 2 (count-full-overlap-pairs example-input))
  (= 4 (count-any-overlap-pairs example-input))

  ,,,)

(defn -main
  "Main function"
  []
  (let [input (util/file->seq "2022/d04.txt")]
    ((juxt count-full-overlap-pairs count-any-overlap-pairs) input)))