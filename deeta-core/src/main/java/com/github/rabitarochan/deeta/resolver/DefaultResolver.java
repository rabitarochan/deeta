package com.github.rabitarochan.deeta.resolver;

import com.github.rabitarochan.deeta.*;

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

    private final DeetaGenerators generators;

    public DefaultResolver(DeetaFetcher fetcher, DeetaRandom random) {
        this.fetcher = fetcher;
        this.random = random;
        this.generators = DeetaGenerators.getInstance();
    }

    @Override
    public String resolve(String key, DeetaContext context) {
        return resolveInternal(key, context);
    }

    protected String resolveInternal(String key, DeetaContext context) {
        String res = key;
        res = numeric(res);
        res = variable(res, context);
        res = unescape(res);
        return res;
    }

    private String numeric(String s) {
        String res = s;
        while (true) {
            Matcher m = NUMERIC_PATTERN.matcher(res);
            if (!m.find()) {
                break;
            }

            String matchString = m.group();
            res = m.replaceFirst(generateNumeric(matchString.length()));
        }
        return res;
    }

    private String variable(String s, DeetaContext context) {
        String res = s;
        while (true) {
            Matcher m = VARIABLE_PATTERN.matcher(res);
            if (!m.find()) {
                break;
            }

            String matchString = m.group();
            String key = matchString.substring(2, matchString.length() - 1);
            LOG.finer("key:" + key);

            // key fix
            int dotCount = countDot(key);
            if (dotCount == 0) {
                DeetaGenerator generator = generators.resolve(key);
                if (generator == null) {
                    res = m.replaceFirst("NULL"); // TODO unresolve key.
                    continue;
                } else {
                    res = m.replaceFirst(generator.generate(context));
                    continue;
                }
            } else if (dotCount == 1) {
                DeetaGenerator generator = generators.resolve(key);
                if (generator != null) {
                    res = m.replaceFirst(generator.generate(context));
                    continue;
                }

                key = "default." + key;
            }

            String replacement = resolve(fetcher.fetch(key, context), context);
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

    private int countDot(String s) {
        int length = s.length();
        int count = 0;
        int startIndex = 0;
        while (true) {
            int res = s.indexOf(".", startIndex);
            if (res == -1) {
                break;
            }

            startIndex = res + 1;
            count++;
        }
        return count;
    }

}
