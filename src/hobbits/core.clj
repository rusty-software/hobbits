(ns hobbits.core)

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn needs-matching-part?
  [part]
  (re-find #"^left-" (:name part)))

(defn make-matching-part
  [part] {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn sym-via-map
  "Makes the parts symmetrical using map instead of recur"
  [asym-body-parts]
  (let [sym-if-necessary (fn [part]
                           (if (needs-matching-part? part)
                             [part (make-matching-part part)]
                             [part]))]
    (vec (apply concat (map sym-if-necessary asym-body-parts)))))

(defn symmetrize-body-parts
  "Expects a seq of maps which nave :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts
            final-body-parts (conj final-body-parts part)]
        (if (needs-matching-part? part)
          (recur remaining (conj final-body-parts (make-matching-part part)))
          (recur remaining final-body-parts))))))

(defn better-symmetrize-body-parts
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (let [final-body-parts (conj final-body-parts part)]
              (if (needs-matching-part? part)
                (conj final-body-parts (make-matching-part part))
                final-body-parts)))
          []
          asym-body-parts))

(defn symmetrize-body-part
  "Given a limit and single body part map, returns a vector with sym-count similar parts"
  [sym-count {:keys [name size]}]
  (reduce (fn [v i] (conj v {:name (format "%s %s" name i) :size size}))
          []
          (range 1 (inc sym-count))))

(defn general-symmetrize-body-parts
  "Given a vector of part maps, returns a vector with maps symmetrized to a given value"
  [parts sym-count]
  (let [sym-parts (map (partial symmetrize-body-part sym-count) parts)
        somefunc (fn [partv acc]
                   (loop [parts partv
                          acc acc]
                     (if (empty? parts)
                       acc
                       (recur (rest parts) (conj acc (first parts))))))]
    (loop [partv sym-parts
           acc []]
      (if (empty? partv)
        acc
        (recur (rest partv) (somefunc (first partv) acc))))))

(defn hit
  [asym-body-parts]
  (let [sym-parts (sym-via-map asym-body-parts)
        body-part-size-sum (reduce + 0 (map :size sym-parts))
        target (inc (rand body-part-size-sum))
        _ (println "sum: " body-part-size-sum "; target: " target)]
    (loop [[part & rest] sym-parts
           accumulated-size (:size part)]
      (do
        (println "current part:" (:name part) "; accumulated size:" accumulated-size)
        (if (> accumulated-size target)
          part
          (recur rest (+ accumulated-size (:size part))))))))

(defn -main
  [& args]
  )
