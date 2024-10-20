(ns clojure-blackjack.game
  (:require
   [clojure-blackjack.card :refer [create-playing-deck]]))

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

(defn setup-game [num-players num-decks]
  (let [deck (create-playing-deck num-decks)]
    (create-game-state num-players deck)))

(defn hit? []
  (print "Do you want to hit? <y/n> ")
  (flush)
  (= "y" (read-line)))

(defn process-player [player deck]
  (loop [current-player player
         current-deck deck]
    (println "You currently have" (:hand-count current-player))
    (if (hit?)
      (let [[updated-player new-deck busted?] (deal-card->player current-player current-deck)]
        (cond
          busted? (do
                    (println "You've busted with" (:hand-count updated-player) "!")
                    [updated-player new-deck])
          (= 21 (:hand-count updated-player)) (do
                                                (println "You got 21!")
                                                [updated-player new-deck])
          :else (recur updated-player new-deck)))
      [current-player current-deck])))

(defn process-dealer [dealer deck]
  (loop [current-dealer dealer
         current-deck deck]
    (if (> 17 (:hand-count current-dealer))
      (let [[updated-dealer new-deck busted?] (deal-card->player current-dealer current-deck)]
        (cond
          busted? (do
                    (println "Dealer busted with" (:hand-count updated-dealer) "!")
                    [updated-dealer new-deck])
          (= 21 (:hand-count updated-dealer)) (do
                                                (println "Dealer got 21!")
                                                [updated-dealer new-deck])
          :else (recur updated-dealer new-deck)))
      [current-dealer current-deck])))

(defn play [game-state]
  (println "The dealer has" (:hand-count (:dealer game-state)))
  (let [[updated-players updated-deck]
        (reduce (fn [[players deck] player]
                  (let [[updated-player new-deck] (if (= 21 (:hand-count player))
                                                    (do
                                                      (println "You have blackjack!")
                                                      [player deck])
                                                    (process-player player deck))]
                    [(conj players updated-player) new-deck]))
                [[] (:deck game-state)]
                (:players game-state))

        [updated-dealer final-deck] (process-dealer (:dealer game-state) updated-deck)]
    (when (> 21 (:hand-count updated-dealer))
      (println "The dealer finished with" (:hand-count updated-dealer)))
    (assoc game-state
           :players updated-players
           :dealer updated-dealer
           :deck final-deck)))

(defn dealer-natural [game-state]
  (println "Dealer has blackjack!")
  (doseq [player (:players game-state)]
    (if (= 21 (:hand-count player))
      (println "Player has blackjack!")
      (println "Player lost with" (:hand-count player)))))

(defn start-game [num-players]
  (let [game-state (setup-game num-players 1)
        game-state (setup-round game-state)]
    (if (= 21 (:hand-count (:dealer game-state)))
      (dealer-natural game-state)
      (play game-state))))

