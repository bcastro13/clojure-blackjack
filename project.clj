(defproject clojure-blackjack "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.12.0"]]
  :main ^:skip-aot clojure-blackjack.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :dev {:dependencies [[io.github.noahtheduke/splint "1.18.0"]
                                  [eftest "0.6.0"]]}}
  :plugins [[dev.weavejester/lein-cljfmt "0.12.0"]
            [jonase/eastwood "1.4.2"]
            [lein-cloverage "1.2.4"]
            [lein-ancient "1.0.0-RC3"]
            [lein-typed "0.4.6"]
            [lein-eftest "0.6.0"]]
  :aliases {"splint" ["run" "-m" "noahtheduke.splint"]}
  :eftest {:multithread? true
           :fail-fast? true
           :test-warn-time 1500}
  :cloverage {:runner :eftest
              :runner-opts {:multithread? :namespaces
                            :fail-fast? true
                            :test-warn-time 1500}}
  :cljfmt {:remove-multiple-non-indenting-spaces? true
           :split-keypairs-over-multiple-lines? true
           :sort-ns-references? true
           :parallel? true
           :paths ["src" "test" "tests"]})
