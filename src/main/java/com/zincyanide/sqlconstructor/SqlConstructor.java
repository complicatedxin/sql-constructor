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

package com.zincyanide.sqlconstructor;

import com.zincyanide.sqlconstructor.dml.query.wrapper.QuerySqlWrapper;

/**
 * a constructor of sql
 *
 * currently supporting
 *  + query sql:
 * @see com.zincyanide.sqlconstructor.dml.query.BaseQuerySql
 * @see QuerySqlWrapper
 *  + update sql:
 * @see com.zincyanide.sqlconstructor.dml.update.UpdateSql
 *  + delete sql:
 * @see com.zincyanide.sqlconstructor.dml.delete.DeleteSql
 *
 * we do not tend to implement insert, it would be
 * more appropriate for orm to handle it
 */
public abstract class SqlConstructor
{
    public abstract String getSql();

}
