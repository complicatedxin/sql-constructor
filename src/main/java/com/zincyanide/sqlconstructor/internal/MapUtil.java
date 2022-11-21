/*
 * Copyright 2022 Zincyanide
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
