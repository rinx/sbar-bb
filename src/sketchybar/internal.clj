(ns sketchybar.internal)

(defn map->kvs [m]
  (->> m
       (map (fn [[k v]]
              (let [v (if (keyword? v)
                        (name v)
                        v)]
                (str (name k) "=" v))))))

(comment
  (map->kvs {:update_freq 5
             :label.color "0x0000000"
             :background.height 15
             :script "bb test"}))
