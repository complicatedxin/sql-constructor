package com.zincyanide.sqlconstructor.dml.update.builder;

import com.zincyanide.sqlconstructor.dml.query.QuerySql;
import com.zincyanide.sqlconstructor.internal.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Update extends BuilderMinion implements Cacheable
{
    static final String UPDATE = "UPDATE ";

    AliasField<String> tableField;
    List<AliasField<QuerySql>> sqlFieldList;

    public Update(SqlBuilder chief)
    {
        super(chief);
    }

    public Update update(QuerySql correlated, String alias)
    {
        if(sqlFieldList == null)
            sqlFieldList = new LinkedList<>();
        sqlFieldList.add(new AliasField<>(correlated, alias));
        return this;
    }

    public Set set(Joint joint)
    {
        return getFellow(Set.class).set(joint);
    }

    public Set set(String col, Object val)
    {
        return getFellow(Set.class).set(col, val);
    }

    public Set set(Map<String, Object> changes)
    {
        Set set = getFellow(Set.class);
        set.changes.putAll(changes);
        return set;
    }


    @Override
    public void clear()
    {
        tableField = null;
        sqlFieldList = null;
    }

    void setTable(String table, String alias)
    {
        if(this.tableField == null)
        {
            this.tableField = new AliasField<>(table, alias);
        }
        this.tableField.setField(table);
        this.tableField.setAlias(alias);
    }
}
