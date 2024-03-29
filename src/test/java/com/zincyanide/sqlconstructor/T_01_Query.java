package com.zincyanide.sqlconstructor;

import com.zincyanide.sqlconstructor.dml.query.BaseQuerySql;
import com.zincyanide.sqlconstructor.dml.query.QuerySql;
import com.zincyanide.sqlconstructor.dml.query.builder.BaseQuerySqlBuilder;
import com.zincyanide.sqlconstructor.internal.Where;
import com.zincyanide.sqlconstructor.dml.query.builder.factory.BaseQuerySqlBuilderFactory;
import com.zincyanide.sqlconstructor.factory.ReusableSqlBuilderFactory;
import com.zincyanide.sqlconstructor.internal.condition.Conditions;
import com.zincyanide.sqlconstructor.internal.Criteria;
import com.zincyanide.sqlconstructor.internal.Omissibl;
import com.zincyanide.sqlconstructor.internal.Essential;
import com.zincyanide.sqlconstructor.dml.query.wrapper.impl.CountSql;
import com.zincyanide.sqlconstructor.dml.query.wrapper.impl.LimitSql;
import com.zincyanide.sqlconstructor.dml.query.wrapper.impl.OrderSql;
import com.zincyanide.sqlconstructor.dml.query.wrapper.impl.WithAsSql;
import org.junit.Test;
import java.util.*;

public class T_01_Query
{
    @Test
    public void t_01_wrapper()
    {
        String sqlStr = "SELECT * FROM T";
        QuerySql base = new BaseQuerySql(sqlStr);
        QuerySql count = new CountSql(base);
        QuerySql limit = new LimitSql(base, 8, 25);
        QuerySql order = new OrderSql(base, "CREAT_TIME", false);

        System.out.println("baseSql: " + base.getSql());
        System.out.println("countSql: " + count.getSql());
        System.out.println("limitSql: " + limit.getSql());
        System.out.println("orderSql: " + order.getSql());

        QuerySql orderThenLimit = new LimitSql(order, 5, 25);
        System.out.println("orderThenLimitSql: " + orderThenLimit.getSql());

        //================================
        WithAsSql withAsSql = new WithAsSql(orderThenLimit, "OAL",
                new BaseQuerySql("select a from OAL where id = 1"));
        System.out.println("withAsSql: " + withAsSql.getSql());
    }

    @Test
    public void t_02_builder()
    {
        BaseQuerySql querySql = (BaseQuerySql) new BaseQuerySqlBuilder()
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
        QuerySql count = new CountSql(querySql);
        QuerySql limit = new LimitSql(querySql, 6, 25);
        QuerySql order = new OrderSql(limit, "CREAT_TIME", true);

        System.out.println(count.getSql());
        System.out.println(limit.getSql());
        System.out.println(order.getSql());

    }

    @Test
    public void t_03_omissibl()
    {
        SqlConstructor querySql = new BaseQuerySqlBuilder()
                .select("*")
                .from("EMP_TASK", "t")
                    .innerJoin("EMP_INFO", "i").on(Essential.JOINT_EQ("t.eid", "i.eid"))
                .where("")
                    .and(Omissibl.EQ("emp_name", ""))
                    .or(Omissibl.LIKE_START_WITH("emp_adr", " "))
                    .and(Omissibl.LE("createTime", null))
                    .and(Omissibl.GT("ope_time", new Date()))
                .build();

        System.out.println(querySql.getSql());

    }

    @Test(expected = RuntimeException.class)
    public void t_04_essential()
    {
        List<Object> longList = new ArrayList<>();
        longList.add(1L);
        longList.add(2L);
        longList.add(3L);

        List<String> stringList = new ArrayList<>();
        stringList.add("w");
        stringList.add("t");
        stringList.add("f");

        List<Object> emptyList = Collections.emptyList();

        SqlConstructor querySql = new BaseQuerySqlBuilder()
                .select("*")
                .from("EMP_TASK", "t")
                .innerJoin("EMP_INFO", "i").on(Essential.JOINT_EQ("t.ei", "i.eid"))
                .where("")
                .and(Essential.EQ("emp_name", null))
                .or(Essential.LIKE("emp_adr", " "))
                .and(Essential.NOT_IN("emp_status", emptyList))
                .build();

        System.out.println(querySql.getSql());
    }

    @Test
    public void t_05_criteria()
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
                .innerJoin("EMP_INFO", "i").on(Essential.JOINT_EQ("t.ei", "i.eid"))
                .where(Criteria.EQUAL("emp_name", null, c-> c!=null && c.length()!=0, Objects::nonNull))
                .or(Criteria.LIKE("emp_adr", " ", c-> c!=null && c.length()!=0, a-> true))
                .and(Criteria.NOT_IN("emp_status", emptyList, c-> c!=null && c.length()!=0, a-> true))
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
                    .and(Omissibl.EQ("emp_name", new BaseQuerySqlBuilder()
                                                                .select("ename")
                                                                .from("USER")
                                                                .where("uid = 1")
                                                                .build()))
                    .or(Omissibl.IN("emp_adr", new LimitSql(new BaseQuerySqlBuilder()
                                                            .select("eadr")
                                                            .from("ADDRESS")
                                                            .build(), 25, 8)))
                    .build();

        System.out.println(querySql.getSql());
    }

    @Test
    public void t_07_multiCondition()
    {
        String sql = new BaseQuerySqlBuilder()
                .select("*")
                .from("emp")
                .where(Conditions
                        .first(Omissibl.EQ("ename", "SMITH"))
                        .or(Omissibl.EQ("job", "SALESMAN"))
                        .finish())
                    .and(Omissibl.GE("sal", 1500))
                .build().getSql();

        System.out.println(sql);
    }

    @Test
    public void t_08_baseQuerySqlBuilderFactory()
    {
        BaseQuerySqlBuilderFactory queryFactory = BaseQuerySqlBuilderFactory.getInstance();

        String sql = queryFactory.manu()
                .select("*")
                .from("emp")
                .where(Conditions
                        .first(Omissibl.EQ("ename", "SMITH"))
                        .or(Omissibl.EQ("job", "SALESMAN"))
                        .finish())
                .and(Omissibl.GE("sal", 1500))
                .build().getSql();
        System.out.println(sql);

        String sql1 = queryFactory.obtain(false).build().getSql();
        System.out.println(sql1);

        String limit;
        int page = 1;
        int size = 25;
        while (page <= 10)
        {
            int offset = (page++ - 1) * size;
            limit = new LimitSql(queryFactory.obtain(false).build(), offset, size).getSql();
            System.out.println(limit);
        }

        String newWhere = queryFactory.obtain(false).clearWhere().where(Where.ANYWHERE).build().getSql();
        System.out.println(newWhere);

    }








}
