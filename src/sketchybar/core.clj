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
  [graph position width]
  ["--add" "graph" (name graph) (name position) (str width)])

(defn add-space
  [space position]
  ["--add" "space" (name space) (name position)])

(defn add-bracket
  [bracket & members]
  (flatten [["--add" "bracket" (name bracket) (map name members)]]))

(defn add-alias
  [app position]
  ["--add" "alias" app (name position)])

(defn add-slider
  [slider position width]
  ["--add" "slider" (name slider) (name position) (str width)])

(defn add-event
  ([event]
   ["--add" "event" (name event)])
  ([event notification]
   ["--add" "event" (name event) (name notification)]))

(defn push-datapoint
  [graph & data]
  (flatten [["--push" (name graph)] (map str data)]))

(defn subscribe
  [item & events]
  (flatten [["--subscribe" (name item)] (map name events)]))

(defn exec
  [& args]
  (->> (flatten args)
       (filter some?)
       (apply shell "sketchybar")))

(defn update
  []
  (exec "--update"))

(defn query
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

  (query "battery"))
