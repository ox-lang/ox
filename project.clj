(defproject org.ox-lang/ox "0.0.1"
  :description "牛, the language"
  :url "https://ox-lang.org"
  :license {:name "MIX/X11 license"
            :url "http://opensource.org/licenses/MIT"}

  :source-paths      ["src/main/clj"
                      "src/main/ox"]
  :java-source-paths ["src/main/java"
                      "src/gen/java"]
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
                 [com.google.guava/guava "18.0"]
                 [com.intellij/annotations "12.0"]
                 [org.antlr/antlr4-runtime "4.5.1"]]

  :profiles {:test {:dependencies      [[junit/junit "4.11"]]
                    :java-source-paths ["src/test/java"]
                    :junit             ["src/test/java"]}
             :dev  {:plugins        [[lein-cloverage "1.0.2"
                                      :exclusions [org.clojure/clojure]]
                                     [lein-auto "0.1.1"
                                      :exclusions [org.clojure/clojure]]
                                     [lein-junit "1.1.8"
                                      :exclusions [org.clojure/clojure]]
                                     [lein-antlr "0.3.0"
                                      :exclusions [org.clojure/clojure]]]
                    :dependencies   [[clj-antlr "0.2.2"
                                      :exclusions [org.antlr/antlr4-runtime]]]
                    :source-paths   ["src/dev/clj"
                                     "src/dev/ox"]
                    :resource-paths ["src/main/antlr"]
                    :antlr-src-dir  "src/main/antlr"
                    :antlr-dest-dir "src/main/java/ox/lang/parser"
                    :aliases        {"test" ["do" "clean" ["with-profile" "test" "junit"] "test"]}
                    :auto           {:default {:file-pattern #"\.(clj|cljs|cljc|edn|ox)$"}}}})
