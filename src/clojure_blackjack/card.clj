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
        value (if parsed-card-name
                (if (and (< 1 parsed-card-name)
                         (> 11 parsed-card-name))
                  parsed-card-name
                  (throw (Exception. "Invalid card type")))
                (condp contains? card-name
                  #{"jack" "queen" "king"} 10
                  #{"ace"} 11
                  (throw (Exception. "Invalid card type"))))]
    {:name card-name
     :suit suit
     :value value}))

(defn create-full-suit [suit]
  (let [full-suit (for [card-num (concat (range 2 11)
                                         ["jack" "queen" "king" "ace"])]
                    (create-card (str card-num) suit))]
    full-suit))

(defn create-full-deck []
  (flatten (map create-full-suit ["spade" "club" "diamond" "heart"])))

(defn create-playing-deck [num-decks]
  (shuffle (flatten (for [_ (range num-decks)]
                      (create-full-deck)))))
