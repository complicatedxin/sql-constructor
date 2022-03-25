package com.zincyanide.sqlconstructor;

import com.zincyanide.sqlconstructor.builder.BaseQuerySqlBuilder;
import com.zincyanide.sqlconstructor.internal.Conditions;
import com.zincyanide.sqlconstructor.builder.Where;
import com.zincyanide.sqlconstructor.wrapper.impl.CountSql;
import com.zincyanide.sqlconstructor.wrapper.impl.LimitSql;
import com.zincyanide.sqlconstructor.wrapper.impl.OrderSql;
import com.zincyanide.sqlconstructor.wrapper.impl.WithAsSql;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class T_01_Query
{
    @Test
    public void t_01_wrapper()
    {
        String sqlStr = "SELECT * FROM T";
        SqlConstructor base = new BaseQuerySql(sqlStr);
        SqlConstructor count = new CountSql(base);
        SqlConstructor limit = new LimitSql(base, 8, 25);
        SqlConstructor order = new OrderSql(base, "CREAT_TIME", false);

        System.out.println("baseSql: " + base.getSql());
        System.out.println("countSql: " + count.getSql());
        System.out.println("limitSql: " + limit.getSql());
        System.out.println("orderSql: " + order.getSql());

        SqlConstructor orderAndLimit = new OrderSql(limit, "UPDATE_TIME", true);
        System.out.println("orderAndLimitSql: " + orderAndLimit.getSql());

        //================================
        WithAsSql withAsSql = new WithAsSql(orderAndLimit, "OAL",
                new BaseQuerySql("select a from OAL where id = 1"));
        System.out.println("withAsSql: " + withAsSql.getSql());
    }

    @Test
    public void t_02_builder()
    {
        BaseQuerySql querySql = new BaseQuerySqlBuilder()
                .select("et.ID", "et.CREATE_TIME", "et.TASK_TYPE")
                .from("EMP_TASK", "et")
                    .innerJoin("EMP_INFO", "ei").on("et.eid = ei.eid")
                    .leftJoin("USER_INFO", "ui").on("ui.eid = ei.eid")
                    .rightJoin("T", "t").on("t.id = ui.id")
                .where("et.TASK_TYPE = 'default'")
                    .and("XXX = xxx")
                    .or("YYY = yyy")
                .build();

        System.out.println(querySql.getSql());

        //======================
        SqlConstructor count = new CountSql(querySql);
        SqlConstructor limit = new LimitSql(querySql, 6, 25);
        SqlConstructor order = new OrderSql(limit, "CREAT_TIME", true);

        System.out.println(count.getSql());
        System.out.println(limit.getSql());
        System.out.println(order.getSql());

    }

    @Test
    public void t_03_conditions()
    {
        SqlConstructor querySql = new BaseQuerySqlBuilder()
                .select("*")
                .from("EMP_TASK", "t")
                    .innerJoin("EMP_INFO", "i").on("t.eid = i.eid")
                .where(Where.ANYWHERE)
                    .and(Conditions.EQUAL("emp_name", "Andy"))
                    .or(Conditions.LIKE_START_WITH("emp_adr", "上海"))
                    .and(Conditions.LE("createTime", new Date()))
                .build();

        System.out.println(querySql.getSql());

    }

    @Test
    public void t_05_paramValidate()
    {
        List<Object> longList = new ArrayList<>();
        longList.add(1L);
        longList.add(2L);
        longList.add(3L);

        List<Object> stringList = new ArrayList<>();
        stringList.add("w");
        stringList.add("t");
        stringList.add("f");

        List<Object> emptyList = Collections.emptyList();

        SqlConstructor querySql = new BaseQuerySqlBuilder()
                .select("*")
                .from("EMP_TASK", "t")
                    .innerJoin("EMP_INFO", "i").on("t.eid = i.eid")
                .where("")
                    .and(Conditions.EQUAL("emp_name", null))
                    .or(Conditions.LIKE("emp_adr", " "))
                    .and(Conditions.NOT_IN("emp_status", emptyList))
                .build();

        System.out.println(querySql.getSql());
    }

    @Test
    public void t_06_nestedSql()
    {
        SqlConstructor querySql =
                new BaseQuerySqlBuilder()
                    .select("*")
                    .from("EMP")
                    .where(Where.ANYWHERE)
                    .and(Conditions.EQUAL("emp_name", new BaseQuerySqlBuilder()
                                                                .select("ename")
                                                                .from("USER")
                                                                .where("uid = 1")
                                                                .build()))
                    .or(Conditions.IN("emp_adr", new LimitSql(new BaseQuerySqlBuilder()
                                                            .select("eadr")
                                                            .from("ADDRESS")
                                                            .build(), 25, 8)))
                    .build();

        System.out.println(querySql.getSql());
    }

}
