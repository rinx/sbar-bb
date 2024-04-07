(ns sketchybar.impl-test
  (:require
   [sketchybar.impl :as impl]
   [clojure.test :as t]))

(t/deftest impl-helper
  (t/testing "map-kvs"
    (t/is (= (impl/map->kvs
              {:update_freq 5
               :label.color "0x0000000"
               :background.height 15
               :script "bb test"})
             '("update_freq=5"
               "label.color=0x0000000"
               "background.height=15"
               "script=bb test")))))
