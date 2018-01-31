(ns tests.all
  (:require
    [cljs.test :refer [deftest is testing run-tests async use-fixtures]]
    [day8.re-frame.test :refer [run-test-async run-test-sync wait-for]]
    [district.ui.router-google-analytics]
    [district.ui.router.events :as router-events]
    [district.ui.router]
    [mount.core :as mount]
    [re-frame.core :refer [reg-event-fx dispatch-sync subscribe reg-fx reg-sub dispatch trim-v]]))


(reg-event-fx
  ::page-view
  [trim-v]
  (constantly nil))


(reg-fx
  :ga/page-view
  (fn [[url]]
    (dispatch [::page-view url])))


(defn page-view? [path]
  (fn [[event page-view-path]]
    (and (= event ::page-view)
         (= page-view-path path))))


(use-fixtures
  :each
  {:after
   (fn []
     (mount/stop))})


(deftest tests
  (run-test-async
    (-> (mount/with-args {:router {:routes [["/a" :route/a]
                                            ["/b/:b" :route/b]]
                                   :default-route :route/a}
                          :router-google-analytics {:enabled? true}})
      (mount/start))

    (wait-for [(page-view? "/a")]
      (dispatch [::router-events/navigate :route/b {:b "abc"}])
      (wait-for [(page-view? "/b/abc")]))))
