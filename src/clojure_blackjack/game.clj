(ns clojure-blackjack.game)

(defn deal [deck]
  (let [card (peek deck)
        deck (pop deck)]
    [card deck]))

(defn deal-card->player [player deck]
  (let [[card deck] (deal deck)]
    [(update player :cards conj card) deck]))

(defn reset-player-cards [player]
  (assoc player :cards []))

(defn player [player-type]
  {:player-type player-type
   :cards []})
