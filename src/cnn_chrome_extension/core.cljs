(ns cnn-chrome-extension.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!]]
            [cljs-http.client :as http]
            [hickory.core :as hickory]
            [hickory.select :as selector]))

(def endpoint "https://lite.cnn.com/")

(comment
  (devtools.core/install! [:formatters :hints]))
(defn init! []
  (prn "init!")
  (go (-> (<! (http/get endpoint))
          :body
          hickory/parse
          hickory/as-hickory
          ))
  (prn "done"))
