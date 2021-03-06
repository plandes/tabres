(ns ^{:doc "Display a graphical table of results using Swing."
      :author "Paul Landes"}
    zensols.tabres.display-results
  (:import (com.zensols.gui.tabres ResultsFrame))
  (:require [clojure.tools.logging :as log]
            [clojure.test :as test]))

(def ^:dynamic *data-limit*
  "The limit of data presented in the GUI, which defaults
  to [[Integer/MAX_VALUE]]."
  Integer/MAX_VALUE)

(def ^:private result-frame-data (atom nil))

(def ^:dynamic frame-factory-fn
  "A function that takes no args used to create the results frame."
  (fn []
    (let [frame (ResultsFrame. "resultsframe")]
      (.init frame)
      frame)))

(def ^:dynamic frame-config-fn
  "A function that configures a frame either after being created or orphaned.
  It takes three arguments: the frame, the title to set if not nil, and a
  boolean if it is being orphaned."
  (fn [frame title orphaned?]
    (if title (.setTitle frame title))))

(defn- new-frame [title]
  (log/debugf "creating new frame with title %s" title)
  (swap! result-frame-data
         (fn [frame]
           (when frame
             (log/debugf "disposing old frame %s" frame)
             (.dispose frame))
           (let [nf (frame-factory-fn)]
             (log/debugf "created new frame %s with %s" nf frame-factory-fn)
             (frame-config-fn nf title false)
             nf))))

(defn- result-frame
  [title]
  (log/debugf "getting result frame with title %s" title)
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
              (log/debugf "orphaning frame %s" frame)
              (.setDefaultCloseOperation frame
                                         javax.swing.JFrame/DISPOSE_ON_CLOSE)
              (.setEnabled (.getPrefSupport frame) false)
              (frame-config-fn frame title true))
            nil))))

(defn display-results
  "Display the results of **data-or-fn**.

  **data-or-fn** is a two dimension seq or a function.  If the former the first
  dimension are the rows and the second the respective colum data.  If it is a
  function, it takes the frame input and what it returns is returned from this
  function.

  Keys
  ----
  *  **:title** the title set on the (maybe new) frame.
  *  **:column-names** seq of the names of the column headers.
  *  **:new-frame?** if `true` then destroy any old frame and recreate."
  [data-or-fn & {:keys [title column-names new-frame?]
                 :or {title "Results"
                      new-frame? false}}]
  (log/debugf "dislaying for title=%s, new=%s" title new-frame?)
  (let [frame (if new-frame?
                (new-frame title)
                (result-frame title))]
    (.setTitle frame title)
    (when (instance? ResultsFrame frame)
      (.setHeightFudge frame (if column-names ResultsFrame/HEIGHT_FUDGE 10)))
    (let [res (if (test/function? data-or-fn)
                (data-or-fn frame)
                (let [data (take *data-limit* data-or-fn)]
                  (log/debugf "display results on frame: %s" frame)
                  (.displayResults frame data column-names)
                  (if (= title "Results")
                    (.setTitle frame (format "Results (%d)" (count data))))
                  frame))]
      (.pack frame)
      (.setVisible frame true)
      res)))
