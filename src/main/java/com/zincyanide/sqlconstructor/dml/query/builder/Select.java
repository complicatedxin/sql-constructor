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

package com.zincyanide.sqlconstructor.dml.query.builder;

import com.zincyanide.sqlconstructor.internal.AliasField;
import com.zincyanide.sqlconstructor.internal.Cacheable;
import com.zincyanide.sqlconstructor.dml.query.QuerySql;
import com.zincyanide.sqlconstructor.internal.StringUtil;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Select extends BuilderMinion implements Cacheable
{
    static final String FROM = "FROM ";

    String mode;

    List<AliasField<String>> cols = new LinkedList<>();


    public Select(BaseQuerySqlBuilder builder)
    {
        super(builder);
    }


    public Select.Chain select(String column)
    {
        this.setMode(Select.Mode.DEFAULT);
        this.addCol(column);
        return (Chain) this;
    }

    public Select select(String... columns)
    {
        return select(Select.Mode.DEFAULT, Arrays.asList(columns));
    }

    public Select.Chain selectDistinct(String column)
    {
        this.setMode(Select.Mode.DEFAULT);
        this.addCol(column);
        return (Chain) this;
    }

    public Select selectDistinct(String... columns)
    {
        return select(Select.Mode.DISTINCT, Arrays.asList(columns));
    }

    private Select select(String mode, List<String> cols)
    {
        this.setMode(mode);
        cols.forEach(this::addCol);
        return this;
    }

    public From from(String table)
    {
        return from(table, "");
    }

    public From from(String table, String alias)
    {
        From from = chief.getMinion(From.class);
        from.setTable(table, alias);

        return from;
    }

    public From from(QuerySql querySql, String alias)
    {
        StringUtil.requireNonWhite(alias, "Derived table should have an alias !");

        From from = chief.getMinion(From.class);
        from.setSubSql(querySql, alias);

        return from;
    }

    void setMode(String mode)
    {
        if(this.mode == null)
            this.mode = mode;
    }

    void addCol(String col)
    {
        cols.add(new AliasField<>(col));
    }

    @Override
    public void clean()
    {
        this.cols = new LinkedList<>();
    }

    interface Mode
    {
        String DEFAULT     =   "";
        String DISTINCT    =   "DISTINCT ";
    }

    public static class Chain extends Select
    {
        public Chain(BaseQuerySqlBuilder builder)
        {
            super(builder);
        }

        public Select as(String alias)
        {
            AliasField af = cols.get(cols.size() - 1); // linked-list's optimized
            af.setAlias(alias);
            return this;
        }

    }

}
