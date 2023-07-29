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

package com.zincyanide.sqlconstructor.factory;

import com.zincyanide.sqlconstructor.internal.Bindable;
import com.zincyanide.sqlconstructor.internal.Cacheable;
import com.zincyanide.sqlconstructor.internal.SqlBuilder;

public interface ReusableSqlBuilderFactory<B extends SqlBuilder>
        extends SqlBuilderFactory<B>
{
    /**
     * @return BaseQuerySqlBuilder which implements {@link Bindable}, {@link Cacheable},
     * or else throws {@link NullPointerException} if not exist in thread local
     */
    B obtain(boolean fresh);
}
