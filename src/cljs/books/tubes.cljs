(ns books.tubes
  (:require
   [re-frame.core :as re-frame]
   [pneumatic-tubes.core :as tubes]))

(defn on-receive [event-v]
  (.log js/console "received from server:" (str event-v))
  (re-frame/dispatch event-v))

(def tube (tubes/tube (str "ws://localhost:9091/ws") on-receive))
(def send-to-server (re-frame/after (fn [_ v] (tubes/dispatch tube v))))

(defn start []
  (tubes/create! tube))

(re-frame/reg-fx
  :send-to-server
  (fn [event]
    (tubes/dispatch tube event)))

;; These are re-frame events received from the server

(re-frame/reg-event-db
  :acknowledge-book-added
  (fn [db [_ book]]
    (assoc db :books (conj (:books db) book) )))

(re-frame/reg-event-db
  :initialize-db-received
  (fn [db [_ book-data]]
    (assoc db :books book-data)))

(re-frame/reg-event-db
  :remove-deleted-book
  (fn [db [_ book-id]]
    (.log js/console "Book ID: " book-id " to be deleted")
    (assoc db :books (remove #(= book-id (:_id %)) (:books db)))))
