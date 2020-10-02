# books

A [re-frame](https://github.com/day8/re-frame) application designed to catalogue and keyword books. Many historical reference
works are available on archive.org but navigating through them is not nearly as easy as it could be. There is not the same
organisation that would be found in a physical reference library and often there are multiple copies of the same works with
no easy way to differentiate the good from the bad without opening them all and taking a look. Also searching within is not
possible as older books are always stored as page photographs. This app aims to speed up access to this kind of material.

## Getting Started
C-c C-x j j  (M-x cider-jack-in-clj) select lein as the app
(start-dev)

C-c C-x s s  (M-x cider-connect-sibling-cljs)
This will open a cljs repl and run the shadow-cljs build and watch.

Open one or more browser windows at localhost:8280

![screenshot](docs/screenshot.png)

## Details

Book details are entered in the re-frame/clojurescript front end. They are then passed to the clojure backend where they are 
stored in the database and passed back to the frontend for display. Any number of front end instances can be connected and
will receive updates as they are entered.

![add book image](docs/adding-a-book.svg)

## TODO

Add editing of books. Currently its only delete and re-add

Add sorting

Add filtering

Authentication. It needs some if it is to be anything other than a private tool. Also needs to separate areas of information
into public and private. book details and keywords should be public, but local copy links and notes should be privately
available for each user.

Book model uses only strings. These should at least be checked in the case of links and dates.

Add a local link for each book, and a mechanism to open it on click. Probably send an event to the server which can easily
open a file using open or xd-open. Browser file:// link would probably be ok as a first step.

