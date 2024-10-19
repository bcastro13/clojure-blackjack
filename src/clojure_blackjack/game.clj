(ns clojure-blackjack.game)

(defn deal [deck]
  (let [card (peek deck)
        deck (pop deck)]
    [card deck]))

(defn deal-card->player [player deck]
  (let [[card deck] (deal deck)
        player (update player :cards conj card)
        player (update player :hand-count + (:value card))
        busted? (< 21 (:hand-count player))]
    [player deck busted?]))

(defn reset-player-cards [player]
  (assoc player :cards [] :hand-count 0))

(defn player [player-type]
  {:player-type player-type
   :hand-count 0
   :cards []})
