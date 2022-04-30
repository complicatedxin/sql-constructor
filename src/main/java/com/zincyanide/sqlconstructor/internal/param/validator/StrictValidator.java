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

import com.zincyanide.sqlconstructor.internal.StringUtil;
import java.security.InvalidParameterException;
import java.util.Collection;

public class StrictValidator implements ValidateStrategy
{
    @Override
    public boolean validateColumnName(Object[] args)
    {
        Object col = args[0];
        if(!(col instanceof String)
                || StringUtil.isWhite((String) col))
            throw new InvalidParameterException(
                    String.format("There is invalid column name: it may be null"));
        return true;
    }

    @Override
    public boolean validateConstraint(Object[] args)
    {
        for(int i = 1; i < args.length ; i++)
            if (args[i] == null
                    || (args[i] instanceof String
                        && StringUtil.isWhite((String) args[i]))
                    || (args[i] instanceof Collection
                        && ((Collection<?>) args[i]).isEmpty()))
                throw new InvalidParameterException(
                        String.format("Column [%s]'s value at index %s is void", args[0], i));
        return true;
    }
}