package com.zincyanide.sqlconstructor;

import com.zincyanide.sqlconstructor.dml.query.builder.BaseQuerySqlBuilder;
import com.zincyanide.sqlconstructor.dml.query.builder.Select.StringValue;
import com.zincyanide.sqlconstructor.dml.query.wrapper.impl.UnionSql;
import com.zincyanide.sqlconstructor.dml.update.UpdateSql;
import com.zincyanide.sqlconstructor.dml.update.builder.UpdateSqlBuilder;
import com.zincyanide.sqlconstructor.internal.Essential;
import com.zincyanide.sqlconstructor.internal.Joint;
import org.junit.Test;

public class T_03_Update
{
    @Test
    public void t_01_update()
    {
        UpdateSql sql = (UpdateSql) new UpdateSqlBuilder()
                .update("student")
                .update(
                        new UnionSql(new BaseQuerySqlBuilder()
                                    .select(new StringValue("Philips1")).as("name")
                                    .select(new StringValue("male")).as("gender").build())
                                .unionAll(new BaseQuerySqlBuilder()
                                        .select(new StringValue("Philips2")).as("name")
                                        .select(new StringValue("female")).as("gender").build())
                                .unionAll(new BaseQuerySqlBuilder()
                                        .select(new StringValue("Philips3")).as("name")
                                        .select(new StringValue("bi")).as("gender").build())
                                .unionAll(new BaseQuerySqlBuilder()
                                        .select(new StringValue("Philips4")).as("name")
                                        .select(new StringValue("trans")).as("gender").build()),
                "new_stu")
                .set(new Joint("student.gender", "new_stu.gender"))
                .where(Essential.JOINT_EQ("student.name", "new_stu.name"))
                .build();

        System.out.println(sql.getSql());
    }
}
