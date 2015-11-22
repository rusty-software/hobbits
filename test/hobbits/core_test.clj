(ns hobbits.core-test
  (:require [clojure.test :refer :all]
            [hobbits.core :refer :all]))

(deftest symmetrize-body-part-tests
  (testing "Given a count and a single body part map, returns a vector with count similar parts"
    (let [part {:name "eye" :size 1}
          expected [{:name "eye 1" :size 1}
                    {:name "eye 2" :size 1}
                    {:name "eye 3" :size 1}]]
      (is (= #{expected} #{(symmetrize-body-part 3 part)})))))

;(deftest general-symmetrize-body-parts-tests
;  (testing "Given a map of body parts and needing 3 total, return a map with 1, 2, and 3"
;    (let [parts [{:name "eye" :size 1}
;                 {:name "ear" :size 1}
;                 {:name "shoulder" :size 3}]
;          expected [{:name "eye 1" :size 1}
;                    {:name "eye 2" :size 1}
;                    {:name "eye 3" :size 1}
;                    {:name "ear 1" :size 1}
;                    {:name "ear 2" :size 1}
;                    {:name "ear 3" :size 1}
;                    {:name "shoulder 1" :size 3}
;                    {:name "shoulder 2" :size 3}
;                    {:name "shoulder 3" :size 3}]
;          actual (general-symmetrize-body-parts parts 3)]
;      (is (= #{expected} #{actual})))) )
