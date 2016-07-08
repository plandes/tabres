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

(ns ^{:doc "Display a graphical table of results using Swing."
      :author "Paul Landes"}
    zensols.tabres.display-results
  (:import (com.zensols.gui.tabres ResultsFrame))
  (:require [clojure.test :as test]))

(def ^:private result-frame-data (atom nil))

(def ^:dynamic frame-factory-fn
  "A function that takes no args used to create the results frame."
  (fn []
    (let [frame (ResultsFrame. "mainframe")]
      (.init frame)
      frame)))

(def ^:dynamic frame-config-fn
  "A function that configures a frame either after being created or orphaned.
  It takes three arguments: the frame, the title to set if not nil, and a
  boolean if it is being orphaned."
  (fn [frame title orphaned?]
    (if title (.setTitle frame title))))

(defn- new-frame [title]
  (swap! result-frame-data
         (fn [frame]
           (when frame
             (.dispose frame))
           (let [nf (frame-factory-fn)]
             (frame-config-fn nf title false)
             nf))))

(defn- result-frame
  [title]
  (swap! result-frame-data #(or % (new-frame title))))

(defn orphan-frame
  "Making a results frame orphaned means it will detach from any new result
  sets on any subsquent calls to [[display-results]] and dispose all resources
  next time the frame is closed."
  ([]
   (orphan-frame nil))
  ([title]
   (swap! result-frame-data
          (fn [frame]
            (when frame
              (.setDefaultCloseOperation frame
                                         javax.swing.JFrame/DISPOSE_ON_CLOSE)
              (.setEnabled (.getPrefSupport frame) false)
              (frame-config-fn frame title true))
            nil))))

(defn display-results
  "Display the results of **data**.

  **data** is a two dimension seq or a function.  If it is a two dimension seq
  the first dimension are the rows and the second the respective colum data.
  If it is a function, it takes the frame input and what it returns is returned
  from this function.

  Keys:
  **title:** the title set on the (maybe new) frame
  **column-names:** seq of the names of the column headers"
  [data-or-fn & {:keys [title column-names] :or {title "Results"}}]
  (let [frame (result-frame title)]
    (.setTitle frame title)
    (when (instance? ResultsFrame frame)
      (.setHeightFudge frame (if column-names ResultsFrame/HEIGHT_FUDGE 10)))
    (let [res (if (test/function? data-or-fn)
                (data-or-fn frame)
                (do
                  (.displayResults frame data-or-fn column-names)
                  (if (= title "Results")
                    (.setTitle frame (format "Results (%d)"
                                             (count data-or-fn))))
                  frame))]
      (.pack frame)
      (.setVisible frame true)
      res)))
