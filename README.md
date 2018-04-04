# district-ui-router-google-analytics

[![Build Status](https://travis-ci.org/district0x/district-ui-router-google-analytics.svg?branch=master)](https://travis-ci.org/district0x/district-ui-router-google-analytics)

Clojurescript [re-mount](https://github.com/district0x/d0x-INFRA/blob/master/re-mount.md) module,
that extends [district-ui-router](https://github.com/district0x/district-ui-router) to automatically report page views into Google Analytics. 

## Installation
Add `[district0x/district-ui-router-google-analytics "1.0.0"]` into your project.clj  
Include `[district.ui.router-google-analytics]` in your CLJS file, where you use `mount/start`

## district.ui.router-google-analytics
This namespace contains router-google-analytics [mount](https://github.com/tolitius/mount) module.

You can pass following args to initiate this module: 
* `:enabled?` Pass true if you want enable reporting to Google Analytics. Default value is true if `goog.DEBUG` is false and
false if `goog.DEBUG` is true. 


```clojure
  (ns my-district.core
    (:require [mount.core :as mount]
              [district.ui.router]
              [district.ui.router-google-analytics]))
              
  (-> (mount/with-args
        {:router {:routes [["/a" :route/a]
                           ["/b/:b" :route/b]]
                  :default-route :route/a}
         :router-google-analytics {:enabled? true}})
    (mount/start))
```

You'll also need to add Google Analytics snippet into your `index.html` with our tracking ID. May look like this, but
I recommend you to copy-paste yours from Analytics website. You should also remove initial pageView line.   
```html
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
ga('create', 'UA-XXXXX-Y', 'auto');
</script>
```

That's all you need to do, now page views will be automatically reported into your Google Analytics. 

## Development
```bash
lein deps
# To run tests and rerun on changes
lein doo chrome tests
```