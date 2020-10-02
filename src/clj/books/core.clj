(ns books.core
  (:require [books.handler :refer [handler]]
            [org.httpkit.server :as server]))

(defonce server (atom nil))

(defn start-server []
  (reset! server (server/run-server handler {:port 9091})))

(defn stop-server []
  (when-not (nil? @server)
    (@server)
    (reset! server nil)))

(defn -main [& args]
  (println "Running -main")
  (start-server))
