package com.github.rabitarochan.deeta;

import com.github.rabitarochan.deeta.annotations.DeetaDataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class DeetaGenerators {

    private final Map<String, DeetaGenerator> generatorMap;

    private static DeetaGenerators instance = null;

    private DeetaGenerators() {
        this.generatorMap = new HashMap<String, DeetaGenerator>();
        load();
    }

    public static DeetaGenerators getInstance() {
        if (instance == null) {
            instance = new DeetaGenerators();
        }
        return instance;
    }

    public DeetaGenerator resolve(String key) {
        return generatorMap.get(key);
    }

    protected void load() {
        ServiceLoader<DeetaGenerator> loader = ServiceLoader.load(DeetaGenerator.class);
        for (DeetaGenerator generator : loader) {
            DeetaDataSource dataSource = checkAnnotation(generator);
            register(generator, dataSource);
        }
    }

    private DeetaDataSource checkAnnotation(DeetaGenerator generator) {
        DeetaDataSource dataSource = generator.getClass().getAnnotation(DeetaDataSource.class);
        if (dataSource == null) {
            // TODO
            throw new UnsupportedOperationException("Generator class must be DeetaDataSource annotation.");
        }

        return dataSource;
    }

    private void register(DeetaGenerator generator, DeetaDataSource dataSource) {
        if (!dataSource.alias().equals("")) {
            generatorMap.put(dataSource.alias(), generator);
        }

        String key = dataSource.name().toLowerCase() + "." + generator.getClass().getSimpleName().toLowerCase();
        generatorMap.put(key, generator);

        if (key.endsWith("generator")) {
            generatorMap.put(key.substring(0, key.length() - "generator".length()), generator);
        }
    }

}
