package com.github.rabitarochan.deeta;

import com.github.rabitarochan.deeta.fetcher.DefaultFetcher;
import com.github.rabitarochan.deeta.resolver.DefaultResolver;

import java.util.logging.Logger;

public class Deeta {

    private static final Logger LOG = Logger.getLogger(Deeta.class.getName());

    private final DeetaRandom random;

    private final DeetaFetcher fetcher;

    private final DeetaResolver resolver;

    public Deeta() {
        this.random = new DeetaRandom();
        this.fetcher = new DefaultFetcher(random);
        this.resolver = new DefaultResolver(fetcher, random);
    }

    @SuppressWarnings("unchecked")
    public String resolve(String key) {
        String x = resolver.resolve(key, new String[] {});
        return x;
    }

}
