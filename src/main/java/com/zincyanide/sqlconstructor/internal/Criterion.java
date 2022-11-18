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

public interface Criterion
{
    String equal(String column, Object val);

    String unequal(String column, Object val);

    String equalJoint(String column1, String column2);

    String unequalJoint(String column1, String column2);

    String lessEqual(String column, Object val);

    String lessThan(String column, Object val);

    String lessEqualJoint(String column1, String column2);

    String lessThanJoint(String column1, String column2);

    String greaterEqual(String column, Object val);

    String greaterThan(String column, Object val);

    String greaterEqualJoint(String column1, String column2);

    String greaterThanJoint(String column1, String column2);

    String between(String column, Object leftBound, Object rightBound);

    String in(String column, Collection<?> vals);

    String in(String column, QuerySql querySql);

    String notIn(String column, Collection<?> vals);

    String notIn(String column, QuerySql querySql);

    String like(String column, String val);

    String likeStartWith(String column, String val);

    String likeEndWith(String column, String val);

    String exists(QuerySql querySql);

    String notExists(QuerySql querySql);
}
