package com.zincyanide.sqlconstructor.internal;

import com.zincyanide.sqlconstructor.SqlConstructor;
import java.lang.reflect.Proxy;
import java.util.List;

public class Conditions
{
    private static ConditionManner conditionsHandler =
            (ConditionManner) Proxy.newProxyInstance(
                    ConditionManner.class.getClassLoader(),
                    new Class[]{ConditionManner.class},
                    new ParamValidator(new ConditionMannerExecutor())
            );

    public static String LIKE(String column, String keyword)
    {
        return conditionsHandler.like(column, keyword);
    }

    public static String LIKE_START_WITH(String column, String keyword)
    {
        return conditionsHandler.likeStartWith(column, keyword);
    }

    public static String LIKE_END_WITH(String column, String keyword)
    {
        return conditionsHandler.likeEndWith(column, keyword);
    }

    public static String EQUAL(String column, Object val)
    {
        return conditionsHandler.equal(column, val);
    }

    public static String UNEQUAL(String column, Object val)
    {
        return conditionsHandler.unequal(column, val);
    }

    public static String LE(String column, Object val)
    {
        return conditionsHandler.lessEqual(column, val);
    }

    public static String LT(String column, Object val)
    {
        return conditionsHandler.lessThan(column, val);
    }

    public static String GE(String column, Object val)
    {
        return conditionsHandler.greaterEqual(column, val);
    }

    public static String GT(String column, Object val)
    {
        return conditionsHandler.greaterThan(column, val);
    }

    public static String IN(String column, List<Object> valList)
    {
        return conditionsHandler.in(column, valList);
    }

    public static String IN(String column, SqlConstructor sqlConstructor)
    {
        return conditionsHandler.in(column, sqlConstructor);
    }

    public static String NOT_IN(String column, List<Object> valList)
    {
        return conditionsHandler.notIn(column, valList);
    }

    public static String NOT_IN(String column, SqlConstructor sqlConstructor)
    {
        return conditionsHandler.notIn(column, sqlConstructor);
    }

}
