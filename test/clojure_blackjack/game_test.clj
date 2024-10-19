(ns clojure-blackjack.game-test
  (:require
   [clojure-blackjack.card :refer [create-playing-deck]]
   [clojure-blackjack.game :refer [deal deal-card->player player
                                   reset-player-cards]]
   [clojure.test :refer [deftest is]]))

(deftest deal-test
  (let [[card deck] (deal (vec (sort-by (juxt :suit :name)
                                        (create-playing-deck 1))))]
    (is (= {:value 10
            :name "queen"
            :suit "spade"}
           card))
    (is (= 51 (count deck)))))

(deftest deal-card-to-player-test
  (let [player (player "player")
        deck (vec (sort-by (juxt :suit :name)
                           (create-playing-deck 1)))
        [player deck] (deal-card->player player deck)]
    (is (= {:player-type "player"
            :cards [{:value 10
                     :name "queen"
                     :suit "spade"}]}
           player))
    (is (= 51 (count deck)))

    (let [[player deck] (deal-card->player player deck)]
      (is (= {:player-type "player"
              :cards [{:value 10
                       :name "queen"
                       :suit "spade"}
                      {:value 10
                       :name "king"
                       :suit "spade"}]}
             player))
      (is (= 50 (count deck))))))

(deftest reset-player-cards-test
  (let [player (player "player")
        deck (vec (sort-by (juxt :suit :name)
                           (create-playing-deck 1)))
        [player _] (deal-card->player player deck)
        player (reset-player-cards player)]
    (is (= {:player-type "player"
            :cards []}
           player))))

