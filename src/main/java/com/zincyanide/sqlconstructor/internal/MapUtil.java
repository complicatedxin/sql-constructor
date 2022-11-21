package com.zincyanide.sqlconstructor.internal;

import java.util.Map;
import java.util.function.Function;

public class MapUtil
{
    public static boolean isEmpty(Map<?, ?> map)
    {
        return map == null || map.isEmpty();
    }

    public static <K, V, R> String asString(Map<K, V> map,
                                         String prefix, String suffix,
                                         String separate,
                                         String bracketL, String bracketR,
                                         Function<Map.Entry<K, V>, Boolean> skip,
                                         Function<Map.Entry<K, V>, R> convert)
    {
        prefix = prefix==null ? "" : prefix;
        suffix = suffix==null ? "" : suffix;
        separate = separate==null ? "" : separate;
        bracketL = bracketL==null ? "" : bracketL;
        bracketR = bracketR==null ? "" : bracketR;

        return CollectionUtil.asString(map.entrySet(),
                prefix, suffix,
                separate,
                bracketL, bracketR,
                skip, convert);
    }
}
