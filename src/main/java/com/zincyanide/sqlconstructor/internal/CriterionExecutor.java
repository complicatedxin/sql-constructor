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

import java.util.Collection;

public class CriterionExecutor implements Criterion
{
    private ValueConverter valConverter = new ValueConverter();

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
        return column + " = " + valConverter.handleObjectVal(val);
    }

    @Override
    public String unequal(String column, Object val)
    {
        return column + " != " + valConverter.handleObjectVal(val);
    }

    @Override
    public String lessEqual(String column, Object val)
    {
        return column + " <= " + valConverter.handleObjectVal(val);
    }

    @Override
    public String lessThan(String column, Object val)
    {
        return column + " < " + valConverter.handleObjectVal(val);
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
        return column + " >= " + valConverter.handleObjectVal(val);
    }

    @Override
    public String greaterThan(String column, Object val)
    {
        return column + " > " + valConverter.handleObjectVal(val);
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
                + " BETWEEN " + valConverter.handleObjectVal(leftBound)
                + " AND " + valConverter.handleObjectVal(rightBound);
    }

    @Override
    public String in(String column, Collection<?> vals)
    {
        if(vals.size() == 0)
            return null;
        return column + " IN " + valConverter.handleValCollection(vals);
    }

    @Override
    public String in(String column, QuerySql querySql)
    {
        return column + " IN " + querySql;
    }

    @Override
    public String notIn(String column, Collection<?> vals)
    {
        if(vals.size() == 0)
            return null;
        return column + " NOT IN " + valConverter.handleValCollection(vals);
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
