;; This file is part of the Zenos Solutions GUI Table Results Library (ZGTR).
;;
;; ZGTR is free software: you can redistribute it and/or modify it under the
;; terms of the GNU Lesser General Public License as published by the Free
;; Software Foundation, either version 3 of the License, or (at your option)
;; any later version.
;;
;; ZGTR is distributed in the hope that it will be useful, but WITHOUT ANY
;; WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
;; FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
;; details.
;;
;; You should have received a copy of the GNU General Public License along with
;; ZGTR.  If not, see <http://www.gnu.org/licenses/>.

(defproject com.zensols.gui/tabres "0.0.5"
  :description "GUI library to visualize (usually DB) results"
  :url "https://github.com/plandes/tabres"
  :license {:name "GPL"
            :url "http://www.gnu.org/licenses/gpl-3.0.en.html"
            :distribution :repo}
  :java-source-paths ["src/java"]
  :source-paths ["src/clojure"]
  :plugins [[lein-codox "0.9.5"]
            [lein-javadoc "0.3.0"]]
  :codox {:metadata {:doc/format :markdown}
          :output-path "doc/codox"}
  :javadoc-opts {:package-names ["com.zensols.gui.tabres"]
                 :output-dir "doc/apidocs"}
  :javac-options ["-Xlint:unchecked"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.zensols.gui/pref "0.0.2"]])
