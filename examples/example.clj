(ns example
  (:require
   [sketchybar.core :as s]
   [sketchybar.extra :refer [event-loop]]))

;; This is an example config.

(defn init []
  (s/exec
   (s/bar {:position :top
           :height 40
           :blur_radius 30
           :color "0x40000000"}))

  (s/default {:padding_left 5
              :padding_right 5
              :icon.font "Hack Nerd Font:Bold:17.0"
              :label.font "Hack Nerd Font:Bold:14.0"
              :icon.color "0xffffffff"
              :label.color "0xffffffff"
              :icon.padding_left 4
              :icon.padding_right 4
              :label.padding_left 4
              :label.padding_right 4}))

(defn setup-time []
  (s/exec
   (s/add-item :time :right)
   (s/set
    :time
    {:icon.padding_right 0
     :label.padding_left 0})))

;; add deps for example
(require '[babashka.deps :as deps])
(deps/add-deps '{:deps {tick/tick {:mvn/version "0.6.0"}}})
(require '[tick.core :as tick])

(defn update-time []
  (let [time (tick/format (tick/formatter "HH:mm") (tick/time))]
    (s/exec
     (s/set :time {:label time}))))

(defn -main [& args]
  (init)

  ;; update `time` widget every 10 seconds
  (event-loop
   {:time {:fn update-time
           :duration-ms 10000}}))
