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

import java.util.*;
import java.util.function.Function;

public class CollectionUtil
{
    public static boolean isEmpty(Collection<?> collection)
    {
        return collection == null || collection.isEmpty();
    }

    public static <E, R> String asString(E arr[],
                                         String prefix, String suffix,
                                         String separate,
                                         String bracketL,String bracketR,
                                         Function<E, Boolean> skip,
                                         Function<E, R> convert)
    {
        return asString(Arrays.asList(arr),
                prefix, suffix,
                separate,
                bracketL, bracketR,
                skip, convert);
    }

    public static <E, R> String asString(Collection<E> collection,
                                         String prefix, String suffix,
                                         String separate,
                                         String bracketL, String bracketR,
                                         Function<E, Boolean> skip,
                                         Function<E, R> convert)
    {
        prefix = prefix==null ? "" : prefix;
        suffix = suffix==null ? "" : suffix;
        separate = separate==null ? "" : separate;
        bracketL = bracketL==null ? "" : bracketL;
        bracketR = bracketR==null ? "" : bracketR;

        StringBuilder sb = new StringBuilder(prefix);
        if(!isEmpty(collection))
        {
            Iterator<E> iterator = collection.iterator();
            E e;
            while (iterator.hasNext())
            {
                e = iterator.next();
                if(skip != null && skip.apply(e))
                    continue;
                sb.append(bracketL)
                        .append(convert == null ? e : convert.apply(e))
                        .append(bracketR)
                        .append(separate);
            }
        }
        int lsi;
        lsi = (lsi=sb.lastIndexOf(separate)) < 0 ? sb.length() : lsi;
        return sb.replace(lsi, sb.length(), suffix)
                    .toString();
    }



}
