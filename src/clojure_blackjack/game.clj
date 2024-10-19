(ns clojure-blackjack.game)

(defn deal [deck]
  {:card (peek deck)
   :deck (pop deck)})

(defn deal-card->player [player deck]
  (let [{:keys [card deck]} (deal deck)
        updated-player (-> player
                           (update :cards conj card)
                           (update :hand-count + (:value card)))
        busted? (< 21 (:hand-count updated-player))]
    [updated-player deck busted?]))

(defn reset-player-cards [player]
  (assoc player :cards [] :hand-count 0))

(defn player [player-type]
  {:player-type player-type
   :hand-count 0
   :cards []})
