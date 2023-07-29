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

package com.zincyanide.sqlconstructor.dml.query.builder.factory;

import com.zincyanide.sqlconstructor.factory.ReusableSqlBuilderFactory;
import com.zincyanide.sqlconstructor.internal.Bindable;
import com.zincyanide.sqlconstructor.dml.query.builder.BaseQuerySqlBuilder;
import java.util.Objects;

public class BaseQuerySqlBuilderFactory implements ReusableSqlBuilderFactory<BaseQuerySqlBuilder>
{
    private static volatile BaseQuerySqlBuilderFactory factory;

    private BaseQuerySqlBuilderFactory()
    {   }

    public static BaseQuerySqlBuilderFactory getInstance()
    {
        if(factory == null)
            synchronized (BaseQuerySqlBuilderFactory.class)
            {
                if(factory == null)
                    factory = new BaseQuerySqlBuilderFactory();
            }
        return factory;
    }

    @Override
    public BaseQuerySqlBuilder manu()
    {
        BaseQuerySqlBuilder sqlBuilder = new BaseQuerySqlBuilder();
        sqlBuilder.bind();
        return sqlBuilder;
    }

    @Override
    public BaseQuerySqlBuilder obtain(boolean fresh)
    {
        BaseQuerySqlBuilder sqlBuilder = (BaseQuerySqlBuilder) Objects.requireNonNull(Bindable.QUERY_BUILDER.get());
        if(fresh)
            sqlBuilder.clear();
        return sqlBuilder;
    }
}
