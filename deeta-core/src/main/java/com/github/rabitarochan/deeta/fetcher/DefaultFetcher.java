package com.github.rabitarochan.deeta.fetcher;

import com.github.rabitarochan.deeta.DeetaFetcher;
import com.github.rabitarochan.deeta.DeetaRandom;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class DefaultFetcher implements DeetaFetcher {

    private static final Logger LOG = Logger.getLogger(DefaultFetcher.class.getName());

    private final Map<String, Object> map;

    private final DeetaRandom random;

    public DefaultFetcher(DeetaRandom random) {
        this.map = load();
        this.random = random;
    }

    @Override
    public String fetch(String key) {
        Object obj = fetchRecursive(key, map);

        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof List) {
            List<String> xs = (List<String>) obj;
            return xs.get(random.nextInt(xs.size()));
        }

        return String.valueOf(obj);
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> load() {
        Yaml yaml = new Yaml();
        InputStream stream = getClass().getResourceAsStream("/default.yml");
        Map<String, Object> map = (Map<String, Object>) yaml.loadAs(stream, Map.class);
        return map;
    }

    private Object fetchRecursive(String key, Map<String, Object> m) {
        String[] keys = key.split("\\.");
        String currentKey = keys[0];

        LOG.fine(String.format("[key:%s, keys:%s, currentKey:%s]", key, keys, currentKey));

        if (!m.containsKey(currentKey)) {
            return null;
        }
        if (keys.length == 1) {
            return m.get(currentKey);
        }

        String nextKey = key.replaceFirst(currentKey + "\\.", "");
        Map<String, Object> nextMap = (Map<String, Object>) m.get(currentKey);

        return fetchRecursive(nextKey, nextMap);
    }

}
