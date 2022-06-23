# SqlConstructor
> An util for assisting to write sql
>
> 目的是为了协同jdbcTemplate等手写sql的场景。
>
> 目前适用的sql语言：
>   + MySQL

### 使用方式
#### 1. 构建查询语句
参考案例：[QuerySql测试类](https://github.com/complicatedxin/sqlconstructor/tree/main/src/test/java/com/zincyanide/sqlconstructor/T_01_Query.java)

首先，此工具中一个查询sql被分为两部分：
+ 基本查询语句：如`select * from T where 1 = 1`
+ 扩展语句：如`select count(1) ...`, `... order by`, `... limit 2,3`等

所以在查询语句被分为两步来构建。

1. 使用`BaseQuerySqlBuilder`来构建基本查询语句
2. 使用`QuerySqlWrapper`的实现类来对基本语句扩展

##### 1.1 QuerySqlWrapper
```java
    String sqlStr = "SELECT * FROM T";
```
一个基本查询语句`"SELECT * FROM T"`已经被确定了，
如果要对其扩展，需要先将其包装成一个`SqlConstructor`对象，这里使用具体实现类`BaseQuerySql`来完成。
```java
    SqlConstructor base = new BaseQuerySql(sqlStr);
```
之后只需要将`base`以参数方式传入具体扩展语句的构造方法。
```java
    SqlConstructor count = new CountSql(base);
    SqlConstructor limit = new LimitSql(base, 8, 25);
    SqlConstructor order = new OrderSql(base, "CREAT_TIME", false);
```
然后调用扩展对象的`getSql()`方法，返回字符串sql。
```java
    System.out.println("countSql: " + count.getSql());
    System.out.println("limitSql: " + limit.getSql());
    System.out.println("orderSql: " + order.getSql());
    System.out.println("baseSql: " + base.getSql());
```
（baseQuerySql同样支持`getSql()`，虽然对于sql不会发生什么变化）

所以，包装器可以作为参数被传入其他包装器，比如用来当构建`... order by xxx limit y,z`：
```java
    SqlConstructor orderThenLimit = new LimitSql(order, 5, 25);
    orderThenLimit.getSql();
```

##### 1.2 BaseQuerySqlBuilder
此工具为辅助构建`BaseQuerySql`的API —— `BaseQuerySqlBuilder`
```java
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
    
    querySql.getSql();
```
之后可以再结合其他`QuerySqlWrapper`实现类进行扩展。

##### 1.3 Omissibl
为在条件语句的书写上提供的API
```java
    SqlConstructor querySql = new BaseQuerySqlBuilder()
                .select("*")
                .from("EMP_TASK", "t")
                    .innerJoin("EMP_INFO", "i").on(Omissibl.JOINT("t.eid", "i.eid"))
                .where(Where.ANYWHERE)
                    .and(Omissibl.EQUAL("emp_name", "Andy"))
                    .or(Omissibl.LIKE_START_WITH("emp_adr", "上海"))
                    .and(Omissibl.LE("createTime", new Date()))
                .build();
```
可以不再手动填加`'`，或者`%`。

同样可以不用再考虑参数是否为空，而使链式编程变得不连贯。
```java
    List<Object> emptyList = Collections.emptyList();
    
    SqlConstructor querySql = new BaseQuerySqlBuilder()
                .select("*")
                .from("EMP_TASK", "t")
                    .innerJoin("EMP_INFO", "i").on(Omissibl.JOINT("t.eid", "i.eid"))
                .where("")
                    .and(Omissibl.EQUAL("emp_name", null))
                    .or(Omissibl.LIKE("emp_adr", " "))
                    .and(Omissibl.NOT_IN("emp_status", emptyList))
                .build();
```

##### 1.4 Essential
相对于`Omissibl`使得空参条件不拼接，`Essential`会严格校验参数，如果参数为空则抛出异常。
```java
    SqlConstructor querySql = new BaseQuerySqlBuilder()
                .select("*")
                .from("EMP_TASK", "t")
                    .innerJoin("EMP_INFO", "i").on(Omissibl.JOINT("t.ei", "i.eid"))
                .where("")
                    .and(Essential.EQUAL("emp_name", null))
                    .or(Essential.LIKE("emp_adr", " "))
                    .and(Essential.NOT_IN("emp_status", emptyList))
                .build();
```

##### 1.5 嵌套查询
```java
    SqlConstructor querySql =
            new BaseQuerySqlBuilder()
                .select("*")
                .from("EMP")
                .where(Where.ANYWHERE)
                .and(Omissibl.EQUAL("emp_name", new BaseQuerySqlBuilder()
                                                    .select("ename")
                                                    .from("USER")
                                                    .where("uid = 1")
                                                    .build()))
                .or(Omissibl.IN("emp_adr", new LimitSql(
                                                new BaseQuerySqlBuilder()
                                                    .select("eadr")
                                                    .from("ADDRESS")
                                                    .build(),
                                                25, 8)))
                .build();
```

##### 1.6 Conditions
为组合条件提供的API
```java
    String sql =
             new BaseQuerySqlBuilder()
                .select("*")
                .from("emp")
                .where(Conditions
                        .first(Omissibl.EQUAL("ename", "SMITH"))
                        .or(Omissibl.EQUAL("job", "SALESMAN"))
                        .finish())
                .and(Omissibl.GE("sal", 1500))
                .build()
                .getSql();
```

##### 1.7 BaseQuerySqlBuilderFactory接口

> 目的：创建BaseQuerySqlBuilder

例如创建`MySQLBaseQuerySqlBuilderFactory`

```java
        BaseQuerySqlBuilderFactory mySqlQuery = MySQLBaseQuerySqlBuilderFactory.getInstance();
        BaseQuerySqlBuilder sqlBuilder = mySqlQuery.manu();
```
###### 1.7.1 ReusableBaseQuerySqlBuilderFactory接口

> 目的：创建可重用的BaseQuerySqlBuilder

`MySQLBaseQuerySqlBuilder`就是可重用的，它实现了Reusable接口，以及Attachable接口，它能与线程绑定以达到可重用。

```java
        ReusableBaseQuerySqlBuilderFactory mySqlQuery = MySQLBaseQuerySqlBuilderFactory.getInstance();
        BaseQuerySqlBuilder sqlBuilder = mySqlQuery.obtain();
```

此方法适用于分页等操作
```java
        String limit;
        int page = 1;
        int size = 25;
        while (page <= 10)
        {
            int offset = (page++ - 1) * size;
            limit = new LimitSql(mySqlQuery.obtain().build(), offset, size).getSql();
        }
```

支持子句某一部分需要修改，包括select、from、join(on)、where
```java
        String newWhere = mySqlQuery.obtain().cleanWhere().where(Where.ANYWHERE).build().getSql();
```





