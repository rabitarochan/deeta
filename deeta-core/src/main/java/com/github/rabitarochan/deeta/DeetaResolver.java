package com.github.rabitarochan.deeta;

public interface DeetaResolver {

    String resolve(String key, DeetaContext context);

    String fetch(String key, DeetaContext context);

}
