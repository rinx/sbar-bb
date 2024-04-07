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
               "update_freq=60")))))
