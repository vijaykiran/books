(ns books.tubes
  (:require
   [re-frame.core :as re-frame]
   [pneumatic-tubes.core :as tubes]))

(defn on-receive [event-v]
  (.log js/console "received from server:" (str event-v))
  (re-frame/dispatch event-v))

(def tube (tubes/tube (str "ws://localhost:9091/ws") on-receive))
(def send-to-server (re-frame/after (fn [_ v] (tubes/dispatch tube v))))

(re-frame/reg-event-db
  :say-hello
  send-to-server
  (fn [db [_ name]]
    (.log js/console (str "Hello " name))
    db))

(re-frame/reg-event-db
  :say-hello-processed
  (fn [db _]
    (.log js/console "Yay!!!")
    db))

(re-frame/reg-event-db
  :acknowledge-book-added
  (fn [db _]
    (.log js/console "(serverside) book added")))
