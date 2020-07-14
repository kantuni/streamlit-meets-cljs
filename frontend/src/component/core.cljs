(ns component.core
  (:require [reagent.core :refer [atom]]
            [reagent.dom :refer [render]]
            [component.lib :refer [add-streamlit-message-listener
                                   send-message-to-streamlit]]))

(defonce counter (atom 0))

(defn increment-counter []
  (swap! counter inc)
  (send-message-to-streamlit :set-component-value {:value @counter}))

(defn app []
  [:button {:on-click increment-counter} "Click Me!"])

(defn ^:dev/after-load start []
  (add-streamlit-message-listener)
  (send-message-to-streamlit :component-ready {:apiVersion 1})
  (send-message-to-streamlit :set-frame-height {:height 50})
  (render [app] (.getElementById js/document "app")))

(defn ^:export init []
  (start))
