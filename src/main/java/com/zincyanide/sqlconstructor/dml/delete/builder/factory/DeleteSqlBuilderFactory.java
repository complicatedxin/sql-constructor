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

package com.zincyanide.sqlconstructor.dml.delete.builder.factory;

import com.zincyanide.sqlconstructor.dml.delete.builder.DeleteSqlBuilder;
import com.zincyanide.sqlconstructor.factory.ReusableSqlBuilderFactory;
import com.zincyanide.sqlconstructor.internal.Bindable;
import java.util.Objects;

public class DeleteSqlBuilderFactory implements ReusableSqlBuilderFactory<DeleteSqlBuilder>
{
    private static volatile DeleteSqlBuilderFactory factory;

    private DeleteSqlBuilderFactory()
    {   }

    public static DeleteSqlBuilderFactory getInstance()
    {
        if(factory == null)
            synchronized (DeleteSqlBuilderFactory.class)
            {
                if(factory == null)
                    factory = new DeleteSqlBuilderFactory();
            }
        return factory;
    }

    @Override
    public DeleteSqlBuilder manu()
    {
        DeleteSqlBuilder sqlBuilder = new DeleteSqlBuilder();
        sqlBuilder.bind();
        return sqlBuilder;
    }


    @Override
    public DeleteSqlBuilder obtain(boolean fresh)
    {
        DeleteSqlBuilder sqlBuilder = (DeleteSqlBuilder) Objects.requireNonNull(Bindable.DELETE_BUILDER.get());
        if(fresh)
            sqlBuilder.clear();
        return sqlBuilder;
    }

}
