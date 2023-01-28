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
          (selector/select
           (selector/descendant
            (selector/class "container--lite" #_"afe4286c")
            (selector/and (selector/tag :ul))
            (selector/and (selector/tag :li))
            (selector/tag :a)))
          cljs.pprint/pprint))
  (prn "done"))

(init!)


(def doc (go (-> (<! (http/get endpoint))
                 :body
                 hickory/parse
                 hickory/as-hickory)))

doc
