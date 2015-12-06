(defproject challenge "1.0.0-SNAPSHOT"
  :description "Solution to challenge from HealthFinch"
  :source-paths ["src/clojure"]
  :frege-source-paths ["src/frege"]
  :test-paths ["test/clojure" "test/frege"]
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [com.theoryinpractise.frege/frege "3.22.367-g2737683"]]
  :plugins [[lein-fregec "3.22.367-i"]])
