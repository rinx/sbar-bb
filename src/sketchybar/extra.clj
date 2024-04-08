(ns sketchybar.extra
  (:require
   [clojure.core.async :as async :refer [go-loop <!]]))

(defn- dispatch [{:keys [name fn duration-ms]}]
  (let [ch (async/chan)]
    (go-loop [i 0]
      (try
        (fn)
        (catch Exception e
          (println
           (str "an error occurred on " name ":" (.getMessage e)))))
      (<! (async/timeout duration-ms))
      (recur (inc i)))
    ch))

(defn event-loop
  [m]
  (->> m
       (map (fn [[k v]]
              {:name (name k)
               :fn (:fn v)
               :duration-ms (:duration-ms v)}))
       (map dispatch)
       (async/alts!!)))

(comment
  (event-loop {:time {:fn (fn []
                            (println "fetch time"))
                      :duration-ms 5000}
               :date {:fn (fn []
                            (println "fetch date"))
                      :duration-ms 60000}}))
