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

public interface Criterion
{
    String joint(String column1, String column2);

    String equal(String column, Object val);

    String unequal(String column, Object val);

    String lessEqual(String column, Object val);

    String lessThan(String column, Object val);

    String greaterEqual(String column, Object val);

    String greaterThan(String column, Object val);

    String between(String column, Object leftBound, Object rightBound);

    String in(String column, List<Object> valList);

    String in(String column, SqlConstructor sqlConstructor);

    String notIn(String column, List<Object> valList);

    String notIn(String column, SqlConstructor sqlConstructor);

    String like(String column, String val);

    String likeStartWith(String column, String val);

    String likeEndWith(String column, String val);
}
