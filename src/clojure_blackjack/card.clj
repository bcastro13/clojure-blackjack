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
  (shuffle (apply concat (repeat num-decks (create-full-deck)))))
