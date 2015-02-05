package io.chesslave.sugar;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Maps {

    public static <K, V> Builder<K, V> build() {
        return new Builder<>(new HashMap<>());
    }

    public static class Builder<K, V> {

        private final Map<K, V> map;

        public Builder(Map<K, V> state) {
            this.map = Ensure.notNull(state, "cannot create a map Builder with a null state");
        }

        public Builder<K, V> add(K key, V value) {
            map.put(key, value);
            return this;
        }

        public Builder<K, V> add(Map<K, ? extends V> other) {
            map.putAll(Ensure.notNull(other, "cannot merge a null map"));
            return this;
        }

        public Map<K, V> get() {
            return map;
        }

        public Map<K, V> getUnmodifiable() {
            return Collections.unmodifiableMap(map);
        }
    }
}
