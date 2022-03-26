package com.zincyanide.sqlconstructor.internal;

import com.zincyanide.sqlconstructor.SqlConstructor;
import java.util.List;

public interface ConditionManner
{
    String joint(String column1, String column2);

    String equal(String column, Object val);

    String unequal(String column, Object val);

    String lessEqual(String column, Object val);

    String lessThan(String column, Object val);

    String greaterEqual(String column, Object val);

    String greaterThan(String column, Object val);

    String in(String column, List<Object> valList);

    String in(String column, SqlConstructor sqlConstructor);

    String notIn(String column, List<Object> valList);

    String notIn(String column, SqlConstructor sqlConstructor);

    String like(String column, String val);

    String likeStartWith(String column, String val);

    String likeEndWith(String column, String val);
}
