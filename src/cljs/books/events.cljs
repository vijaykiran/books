(ns books.events
  (:require
   [re-frame.core :as re-frame]
   [books.db :as db]
   [books.tubes :as tubes]))

;; These are internal re-frame events. Events coming from the server are in books.tubes

(re-frame/reg-event-db
  ::initialize-db
  tubes/send-to-server
  (fn [_ _]
    db/default-db))

(re-frame/reg-event-fx
  ::set-name-2
  tubes/send-to-server
  (fn [{:keys [db]} [_ name]]
    { :db (assoc db :name name)}))

(re-frame/reg-event-db
  ::set-name
  tubes/send-to-server
  (fn [db [_ name]]
    (assoc db :name name)))

(re-frame/reg-event-db
  ::add-book
  tubes/send-to-server
  (fn [db [_ book]]
    db))

(re-frame/reg-event-db
  ::delete-book
  tubes/send-to-server
  (fn [db [_ book-id]]
    db))

(re-frame/reg-event-db
  ::show-add-book
  (fn [db [_ _]]
    (assoc db :show-add-book true)))

(re-frame/reg-event-db
  ::hide-add-book
  (fn [db [_ _]]
    (assoc db :show-add-book false)))

(re-frame/reg-event-db
  ::toggle-add-book
  (fn [db [_ _]]
    (assoc db :show-add-book (not (:show-add-book db)))))
