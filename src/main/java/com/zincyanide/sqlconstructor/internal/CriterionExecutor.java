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

import com.zincyanide.sqlconstructor.SqlConstructor;
import java.util.Date;
import java.util.List;

public class CriterionExecutor implements Criterion
{
    private String handleObjectVal(Object val)
    {
        if(isStringClass(val.getClass()))
        {
            String str = (String) val;
            if(isSql(str))
                return Separate.BRACKET_LEFT + str + Separate.BRACKET_RIGHT;
            else
                return Separate.QUOTE_SINGLE + str + Separate.QUOTE_SINGLE;
        }
        else if(isDateClass(val))
            return Separate.QUOTE_SINGLE + val + Separate.QUOTE_SINGLE;
        else
            return val.toString();
    }

    private boolean isStringClass(Class clazz)
    {
        return clazz == String.class;
    }

    private boolean isDateClass(Object obj)
    {
        return obj instanceof Date;
    }

    private boolean isSql(String str)
    {
        str = str.trim();
        if(str.startsWith(Separate.BRACKET_LEFT))
            str = str.substring(1).trim();

        return str.length()>=15
                && str.substring(0, 7).equalsIgnoreCase("SELECT ");
    }

    private String handleListVal(List<Object> valList)
    {
        StringBuilder sb = new StringBuilder(Separate.BRACKET_LEFT);
        if(isStringClass(valList.get(0).getClass())
                || isDateClass(valList.get(0)))
        {
            for(Object o : valList)
            {
                sb.append(Separate.QUOTE_SINGLE)
                        .append(o)
                        .append(Separate.QUOTE_SINGLE)
                        .append(Separate.COMMA);
            }
        }
        else
        {
            for(Object o : valList)
            {
                sb.append(o).append(Separate.COMMA);
            }
        }
        sb.replace(sb.length()-1, sb.length(), Separate.BRACKET_RIGHT);
        return sb.toString();
    }

    @Override
    public String joint(String column1, String column2)
    {
        return column1 + " = " + column2;
    }

    @Override
    public String equal(String column, Object val)
    {
        return column + " = " + handleObjectVal(val);
    }

    @Override
    public String unequal(String column, Object val)
    {
        return column + " != " + handleObjectVal(val);
    }

    @Override
    public String lessEqual(String column, Object val)
    {
        return column + " <= " + handleObjectVal(val);
    }

    @Override
    public String lessThan(String column, Object val)
    {
        return column + " < " + handleObjectVal(val);
    }

    @Override
    public String greaterEqual(String column, Object val)
    {
        return column + " >= " + handleObjectVal(val);
    }

    @Override
    public String greaterThan(String column, Object val)
    {
        return column + " > " + handleObjectVal(val);
    }

    @Override
    public String in(String column, List<Object> valList)
    {
        if(valList.size() == 0)
            return null;
        return column + " IN " + handleListVal(valList);
    }

    @Override
    public String in(String column, SqlConstructor sqlConstructor)
    {
        return column + " IN " + sqlConstructor;
    }

    @Override
    public String notIn(String column, List<Object> valList)
    {
        if(valList.size() == 0)
            return null;
        return column + " NOT IN " + handleListVal(valList);
    }

    @Override
    public String notIn(String column, SqlConstructor sqlConstructor)
    {
        return column + " NOT IN " + sqlConstructor;
    }

    @Override
    public String like(String column, String val)
    {
        return column + " LIKE '%" + val + "%'";
    }

    @Override
    public String likeStartWith(String column, String val)
    {
        return column + " LIKE '" + val + "%'";
    }

    @Override
    public String likeEndWith(String column, String val)
    {
        return column + " LIKE '%" + val + "'";
    }
}
