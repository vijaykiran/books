(ns books.events
  (:require
   [re-frame.core :as re-frame]
   [books.db :as db]
   [books.tubes :as tubes]))

(defn allocate-next-id
  "Returns one more than the current largest id. This will break when deletion of books is added."
  [books]
  (inc (count books)))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-fx
  ::set-name
  tubes/send-to-server
  (fn [{:keys [db]} [_ name]]
    { :db (assoc db :name name)}))

(re-frame/reg-event-db
  ::set-name-2
  (fn [db [_ name]]
    (assoc db :name name)))

(re-frame/reg-event-db
  ::add-book
  tubes/send-to-server
  (fn [db [_ title]]
    (let [books (:books db)
          id (allocate-next-id books)]
      (assoc db :books (conj books {:id id :title title :author "author goes here" :keywords "keywords go here"})))))
