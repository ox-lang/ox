(defproject org.ox-lang/ox "0.1.0-SNAPSHOT"
  :description "牛, the language"
  :url "http://github.com/ox-lang/ox"
  :license {:name "MIX/X11 license"
            :url "http://opensource.org/licenses/MIT"}

  :whitelist #"ox.lang.*"

  :plugins [[lein-cloverage "1.0.2"]
            [lein-auto "0.1.1"]]

  :auto {:default {:file-pattern #"\.(clj|cljs|cljx|edn|g4|ox)$"}}
  
  :source-paths      ["src/main/clj"
                      "src/main/ox"]
  :java-source-paths ["src/main/java"]
  :test-paths        ["src/test/clj"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [me.arrdem/guten-tag "0.1.0"
                  :exclusions [org.clojure/clojure]]
                 [org.clojure/test.check "0.5.9"
                  :exclusions [org.clojure/clojure]]
                 [org.clojure/core.match "0.3.0-alpha3"
                  :exclusions [org.clojure/clojure]]
                 [clj-antlr "0.2.2"
                  :exclusions [org.clojure/clojure]]])
