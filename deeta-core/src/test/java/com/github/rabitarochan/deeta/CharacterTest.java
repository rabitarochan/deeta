package com.github.rabitarochan.deeta;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class CharacterTest {

    @Test
    public void simpleCharacterTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("???");
        assertThat(res.matches("[a-z]{3}"), is(true));
    }

    @Test
    public void multipleCharacterTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("???-???");
        assertThat(res.matches("[a-z]{3}-[a-z]{3}"), is(true));
    }

    @Test
    public void simpleCharacterFromYamlTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${test.character.simple}");
        assertThat(res.matches("[a-z]{3}"), is(true));
    }

    @Test
    public void multipleCharacterFromYamlTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${test.character.multiple}");
        assertThat(res.matches("[a-z]{3}-[a-z]{3}"), is(true));
    }

    @Test
    public void longCharacterTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("???????????????");
        System.out.println(res);
        assertThat(res.matches("[a-z]{15}"), is(true));
    }

    @Test
    public void escapedSimpleCharacterTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("\\?\\?\\?");
        assertThat(res, is("???"));
    }

    @Test
    public void escapedMultipleCharacterTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("\\?\\?\\?-\\?\\?\\?");
        assertThat(res, is("???-???"));
    }

    @Test
    public void escapedSimpleCharacterFromYamlTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${test.character.escapedSimple}");
        assertThat(res, is("???"));
    }

    @Test
    public void escapedMultipleCharacterFromYamlTest() {
        Deeta deeta = new Deeta();

        String res = deeta.resolve("${test.character.escapedMultiple}");
        assertThat(res, is("???-???"));
    }

}
