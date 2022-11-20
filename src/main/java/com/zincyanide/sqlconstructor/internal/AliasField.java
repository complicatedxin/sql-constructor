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

public class AliasField<F>
{
    private F field;

    private String alias;

    public AliasField(F field)
    {
        this(field, "");
    }
    public AliasField(F field, String alias)
    {
        this.field = field;
        this.alias = alias;
    }

    public F getField()
    {
        return field;
    }

    public void setField(F field)
    {
        this.field = field;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    @Override
    public String toString()
    {
        if(alias == null || "".equals(alias))
            return field.toString();
        else
            return field + " AS " + alias;
    }
}
