package com.github.rabitarochan.deeta;

import com.github.rabitarochan.deeta.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultDeetaResolver implements DeetaResolver {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDeetaResolver.class);

    private static final String DEFAULT_KEY_PREFIX = "deeta";

    private static final Pattern VARIABLE_PATTERN = Pattern.compile("(?<!\\\\)\\$\\{.+?\\}");

    private static final Pattern NUMERIC_PATTERN = Pattern.compile("(?<!\\\\)#+");

    private static final Pattern CHARACTER_PATTERN = Pattern.compile("(?<!\\\\)\\?+");

    private final DeetaFetcher fetcher;

    private final DeetaRandom random;

    private final DeetaGenerators generators;

    public DefaultDeetaResolver(DeetaFetcher fetcher, DeetaRandom random) {
        this.fetcher = fetcher;
        this.random = random;
        this.generators = DeetaGenerators.getInstance();
    }

    @Override
    public String resolve(String key, DeetaContext context) {
        return resolveInternal(key, context);
    }

    @Override
    public String fetch(String key, DeetaContext context) {
        return fetcher.fetch(key, context);
    }

    protected String resolveInternal(String key, DeetaContext context) {
        String res = key;
        res = resolveNumeric(res);
        res = resolveCharacter(res);
        res = variable(res, context);
        res = unescape(res);
        return res;
    }

    protected String unresolved(String key, DeetaContext context) {
        return "NULL";
    }

    protected String resolveNumeric(String s) {
        String res = s;
        while (true) {
            Matcher m = NUMERIC_PATTERN.matcher(res);
            if (!m.find()) { break; }

            String matchString = m.group();
            res = m.replaceFirst(generateNumeric(matchString.length()));
        }
        return res;
    }

    protected String resolveCharacter(String s) {
        String res = s;
        while (true) {
            Matcher m = CHARACTER_PATTERN.matcher(res);
            if (!m.find()) { break; }

            String matchString = m.group();
            res = m.replaceFirst(generateCharacter(matchString.length()));
        }
        return res;
    }

    private String variable(String s, DeetaContext context) {
        String res = s;
        while (true) {
            Matcher m = VARIABLE_PATTERN.matcher(res);
            if (!m.find()) { break; }

            String matchString = m.group();
            String key = matchString.substring(2, matchString.length() - 1);
            LOG.debug("[key:{}]", key);

            int dotCount = StringUtils.count(key, ".");
            if (dotCount == 0) {
                res = m.replaceFirst(resolveFromGenerator(/* alias */ key, context));
                continue;
            } else if (dotCount == 1) {
                String generated = tryResolveFromGenerator(key, context);
                if (generated != null) {
                    res = m.replaceFirst(generated);
                    continue;
                }

                key = DEFAULT_KEY_PREFIX + "." + key;
                LOG.debug("Unresolved generator. Try with default key.[key:{}]", key);
            }

            String fetched = fetch(key, context);
            if (fetched == null) {
                res = m.replaceFirst(unresolved(key, context));
            } else {
                String replacement = resolve(fetched, context);
                res = m.replaceFirst(replacement);
            }
        }
        return res;
    }

    private String resolveFromGenerator(String alias, DeetaContext context) {
        String res = tryResolveFromGenerator(alias, context);
        if (res != null) { return res; }

        LOG.warn("Unresolved generator.[alias:{}]", alias);
        return unresolved(alias, context);
    }

    private String tryResolveFromGenerator(String key, DeetaContext context) {
        DeetaGenerator generator = generators.resolve(key);
        if (generator == null) { return null; }

        return generator.generate(context);
    }

    private String unescape(String s) {
        String res = s;
        res = res.replace("\\#", "#");
        res = res.replace("\\?", "?");
        res = res.replace("\\$", "$");
        return res;
    }

    private String generateNumeric(int length) {
        int len = length;
        StringBuilder sb = new StringBuilder();
        while (len > 0) {
            int genLen = len;
            if (len >= 18) { genLen = 18; }
            String prefix = StringUtils.repeat("0", genLen);
            long max = Long.valueOf(StringUtils.repeat("9", genLen));
            String s = prefix + String.valueOf(random.nextLong(0, max));
            sb.append(StringUtils.right(s, genLen));
            len -= genLen;
        }
        return sb.toString();
    }

    private String generateCharacter(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) (97 + random.nextInt(26)));
        }
        return sb.toString();
    }

}
