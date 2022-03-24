package com.zincyanide.sqlconstructor.internal;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ParamValidator implements InvocationHandler
{
    private ConditionManner manner;

    public ParamValidator(ConditionManner manner)
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
