package com.github.rabitarochan.deeta;

import com.github.rabitarochan.deeta.fetcher.DefaultFetcher;
import com.github.rabitarochan.deeta.resolver.DefaultResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Deeta {

    private static final Logger LOG = LoggerFactory.getLogger(Deeta.class);

    private final DeetaRandom random;

    private final DeetaFetcher fetcher;

    private final DeetaResolver resolver;

    public Deeta() {
        this.random = new DeetaRandom();
        this.fetcher = new DefaultFetcher();
        this.resolver = new DefaultResolver(fetcher, random);
    }

    public String resolve(String key) {
        return resolve(key, createContext());
    }

    @SuppressWarnings("unchecked")
    public String resolve(String key, int seq) {
        return resolve(key, createContext(seq));
    }

    public String resolve(String key, DeetaContext context) {
        return resolver.resolve(key, context);
    }

    public DeetaContext createContext() {
        return createContext(1);
    }

    public DeetaContext createContext(int seq) {
        return new DeetaContext(seq, resolver, random);
    }

}
