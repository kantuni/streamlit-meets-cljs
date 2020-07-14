(ns component.lib)

(def streamlit-message-types
  {:component-ready "streamlit:componentReady"
   :set-component-value "streamlit:setComponentValue"
   :set-frame-height "streamlit:setFrameHeight"
   :render "streamlit:render"})

(defn type->streamlit-message [type]
  (get streamlit-message-types type))

(defn send-message-to-streamlit [type data]
  (let [message (into {:isStreamlitMessage true
                       :type (type->streamlit-message type)} data)]
    (.postMessage (.-parent js/window) (clj->js message) "*")))

(defn on-render [data]
  (.log js/console data))

(defn on-message [event]
  (let [message-type (.. event -data -type)]
    (when (= message-type (type->streamlit-message :render))
      (on-render (.-data event)))))

(defn add-streamlit-message-listener []
  (.addEventListener js/window "message" on-message))
