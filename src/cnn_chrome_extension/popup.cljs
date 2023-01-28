(ns cnn-chrome-extension.popup
  (:require [chromex.support :refer [runonce]]
            [cnn-chrome-extension.core :as core]))

(runonce
 (core/init!))
