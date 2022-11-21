package com.zincyanide.sqlconstructor;

import com.zincyanide.sqlconstructor.dml.delete.builder.DeleteSqlBuilder;
import org.junit.Test;

public class T_02_Delete
{
    @Test
    public void t_01_delete()
    {
        String sql = new DeleteSqlBuilder()
                .from("table1")
                .where("1 = 1")
                .build().getSql();

        System.out.println(sql);
    }
}
