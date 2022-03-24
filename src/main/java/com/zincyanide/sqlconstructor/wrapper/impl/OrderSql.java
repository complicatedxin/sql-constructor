package com.zincyanide.sqlconstructor.wrapper.impl;

import com.zincyanide.sqlconstructor.SqlConstructor;
import com.zincyanide.sqlconstructor.wrapper.QuerySqlWrapper;

public class OrderSql extends QuerySqlWrapper
{
    private static final String ASC = "ASC";
    private static final String DESC = "DESC";

    private String orderColumn;
    private boolean isDesc = false;

    public OrderSql(SqlConstructor sqlConstructor, String orderColumn, boolean isDesc)
    {
        super(sqlConstructor);
        this.orderColumn = orderColumn;
        this.isDesc = isDesc;
    }

    @Override
    public String getSql()
    {
        return super.getSql()
                + " ORDER BY " + orderColumn + " " + (isDesc ? DESC : ASC);
    }
}
