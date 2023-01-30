(ns cnn-chrome-extension.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!] :as async]
            [cljs-http.client :as http]
            [clojure.zip :as zip]
            [clojure.string]
            [hickory.zip]
            [hickory.core]
            [reagent.dom]
            [chromex.logging :refer-macros [log]]))

(def endpoint "https://lite.cnn.com")

(comment
  (devtools.core/install! [:formatters :hints]))

(defn escape-single-quote [x]
  (clojure.string/replace x #"&#39;" "'"))

(defn maybe-parse [z]
  (try
    (let [uls (-> z
                  zip/next
                  zip/next  ;; html
                  first
                  last
                  (nth 5)   ;; div.layout-homepage__lite
                  (nth 3)   ;; div.static
                  (nth 3)   ;; section
                  (nth 3)   ;; div.container--lite
                  (nth 5))] ;; ul
      (->> uls
           (filter vector?)
           (map (comp rest rest rest))
           (map (juxt (comp #(str endpoint %) :href second first)
                      (comp escape-single-quote last first)))))
    (catch js/Error e
      (log "failed to parse: " e)
      [])))

(defn render [data]
  (let [component
        [:div#main
         [:div.newslist
          [:span
           [:a.cnn {:href endpoint} "CNN"]
           #_" | "
           #_(.toString (js/Date.))]
          [:ul
           (for [[link title] data]
             [:li.news {:key link}
              [:span [:a.link {:href link :target :_blank} title]]])]]]]
    (reagent.dom/render
     [(constantly component)]
     (.getElementById js/document "app"))))

(defn init! []
  (log "init!")
  (go (-> (<! (http/get endpoint))
          :body
          hickory.core/parse
          hickory.core/as-hiccup
          hickory.zip/hiccup-zip
          maybe-parse
          render)))
