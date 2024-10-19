(ns clojure-blackjack.game)

(defn deal [deck]
  (let [card (peek deck)
        deck (pop deck)]
    [card deck]))
