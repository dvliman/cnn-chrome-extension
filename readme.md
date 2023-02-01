
# CNN Chrome Extension 
A chrome extension to read CNN news (lite.cnn.com) in a single click.
https://chrome.google.com/webstore/detail/cnn-chrome-extension/lbfjecjnndgobkgagllainiphdbhhalg?hl=en&authuser=0

[![Build Status][build-status]][build-status-url]
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

<p align="center">
  <img src=https://github.com/dvliman/cnn-chrome-extension/blob/master/screenshot.png>
</p>

Start the shadow-cljs in Clojure REPL (i.e `cider-jack-in`) or `shadow-cljs watch extension` in a terminal and `cider-connect-cljs`

```
/bin/npx shadow-cljs -d nrepl/nrepl:1.0.0 -d cider/cider-nrepl:0.29.0 -d refactor-nrepl/refactor-nrepl:3.5.5 server`
```


Watch the extension build (wait until it says "Build completed" so you don't get Stale Output warning)

```
shadow.user> (shadow/watch :extension)
[:extension] Configuring build.
[:extension] Compiling ...
[:extension] Build completed. (196 files, 1 compiled, 2 warnings, 1.63s)
;; => :watching
```


Upgrade to CLJS REPL

```
shadow.user> (shadow/repl :extension)
To quit, type: :cljs/quit
;; => [:selected :extension]
cljs.user> 
```

Quit CLJS REPL

```
cljs.user> :cljs/quit
```

## License

Copyright © 2023 David Liman.

Distributed under the [MIT License](LICENSE). See [LICENSE](LICENSE).


[build-status-url]: https://github.com/dvliman/cnn-chrome-extension/actions/workflows/kondo.yml
[build-status]: https://github.com/dvliman/cnn-chrome-extension/actions/workflows/kondo.yml/badge.svg?branch=master
