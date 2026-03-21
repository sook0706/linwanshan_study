1.1 数据库相关概念
数据库DataBase（DB）：存储数据的仓库，数据是有组织的进行存储
数据库管理系统DataBase Management System (DBMS)：操纵和管理数据库的大型软件
SQL Structured QueryLanguage (SQL)：操作关系型数据库的编程语言，定义了一套操作关系型数据库统一标准

2.1 SQL通用语法
1)SQL语句可以单行或多行书写，以分号结尾。
2)SQL语句可以使用空格/缩进来增强语句的可读性。
3)MySQL数据库的SQL语句不区分大小写，关键字建议使用大写。
4)注释：单行注释：--注释内容或#注释内容
  多行注释：/*注释内容*/

2.2 SQL分类
  SQL语句，根据其功能，主要分为四类：DDL、DML、DQL、DCL
  DDL Data DefinitionLanguage数据定义语言，用来定义数据库对象(数据库，表，字段)
  DML Data ManipulationLanguage数据操作语言，用来对数据库表中的数据进行增删改
  DQL Data Query Language数据查询语言，用来查询数据库中表的记录
  DCL Data Control Language数据控制语言，用来创建数据库用户、控制数据库的访问权限

2.3.1 数据库操作
1).查询所有数据库
  show databases;
2).查询当前数据库
  select database();
3).创建数据库
  create database[if not exists]数据库名[defaultcharset字符集][collate排序规则];
  案例：
  A.创建一个itcast数据库,使用数据库默认的字符集。create database itcast;
  在同一个数据库服务器中，不能创建两个名称相同的数据库，否则将会报错。
  可以通过if not exists参数来解决这个问题，数据库不存在,则创建该数据库，如果存在，则不创建。create database if not extists itcast;
  B.创建一个itheima数据库，并且指定字符集create database itheima default charset utf8mb4;
4).删除数据库
drop database [ if exists ]数据库名;
如果删除一个不存在的数据库，将会报错。此时，可以加上参数if exists，如果数据库存在，再执行删除，否则不执行删除。
5).切换数据库
use 数据库名;
我们要操作某一个数据库下的表时，就需要通过该指令，切换到对应的数据库下，否则是不能操作的。
比如，切换到itcast数据，执行如下SQL：use itcast;


