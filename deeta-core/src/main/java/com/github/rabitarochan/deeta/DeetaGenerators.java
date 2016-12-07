package com.github.rabitarochan.deeta;

import com.github.rabitarochan.deeta.annotation.DeetaDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

public class DeetaGenerators {

    private static final Logger LOG = LoggerFactory.getLogger(DeetaGenerators.class);

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
            if (LOG.isDebugEnabled()) {
                LOG.debug("Register generator alias. [alias:{}, generator:{}]", dataSource.alias(), generator.getClass().getName());
            }
        }

        String key = dataSource.name().toLowerCase() + "." + generator.getClass().getSimpleName().toLowerCase();
        generatorMap.put(key, generator);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Register generator. [name:{}, generator:{}]", key, generator.getClass().getName());
        }

        if (key.endsWith("generator")) {
            String name = key.substring(0, key.length() - "generator".length());
            generatorMap.put(name, generator);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Register generator. [name, generator:{}]", name, generator.getClass().getName());
            }
        }
    }

}
