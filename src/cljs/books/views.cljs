(ns books.views
  (:require
   [re-frame.core :as re-frame]
   [books.subs :as subs]
   [books.events :as events]
   [clojure.string :as str]
   [reagent.core  :as reagent]
   ))
(defn book-input [{:keys [title on-save on-stop]}]
  (let [val  (reagent/atom title)
        stop #(do (reset! val "")
                  (when on-stop (on-stop)))
        save #(let [v (-> @val str str/trim)]
                (on-save v)
                (stop))]
    (fn [props]
      [:input (merge (dissoc props :on-save :on-stop :title)
                     {:type        "text"
                      :value       @val
                      :auto-focus  true
                      :on-blur     save
                      :on-change   #(reset! val (-> % .-target .-value))
                      :on-key-down #(case (.-which %)
                                      13 (save)
                                      27 (stop)
                                      nil)})])))

(defn book-item []
  (fn [{:keys [id title author keywords]}]
    [:li
     [:div.view
      [:h2 title]
      [:h3 author]
      [:p keywords]]]))

(defn book-entry []
  [book-input
   {:id "new-book"
    :placeholder "Book name"
    :on-save #(when (seq %)
                (re-frame/dispatch [::events/add-book %]))}])

(defn book-list []
  (let [visible-books (re-frame/subscribe [::subs/books])]
    [:section#main
     ;; [:p (str @visible-books)]
     [:ul#todo-list
      (for [book  @visible-books]
        ^{:key (:id book)} [book-item book])]
     ]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 @name]
     [book-entry]
     [book-list]]))
