Display a graphical table of results using Swing.
=================================================

This is a simple graphical results for use in Java and Clojure.

Features
--------
* Table data (i.e. SQL results, spreadsheet data, matrices) visualized.
* All columns are sortable.
* Width of all columns and frame can be automatically (re)sized.
* Frames include scroll bars.

Obtaining
---------
For Clojure, in your `project.clj` file, add:

```clojure
[com.zensols.gui/tabres "0.0.2"]
```

For Java, in your `pom.xml` file, add:
```xml
<repositories>
    <repository>
        <id>clojars</id>
        <url>http://clojars.org/repo/</url>
    </repository>
</repositories>

<dependency>
    <groupId>com.zensols.gui</groupId>
    <artifactId>tabres</artifactId>
    <version>0.0.2</version>
</dependency>
```

Usage
-----
* Display an adhoc list of animals:
```clojure
(require '[com.zensols.gui.tabres.display-results :as dr])

(dr/display-results [["dog" "brown" 1]
                     ["cat" "yellow" 33]
                     ["fish" "silver" 14]]
                    :column-names ["Animal" "Color" "ID"]
                    :title "Animals")
```

* Display the first 150 rows of the
[Iris flower](https://en.wikipedia.org/wiki/Iris_flower_data_set) data set:
```clojure
(require '[clojure.java.io :as io])
(require '[clojure.data.csv :as csv])
(require '[com.zensols.gui.tabres.display-results :as dr])

(let [url "https://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data"]
  (with-open [in (clojure.java.io/reader url)]
    (->> (csv/read-csv in)
         (take 150)
         (#(dr/display-results % :title "iris dataset")))))
```

License
-------
Copyright © 2016 Paul Landes

GNU Lesser General Public License, Version 3.0
