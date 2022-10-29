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

import com.zincyanide.sqlconstructor.dml.query.QuerySql;
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
                return Symbol.BRACKET_LEFT + str + Symbol.BRACKET_RIGHT;
            else
                return Symbol.QUOTE_SINGLE + str + Symbol.QUOTE_SINGLE;
        }
        else if(isDateClass(val))
            return Symbol.QUOTE_SINGLE + val + Symbol.QUOTE_SINGLE;
        else
            return val.toString();
    }

    private boolean isStringClass(Class<?> clazz)
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
        if(str.startsWith(Symbol.BRACKET_LEFT))
            str = str.substring(1).trim();

        return str.length()>=15
                && str.substring(0, 7).equalsIgnoreCase("SELECT ");
    }

    private String handleListVal(List<?> valList)
    {
        StringBuilder sb = new StringBuilder(Symbol.BRACKET_LEFT);
        if(isStringClass(valList.get(0).getClass())
                || isDateClass(valList.get(0)))
        {
            for(Object o : valList)
            {
                sb.append(Symbol.QUOTE_SINGLE)
                        .append(o)
                        .append(Symbol.QUOTE_SINGLE)
                        .append(Symbol.COMMA);
            }
        }
        else
        {
            for(Object o : valList)
            {
                sb.append(o).append(Symbol.COMMA);
            }
        }
        sb.replace(sb.length()-1, sb.length(), Symbol.BRACKET_RIGHT);
        return sb.toString();
    }

    @Override
    public String equalJoint(String column1, String column2)
    {
        return column1 + " = " + column2;
    }

    @Override
    public String unequalJoint(String column1, String column2)
    {
        return column1 + " != " + column2;
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
    public String lessEqualJoint(String column1, String column2)
    {
        return column1 + " <= " + column2;
    }

    @Override
    public String lessThanJoint(String column1, String column2)
    {
        return column1 + " < " + column2;
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
    public String greaterEqualJoint(String column1, String column2)
    {
        return column1 + " >= " + column2;
    }

    @Override
    public String greaterThanJoint(String column1, String column2)
    {
        return column1 + " > " + column2;
    }

    @Override
    public String between(String column, Object leftBound, Object rightBound)
    {
        return column
                + " BETWEEN " + handleObjectVal(leftBound)
                + " AND " + handleObjectVal(rightBound);
    }

    @Override
    public String in(String column, List<?> valList)
    {
        if(valList.size() == 0)
            return null;
        return column + " IN " + handleListVal(valList);
    }

    @Override
    public String in(String column, QuerySql querySql)
    {
        return column + " IN " + querySql;
    }

    @Override
    public String notIn(String column, List<?> valList)
    {
        if(valList.size() == 0)
            return null;
        return column + " NOT IN " + handleListVal(valList);
    }

    @Override
    public String notIn(String column, QuerySql querySql)
    {
        return column + " NOT IN " + querySql;
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

    @Override
    public String exists(QuerySql querySql)
    {
        return "EXISTS " + querySql;
    }

    @Override
    public String notExists(QuerySql querySql)
    {
        return "NOT EXISTS " + querySql;
    }
}
