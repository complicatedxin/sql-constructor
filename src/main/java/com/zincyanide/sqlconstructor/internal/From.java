package com.zincyanide.sqlconstructor.internal;

import com.zincyanide.sqlconstructor.internal.condition.ConditionNode;

public class From extends BuilderMinion implements Cacheable
{
    public static final String WHERE = "WHERE ";

    protected AliasField<String> tableField;


    public Where where(String condition)
    {
        return where(new ConditionNode(condition));
    }

    public Where where(ConditionNode node)
    {
        Where where = getChief().getMinion(Where.class);
        where.and(node);
        return where;
    }

    public From(SqlBuilder chief)
    {
        super(chief);
    }

    public void setTable(String table, String alias)
    {
        if(this.tableField == null)
        {
            this.tableField = new AliasField<>(table, alias);
        }
        this.tableField.setField(table);
        this.tableField.setAlias(alias);
    }

    public AliasField<String> getTableField()
    {
        return tableField;
    }

    @Override
    public void clear()
    {
        tableField = null;
    }
}
