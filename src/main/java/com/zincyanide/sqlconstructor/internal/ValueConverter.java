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

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class ValueConverter
{
    // FIXME temporary fix
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public String handleObjectVal(Object val)
    {
        if(isString(val.getClass()))
        {
            String str = (String) val;
            if(isSql(str))
                return Symbol.BRACKET_LEFT + str + Symbol.BRACKET_RIGHT;
            else
                return Symbol.QUOTE_SINGLE + str + Symbol.QUOTE_SINGLE;
        }
        else if(isDate(val))
            return Symbol.QUOTE_SINGLE + sdf.format((Date) val) + Symbol.QUOTE_SINGLE;
        else
            return val.toString();
    }

    private boolean isString(Class<?> clazz)
    {
        return clazz == String.class;
    }

    private boolean isDate(Object obj)
    {
        return obj instanceof Date;
    }

    private boolean isSql(String str)
    {
        str = str.trim();
        if(str.startsWith(Symbol.BRACKET_LEFT))
            str = str.substring(1).trim();

        return str.length()>=15
                && str.substring(0, 7).equalsIgnoreCase("SELECT ");
    }

    public String handleValCollection(Collection<?> vals)
    {
        if(vals.isEmpty())
            return "( )";

        Object val = vals.iterator().next();
        if(isString(val.getClass()))
            return CollectionUtil.asString(
                    vals,
                    "(", ")", ",", "'", "'",
                    null, null);
        else if(isDate(val))
            return CollectionUtil.asString(
                    vals,
                    "(", ")", ",", "'", "'",
                    null, (v) -> DateUtil.format((Date) v));
        else
            return CollectionUtil.asString(
                    vals,
                    "(", ")", ",", "'", "'",
                    null, null);
    }

    public void setSdf(SimpleDateFormat sdf)
    {
        this.sdf = sdf;
    }
}
