(ns clojure-blackjack.game-test
  (:require
   [clojure-blackjack.card :refer [create-playing-deck]]
   [clojure-blackjack.game :refer [deal]]
   [clojure.test :refer [deftest is]]))

(deftest deal-test
  (let [[card deck] (deal (vec (sort-by (juxt :suit :name)
                                        (create-playing-deck 1))))]
    (is (= {:value 10
            :name "queen"
            :suit "spade"}
           card))
    (is (= 51 (count deck)))))
