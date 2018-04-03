(ns zensols.tabres.display-results-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]
            [zensols.tabres.display-results :as dr]))

(defn- create-frame []
  (let [url "https://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data"
        cols ["sepal-length" "sepal-width" "petal-length" "petal-width" "class"]]
    (with-open [in (clojure.java.io/reader url)]
      (-> (csv/read-csv in)
          (#(take 100 %))
          (dr/display-results :title "iris dataset"
                              :column-names cols)))))

(deftest test-gui
  (testing "gui"
    (is (= com.zensols.gui.tabres.ResultsFrame
           (.getClass (create-frame))))))
