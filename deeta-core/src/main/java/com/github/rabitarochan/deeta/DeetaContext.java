package com.github.rabitarochan.deeta;

import java.util.HashMap;
import java.util.Map;

public class DeetaContext {

    private final int seq;

    private final Map<String, Integer> indexMap;

    private final Map<String, Object> envMap;

    private final DeetaRandom random;

    public DeetaContext(DeetaRandom random) {
        this(1, random);
    }

    public DeetaContext(int seq, DeetaRandom random) {
        this.seq = seq;
        this.indexMap = new HashMap<String, Integer>();
        this.envMap = new HashMap<String, Object>();
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

    public Object getEnv(String key) {
        return envMap.get(key);
    }

    public void setEnv(String key, Object obj) {
        envMap.put(key, obj);
    }

    public DeetaRandom getRandom() {
        return random;
    }

    public DeetaContext getNext() {
        int nextSeq = seq + 1;
        return new DeetaContext(nextSeq, random);
    }

}
