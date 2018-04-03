(defproject com.zensols.gui/tabres "0.1.0-SNAPSHOT"
  :description "GUI library to visualize (usually DB) results"
  :url "https://github.com/plandes/tabres"
  :license {:name "Apache License version 2.0"
            :url "https://www.apache.org/licenses/LICENSE-2.0"
            :distribution :repo}
  :plugins [[lein-codox "0.10.3"]
            [lein-javadoc "0.3.0"]
            [org.clojars.cvillecsteele/lein-git-version "1.2.7"]]
  :codox {:metadata {:doc/format :markdown}
          :project {:name "GUI library to visualize results"}
          :output-path "target/doc/codox"
          :source-uri "https://github.com/plandes/tabres/blob/v{version}/{filepath}#L{line}"}
  :javadoc-opts {:package-names ["com.zensols.gui.tabres"]
                 :output-dir "target/doc/apidocs"}
  :git-version {:root-ns "zensols.tabres"
                :path "src/clojure/zensols/tabres"
                :version-cmd "git describe --match v*.* --abbrev=4 --dirty=-dirty"}
  :source-paths ["src/clojure"]
  :test-paths ["test" "test-resources"]
  :java-source-paths ["src/java"]
  :javac-options ["-Xlint:unchecked"]
  :jar-exclusions [#".gitignore"]
  :dependencies [[org.clojure/clojure "1.8.0"]

                 ;; preference framework
                 [com.zensols.gui/pref "0.0.2"]]
  :profiles {:1.9 {:dependencies [[org.clojure/clojure "1.9.0"]]}
             :appassem {:aot :all}
             :snapshot {:git-version {:version-cmd "echo -snapshot"}}
             :dev {:dependencies [[org.clojure/data.csv "0.1.4"]]}})
