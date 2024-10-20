(ns clojure-blackjack.core
  (:gen-class)
  (:require
   [clojure-blackjack.game :refer [start-game]]))

(defn -main []
  (println "Welcome to Clojure Blackjack!")
  (start-game 1)
  (println "Game Over! Thanks for playing!"))
