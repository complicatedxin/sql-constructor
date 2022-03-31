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
    SqlConstructor orderAndLimit = new OrderSql(limit, "UPDATE_TIME", true);
    orderAndLimit.getSql()
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

##### 1.3 Criteria
为在条件语句的书写上提供的API
```java
    SqlConstructor querySql = new BaseQuerySqlBuilder()
                .select("*")
                .from("EMP_TASK", "t")
                    .innerJoin("EMP_INFO", "i").on(Criteria.JOINT("t.eid", "i.eid"))
                .where(Where.ANYWHERE)
                    .and(Criteria.EQUAL("emp_name", "Andy"))
                    .or(Criteria.LIKE_START_WITH("emp_adr", "上海"))
                    .and(Criteria.LE("createTime", new Date()))
                .build();
```
可以不再手动填加`'`，或者`%`。

同样可以不用再考虑参数是否为空，而使链式编程变得不连贯。
```java
    List<Object> emptyList = Collections.emptyList();
    
    SqlConstructor querySql = new BaseQuerySqlBuilder()
                .select("*")
                .from("EMP_TASK", "t")
                    .innerJoin("EMP_INFO", "i").on(Criteria.JOINT("t.eid", "i.eid"))
                .where("")
                    .and(Criteria.EQUAL("emp_name", null))
                    .or(Criteria.LIKE("emp_adr", " "))
                    .and(Criteria.NOT_IN("emp_status", emptyList))
                .build();
```

##### 1.4 嵌套查询
```java
    SqlConstructor querySql =
            new BaseQuerySqlBuilder()
                .select("*")
                .from("EMP")
                .where(Where.ANYWHERE)
                .and(Criteria.EQUAL("emp_name", new BaseQuerySqlBuilder()
                                                    .select("ename")
                                                    .from("USER")
                                                    .where("uid = 1")
                                                    .build()))
                .or(Criteria.IN("emp_adr", new LimitSql(
                                                new BaseQuerySqlBuilder()
                                                    .select("eadr")
                                                    .from("ADDRESS")
                                                    .build(),
                                                25, 8)))
                .build();
```

##### 1.5 MultiCondition
为组合条件提供的API
```java
    String sql =
             new BaseQuerySqlBuilder()
                .select("*")
                .from("emp")
                .where(MultiCondition
                        .first(Criteria.EQUAL("ename", "SMITH"))
                        .or(Criteria.EQUAL("job", "SALESMAN"))
                        .finish())
                .and(Criteria.GE("sal", 1500))
                .build()
                .getSql();
```


