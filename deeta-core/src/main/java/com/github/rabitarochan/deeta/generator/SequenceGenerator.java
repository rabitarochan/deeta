package com.github.rabitarochan.deeta.generator;

import com.github.rabitarochan.deeta.DeetaContext;
import com.github.rabitarochan.deeta.DeetaGenerator;
import com.github.rabitarochan.deeta.annotations.DeetaDataSource;

@DeetaDataSource(name = "deeta", alias = "seq")
public class SequenceGenerator implements DeetaGenerator {

    @Override
    public String generate(DeetaContext context) {
        return String.valueOf(context.getSeq());
    }

}
