(ns sketchybar.internal-test
  (:require
   [sketchybar.internal :as internal]
   [clojure.test :as t]))

(t/deftest impl-helper
  (t/testing "map-kvs"
    (t/is (= (internal/map->kvs
              {:update_freq 5
               :label.color "0x0000000"
               :background.height 15
               :script "bb test"})
             '("update_freq=5"
               "label.color=0x0000000"
               "background.height=15"
               "script=bb test")))))
