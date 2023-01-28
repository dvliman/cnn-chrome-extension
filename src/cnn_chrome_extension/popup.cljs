(ns cnn-chrome-extension.popup
  (:require [chromex.support :refer [runonce]]
            [cljs-http.client :as http]))

(defn init! [])

(runonce init!)

(.log js/console "test")
