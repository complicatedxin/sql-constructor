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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * To validate whether column name, comparing object, ...
 * is necessary.
 */
public class ParamValidator implements InvocationHandler
{
    private Criterion manner;

    public ParamValidator(Criterion manner)
    {
        this.manner = manner;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        if(!validateColumnName(args[0])
                || !validateConstraint(args[1]))
            return null;

        return method.invoke(manner, args);
    }

    private boolean validateColumnName(Object col)
    {
        return col instanceof String
                && !StringUtil.isWhite((String) col);
    }

    private boolean validateConstraint(Object cons)
    {
        return cons != null
                && !(cons instanceof String && StringUtil.isWhite((String) cons));
    }

}
