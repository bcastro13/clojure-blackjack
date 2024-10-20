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

(defn create-game-state [num-players deck]
  {:players (vec (repeat num-players (player "player")))
   :dealer (player "dealer")
   :deck deck})

(defn deal-cards [game-state]
  (let [[updated-players updated-deck]
        (reduce (fn [[players deck] player]
                  (let [[updated-player new-deck _] (deal-card->player player
                                                                       deck)]
                    [(conj players updated-player) new-deck]))
                [[] (:deck game-state)]
                (:players game-state))
        [updated-dealer final-deck _] (deal-card->player (:dealer game-state)
                                                         updated-deck)]
    (assoc game-state
           :players updated-players
           :dealer updated-dealer
           :deck final-deck)))

(defn naturals? [game-state]
  (let [dealer-natural? (= 21 (:hand-count (:dealer game-state)))
        player-naturals? (map #(= 21 (:hand-count %)) (:players game-state))]
    {:dealer dealer-natural?
     :players player-naturals?}))

(defn setup-round [game-state]
  (-> game-state
      deal-cards
      deal-cards))
