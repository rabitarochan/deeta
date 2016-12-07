package com.github.rabitarochan.deeta.generator;

import com.github.rabitarochan.deeta.DeetaContext;
import com.github.rabitarochan.deeta.DeetaGenerator;
import com.github.rabitarochan.deeta.annotation.DeetaDataSource;

import java.util.UUID;

@DeetaDataSource(name = "deeta", alias = "uuid")
public class UuidGenerator implements DeetaGenerator {

    @Override
    public String generate(DeetaContext context) {
        return UUID.randomUUID().toString();
    }

}
