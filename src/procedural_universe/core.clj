(ns procedural-universe.core)

(def order [:universe :galaxy :star :solar-system :planet :continent :country :city])

(defn next-layer [k]
  (second (drop-while #(not= k %) (cycle order))))

(defn update-leaves
  ([f m layer]
     (if (empty? m)
       (f layer m)
       (into {} (map (fn [[k v]] [k (update-leaves f v (next-layer layer) )]) m))))
  ([f m]
     (update-leaves f m :universe)))

(defn append-to-k [k i]
  (keyword (str (name k) "-" i)))

(defn add-stuff [layer m]
  (assoc m (append-to-k layer (rand-int 10000)) {} (append-to-k layer (rand-int 10000)) {}))

(def update (partial update-leaves add-stuff))

(nth (iterate update {}) (count order))
