(ns clojure-blackjack.card)

(defn parse-int [val]
  (cond
    (int? val) val

    (string? val)
    (try (Integer/parseInt val)
         (catch Exception _ nil))

    :else nil))

(defn create-card [card-name suit]
  (let [parsed-card-name (parse-int card-name)
        value (cond
                (and parsed-card-name (<= 2 parsed-card-name 10)) parsed-card-name
                (contains? #{"jack" "queen" "king"} card-name) 10
                (= "ace" card-name) 11
                :else (throw (Exception. "Invalid card type")))]
    {:name card-name
     :suit suit
     :value value}))

(defn create-full-suit [suit]
  (map #(create-card (str %) suit)
       (concat (range 2 11)
               ["jack" "queen" "king" "ace"])))

(defn create-full-deck []
  (mapcat create-full-suit ["spade" "club" "diamond" "heart"]))

(defn create-playing-deck [num-decks]
  (vec (shuffle (apply concat (repeat num-decks (create-full-deck))))))

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

