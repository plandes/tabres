# Display a graphical table of results using Swing.

This is a simple graphical results for use in Java and Clojure.  The data is
either in the form of a list of lists or comes directly from a
`java.sql.ResultSet`.

<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-refresh-toc -->
## Table of Contents

- [Features](#features)
- [Obtaining](#obtaining)
- [Documentation](#documentation)
- [Usage](#usage)
- [License](#license)

<!-- markdown-toc end -->



## Features

* Table data (i.e. SQL results, spreadsheet data, matrices) visualized.
* All columns are sortable.
* Width of all columns and frame can be automatically (re)sized.
* Frames include scroll bars.

## Obtaining

For Clojure, in your `project.clj` or `pom.xml` file, add:

[![Clojars Project](http://clojars.org/com.zensols.gui/tabres/latest-version.svg)](http://clojars.org/com.zensols.gui/tabres/)

## Documentation

Additional documentation:
* [Clojure](https://plandes.github.io/tabres/codox/index.html)
* [Java](https://plandes.github.io/tabres/apidocs/index.html)


## Usage

* Display an adhoc list of animals:

```clojure
(require '[zensols.tabres.display-results :as dr])

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
(require '[zensols.tabres.display-results :as dr])

(let [url "https://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data"]
  (with-open [in (clojure.java.io/reader url)]
    (->> (csv/read-csv in)
         (take 150)
         (#(dr/display-results % :title "iris dataset")))))
```


## License

Copyright © 2016-2018 Paul Landes

Apache License version 2.0

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
