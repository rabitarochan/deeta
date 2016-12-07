package com.github.rabitarochan.deeta.resolver;

import com.github.rabitarochan.deeta.*;
import com.github.rabitarochan.deeta.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultResolver implements DeetaResolver {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultResolver.class);

    private static final String DEFAULT_KEY_PREFIX = "deeta";

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

    protected String unresolved(String key, DeetaContext context) {
        return "NULL";
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
            LOG.debug("key:{}", key);

            // key fix
            int dotCount = StringUtils.count(key, ".");
            if (dotCount == 0) {
                DeetaGenerator generator = generators.resolve(key);
                if (generator == null) {
                    LOG.warn("Unresolved generator.[key:{}]", key);
                    res = m.replaceFirst(unresolved(key, context));
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

                key = DEFAULT_KEY_PREFIX + "." + key;
                LOG.debug("Unresolved generator. Try with default key.[key:{}]", key);
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
        String prefix = StringUtils.repeat("0", length);
        int max = Integer.valueOf(StringUtils.repeat("9", length));
        String s = prefix + String.valueOf(random.nextInt(0, max));
        return StringUtils.right(s, length);
    }

}
