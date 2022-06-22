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

    /**
     * for join on
     * @param col1 columnName
     * @param col2 another columnName
     * @return [col1] = [col2]
     */
    public static String JOINT(String col1, String col2, Function<String, Boolean> col1Valid, Function<String, Boolean> col2Valid)
    {
        if(!col1Valid.apply(col1) || !col2Valid.apply(col2))
            return null;
        return executor.joint(col1, col2);
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

    public static String BETWEEN(String column, Object leftBound, Object rightBound, Function<String, Boolean> colValid, Function<Object, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(leftBound) || !argValid.apply(rightBound))
            return null;
        return executor.between(column, leftBound, rightBound);
    }

    public static String IN(String column, List<Object> valList, Function<String, Boolean> colValid, Function<List<Object>, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(valList))
            return null;
        return executor.in(column, valList);
    }

    public static String IN(String column, SqlConstructor sqlConstructor, Function<String, Boolean> colValid, Function<SqlConstructor, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(sqlConstructor))
            return null;
        return executor.in(column, sqlConstructor);
    }

    public static String NOT_IN(String column, List<Object> valList, Function<String, Boolean> colValid, Function<List<Object>, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(valList))
            return null;
        return executor.notIn(column, valList);
    }

    public static String NOT_IN(String column, SqlConstructor sqlConstructor, Function<String, Boolean> colValid, Function<SqlConstructor, Boolean> argValid)
    {
        if(!colValid.apply(column) || !argValid.apply(sqlConstructor))
            return null;
        return executor.notIn(column, sqlConstructor);
    }

    public static String EXISTS(SqlConstructor sqlConstructor, Function<SqlConstructor, Boolean> argValid)
    {
        if(!argValid.apply(sqlConstructor))
            return null;
        return executor.exists(sqlConstructor);
    }

    public static String NOT_EXISTS(SqlConstructor sqlConstructor, Function<SqlConstructor, Boolean> argValid)
    {
        if(!argValid.apply(sqlConstructor))
            return null;
        return executor.notExists(sqlConstructor);
    }
}
