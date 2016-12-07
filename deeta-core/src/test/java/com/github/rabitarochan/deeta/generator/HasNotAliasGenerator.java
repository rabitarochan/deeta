package com.github.rabitarochan.deeta.generator;

import com.github.rabitarochan.deeta.DeetaContext;
import com.github.rabitarochan.deeta.DeetaGenerator;
import com.github.rabitarochan.deeta.annotation.DeetaDataSource;

@DeetaDataSource(name = "deetatest")
public class HasNotAliasGenerator implements DeetaGenerator {

    @Override
    public String generate(DeetaContext context) {
        return "HasNotAlias";
    }

}
