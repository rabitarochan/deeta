package com.github.rabitarochan.deeta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class DefaultDeetaFetcher implements DeetaFetcher {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDeetaFetcher.class);

    private final Map<String, Object> valueMap;

    public DefaultDeetaFetcher() {
        this.valueMap = load();
    }

    @Override
    @SuppressWarnings("unchecked")
    public String fetch(String key, DeetaContext context) {
        Object obj = fetchRecursive(key, valueMap);

        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof List) {
            return fetchByList((List<String>) obj, context);
        }

        if (obj == null) {
            return null;
        } else {
            return obj.toString();
        }
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> load() {
        Yaml yaml = new Yaml();
        InputStream stream = getClass().getResourceAsStream("/deeta.yml");
        Map<String, Object> map = (Map<String, Object>) yaml.loadAs(stream, Map.class);
        return map;
    }

    @SuppressWarnings("unchecked")
    private Object fetchRecursive(String key, Map<String, Object> m) {
        String[] keys = key.split("\\.");
        String currentKey = keys[0];

        LOG.trace("[key:{}, keys:{}, currentKey:{}]", key, keys, currentKey);

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

    private String fetchByList(List<String> xs, DeetaContext context) {
        return xs.get(context.getRandom().nextInt(xs.size()));
    }

}
