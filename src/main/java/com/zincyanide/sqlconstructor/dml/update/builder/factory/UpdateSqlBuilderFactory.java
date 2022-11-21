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

package com.zincyanide.sqlconstructor.dml.update.builder.factory;

import com.zincyanide.sqlconstructor.dml.update.builder.UpdateSqlBuilder;
import com.zincyanide.sqlconstructor.factory.ReusableSqlBuilderFactory;
import com.zincyanide.sqlconstructor.internal.Bindable;
import java.util.Objects;

public class UpdateSqlBuilderFactory implements ReusableSqlBuilderFactory<UpdateSqlBuilder>
{
    private static volatile UpdateSqlBuilderFactory factory;

    private UpdateSqlBuilderFactory()
    {   }

    public static UpdateSqlBuilderFactory getInstance()
    {
        if(factory == null)
            synchronized (UpdateSqlBuilderFactory.class)
            {
                if(factory == null)
                    factory = new UpdateSqlBuilderFactory();
            }
        return factory;
    }

    @Override
    public UpdateSqlBuilder manu()
    {
        return new UpdateSqlBuilder();
    }

    @Override
    public UpdateSqlBuilder manu(boolean fresh)
    {
        UpdateSqlBuilder sqlBuilder = (UpdateSqlBuilder) Bindable.UPDATE_BUILDER.get();
        if(sqlBuilder == null)
        {
            sqlBuilder = manu();
            sqlBuilder.bind();
        }
        else if (fresh)
            sqlBuilder.clear();

        return sqlBuilder;
    }

    @Override
    public UpdateSqlBuilder obtain()
    {
        return (UpdateSqlBuilder) Objects.requireNonNull(Bindable.UPDATE_BUILDER.get());
    }

}
