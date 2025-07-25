(ns test-calva-format.core
  (:require [missionary.core :as m]))

(defn via-fn
  [req]
  (m/via m/blk
         (let [deps (req :deps)]
           (+ 1 1))))

(defn sp-fn
  [req]
  (m/sp
   (let [deps (req :deps)]
     (+ 1 1))))