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

package com.zincyanide.sqlconstructor.internal.param.validator;

import com.zincyanide.sqlconstructor.dml.query.QuerySql;
import com.zincyanide.sqlconstructor.internal.StringUtil;

import java.util.List;

public class NotStrictValidator implements ValidateStrategy
{
    @Override
    public Boolean validateColumn(String col)
    {
        return !StringUtil.isBlank(col);
    }

    @Override
    public Boolean validateArg(Object arg)
    {
        return (arg != null
                && !(arg instanceof String && StringUtil.isBlank((String) arg)));
    }

    @Override
    public Boolean validateArgs(List<?> args)
    {
        return args != null && args.size() > 0;
    }

    @Override
    public Boolean validateSubSql(QuerySql subSql)
    {
        return subSql != null;
    }
}
