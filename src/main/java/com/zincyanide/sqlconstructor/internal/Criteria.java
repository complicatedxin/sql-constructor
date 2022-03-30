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
import java.lang.reflect.Proxy;
import java.util.List;

public class Criteria
{
    private static Criterion executor =
            (Criterion) Proxy.newProxyInstance(
                    Criterion.class.getClassLoader(),
                    new Class[]{Criterion.class},
                    new ParamValidator(new CriterionExecutor())
            );

    /**
     * all fuzzy query
     */
    public static String LIKE(String column, String keyword)
    {
        return executor.like(column, keyword);
    }

    /**
     * prefix fuzzy query
     */
    public static String LIKE_START_WITH(String column, String keyword)
    {
        return executor.likeStartWith(column, keyword);
    }

    /**
     * suffix fuzzy query
     */
    public static String LIKE_END_WITH(String column, String keyword)
    {
        return executor.likeEndWith(column, keyword);
    }

    /**
     * for join on
     * @param col1 columnName
     * @param col2 another columnName
     * @return [col1] = [col2]
     */
    public static String JOINT(String col1, String col2)
    {
        return executor.joint(col1, col2);
    }

    public static String EQUAL(String column, Object val)
    {
        return executor.equal(column, val);
    }

    public static String UNEQUAL(String column, Object val)
    {
        return executor.unequal(column, val);
    }

    public static String LE(String column, Object val)
    {
        return executor.lessEqual(column, val);
    }

    public static String LT(String column, Object val)
    {
        return executor.lessThan(column, val);
    }

    public static String GE(String column, Object val)
    {
        return executor.greaterEqual(column, val);
    }

    public static String GT(String column, Object val)
    {
        return executor.greaterThan(column, val);
    }

    public static String BETWEEN(String column, Object leftBound, Object rightBound)
    {
        return executor.between(column, leftBound, rightBound);
    }

    public static String IN(String column, List<Object> valList)
    {
        return executor.in(column, valList);
    }

    public static String IN(String column, SqlConstructor sqlConstructor)
    {
        return executor.in(column, sqlConstructor);
    }

    public static String NOT_IN(String column, List<Object> valList)
    {
        return executor.notIn(column, valList);
    }

    public static String NOT_IN(String column, SqlConstructor sqlConstructor)
    {
        return executor.notIn(column, sqlConstructor);
    }

}
