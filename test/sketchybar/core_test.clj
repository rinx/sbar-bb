(ns sketchybar.core-test
  (:require
   [sketchybar.core :as core]
   [clojure.test :as t]))

(t/deftest sbar-helper

  (t/testing "bar"
    (t/is (= (core/bar
              {:update_freq 5})
             '("--bar"
               "update_freq=5"))))

  (t/testing "default"
    (t/is (= (core/default
              {:update_freq 5})
             '("--default"
               "update_freq=5"))))

  (t/testing "set"
    (t/is (= (core/set
              :date
              {:update_freq 60})
             '("--set"
               "date"
               "update_freq=60"))))

  (t/testing "add-item"
    (t/is (= (core/add-item :date :right)
             '("--add"
               "item"
               "date"
               "right"))))

  (t/testing "add-graph"
    (t/is (= (core/add-graph :temperature :right 100)
             '("--add"
               "graph"
               "temperature"
               "right"
               "100"))))

  (t/testing "add-space"
    (t/is (= (core/add-space :space1 :left)
             '("--add"
               "space"
               "space1"
               "left"))))

  (t/testing "add-bracket"
    (t/is (= (core/add-bracket :bracket1 :space.1 :space.2)
             '("--add"
               "bracket"
               "bracket1"
               "space.1"
               "space.2"))))

  (t/testing "add-alias"
    (t/is (= (core/add-alias "Control Center,WiFi" :center)
             '("--add"
               "alias"
               "Control Center,WiFi"
               "center"))))

  (t/testing "add-slider"
    (t/is (= (core/add-slider :volume :left 100)
             '("--add"
               "slider"
               "volume"
               "left"
               "100"))))

  (t/testing "add-event"
    (t/is (= (core/add-event :event1)
             '("--add"
               "event"
               "event1")))
    (t/is (= (core/add-event :event2 :com.spotify.client.PlaybackStateChanged)
             '("--add"
               "event"
               "event2"
               "com.spotify.client.PlaybackStateChanged"))))

  (t/testing "push-datapoint"
    (t/is (= (core/push-datapoint :temperature 20 21 22 23)
             '("--push"
               "temperature"
               "20"
               "21"
               "22"
               "23"))))

  (t/testing "subscribe"
    (t/is (= (core/subscribe :battery :system_woke)
             '("--subscribe"
               "battery"
               "system_woke")))))
