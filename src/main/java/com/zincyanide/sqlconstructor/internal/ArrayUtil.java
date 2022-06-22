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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class ArrayUtil
{
    public static boolean isEmpty(List<?> list)
    {
        return list == null || list.size() == 0;
    }

    public static <E> String asString(E arr[], String prefix, String separate, String suffix, Function<E, Boolean> evade)
    {
        return asString(Arrays.asList(arr), prefix, separate, suffix, evade);
    }

    public static <E> String asString(List<E> list, String prefix, String separate, String suffix, Function<E, Boolean> evade)
    {
        list = Collections.unmodifiableList(list);

        prefix = prefix==null ? "" : prefix;
        suffix = suffix==null ? "" : suffix;
        separate = separate==null ? "" : separate;

        StringBuilder sb = new StringBuilder(prefix);
        if(!isEmpty(list))
        {
            int i = 0;
            sb.append(list.get(i));
            while(++i < list.size())
                if (!evade.apply(list.get(i)))
                    sb.append(separate).append(list.get(i));
        }
        return sb.append(suffix).toString();
    }


}
