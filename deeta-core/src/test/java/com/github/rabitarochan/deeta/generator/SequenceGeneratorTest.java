package com.github.rabitarochan.deeta.generator;

import com.github.rabitarochan.deeta.Deeta;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

public class SequenceGeneratorTest {

    @Test
    public void defaultSequenceTest() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${seq}");
        assertThat(s, is("1"));
    }

    @Test
    public void applySequenceTest() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${seq}", 123);
        assertThat(s, is("123"));
    }

    @Test
    public void fromSimpleNameTest() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${deeta.sequence}");
        assertThat(s, is("1"));
    }

    @Test
    public void fromFullNameTest() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${deeta.sequencegenerator}");
        assertThat(s, is("1"));
    }

}
