package com.zincyanide.sqlconstructor.internal;

import com.zincyanide.sqlconstructor.SqlConstructor;
import java.lang.reflect.Proxy;
import java.util.List;

public class Conditions
{
    private static ConditionManner executor =
            (ConditionManner) Proxy.newProxyInstance(
                    ConditionManner.class.getClassLoader(),
                    new Class[]{ConditionManner.class},
                    new ParamValidator(new ConditionMannerExecutor())
            );

    public static String LIKE(String column, String keyword)
    {
        return executor.like(column, keyword);
    }

    public static String LIKE_START_WITH(String column, String keyword)
    {
        return executor.likeStartWith(column, keyword);
    }

    public static String LIKE_END_WITH(String column, String keyword)
    {
        return executor.likeEndWith(column, keyword);
    }

    public static String JOINT(String col1, String col2)
    {
        return executor.joint(col1, col2);
    }

    public static String EQUAL(String column, Object val)
    {
        return executor.equal(column, val);
    }

    public static String UNEQUAL(String column, Object val)
    {
        return executor.unequal(column, val);
    }

    public static String LE(String column, Object val)
    {
        return executor.lessEqual(column, val);
    }

    public static String LT(String column, Object val)
    {
        return executor.lessThan(column, val);
    }

    public static String GE(String column, Object val)
    {
        return executor.greaterEqual(column, val);
    }

    public static String GT(String column, Object val)
    {
        return executor.greaterThan(column, val);
    }

    public static String IN(String column, List<Object> valList)
    {
        return executor.in(column, valList);
    }

    public static String IN(String column, SqlConstructor sqlConstructor)
    {
        return executor.in(column, sqlConstructor);
    }

    public static String NOT_IN(String column, List<Object> valList)
    {
        return executor.notIn(column, valList);
    }

    public static String NOT_IN(String column, SqlConstructor sqlConstructor)
    {
        return executor.notIn(column, sqlConstructor);
    }

}
