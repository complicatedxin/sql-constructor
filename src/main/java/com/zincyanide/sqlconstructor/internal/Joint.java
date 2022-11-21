package com.zincyanide.sqlconstructor.internal;

public class Joint
{
    private String col1;

    private String col2;

    public Joint(String col1, String col2)
    {
        this.col1 = col1;
        this.col2 = col2;
    }

    @Override
    public String toString()
    {
        return col1 + " = " + col2;
    }
}
