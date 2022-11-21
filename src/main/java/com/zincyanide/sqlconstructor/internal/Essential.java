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
import com.zincyanide.sqlconstructor.internal.param.validator.StrictValidator;
import com.zincyanide.sqlconstructor.internal.param.validator.ValidateStrategy;
import java.util.List;

public class Essential
{
    private static final ValidateStrategy validator = new StrictValidator();

    /**
     * fuzzy query
     */
    public static String LIKE(String column, String keyword)
    {
        return Criteria.LIKE(column, keyword, validator::validateColumn, validator::validateArg);
    }

    /**
     * prefix fuzzy query
     */
    public static String LIKE_START_WITH(String column, String keyword)
    {
        return Criteria.LIKE_START_WITH(column, keyword, validator::validateColumn, validator::validateArg);
    }

    /**
     * suffix fuzzy query
     */
    public static String LIKE_END_WITH(String column, String keyword)
    {
        return Criteria.LIKE_END_WITH(column, keyword, validator::validateColumn, validator::validateArg);
    }

    public static String EQ(String column, Object val)
    {
        return Criteria.EQUAL(column, val, validator::validateColumn, validator::validateArg);
    }

    public static String UNEQ(String column, Object val)
    {
        return Criteria.UNEQUAL(column, val, validator::validateColumn, validator::validateArg);
    }

    public static String LE(String column, Object val)
    {
        return Criteria.LE(column, val, validator::validateColumn, validator::validateArg);
    }

    public static String LT(String column, Object val)
    {
        return Criteria.LT(column, val, validator::validateColumn, validator::validateArg);
    }

    public static String GE(String column, Object val)
    {
        return Criteria.GE(column, val, validator::validateColumn, validator::validateArg);
    }

    public static String GT(String column, Object val)
    {
        return Criteria.GT(column, val, validator::validateColumn, validator::validateArg);
    }

    public static String BETWEEN(String column, Object leftBound, Object rightBound)
    {
        return Criteria.BETWEEN(column, leftBound, rightBound, validator::validateColumn, validator::validateArg);
    }

    public static String IN(String column, List<?> valList)
    {
        return Criteria.IN(column, valList, validator::validateColumn, validator::validateArgs);
    }

    public static String IN(String column, QuerySql querySql)
    {
        return Criteria.IN(column, querySql, validator::validateColumn, validator::validateSubSql);
    }

    public static String NOT_IN(String column, List<?> valList)
    {
        return Criteria.NOT_IN(column, valList, validator::validateColumn, validator::validateArgs);
    }

    public static String NOT_IN(String column, QuerySql querySql)
    {
        return Criteria.NOT_IN(column, querySql, validator::validateColumn, validator::validateSubSql);
    }

    public static String EXISTS(QuerySql querySql)
    {
        return Criteria.EXISTS(querySql, validator::validateSubSql);
    }

    public static String NOT_EXISTS(QuerySql querySql)
    {
        return Criteria.NOT_EXISTS(querySql, validator::validateSubSql);
    }

    public static String JOINT_EQ(String col1, String col2)
    {
        return Criteria.JOINT_EQ(col1, col2, validator::validateColumn);
    }

    public static String JOINT_UNEQ(String col1, String col2)
    {
        return Criteria.JOINT_UNEQ(col1, col2, validator::validateColumn);
    }

    public static String JOINT_LE(String col1, String col2)
    {
        return Criteria.JOINT_LE(col1, col2, validator::validateColumn);
    }

    public static String JOINT_LT(String col1, String col2)
    {
        return Criteria.JOINT_LT(col1, col2, validator::validateColumn);
    }

    public static String JOINT_GE(String col1, String col2)
    {
        return Criteria.JOINT_GE(col1, col2, validator::validateColumn);
    }

    public static String JOINT_GT(String col1, String col2)
    {
        return Criteria.JOINT_GT(col1, col2, validator::validateColumn);
    }
}
