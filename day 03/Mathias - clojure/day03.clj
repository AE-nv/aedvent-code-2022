(ns aoc-2022.day03
  (:require [util :as util]
            [clojure.string :as str]
            [clojure.set :as s]))

(def example-input (list "vJrwpWtwJgWrhcsFMMfFFhFp"
                     "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
                     "PmmdzqPrVvPwwTWBwg"
                     "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
                     "ttgJtRGJQctTZtZT"
                     "CrZsJsPPZsGzwwsLwLmpwMDw"))

(def priorities (->> (map-indexed (fn [idx it] [it (inc idx)]) "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
                     (into {})))

(defn find-badly-packed-item-types [rucksack]
  (let [item-count-per-compartment (/ (count rucksack) 2)
        first-compartment (take item-count-per-compartment rucksack)
        second-compartment (drop item-count-per-compartment rucksack)]
    (s/intersection (set first-compartment) (set second-compartment))))

(defn sum-priorities [badly-backed-item-types]
  (->> (map priorities badly-backed-item-types)
       (reduce + 0)))
(defn sum-of-priorities-of-badly-packed-item-types [input]
  (->> input
       (map find-badly-packed-item-types)
       (map sum-priorities)
       (reduce + 0)))

(comment
  (= #{\p} (find-badly-packed-item-types "vJrwpWtwJgWrhcsFMMfFFhFp"))
  (= 17 (sum-priorities #{\p \a}))

  ;; verify example input
  (= 157 (sum-of-priorities-of-badly-packed-item-types example-input))

  ,,,)

;; PART 2
(defn split-into-groups [input]
  (partition 3 input))

(defn find-shared-item-type [group-rucksacks]
  (first (apply s/intersection (map set group-rucksacks))))

(defn sum-of-priorities-of-group-badges [input]
  (->> (split-into-groups input)
       (map find-shared-item-type)
       (map priorities)
       (reduce + 0)))

(comment
  (= \r (find-shared-item-type (take 3 example-input)))
  (sum-of-priorities-of-group-badges example-input)
  (= 70 (sum-of-priorities-of-group-badges example-input))

  ,,,)

(defn -main
  "Main function"
  []
  (let [input (util/file->seq "2022/d03.txt")]
    ((juxt sum-of-priorities-of-badly-packed-item-types sum-of-priorities-of-group-badges) input)))