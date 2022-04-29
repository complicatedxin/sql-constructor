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

import com.zincyanide.sqlconstructor.internal.Criterion;
import com.zincyanide.sqlconstructor.internal.StringUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * To validate whether column name, comparing object, ...
 * is necessary.
 */
public class ParamValidator implements InvocationHandler
{
    private Criterion executor;

    /**
     * strict degree of args validation depends on this property.
     */
    private final ValidateStrategy strategy;

    public ParamValidator(Criterion executor, ValidateStrategy strategy)
    {
        this.executor = executor;
        this.strategy = strategy;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        if(!validateColumnName(args)
                || !validateConstraint(args))
            return null;

        return method.invoke(executor, args);
    }

    private boolean validateColumnName(Object[] args)
    {
        return strategy.validateColumnName(args);
    }

    private boolean validateConstraint(Object[] args)
    {
        return strategy.validateConstraint(args);
    }

}
