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
import java.util.List;
import java.util.function.Function;

public class Criteria
{
    private static final Criterion executor = new CriterionExecutor();

    /**
     * all fuzzy query
     */
    public static String LIKE(String column, String keyword, Function<String, Boolean> colValid, Function<String, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(keyword))
            return null;
        return executor.like(column, keyword);
    }

    /**
     * prefix fuzzy query
     */
    public static String LIKE_START_WITH(String column, String keyword, Function<String, Boolean> colValid, Function<String, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(keyword))
            return null;
        return executor.likeStartWith(column, keyword);
    }

    /**
     * suffix fuzzy query
     */
    public static String LIKE_END_WITH(String column, String keyword, Function<String, Boolean> colValid, Function<String, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(keyword))
            return null;
        return executor.likeEndWith(column, keyword);
    }

    public static String EQUAL(String column, Object val, Function<String, Boolean> colValid, Function<Object, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(val))
            return null;
        return executor.equal(column, val);
    }

    public static String UNEQUAL(String column, Object val, Function<String, Boolean> colValid, Function<Object, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(val))
            return null;
        return executor.unequal(column, val);
    }

    /**
     * for join on
     * @param col1 columnName
     * @param col2 another columnName
     * @return [col1] = [col2]
     */
    public static String EQ_JOINT(String col1, String col2, Function<String, Boolean> colValid)
    {
        if(!colValid.apply(col1) || !colValid.apply(col2))
            return null;
        return executor.equalJoint(col1, col2);
    }

    public static String UNEQ_JOINT(String col1, String col2, Function<String, Boolean> colValid)
    {
        if(!colValid.apply(col1) || !colValid.apply(col2))
            return null;
        return executor.unequalJoint(col1, col2);
    }

    public static String LE(String column, Object val, Function<String, Boolean> colValid, Function<Object, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(val))
            return null;
        return executor.lessEqual(column, val);
    }

    public static String LT(String column, Object val, Function<String, Boolean> colValid, Function<Object, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(val))
            return null;
        return executor.lessThan(column, val);
    }

    public static String LE_JOINT(String col1, String col2, Function<String, Boolean> colValid)
    {
        if(!colValid.apply(col1) || !colValid.apply(col2))
            return null;
        return executor.lessEqualJoint(col1, col2);
    }

    public static String LT_JOINT(String col1, String col2, Function<String, Boolean> colValid)
    {
        if(!colValid.apply(col1) || !colValid.apply(col2))
            return null;
        return executor.lessThanJoint(col1, col2);
    }

    public static String GE(String column, Object val, Function<String, Boolean> colValid, Function<Object, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(val))
            return null;
        return executor.greaterEqual(column, val);
    }

    public static String GT(String column, Object val, Function<String, Boolean> colValid, Function<Object, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(val))
            return null;
        return executor.greaterThan(column, val);
    }

    public static String GE_JOINT(String col1, String col2, Function<String, Boolean> colValid)
    {
        if(!colValid.apply(col1) || !colValid.apply(col2))
            return null;
        return executor.greaterEqualJoint(col1, col2);
    }

    public static String GT_JOINT(String col1, String col2, Function<String, Boolean> colValid)
    {
        if(!colValid.apply(col1) || !colValid.apply(col2))
            return null;
        return executor.greaterThanJoint(col1, col2);
    }

    public static String BETWEEN(String column, Object leftBound, Object rightBound, Function<String, Boolean> colValid, Function<Object, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(leftBound) || !argValid.apply(rightBound))
            return null;
        return executor.between(column, leftBound, rightBound);
    }

    public static String IN(String column, List<?> valList, Function<String, Boolean> colValid, Function<List<?>, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(valList))
            return null;
        return executor.in(column, valList);
    }

    public static String IN(String column, QuerySql querySql, Function<String, Boolean> colValid, Function<QuerySql, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(querySql))
            return null;
        return executor.in(column, querySql);
    }

    public static String NOT_IN(String column, List<?> valList, Function<String, Boolean> colValid, Function<List<?>, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(valList))
            return null;
        return executor.notIn(column, valList);
    }

    public static String NOT_IN(String column, QuerySql querySql, Function<String, Boolean> colValid, Function<QuerySql, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(querySql))
            return null;
        return executor.notIn(column, querySql);
    }

    public static String EXISTS(QuerySql querySql, Function<QuerySql, Boolean> argValid)
    {
        if(!argValid.apply(querySql))
            return null;
        return executor.exists(querySql);
    }

    public static String NOT_EXISTS(QuerySql querySql, Function<QuerySql, Boolean> argValid)
    {
        if(!argValid.apply(querySql))
            return null;
        return executor.notExists(querySql);
    }
}
