(ns db
  (:require [monger.core :as mg]))

(def database "book-catalogue")
(def host "127.0.0.1")
(def port 27017)
(def coll "books")

(defn db []
  (let [conn (mg/connect [:host host
                          :port port])]
    (mg/get-db conn database)))
