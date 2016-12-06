package com.github.rabitarochan.deeta.resolver;

import com.github.rabitarochan.deeta.DeetaFetcher;
import com.github.rabitarochan.deeta.DeetaRandom;
import com.github.rabitarochan.deeta.DeetaResolver;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultResolver implements DeetaResolver {

    private static final Logger LOG = Logger.getLogger(DefaultResolver.class.getName());

    private static final String VARIABLE_PATTERN_STRING = "(?<!\\\\)\\$\\{.+?\\}";

    private static final String NUMERIC_PATTERN_STRING = "(?<!\\\\)#+";

    private static final Pattern VARIABLE_PATTERN = Pattern.compile(VARIABLE_PATTERN_STRING);

    private static final Pattern NUMERIC_PATTERN = Pattern.compile(NUMERIC_PATTERN_STRING);

    private final DeetaFetcher fetcher;

    private final DeetaRandom random;

    public DefaultResolver(DeetaFetcher fetcher, DeetaRandom random) {
        this.fetcher = fetcher;
        this.random = random;
    }

    @Override
    public String resolve(String key, String[] args) {
        String res = fetcher.fetch(key);
        res = numeric(res);
        res = variable(res);
        res = unescape(res);
        return res;
    }

    private String numeric(String s) {
        String res = s;
        Matcher m = NUMERIC_PATTERN.matcher(res);
        while (m.find()) {
            String matchString = m.group();
            res = m.replaceFirst(generateNumeric(matchString.length()));
        }
        return res;
    }

    private String variable(String s) {
        String res = s;
        Matcher m = VARIABLE_PATTERN.matcher(res);
        while (m.find()) {
            String matchString = m.group();
            String key = matchString.substring(2, matchString.length() - 1);
            LOG.finer("key:" + key);
            String replacement = resolve(key, new String[] {});
            res = m.replaceFirst(replacement);
        }
        return res;
    }

    private String unescape(String s) {
        String res = s;
        res = res.replace("\\#", "#");
        res = res.replace("\\$", "$");
        return res;
    }

    private String generateNumeric(int length) {
        String prefix = repeatString("0", length);
        int max = Integer.valueOf(repeatString("9", length)) + 1;
        String s = prefix + String.valueOf(random.nextInt(max));
        return s.substring(s.length() - length);
    }

    private String repeatString(String s, int length) {
        return new String(new char[length]).replace("\0", s);
    }

}
