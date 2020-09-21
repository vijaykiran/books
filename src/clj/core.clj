(ns core
  (:require [org.httpkit.server :as server]
            [pneumatic-tubes.core :as tubes]
            [pneumatic-tubes.httpkit :as tubes-httpkit]))

(def tx (tubes/transmitter))
(def dispatch-to (partial tubes/dispatch tx))
(defonce server (atom nil))

(def rx
  (tubes/receiver
    {:say-hello
     (fn [tube [_ name]]
       (println "hello" name)
       (dispatch-to tube [:say-hello-processed]))
     :books.events/set-name
     (fn [tube [_ name]]
       (println "Name set to " name "."))
     :books.events/add-book
     (fn [tube [_ name]]
       (println "Book: " name " added.")
       (dispatch-to tube [:acknowledge-book-added]))}))

(def handler (tubes-httpkit/websocket-handler rx))

(defn start-server []
  (reset! server (server/run-server handler {:port 9091})))

(defn stop-server []
  (when-not (nil? @server)
    (@server)
    (reset! server nil)))

(defn -main [& args]
  (println "Running -main"))
