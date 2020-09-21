# books

A [re-frame](https://github.com/day8/re-frame) application designed to catalogue and keyword a collection of books.
(but mostly to demo using pneumatic tubes to send re-frame events from clojure)

## Getting Started
Start the server from the clj repl in ns books.core with (start-server)
Create the pipe from the cljs repl in ns books.tubes with (tubes/create! tube)
Open a browser window at localhost:8280

Adding a book will send the name of the book to the server where it can be seen in the clj repl.
A re-frame event :acknowledge-book-added will also be sent from the server to the client which can be seen in the js console.

