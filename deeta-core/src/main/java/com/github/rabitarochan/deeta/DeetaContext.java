package com.github.rabitarochan.deeta;

import java.util.HashMap;
import java.util.Map;

public class DeetaContext {

    private final int seq;

    private final Map<String, Integer> indexMap;

    private final DeetaResolver resolver;

    private final DeetaRandom random;

    public DeetaContext(DeetaResolver resolver, DeetaRandom random) {
        this(1, resolver, random);
    }

    public DeetaContext(int seq, DeetaResolver resolver, DeetaRandom random) {
        this.seq = seq;
        this.indexMap = new HashMap<String, Integer>();
        this.resolver = resolver;
        this.random = random;
    }

    public int getSeq() {
        return seq;
    }

    public Integer getIndex(String key) {
        return indexMap.get(key);
    }

    public void setIndex(String key, int index) {
        indexMap.put(key, index);
    }

    public DeetaResolver getResolver() {
        return resolver;
    }

    public DeetaRandom getRandom() {
        return random;
    }

    public DeetaContext getNext() {
        int nextSeq = seq + 1;
        return new DeetaContext(nextSeq, resolver, random);
    }

}
