(defproject org.oxlang/oxlang "0.1.0-SNAPSHOT"
  :description "牛, the language"
  :url "http://github.com/oxlang/oxlang"
  :license {:name "MIX/X11 license"
            :url "http://opensource.org/licenses/MIT"}
  :whitelist #"oxlang.*"
  :plugins [[lein-cloverage "1.0.2"]]
  
  :source-paths      ["src/main/clj"]
  :java-source-paths ["src/main/java"]
  :test-paths        ["src/test/clj"]

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/test.check "0.5.9"]
                 [org.clojure/core.match "0.3.0-alpha3"]
                 [clj-antlr "0.2.2"]])
