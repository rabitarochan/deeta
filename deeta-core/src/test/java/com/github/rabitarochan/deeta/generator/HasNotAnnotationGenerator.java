package com.github.rabitarochan.deeta.generator;

import com.github.rabitarochan.deeta.DeetaContext;
import com.github.rabitarochan.deeta.DeetaGenerator;

public class HasNotAnnotationGenerator implements DeetaGenerator {

    @Override
    public String generate(DeetaContext context) {
        return "HasNotAnnotation";
    }

}
