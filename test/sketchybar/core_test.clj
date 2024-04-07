(ns sketchybar.core-test
  (:require
   [sketchybar.core :as core]
   [clojure.test :as t]))

(t/deftest impl-helper
  (t/testing "map-kvs"
    (t/is (= (core/map->kvs {:update_freq 5
                             :label.color "0x0000000"
                             :background.height 15
                             :script "bb test"})
             '("update_freq=5"
               "label.color=0x0000000"
               "background.height=15"
               "script=bb test")))))

(t/deftest sbar-helper
  (t/testing "bar"
    (t/is (= (core/bar {:update_freq 5})
             '("--bar"
               "update_freq=5")))))
