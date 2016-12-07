package com.github.rabitarochan.deeta;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GeneratorsTest {

    @Test
    public void hasNotAliasGeneratorTest() {
        Deeta deeta = new Deeta();

        {
            String s = deeta.resolve("${deetatest.hasnotalias}");
            assertThat(s, is("HasNotAlias"));
        }

        {
            String s = deeta.resolve("${deetatest.hasnotaliasgenerator}");
            assertThat(s, is("HasNotAlias"));
        }
    }

    @Test
    public void hasNotAnnotationGeneratorTest() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${deetatest.hasnotannotation}");
        assertThat(s, is("NULL"));
    }

    @Test
    public void testFactoryTest() {
        Deeta deeta = new Deeta();

        {
            String s = deeta.resolve("${deetatest.testfactory}");
            assertThat(s, is("TestFactory"));
        }

        {
            String s = deeta.resolve("${deetatest.test}");
            assertThat(s, is("NULL"));
        }
    }

    @Test
    public void unresolveAliasTest() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${unresolvealias}");
        assertThat(s, is("NULL"));
    }

}
