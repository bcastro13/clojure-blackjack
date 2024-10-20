(ns clojure-blackjack.game-test
  (:require
   [clojure-blackjack.card :refer [deal-cards]]
   [clojure-blackjack.game :refer [create-game-state setup-game setup-round]]
   [clojure.test :refer [deftest is]]))

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

(deftest setup-game-test
  (let [game-state (setup-game 1 1)]
    (is (= 1 (count (:players game-state))))
    (is (= 52 (count (:deck game-state))))))
