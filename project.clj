(defproject org.ox-lang/ox "0.0.1"
  :description "牛, the language"
  :url "http://github.com/ox-lang/ox"
  :license {:name "MIX/X11 license"
            :url "http://opensource.org/licenses/MIT"}

  :source-paths      ["src/main/clj"
                      "src/main/ox"]
  :java-source-paths ["src/main/java"]
  :test-paths        ["src/test/clj"]

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/test.check "0.8.1"
                  :exclusions [org.clojure/clojure]]
                 [org.clojure/core.match "0.3.0-alpha3"
                  :exclusions [org.clojure/clojure]]
                 [me.arrdem/guten-tag "0.1.4"
                  :exclusions [org.clojure/clojure]]
                 [clj-tuple "0.2.2"
                  :exclusions [org.clojure/clojure]]
                 [com.google.guava/guava "18.0"]]

  :profiles {:dev {:plugins [[lein-cloverage "1.0.2"]
                             [lein-auto "0.1.1"]]
                   :whitelist #"ox.lang.*"
                   :auto {:default {:file-pattern #"\.(clj|cljs|cljc|edn|ox)$"}}}})
