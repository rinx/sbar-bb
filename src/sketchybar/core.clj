(ns sketchybar.core
  (:require
   [babashka.process :refer [shell]]
   [camel-snake-kebab.core :as csk]
   [cheshire.core :as json]
   [sketchybar.impl :as impl])
  (:refer-clojure :exclude [set update]))

(defn bar
  "Configure Bar properties.

  See: https://felixkratz.github.io/SketchyBar/config/bar"
  [m]
  (flatten [["--bar"] (impl/map->kvs m)]))

(defn default
  "Change default properties

  See: https://felixkratz.github.io/SketchyBar/config/items#changing-the-default-values-for-all-further-items"
  [m]
  (flatten [["--default"] (impl/map->kvs m)]))

(defn set
  "Change item properties

  See: https://felixkratz.github.io/SketchyBar/config/items#changing-item-properties"
  [item m]
  (flatten [["--set" (name item)] (impl/map->kvs m)]))

(defn add-item
  "Add an item.

  See: https://felixkratz.github.io/SketchyBar/config/items#adding-items-to-sketchybar"
  [item position]
  ["--add" "item" (name item) (name position)])

(defn add-graph
  "Add a graph.

  See: https://felixkratz.github.io/SketchyBar/config/components#data-graph----draws-an-arbitrary-graph-into-the-bar"
  [graph position width]
  ["--add" "graph" (name graph) (name position) (str width)])

(defn add-space
  "Add a space.

  See: https://felixkratz.github.io/SketchyBar/config/components#space----associate-mission-control-spaces-with-an-item"
  [space position]
  ["--add" "space" (name space) (name position)])

(defn add-bracket
  "Add a bracket.

  See: https://felixkratz.github.io/SketchyBar/config/components#item-bracket----group-items-in-eg-colored-sections"
  [bracket & members]
  (flatten [["--add" "bracket" (name bracket) (map name members)]]))

(defn add-alias
  "Add an alias.

  See: https://felixkratz.github.io/SketchyBar/config/components#item-alias----mirror-items-of-the-original-macos-status-bar-into-sketchybar"
  [app position]
  ["--add" "alias" app (name position)])

(defn add-slider
  "Add a slider.

  See: https://felixkratz.github.io/SketchyBar/config/components#slider----a-draggable-progression-indicator"
  [slider position width]
  ["--add" "slider" (name slider) (name position) (str width)])

(defn add-event
  "Create custom event.

  See: https://felixkratz.github.io/SketchyBar/config/events#creating-custom-events"
  ([event]
   ["--add" "event" (name event)])
  ([event notification]
   ["--add" "event" (name event) (name notification)]))

(defn push-datapoint
  "Push datapoints to a graph.

  See: https://felixkratz.github.io/SketchyBar/config/components#data-graph----draws-an-arbitrary-graph-into-the-bar"
  [graph & data]
  (flatten [["--push" (name graph)] (map str data)]))

(defn subscribe
  "Subscribe events.

  See: https://felixkratz.github.io/SketchyBar/config/events"
  [item & events]
  (flatten [["--subscribe" (name item)] (map name events)]))

(defn exec
  "Execute sketchybar command.

  Example:
  (sketchybar.core/exec
   (sketchybar.core/add-item :date :right)
   (sketchybar.core/set :date {:update_freq 60
                               ...}))
  "
  [& args]
  (->> (flatten args)
       (filter some?)
       (apply shell "sketchybar")))

(defn update
  "Refresh the bar.

  See: https://felixkratz.github.io/SketchyBar/config/events#forcing-all-shell-scripts-to-run-and-the-bar-to-refresh"
  []
  (exec "--update"))

(defn query
  "Query informations and return the result as edn.

  See: https://felixkratz.github.io/SketchyBar/config/querying"
  [& args]
  (-> (apply shell {:out :string} "sketchybar" "--query" args)
      :out
      (json/parse-string csk/->kebab-case-keyword)))

(comment
  (bar {})
  (default {})
  (set :battery {:script "bb battery.jar"
                 :update_freq 5
                 :background.color "0x000000"
                 :label.color "0x000000"
                 :background.height 15})
  (add-item :battery :right)
  (add-graph :temperature :right 100)
  (add-space :space1 :left)
  (add-bracket :bracket1 :space.1 :space.2)
  (add-alias "Control Center,WiFi" :center)
  (add-slider :volume :left 100)
  (push-datapoint :temperature 20 21 22 23)
  (subscribe :battery :system_woke)

  (exec)
  (update)

  (query "battery")
  (query "defaults"))
