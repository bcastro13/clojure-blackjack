(ns clojure-blackjack.game-test
  (:require
   [clojure-blackjack.card :refer [create-playing-deck]]
   [clojure-blackjack.game :refer [create-game-state deal deal-card->player
                                   deal-cards dealer-natural naturals? player
                                   reset-player-cards setup-game setup-round]]
   [clojure.test :refer [deftest is]]))

(deftest deal-test
  (let [{:keys [card deck]} (deal (vec (sort-by (juxt :suit :name)
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
        [player deck _] (deal-card->player player deck)]
    (is (= {:player-type "player"
            :hand-count 10
            :cards [{:value 10
                     :name "queen"
                     :suit "spade"}]}
           player))
    (is (= 51 (count deck)))

    (let [[player deck _] (deal-card->player player deck)]
      (is (= {:player-type "player"
              :hand-count 20
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
        [player _ _] (deal-card->player player deck)
        player (reset-player-cards player)]
    (is (= {:player-type "player"
            :hand-count 0
            :cards []}
           player))))

(deftest busted?-test
  (let [player (player "player")
        deck (vec (sort-by (juxt :suit :name)
                           (create-playing-deck 1)))
        [player deck busted?] (deal-card->player player deck)
        _ (is (false? busted?))

        [player deck busted?] (deal-card->player player deck)
        _ (is (false? busted?))

        [player deck busted?] (deal-card->player player deck)]
    (is (= 30 (:hand-count player)))
    (is (= 49 (count deck)))
    (is (true? busted?))))

(deftest creaet-game-state-test
  (let [deck [{:value 10
               :name "jack"
               :suit "spade"}]
        game-state (create-game-state 1 deck)]
    (println game-state)
    (is (= {:players [{:player-type "player"
                       :hand-count 0
                       :cards []}]
            :dealer {:player-type "dealer"
                     :hand-count 0
                     :cards []}
            :deck deck}
           game-state))))

(deftest deal-cards-test
  (let [initial-game-state (create-game-state 1 [{:value 10
                                                  :name "jack"
                                                  :suit "spade"}
                                                 {:value 10
                                                  :name "queen"
                                                  :suit "spade"}
                                                 {:value 10
                                                  :name "king"
                                                  :suit "spade"}
                                                 {:value 9
                                                  :name "9"
                                                  :suit "spade"}])
        final-game-state (deal-cards initial-game-state)]
    (is (= {:players [{:player-type "player"
                       :hand-count 9
                       :cards [{:value 9
                                :name "9"
                                :suit "spade"}]}]
            :dealer {:player-type "dealer"
                     :hand-count 10
                     :cards [{:value 10
                              :name "king"
                              :suit "spade"}]}
            :deck [{:value 10
                    :name "jack"
                    :suit "spade"}
                   {:value 10
                    :name "queen"
                    :suit "spade"}]}
           final-game-state))))

(deftest setup-round-test
  (let [initial-game-state (create-game-state 1 [{:value 10
                                                  :name "jack"
                                                  :suit "spade"}
                                                 {:value 10
                                                  :name "queen"
                                                  :suit "spade"}
                                                 {:value 10
                                                  :name "king"
                                                  :suit "spade"}
                                                 {:value 9
                                                  :name "9"
                                                  :suit "spade"}])
        final-game-state (setup-round initial-game-state)]
    (is (= {:players [{:player-type "player"
                       :hand-count 19
                       :cards [{:value 9
                                :name "9"
                                :suit "spade"}
                               {:value 10
                                :name "queen"
                                :suit "spade"}]}]
            :dealer {:player-type "dealer"
                     :hand-count 20
                     :cards [{:value 10
                              :name "king"
                              :suit "spade"}
                             {:value 10
                              :name "jack"
                              :suit "spade"}]}
            :deck []}
           final-game-state))))

(deftest naturals?-test
  (let [game-state {:players [{:player-type "player"
                               :hand-count 19
                               :cards [{:value 9
                                        :name "9"
                                        :suit "spade"}
                                       {:value 10
                                        :name "queen"
                                        :suit "spade"}]}
                              {:player-type "player"
                               :hand-count 21
                               :cards [{:value 11
                                        :name "ace"
                                        :suit "club"}
                                       {:value 10
                                        :name "jack"
                                        :suit "spade"}]}]
                    :dealer {:player-type "dealer"
                             :hand-count 21
                             :cards [{:value 10
                                      :name "king"
                                      :suit "spade"}
                                     {:value 11
                                      :name "ace"
                                      :suit "spade"}]}
                    :deck []}
        naturals (naturals? game-state)]
    (is (= {:players [false true]
            :dealer true}
           naturals))))

(deftest setup-game-test
  (let [game-state (setup-game 1 1)]
    (is (= 1 (count (:players game-state))))
    (is (= 52 (count (:deck game-state))))))
