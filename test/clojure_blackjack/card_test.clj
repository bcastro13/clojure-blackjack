(ns clojure-blackjack.card-test
  (:require
   [clojure-blackjack.card :refer [create-card create-full-deck
                                   create-full-suit]]
   [clojure.test :refer [deftest is]]))

(deftest create-a-digit-card-test
  (let [card (create-card "2" "spade")]
    (is (= {:value 2
            :name "2"
            :suit "spade"}
           card))))

(deftest create-a-face-card-test
  (let [card (create-card "jack" "spade")]
    (is (= {:value 10
            :name "jack"
            :suit "spade"}
           card))))

(deftest create-an-ace-card-test
  (let [card (create-card "ace" "spade")]
    (is (= {:value 11
            :name "ace"
            :suit "spade"}
           card))))

(deftest create-an-invalid-card-test
  (is (thrown-with-msg? Exception
                        #"Invalid card type"
                        (create-card "invalid" "spade")))
  (is (thrown-with-msg? Exception
                        #"Invalid card type"
                        (create-card "1" "spade")))
  (is (thrown-with-msg? Exception
                        #"Invalid card type"
                        (create-card "11" "spade"))))

(deftest create-full-suit-test
  (let [full-suit (create-full-suit "spade")]
    (is (= 13 (count full-suit)))
    (is (= [{:value 2
             :name "2"
             :suit "spade"}
            {:value 3
             :name "3"
             :suit "spade"}
            {:value 4
             :name "4"
             :suit "spade"}
            {:value 5
             :name "5"
             :suit "spade"}
            {:value 6
             :name "6"
             :suit "spade"}
            {:value 7
             :name "7"
             :suit "spade"}
            {:value 8
             :name "8"
             :suit "spade"}
            {:value 9
             :name "9"
             :suit "spade"}
            {:value 10
             :name "10"
             :suit "spade"}
            {:value 10
             :name "jack"
             :suit "spade"}
            {:value 10
             :name "queen"
             :suit "spade"}
            {:value 10
             :name "king"
             :suit "spade"}
            {:value 11
             :name "ace"
             :suit "spade"}]
           full-suit))))

(deftest create-full-deck-test
  (let [full-deck (create-full-deck)]
    (is (= 52 (count full-deck)))
    (is (= [{:value 2
             :name "2"
             :suit "spade"}
            {:value 3
             :name "3"
             :suit "spade"}
            {:value 4
             :name "4"
             :suit "spade"}
            {:value 5
             :name "5"
             :suit "spade"}
            {:value 6
             :name "6"
             :suit "spade"}
            {:value 7
             :name "7"
             :suit "spade"}
            {:value 8
             :name "8"
             :suit "spade"}
            {:value 9
             :name "9"
             :suit "spade"}
            {:value 10
             :name "10"
             :suit "spade"}
            {:value 10
             :name "jack"
             :suit "spade"}
            {:value 10
             :name "queen"
             :suit "spade"}
            {:value 10
             :name "king"
             :suit "spade"}
            {:value 11
             :name "ace"
             :suit "spade"}
            {:value 2
             :name "2"
             :suit "club"}
            {:value 3
             :name "3"
             :suit "club"}
            {:value 4
             :name "4"
             :suit "club"}
            {:value 5
             :name "5"
             :suit "club"}
            {:value 6
             :name "6"
             :suit "club"}
            {:value 7
             :name "7"
             :suit "club"}
            {:value 8
             :name "8"
             :suit "club"}
            {:value 9
             :name "9"
             :suit "club"}
            {:value 10
             :name "10"
             :suit "club"}
            {:value 10
             :name "jack"
             :suit "club"}
            {:value 10
             :name "queen"
             :suit "club"}
            {:value 10
             :name "king"
             :suit "club"}
            {:value 11
             :name "ace"
             :suit "club"}
            {:value 2
             :name "2"
             :suit "diamond"}
            {:value 3
             :name "3"
             :suit "diamond"}
            {:value 4
             :name "4"
             :suit "diamond"}
            {:value 5
             :name "5"
             :suit "diamond"}
            {:value 6
             :name "6"
             :suit "diamond"}
            {:value 7
             :name "7"
             :suit "diamond"}
            {:value 8
             :name "8"
             :suit "diamond"}
            {:value 9
             :name "9"
             :suit "diamond"}
            {:value 10
             :name "10"
             :suit "diamond"}
            {:value 10
             :name "jack"
             :suit "diamond"}
            {:value 10
             :name "queen"
             :suit "diamond"}
            {:value 10
             :name "king"
             :suit "diamond"}
            {:value 11
             :name "ace"
             :suit "diamond"}
            {:value 2
             :name "2"
             :suit "heart"}
            {:value 3
             :name "3"
             :suit "heart"}
            {:value 4
             :name "4"
             :suit "heart"}
            {:value 5
             :name "5"
             :suit "heart"}
            {:value 6
             :name "6"
             :suit "heart"}
            {:value 7
             :name "7"
             :suit "heart"}
            {:value 8
             :name "8"
             :suit "heart"}
            {:value 9
             :name "9"
             :suit "heart"}
            {:value 10
             :name "10"
             :suit "heart"}
            {:value 10
             :name "jack"
             :suit "heart"}
            {:value 10
             :name "queen"
             :suit "heart"}
            {:value 10
             :name "king"
             :suit "heart"}
            {:value 11
             :name "ace"
             :suit "heart"}]
           full-deck))))

