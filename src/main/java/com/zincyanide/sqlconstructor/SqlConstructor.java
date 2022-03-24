package com.zincyanide.sqlconstructor;

import com.zincyanide.sqlconstructor.internal.Separate;

public abstract class SqlConstructor
{
    public abstract String getSql();

    @Override
    public String toString()
    {
        return Separate.BRACKET_LEFT + " "
                + getSql()
                + " " + Separate.BRACKET_RIGHT;
    }
}
