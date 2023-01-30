(ns cnn-chrome-extension.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!] :as async]
            [cljs-http.client :as http]
            [clojure.zip :as zip]
            [clojure.string :as str]
            [hickory.zip]
            [hickory.core]
            [reagent.dom]))

(def endpoint "https://lite.cnn.com/")

(comment
  (devtools.core/install! [:formatters :hints]))

(defn maybe-parse [z]
  (try
    (let [uls (-> z
                  zip/next
                  zip/next ;; html
                  first
                  last
                  (nth 5) ;; div.layout-homepage__lite
                  (nth 3) ;; div.static
                  (nth 3) ;; section
                  (nth 3) ;; div.container--lite
                  (nth 5))] ;; ul
      (->> uls
           (filter vector?)
           (map (comp rest rest rest))
           (map (juxt (comp :href second first)
                      (comp str/trim last first)))))
    (catch js/Error e
      (prn "failed to parse: " e)
      [])))


(defn render [data]
  (let [component
        [:div#main
         [:ul.newslist
          (for [[link title] data]
            [:li.news [:a {:href link} title]])]]]
    (reagent.dom/render
     [component]
     (.getElementById js/document "app"))))

(defn init! []
  (prn "init!")
  (go (-> (<! (http/get endpoint))
          :body
          hickory.core/parse
          hickory.core/as-hiccup
          hickory.zip/hiccup-zip
          maybe-parse
          render))
  (prn "done"))

(go (-> (<! (http/get endpoint))
          :body
          hickory.core/parse
          hickory.core/as-hiccup
          hickory.zip/hiccup-zip
          maybe-parse

          cljs.pprint/pprint))
