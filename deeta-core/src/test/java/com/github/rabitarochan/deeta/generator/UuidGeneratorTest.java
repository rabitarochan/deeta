package com.github.rabitarochan.deeta.generator;

import com.github.rabitarochan.deeta.Deeta;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

public class UuidGeneratorTest {

    @Test
    public void test() {
        Deeta deeta = new Deeta();

        String s = deeta.resolve("${uuid}");
        assertThat(s.matches("\\S{8}-\\S{4}-\\S{4}-\\S{4}-\\S{12}"), is(true));
    }

}
