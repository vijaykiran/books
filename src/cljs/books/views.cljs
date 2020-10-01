(ns books.views
  (:require
   [re-frame.core :as re-frame]
   [books.subs :as subs]
   [books.events :as events]
   [clojure.string :as str]
   [reagent.core  :as reagent]
   ))

(defn input-element
  "An input element which updates its value on change"
  [name placeholder  value]
  [:input {:name name
           :placeholder placeholder
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn title-input [title-atom]
  (input-element "title" "Book title" title-atom))

(defn author-input [author-atom]
  (input-element "author" "Author" author-atom))

(defn keywords-input [keywords-atom]
  (input-element "keywords" "Keywords" keywords-atom))

(defn book-form []
  (let [title-input-atom    (reagent/atom "")
        author-input-atom   (reagent/atom "")
        keywords-input-atom (reagent/atom "")]
    (fn []
      [:form
       {:on-submit (fn [e] (.preventDefault e)
                     (re-frame/dispatch [::events/add-book {:title    @title-input-atom
                                                            :author   @author-input-atom
                                                            :keywords @keywords-input-atom}]))}
       [title-input    title-input-atom]
       [author-input   author-input-atom]
       [keywords-input keywords-input-atom]
       [:button {:type :submit} "Submit"]])))

(defn book-item []
  (fn [{:keys [_id title author keywords]}]
    [:li
     [:div.view
      [:h2 title]
      [:h3 author]
      [:p keywords]
      [:button {:on-click #(re-frame/dispatch [::events/delete-book _id])} "Delete"]]]))

(defn book-list []
  (let [visible-books (re-frame/subscribe [::subs/books])]
    [:section#main
     [:ul#todo-list
      (for [book  @visible-books]
        ^{:key (:_id book)} [book-item book])]]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [:h1 @name]
     [book-form]
     [book-list]]))
