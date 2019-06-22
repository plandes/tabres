# Display a graphical table of results using Swing.

[![Travis CI Build Status][travis-badge]][travis-link]

This is a simple graphical results for use in Java and Clojure.  The data is
either in the form of a list of lists or comes directly from a
`java.sql.ResultSet`.

Features:

* Table data (i.e. SQL results, spreadsheet data, matrices) visualized.
* All columns are sortable.
* Width of all columns and frame can be automatically (re)sized.
* Frames include scroll bars.

**Note:** While this is *built* as a Clojure project, it functions
perfectly as a Java dependency as well (see
the [Java documentation](#documentation) and [dependency](#obtaining)).


<!-- markdown-toc start - Don't edit this section. Run M-x markdown-toc-refresh-toc -->
## Table of Contents

- [Obtaining](#obtaining)
- [Documentation](#documentation)
- [Usage](#usage)
- [Building](#building)
- [Changelog](#changelog)
- [License](#license)

<!-- markdown-toc end -->



## Obtaining

In your `project.clj` file, add the following:

[![Clojars Project](https://clojars.org/com.zensols.gui/tabres/latest-version.svg)](https://clojars.org/com.zensols.gui/tabres/)

For Java projects, add the following to your [maven] `pom.xml`:

```xml
...
    <repositories>
        <repository>
            <id>clojars</id>
            <url>http://clojars.org/repo/</url>
        </repository>
    </repositories>
    <dependencies>
        <dependency>
            <groupId>com.zensols.gui</groupId>
            <artifactId>tabres</artifactId>
            <version>0.0.7</version>
        </dependency>
    </dependencies>
...
```


## Documentation

Additional documentation:
* [Clojure](https://plandes.github.io/tabres/codox/index.html)
* [Java](https://plandes.github.io/tabres/apidocs/index.html)


## Usage

* Display an adhoc list of animals:
x
```clojure
(require '[zensols.tabres.display-results :as dr])

(dr/display-results [["dog" "brown" 1]
                     ["cat" "yellow" 33]
                     ["fish" "silver" 14]]
                    :column-names ["Animal" "Color" "ID"]
                    :title "Animals")
```

* Display the first 100 rows of the
[Iris flower](https://en.wikipedia.org/wiki/Iris_flower_data_set) data set:

```clojure
(require '[clojure.java.io :as io])
(require '[clojure.data.csv :as csv])
(require '[zensols.tabres.display-results :as dr])

(let [url "https://archive.ics.uci.edu/ml/machine-learning-databases/iris/iris.data"
        cols ["sepal-length" "sepal-width" "petal-length" "petal-width" "class"]]
    (with-open [in (clojure.java.io/reader url)]
      (-> (csv/read-csv in)
          (#(take 100 %))
          (dr/display-results :title "iris dataset"
                              :column-names cols))))
```


## Building

To build from source, do the folling:

- Install [Leiningen](http://leiningen.org) (this is just a script)
- Install [GNU make](https://www.gnu.org/software/make/)
- Install [Git](https://git-scm.com)
- Download the source: `git clone --recurse-submodules https://github.com/plandes/tabres && cd tabres`
- Build the software: `make jar`

Note that you can also build a single jar file with all the dependencies with: `make uber`


## Changelog

An extensive changelog is available [here](CHANGELOG.md).


## License

Copyright © 2017, 2018 Paul Landes

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


<!-- links -->
[travis-link]: https://travis-ci.org/plandes/tabres
[travis-badge]: https://travis-ci.org/plandes/tabres.svg?branch=master
[maven]: https://maven.apache.org
