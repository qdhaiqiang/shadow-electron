(ns app.renderer.core
  (:require [reagent.core :refer [atom]]
            [reagent.dom :as rd]))

(enable-console-print!)

(defonce sdk (js/require "@jitsi/electron-sdk"))

(def domain "meet.jit.si")

(def options
  #js
  {:roomName "room-name"
   :width 700
   :height 600
   :parentNode (.querySelector js/document "#app-container")
   :lang "en"
   })

(def api (new js/JitsiMeetExternalAPI domain options))

(defn create-meeting
  "创建会议,参加会议"
  [room-name]
  (new js/JitsiMeetExternalAPI domain (options room-name)))


(defonce state (atom 0))

(defn root-component []
  [:div
   [:div.logos
    [:img.electron {:src "img/electron-logo.png"}]
    [:img.cljs {:src "img/cljs-logo.svg"}]
    [:img.reagent {:src "img/reagent-logo.png"}]]
   [:button
    {:on-click #(swap! state inc)}
    (str "Clicked " @state " times")]])

(defn ^:dev/after-load start! []
  (rd/render
   [root-component]
   (js/document.getElementById "app-container")))
