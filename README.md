# smbms_java_web

基于Java web的超市管理系统，数据库课程设计

## 参考视频

[狂神说Java](https://www.bilibili.com/video/BV12J411M7Sj?p=38&share_source=copy_web)


## 1.引言

本项目参考自狂神说Java。

是一个基于Java Web连接MySQL的小项目。

<!--more-->

参考教程： [狂神说Java](https://www.bilibili.com/video/BV12J411M7Sj?p=30&share_source=copy_web)

教程相关资源：[CSDN](https://blog.csdn.net/csnz123123/article/details/116236515),[CSDN](https://blog.csdn.net/niannujiao6/article/details/108569138)，[gitee](https://gitee.com/git_baboben/smbms/tree/master)

> 附上本项目的GitHub地址： https://github.com/grant1499/smbms_java_web/tree/master

超市管理系统(smbms)作为每个计算机专业的大学生都是一个很好的练手项目，逻辑层次分明，基础功能包括用户的登录和注销，用户和供应商以及订单信息的增删查改的基础功能。可以帮助我们更好的加深理解三层架构的理念，本项目作为纯Java Web版，不涉及Spring和SpringBoot的知识，就是帮助我们从底层和从源代码开始理解，为以后的微服务和作铺垫。

## 2.项目框图

**系统整体架构简略图如下：**

![image-20211209220942861](https://gitee.com/grant1499/blog-pic/raw/master/img/202112092209932.png)

## 3.数据库准备工作

数据库以及数据表的架构设计如下：

![image-20211209222332228](https://gitee.com/grant1499/blog-pic/raw/master/img/202112092223295.png)

打开SQL yog，输入以下数据库创建语句。

```sql
CREATE DATABASE `smbms`;

USE `smbms`;

CREATE TABLE `smbms_address` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `contact` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系人姓名',
  `addressDesc` VARCHAR(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '收货地址明细',
  `postCode` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '邮编',
  `tel` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系人电话',
  `createdBy` BIGINT(20) DEFAULT NULL COMMENT '创建者',
  `creationDate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modifyBy` BIGINT(20) DEFAULT NULL COMMENT '修改者',
  `modifyDate` DATETIME DEFAULT NULL COMMENT '修改时间',
  `userId` BIGINT(20) DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT  INTO `smbms_address`(`id`,`contact`,`addressDesc`,`postCode`,`tel`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`,`userId`) VALUES (1,'王丽','北京市东城区东交民巷44号','100010','13678789999',1,'2016-04-13 00:00:00',NULL,NULL,1),(2,'张红丽','北京市海淀区丹棱街3号','100000','18567672312',1,'2016-04-13 00:00:00',NULL,NULL,1),(3,'任志强','北京市东城区美术馆后街23号','100021','13387906742',1,'2016-04-13 00:00:00',NULL,NULL,1),(4,'曹颖','北京市朝阳区朝阳门南大街14号','100053','13568902323',1,'2016-04-13 00:00:00',NULL,NULL,2),(5,'李慧','北京市西城区三里河路南三巷3号','100032','18032356666',1,'2016-04-13 00:00:00',NULL,NULL,3),(6,'王国强','北京市顺义区高丽营镇金马工业区18号','100061','13787882222',1,'2016-04-13 00:00:00',NULL,NULL,3);


DROP TABLE IF EXISTS `smbms_bill`;

CREATE TABLE `smbms_bill` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `billCode` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '账单编码',
  `productName` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品名称',
  `productDesc` VARCHAR(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品描述',
  `productUnit` VARCHAR(10) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '商品单位',
  `productCount` DECIMAL(20,2) DEFAULT NULL COMMENT '商品数量',
  `totalPrice` DECIMAL(20,2) DEFAULT NULL COMMENT '商品总额',
  `isPayment` INT(10) DEFAULT NULL COMMENT '是否支付（1：未支付 2：已支付）',
  `createdBy` BIGINT(20) DEFAULT NULL COMMENT '创建者（userId）',
  `creationDate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modifyBy` BIGINT(20) DEFAULT NULL COMMENT '更新者（userId）',
  `modifyDate` DATETIME DEFAULT NULL COMMENT '更新时间',
  `providerId` BIGINT(20) DEFAULT NULL COMMENT '供应商ID',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT  INTO `smbms_bill`(`id`,`billCode`,`productName`,`productDesc`,`productUnit`,`productCount`,`totalPrice`,`isPayment`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`,`providerId`) VALUES (2,'BILL2016_002','香皂、肥皂、药皂','日用品-皂类','块','1000.00','10000.00',2,1,'2016-03-23 04:20:40',NULL,NULL,13),(3,'BILL2016_003','大豆油','食品-食用油','斤','300.00','5890.00',2,1,'2014-12-14 13:02:03',NULL,NULL,6),(4,'BILL2016_004','橄榄油','食品-进口食用油','斤','200.00','9800.00',2,1,'2013-10-10 03:12:13',NULL,NULL,7),(5,'BILL2016_005','洗洁精','日用品-厨房清洁','瓶','500.00','7000.00',2,1,'2014-12-14 13:02:03',NULL,NULL,9),(6,'BILL2016_006','美国大杏仁','食品-坚果','袋','300.00','5000.00',2,1,'2016-04-14 06:08:09',NULL,NULL,4),(7,'BILL2016_007','沐浴液、精油','日用品-沐浴类','瓶','500.00','23000.00',1,1,'2016-07-22 10:10:22',NULL,NULL,14),(8,'BILL2016_008','不锈钢盘碗','日用品-厨房用具','个','600.00','6000.00',2,1,'2016-04-14 05:12:13',NULL,NULL,14),(9,'BILL2016_009','塑料杯','日用品-杯子','个','350.00','1750.00',2,1,'2016-02-04 11:40:20',NULL,NULL,14),(10,'BILL2016_010','豆瓣酱','食品-调料','瓶','200.00','2000.00',2,1,'2013-10-29 05:07:03',NULL,NULL,8),(11,'BILL2016_011','海之蓝','饮料-国酒','瓶','50.00','10000.00',1,1,'2016-04-14 16:16:00',NULL,NULL,1),(12,'BILL2016_012','芝华士','饮料-洋酒','瓶','20.00','6000.00',1,1,'2016-09-09 17:00:00',NULL,NULL,1),(13,'BILL2016_013','长城红葡萄酒','饮料-红酒','瓶','60.00','800.00',2,1,'2016-11-14 15:23:00',NULL,NULL,1),(14,'BILL2016_014','泰国香米','食品-大米','斤','400.00','5000.00',2,1,'2016-10-09 15:20:00',NULL,NULL,3),(15,'BILL2016_015','东北大米','食品-大米','斤','600.00','4000.00',2,1,'2016-11-14 14:00:00',NULL,NULL,3),(16,'BILL2016_016','可口可乐','饮料','瓶','2000.00','6000.00',2,1,'2012-03-27 13:03:01',NULL,NULL,2),(17,'BILL2016_017','脉动','饮料','瓶','1500.00','4500.00',2,1,'2016-05-10 12:00:00',NULL,NULL,2),(18,'BILL2016_018','哇哈哈','饮料','瓶','2000.00','4000.00',2,1,'2015-11-24 15:12:03',NULL,NULL,2);

DROP TABLE IF EXISTS `smbms_provider`;

CREATE TABLE `smbms_provider` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `proCode` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '供应商编码',
  `proName` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '供应商名称',
  `proDesc` VARCHAR(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '供应商详细描述',
  `proContact` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '供应商联系人',
  `proPhone` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '联系电话',
  `proAddress` VARCHAR(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地址',
  `proFax` VARCHAR(20) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '传真',
  `createdBy` BIGINT(20) DEFAULT NULL COMMENT '创建者（userId）',
  `creationDate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modifyDate` DATETIME DEFAULT NULL COMMENT '更新时间',
  `modifyBy` BIGINT(20) DEFAULT NULL COMMENT '更新者（userId）',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT  INTO `smbms_provider`(`id`,`proCode`,`proName`,`proDesc`,`proContact`,`proPhone`,`proAddress`,`proFax`,`createdBy`,`creationDate`,`modifyDate`,`modifyBy`) VALUES (1,'BJ_GYS001','北京三木堂商贸有限公司','长期合作伙伴，主营产品:茅台、五粮液、郎酒、酒鬼酒、泸州老窖、赖茅酒、法国红酒等','张国强','13566667777','北京市丰台区育芳园北路','010-58858787',1,'2013-03-21 16:52:07',NULL,NULL),(2,'HB_GYS001','石家庄帅益食品贸易有限公司','长期合作伙伴，主营产品:饮料、水饮料、植物蛋白饮料、休闲食品、果汁饮料、功能饮料等','王军','13309094212','河北省石家庄新华区','0311-67738876',1,'2016-04-13 04:20:40',NULL,NULL),(3,'GZ_GYS001','深圳市泰香米业有限公司','初次合作伙伴，主营产品：良记金轮米,龙轮香米等','郑程瀚','13402013312','广东省深圳市福田区深南大道6006华丰大厦','0755-67776212',1,'2014-03-21 16:56:07',NULL,NULL),(4,'GZ_GYS002','深圳市喜来客商贸有限公司','长期合作伙伴，主营产品：坚果炒货.果脯蜜饯.天然花茶.营养豆豆.特色美食.进口食品.海味零食.肉脯肉','林妮','18599897645','广东省深圳市福龙工业区B2栋3楼西','0755-67772341',1,'2013-03-22 16:52:07',NULL,NULL),(5,'JS_GYS001','兴化佳美调味品厂','长期合作伙伴，主营产品：天然香辛料、鸡精、复合调味料','徐国洋','13754444221','江苏省兴化市林湖工业区','0523-21299098',1,'2015-11-22 16:52:07',NULL,NULL),(6,'BJ_GYS002','北京纳福尔食用油有限公司','长期合作伙伴，主营产品：山茶油、大豆油、花生油、橄榄油等','马莺','13422235678','北京市朝阳区珠江帝景1号楼','010-588634233',1,'2012-03-21 17:52:07',NULL,NULL),(7,'BJ_GYS003','北京国粮食用油有限公司','初次合作伙伴，主营产品：花生油、大豆油、小磨油等','王驰','13344441135','北京大兴青云店开发区','010-588134111',1,'2016-04-13 00:00:00',NULL,NULL),(8,'ZJ_GYS001','慈溪市广和绿色食品厂','长期合作伙伴，主营产品：豆瓣酱、黄豆酱、甜面酱，辣椒，大蒜等农产品','薛圣丹','18099953223','浙江省宁波市慈溪周巷小安村','0574-34449090',1,'2013-11-21 06:02:07',NULL,NULL),(9,'GX_GYS001','优百商贸有限公司','长期合作伙伴，主营产品：日化产品','李立国','13323566543','广西南宁市秀厢大道42-1号','0771-98861134',1,'2013-03-21 19:52:07',NULL,NULL),(10,'JS_GYS002','南京火头军信息技术有限公司','长期合作伙伴，主营产品：不锈钢厨具等','陈女士','13098992113','江苏省南京市浦口区浦口大道1号新城总部大厦A座903室','025-86223345',1,'2013-03-25 16:52:07',NULL,NULL),(11,'GZ_GYS003','广州市白云区美星五金制品厂','长期合作伙伴，主营产品：海绵床垫、坐垫、靠垫、海绵枕头、头枕等','梁天','13562276775','广州市白云区钟落潭镇福龙路20号','020-85542231',1,'2016-12-21 06:12:17',NULL,NULL),(12,'BJ_GYS004','北京隆盛日化科技','长期合作伙伴，主营产品：日化环保清洗剂，家居洗涤专卖、洗涤用品网、墙体除霉剂、墙面霉菌清除剂等','孙欣','13689865678','北京市大兴区旧宫','010-35576786',1,'2014-11-21 12:51:11',NULL,NULL),(13,'SD_GYS001','山东豪克华光联合发展有限公司','长期合作伙伴，主营产品：洗衣皂、洗衣粉、洗衣液、洗洁精、消杀类、香皂等','吴洪转','13245468787','山东济阳济北工业区仁和街21号','0531-53362445',1,'2015-01-28 10:52:07',NULL,NULL),(14,'JS_GYS003','无锡喜源坤商行','长期合作伙伴，主营产品：日化品批销','周一清','18567674532','江苏无锡盛岸西路','0510-32274422',1,'2016-04-23 11:11:11',NULL,NULL),(15,'ZJ_GYS002','乐摆日用品厂','长期合作伙伴，主营产品：各种中、高档塑料杯，塑料乐扣水杯（密封杯）、保鲜杯（保鲜盒）、广告杯、礼品杯','王世杰','13212331567','浙江省金华市义乌市义东路','0579-34452321',1,'2016-08-22 10:01:30',NULL,NULL);


DROP TABLE IF EXISTS `smbms_role`;

CREATE TABLE `smbms_role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `roleCode` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色编码',
  `roleName` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '角色名称',
  `createdBy` BIGINT(20) DEFAULT NULL COMMENT '创建者',
  `creationDate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modifyBy` BIGINT(20) DEFAULT NULL COMMENT '修改者',
  `modifyDate` DATETIME DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


INSERT  INTO `smbms_role`(`id`,`roleCode`,`roleName`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`) VALUES (1,'SMBMS_ADMIN','系统管理员',1,'2016-04-13 00:00:00',NULL,NULL),(2,'SMBMS_MANAGER','经理',1,'2016-04-13 00:00:00',NULL,NULL),(3,'SMBMS_EMPLOYEE','普通员工',1,'2016-04-13 00:00:00',NULL,NULL);


DROP TABLE IF EXISTS `smbms_user`;

CREATE TABLE `smbms_user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `userCode` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户编码',
  `userName` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名称',
  `userPassword` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户密码',
  `gender` INT(10) DEFAULT NULL COMMENT '性别（1:女、 2:男）',
  `birthday` DATE DEFAULT NULL COMMENT '出生日期',
  `phone` VARCHAR(15) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机',
  `address` VARCHAR(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地址',
  `userRole` BIGINT(20) DEFAULT NULL COMMENT '用户角色（取自角色表-角色id）',
  `createdBy` BIGINT(20) DEFAULT NULL COMMENT '创建者（userId）',
  `creationDate` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modifyBy` BIGINT(20) DEFAULT NULL COMMENT '更新者（userId）',
  `modifyDate` DATETIME DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT  INTO `smbms_user`(`id`,`userCode`,`userName`,`userPassword`,`gender`,`birthday`,`phone`,`address`,`userRole`,`createdBy`,`creationDate`,`modifyBy`,`modifyDate`) VALUES (1,'admin','系统管理员','1234567',1,'1983-10-10','13688889999','北京市海淀区成府路207号',1,1,'2013-03-21 16:52:07',NULL,NULL),(2,'liming','李明','0000000',2,'1983-12-10','13688884457','北京市东城区前门东大街9号',2,1,'2014-12-31 19:52:09',NULL,NULL),(5,'hanlubiao','韩路彪','0000000',2,'1984-06-05','18567542321','北京市朝阳区北辰中心12号',2,1,'2014-12-31 19:52:09',NULL,NULL),(6,'zhanghua','张华','0000000',1,'1983-06-15','13544561111','北京市海淀区学院路61号',3,1,'2013-02-11 10:51:17',NULL,NULL),(7,'wangyang','王洋','0000000',2,'1982-12-31','13444561124','北京市海淀区西二旗辉煌国际16层',3,1,'2014-06-11 19:09:07',NULL,NULL),(8,'zhaoyan','赵燕','0000000',1,'1986-03-07','18098764545','北京市海淀区回龙观小区10号楼',3,1,'2016-04-21 13:54:07',NULL,NULL),(10,'sunlei','孙磊','0000000',2,'1981-01-04','13387676765','北京市朝阳区管庄新月小区12楼',3,1,'2015-05-06 10:52:07',NULL,NULL),(11,'sunxing','孙兴','0000000',2,'1978-03-12','13367890900','北京市朝阳区建国门南大街10号',3,1,'2016-11-09 16:51:17',NULL,NULL),(12,'zhangchen','张晨','0000000',1,'1986-03-28','18098765434','朝阳区管庄路口北柏林爱乐三期13号楼',3,1,'2016-08-09 05:52:37',1,'2016-04-14 14:15:36'),(13,'dengchao','邓超','0000000',2,'1981-11-04','13689674534','北京市海淀区北航家属院10号楼',3,1,'2016-07-11 08:02:47',NULL,NULL),(14,'yangguo','杨过','0000000',2,'1980-01-01','13388886623','北京市朝阳区北苑家园茉莉园20号楼',3,1,'2015-02-01 03:52:07',NULL,NULL),(15,'zhaomin','赵敏','0000000',1,'1987-12-04','18099897657','北京市昌平区天通苑3区12号楼',2,1,'2015-09-12 12:02:12',NULL,NULL);
```

## 4.创建Maven Web项目

通过webapp模板创建，不会的参考java web相关笔记。

搭建项目，创建相关包、目录结构，然后配置Tomcat服务器。

设置好`pom.xml`的依赖环境：

```xml
<dependencies>
    <!--    servlet-->
     <dependency>
         <groupId>javax.servlet</groupId>
         <artifactId>javax.servlet-api</artifactId>
         <version>4.0.0</version>
         <scope>provided</scope>
    </dependency>
    <!--    jsp-->
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
    <!--      mysql-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.47</version>
    </dependency>
    <!--    JSTL表达式-->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>
    <!--      standard-->
    <dependency>
        <groupId>taglibs</groupId>
        <artifactId>standard</artifactId>
        <version>1.1.2</version>
    </dependency>
</dependencies>
```

创建项目包结构：

![image-20211210135413620](https://gitee.com/grant1499/blog-pic/raw/master/img/202112101354761.png)

`dao`层用于操作数据库的数据，将数据库的连接，关闭以及增删改查等方法封装起来。

`pojo`是根据数据库中的字段信息编写的实体类。

`service`业务层调用`DAO`层的方法。

`servlet`层实现前后端的交互。

测试idea连接数据库：参考MySQL学习笔记（十）。

`DAO`模式：

DAO (DataAccessobjects 数据存取对象)是指位于业务逻辑和持久化数据之间实现对持久化数据的访问。通俗来讲，就是将数据库操作都封装起来。

一篇简短的[介绍](https://www.runoob.com/note/27029)

关于`DAO`的详细知识参考[韩顺平jdbc教程](https://www.bilibili.com/video/BV1zv41157NC?p=33&share_source=copy_web)

**一个典型的DAO 模式主要由以下几部分组成。**

- 1、DAO接口： 把对数据库的所有操作定义成抽象方法，可以提供多种实现。
- 2、DAO 实现类： 针对不同数据库给出DAO接口定义方法的具体实现。
- 3、实体类：用于存放与传输对象数据。
- 4、数据库连接和关闭工具类： 避免了数据库连接和关闭代码的重复使用，方便修改。

## 5.建立实体类

就是ORM映射思想 将数据库表和实体类一 一对应。（表-->类映射）

建立四个实体类。

在`pojo`之下新建`User.java`：

```java
import java.util.Date;

public class User {
    private Integer id;
    private String userCode;    //用户编码
    private String userName;
    private String userPassword;
    private Integer gender;
    private Date birthday;
    private String phone;
    private String address;
    private Integer userRole;
    private Integer createdBy;  //创建者
    private Date creationDate;
    private Integer modifyBy;   //更新者
    private Date modifyDate;    //更新时间

    private Integer age;    //年龄
    private String userRoleName;    //用户角色名称

    public Integer getAge() {
        Date date = new Date();
        Integer age = date.getYear()-birthday.getYear();
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserRoleName() {
        return userRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getcreationDate() {
        return creationDate;
    }

    public void setcreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
```

在`pojo`之下新建`Role.java`：

```java
import java.util.Date;
public class Role {
    private Integer id;   //id
    private String roleCode; //角色编码
    private String roleName; //角色名称
    private Integer createdBy; //创建者
    private Date creationDate; //创建时间
    private Integer modifyBy; //更新者
    private Date modifyDate;//更新时间

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getRoleCode() {
        return roleCode;
    }
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public Integer getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public Integer getModifyBy() {
        return modifyBy;
    }
    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }
    public Date getModifyDate() {
        return modifyDate;
    }
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
```

在`pojo`之下新建`Provider.java`：

```java
import java.util.Date;
public class Provider {
    private Integer id;   //id
    private String proCode; //供应商编码
    private String proName; //供应商名称
    private String proDesc; //供应商描述
    private String proContact; //供应商联系人
    private String proPhone; //供应商电话
    private String proAddress; //供应商地址
    private String proFax; //供应商传真
    private Integer createdBy; //创建者
    private Date creationDate; //创建时间
    private Integer modifyBy; //更新者
    private Date modifyDate;//更新时间

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getProCode() {
        return proCode;
    }
    public void setProCode(String proCode) {
        this.proCode = proCode;
    }
    public String getProName() {
        return proName;
    }
    public void setProName(String proName) {
        this.proName = proName;
    }
    public String getProDesc() {
        return proDesc;
    }
    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }
    public String getProContact() {
        return proContact;
    }
    public void setProContact(String proContact) {
        this.proContact = proContact;
    }
    public String getProPhone() {
        return proPhone;
    }
    public void setProPhone(String proPhone) {
        this.proPhone = proPhone;
    }
    public String getProAddress() {
        return proAddress;
    }
    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }
    public String getProFax() {
        return proFax;
    }
    public void setProFax(String proFax) {
        this.proFax = proFax;
    }
    public Integer getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public Integer getModifyBy() {
        return modifyBy;
    }
    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }
    public Date getModifyDate() {
        return modifyDate;
    }
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
```

在`pojo`之下新建`Bill.java`：

```java
import java.math.BigDecimal;
import java.util.Date;
public class Bill {
    private Integer id;   //id
    private String billCode; //账单编码
    private String productName; //商品名称
    private String productDesc; //商品描述
    private String productUnit; //商品单位
    private BigDecimal productCount; //商品数量
    private BigDecimal totalPrice; //总金额
    private Integer isPayment; //是否支付
    private Integer providerId; //供应商ID
    private Integer createdBy; //创建者
    private Date creationDate; //创建时间
    private Integer modifyBy; //更新者
    private Date modifyDate;//更新时间

    private String providerName;//供应商名称


    public String getProviderName() {
        return providerName;
    }
    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getBillCode() {
        return billCode;
    }
    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductDesc() {
        return productDesc;
    }
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
    public String getProductUnit() {
        return productUnit;
    }
    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }
    public BigDecimal getProductCount() {
        return productCount;
    }
    public void setProductCount(BigDecimal productCount) {
        this.productCount = productCount;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Integer getIsPayment() {
        return isPayment;
    }
    public void setIsPayment(Integer isPayment) {
        this.isPayment = isPayment;
    }

    public Integer getProviderId() {
        return providerId;
    }
    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
    public Integer getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public Integer getModifyBy() {
        return modifyBy;
    }
    public void setModifyBy(Integer modifyBy) {
        this.modifyBy = modifyBy;
    }
    public Date getModifyDate() {
        return modifyDate;
    }
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
```

## 6.编写基础公共类

### 6.1 数据库配置文件

在`resources`之下新建`db.properties`：

```java
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/smbms?useSSL=false&useUnicode=true&characterEncoding=utf8
username=root
password=xxxxx        // 数据库名和密码自己改一下
```

### 6.2 在Dao层编写操作数据库的公共类

在`dao` 下新建`BaseDao.java`：

```java
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class BaseDao {
    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    //静态代码块 类加载的时候初始化
    static {
        Properties properties = new Properties();
        //通过类加载器读取对应的资源
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        //  properties读取文件内容
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver = properties.getProperty("driver");
        url = properties.getProperty("url");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
    }
    //获取数据库的连接
    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    //编写查询公共方法
    public static ResultSet execute(Connection conn,String sql,PreparedStatement preparedStatement,Object[] params,ResultSet resultSet) throws SQLException {
        //预编译的sql在后面直接执行即可
        preparedStatement = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject 占位符从1开始 而数字从0开始
            preparedStatement.setObject(i+1,params[i]);
        }
        resultSet = preparedStatement.executeQuery();
        return resultSet;
    }
    //编写增删改查公共方法
    public static int execute(Connection conn,String sql,PreparedStatement preparedStatement,Object[] params) throws SQLException {
        //预编译的sql在后面直接执行即可
        int updateRow;
        preparedStatement = conn.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //setObject 占位符从1开始 而数字从0开始
            preparedStatement.setObject(i+1,params[i]);
        }
        updateRow = preparedStatement.executeUpdate();
        return updateRow;
    }
    //释放资源
    public static boolean closeResource(Connection conn,PreparedStatement preparedStatement,ResultSet resultSet){
        boolean flag = true;
        if(conn!=null){
            try {
                conn.close();
                //GC回收
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if(preparedStatement!=null){
            try {
                preparedStatement.close();
                //GC回收
                preparedStatement = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        if(resultSet!=null){
            try {
                resultSet.close();
                //GC回收
                resultSet = null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }
}
```

## 7.编写过滤器处理中文乱码

在`filter`下新建`CharacterENcodingFilter.java`：

```java
import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
```

写完过滤器记得在WEB.xml中进行注册：

```xml
<!--字符过滤器-->
<filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>top.grantdrew.filter.CharacterEncodingFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>CharacterEncodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

## 8.导入前端资源文件

记得是在webapp下面：

![image-20211210161042640](https://gitee.com/grant1499/blog-pic/raw/master/img/202112101612783.png)

## 9.编写登录模块

登录流程图解：

![image-20211210161456464](https://gitee.com/grant1499/blog-pic/raw/master/img/202112101614573.png)

编写`login.jsp`：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>系统登录 - 超市订单管理系统</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath }/css/style.css" />
    <script type="text/javascript">
        /* if(top.location!=self.location){
              top.location=self.location;
         } */
    </script>
</head>
<body class="login_bg">
<section class="loginBox">
    <header class="loginHeader">
        <h1>超市订单管理系统</h1>
    </header>
    <section class="loginCont">
        <form class="loginForm" action="${pageContext.request.contextPath }/login.do"  name="actionForm" id="actionForm"  method="post" >
            <div class="info">${error}</div>
            <div class="inputbox">
                <label for="userCode">用户名：</label>
                <input type="text" class="input-text" id="userCode" name="userCode" placeholder="请输入用户名" required/>
            </div>
            <div class="inputbox">
                <label for="userPassword">密码：</label>
                <input type="password" id="userPassword" name="userPassword" placeholder="请输入密码" required/>
            </div>
            <div class="subBtn">

                <input type="submit" value="登录"/>
                <input type="reset" value="重置"/>
            </div>
        </form>
    </section>
</section>
</body>
</html>
```

在`web.xml`中将`login.jsp`设置为欢迎页（首页）：

```xml
<welcome-file-list>
    <welcome-file>login.jsp</welcome-file>
</welcome-file-list>
```

### 9.1 编写dao层用户登录的接口UserDao

这一步的主要功能就是从首页获取用户输入的名称和密码，与数据库中的数据进行对比。

在`dao`包下新建`user`包，在`user`包下新建`UserDao`接口：

```java
import top.grantdrew.pojo.User;

import java.sql.Connection;

public interface UserDao {
    public User getLoginUser(Connection con,String userCode);
}
```

### 9.2 编写接口UserDao的实现类

`UserDaoImpl.java`：

```java
import com.csnz.dao.BaseDao;
import com.csnz.pojo.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//登录 判断 的实现类
public class UserDaoImpl implements UserDao {
    @Override
    //得到要登录的用户信息
    public User getLoginInfo(Connection conn, String userCode) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        User user = null;

        //如果连数据库都没连接就无需判断了
        if(conn!=null){
            //编写sql语句
            String sql = "select * from smbms_user where userCode = ?";
            //存放参数
            Object[] params = {userCode};
            //使用预处理对象调用  操作数据库的公共类 的执行 sql查询语句
            rs = BaseDao.executeQuery(conn, sql, preparedStatement, params,rs);
            //遍历结果集  封装到一个用户中
            if(rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreateDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }
            //调用  操作数据库的公共类 的执行 释放资源
            BaseDao.closeResource(null,preparedStatement,rs); // 数据库连接暂时不用关闭
        }
        //返回一个用户
        return user;
    }
}
```

### 9.3 编写业务层接口与实现类

在`service`包下新建`user`包，然后在下面新建`UserService`接口：

```java
import com.csnz.pojo.User;
public interface UserService {
    //用户登录
    public abstract User login(String userCode,String passWord);
}
```

在`service`包下的`user`包下新建`UserServlce`接口的实现类：

```java
import org.junit.Test;
import top.grantdrew.dao.BaseDao;
import top.grantdrew.dao.user.UserDao;
import top.grantdrew.dao.user.UserDaoImpl;
import top.grantdrew.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceImpl implements UserService{
    private UserDao userDao;

    public UserServiceImpl(){
        userDao = new UserDaoImpl();
    }

    @Override
    public User login(String userCode, String password) {
        Connection con = null;
        User user = null;

        con = BaseDao.getConnection();
        user = userDao.getLoginUser(con,userCode);

        try {
            BaseDao.release(con,null,null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  user;
    }

    @Test 
    public void test(){ // 这里写了一个临时的测试方法，测试完后删除
        UserServiceImpl userService = new UserServiceImpl();
        User user = userService.login("admin","1243534");
        System.out.println(user.getUserName());
        System.out.println(user.getUserPassword());
    }
}
```

## 10.编写用户登录的Servlet

在`util`包之下新建`Constant`类，存放用户登录状态字符串。

```java
package top.grantdrew.util;

public class Constant {
    public final static String USER_SESSION = "userSession";
}
```

在`servlet`包之下新建`user`包，建立`LoginServlet.java`：

```java
import top.grantdrew.pojo.User;
import top.grantdrew.service.user.UserService;
import top.grantdrew.service.user.UserServiceImpl;
import top.grantdrew.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取前端表单的参数
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        UserService userService = new UserServiceImpl();
        User user = userService.login(userCode,userPassword);

        if (user != null){
            req.getSession().setAttribute(Constant.USER_SESSION,user);
            resp.sendRedirect("jsp/frame.jsp"); // 登录成功跳转主页
        }else{
            req.setAttribute("error","用户名或密码不正确！"); // 在前端jsp页面输出相应信息
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

别忘了在`web.xml`中注册servlet：

```xml
<servlet>
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>top.grantdrew.servlet.user.LoginServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>LoginServlet</servlet-name>
    <url-pattern>/login.do</url-pattern>
</servlet-mapping>
```

BUG修复：

tomcat上运行servlet使用jdbc异常java.lang.ClassNotFoundException: com.mysql.jdbc.Driver。

解决方案：

在tomcat的lib目录下添加一个mysql对应版本的`mysql-connector-java-5.1.47`jar包就行。

BUG修复：

![image-20211210214810567](https://gitee.com/grant1499/blog-pic/raw/master/img/202112102148654.png)

解决方案：

在tomcat的lib目录下添加报错的jar包（jstl包）就行。（去maven仓库下载）

11.编写用户注销登录的Servlet

在`user`包，建立`LogoutServlet.java`：

```java
import top.grantdrew.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute(Constant.USER_SESSION);

        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
```

别忘了注册一下注销的servlet：

```xml
<servlet>
    <servlet-name>LogoutServlet</servlet-name>
    <servlet-class>top.grantdrew.servlet.user.LogoutServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>LogoutServlet</servlet-name>
    <url-pattern>/jsp/logout.do</url-pattern>
</servlet-mapping>
```

## 11.用户登录拦截优化

使用过滤器来拦截未登录用户的非法访问。

新建`SysFilter.java`：

```java
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        User user = (User) req.getSession().getAttribute(Constant.USER_SESSION);

        if (user == null){
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }else{
            filterChain.doFilter(req,resp);
        }
    }

    @Override
    public void destroy() {

    }
}
```

注册过滤器：

```xml
<filter>
    <filter-name>SysFilter</filter-name>
    <filter-class>top.grantdrew.filter.SysFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>SysFilter</filter-name>
    <url-pattern>/jsp/*</url-pattern>
</filter-mapping>
```

配置之后 当我们注销登录后 试图访问用户信息的界面 就会转向到 我们自定义——"找不到界面的 "页面。

## 12.修改密码模块

BUG修复：

浏览器显示jsp页面引入js的部分出现**中文乱码**的情况，尝试在引入js的标签中设置编码为`utf-8`没有用。

在浏览器通过F12查看上述js文件发现乱码。

解决方案：

记事本打开引起乱码的js文件，右键另存为`ANSI`格式覆盖。

引入js的标签中不需要设置编码为`utf-8`了。

改完格式之后**最好清空项目的target目录并使用maven清空一下编译文件，然后重启tomcat，最后再建议清空一下浏览器缓存**。

---

建议的项目开发流程：自底向上，逐层调用。

![image-20211211152222395](https://gitee.com/grant1499/blog-pic/raw/master/img/202112111524539.png)

修改密码并验证两次输入的密码是否一致等工作，交给前端js来处理，也可以用后端servlet处理。

在`dao`包下的`user`包下的`UserDao`接口中完善修改密码等方法，在它的实现类`UserDaoImpl`中实现方法。

```java
 @Override
    public int updatePwd(Connection con, int id, String password) {
        PreparedStatement preparedStatement = null;

        int rs = 0;
        if (con != null){
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object[] params = {password,id};
            try {
                rs = BaseDao.execute(con,sql,preparedStatement,params);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            try {
                BaseDao.release(null,preparedStatement,null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return rs;
    }
```

然后在`service`包下的`user`包下实现相应方法：

```java
@Override
public boolean updatePwd(int id, String password) {
    Connection con = BaseDao.getConnection();
    boolean flag = false;

    if (con != null){
        if (userDao.updatePwd(con,id,password) > 0){
            flag = true;
        }
    }
    return flag;
}
```

然后在`servlet`包下的`user`包下实现`UserServlet`：

```java
import top.grantdrew.pojo.User;
import top.grantdrew.service.user.UserService;
import top.grantdrew.service.user.UserServiceImpl;
import top.grantdrew.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");  // 这个参数封装在前端页面
        if (method != null && method.equals("savepwd")){
            this.updatePwd(req,resp); // 后面对于用户的操作还有很多，这样提取出方法实现了servlet复用
        }
    }

    public void updatePwd(HttpServletRequest req,HttpServletResponse resp){
        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        String password = req.getParameter("newpassword");
        Boolean flag = false;

        if (o != null && password != null && password.length() != 0){
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User)o).getId(),password);
            if (flag){
                req.getSession().setAttribute("message","修改密码成功，请使用新密码登录！");
                req.getSession().removeAttribute(Constant.USER_SESSION);
            }else{
                req.getSession().setAttribute("message","修改密码失败，请重新修改！");
            }
        }else{
            req.getSession().setAttribute("message","新密码不符合要求，请重新输入！");
        }

        try {
            req.getRequestDispatcher(req.getContextPath() + "/login.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

注册一下servl：

```xml
<servlet>
    <servlet-name>UserServlet</servlet-name>
    <servlet-class>top.grantdrew.servlet.user.UserServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>UserServlet</servlet-name>
    <url-pattern>/jsp/user.do</url-pattern>
</servlet-mapping>
```

## 13.使用Ajax优化密码修改

导阿里巴巴的fastjson依赖：

```xml
<!--      阿里巴巴的fastjson:用于转换json格式-->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.61</version>
</dependency>
```

BUG修复：

导入上述依赖之后项目build失败，找不到fastjson的相关类。

推测原因：idea 2020版本与maven构建的build不相容。

解决方案：

![image-20211211210354240](https://gitee.com/grant1499/blog-pic/raw/master/img/202112112103344.png)

将idea的构建和运行托管到maven下面。勾选后，重新编译就能正常运行。

修改上面的`UserServlet`：

```java
import com.alibaba.fastjson.JSONArray;
import top.grantdrew.pojo.User;
import top.grantdrew.service.user.UserService;
import top.grantdrew.service.user.UserServiceImpl;
import top.grantdrew.util.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        System.out.println("method:" + method);
        if (method != null && method.equals("savepwd")){
            this.updatePwd(req,resp);
        }else if (method != null && method.equals("pwdmodify")){
            this.pwdModify(req,resp);
        }
    }

    // 修改密码
    public void updatePwd(HttpServletRequest req,HttpServletResponse resp){
        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        String password = req.getParameter("newpassword");
        Boolean flag = false;

        if (o != null && password != null && password.length() != 0){
            UserService userService = new UserServiceImpl();
            flag = userService.updatePwd(((User)o).getId(),password);
            if (flag){
                req.getSession().setAttribute("message","修改密码成功，请使用新密码登录！");
                req.getSession().removeAttribute(Constant.USER_SESSION);
            }else{
                req.getSession().setAttribute("message","修改密码失败，请重新修改！");
            }
        }else{
            req.getSession().setAttribute("message","新密码不符合要求，请重新输入！");
        }

        try {
            req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 验证旧密码，与ajax对应
    public void pwdModify(HttpServletRequest req,HttpServletResponse resp){
        Object o = req.getSession().getAttribute(Constant.USER_SESSION);
        String oldpqssword = req.getParameter("oldpassword");

        Map<String,String> resultMap = new HashMap<>();

        if (o == null){
            resultMap.put("result","sessionerror");
        }else if (!(oldpqssword != null && oldpqssword.length() != 0)){
            resultMap.put("result","error");
        } else {
            String password = ((User)o).getUserPassword();
            System.out.println("oldpassword: " + oldpqssword);
            System.out.println("password " + password);
            if (password != null && password.equals(oldpqssword)){
                resultMap.put("result","true");
            }else{
                resultMap.put("result","false");
            }
        }

        resp.setContentType("application/json"); // ajax异步请求
        try {
            PrintWriter printWriter = resp.getWriter();
            // JSONArray 阿里巴巴的JSON工具类，转换格式
            // Map -- > json
            printWriter.write(JSONArray.toJSONString(resultMap));
            printWriter.flush();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
```

注意：前面写用户首页登录逻辑时并没有验证密码是否与用户名一致，可以自己加上验证密码功能，很简单。

## 14.编写用户管理模块

思路：整个项目中的难点。

![image-20211211213351264](https://gitee.com/grant1499/blog-pic/raw/master/img/202112112133397.png)

新建分页的工具类 PageSupport。

```java
public class PageSupport {
    //当前页码-来自于用户输入
    private int currentPageNo = 1;

    //总数量（表）
    private int totalCount = 0;

    //页面容量
    private int pageSize = 0;

    //总页数-totalCount/pageSize（+1）
    private int totalPageCount = 1;

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        if(currentPageNo > 0){
            this.currentPageNo = currentPageNo;
        }
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        if(totalCount > 0){
            this.totalCount = totalCount;
            //设置总页数
            this.setTotalPageCountByRs();
        }
    }
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(pageSize > 0){
            this.pageSize = pageSize;
        }
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public void setTotalPageCountByRs(){
        if(this.totalCount % this.pageSize == 0){
            this.totalPageCount = this.totalCount / this.pageSize;
        }else if(this.totalCount % this.pageSize > 0){
            this.totalPageCount = this.totalCount / this.pageSize + 1;
        }else{
            this.totalPageCount = 0;
        }
    }
}
```

### 14.1 获取用户数量，涉及多表查询

```java
// 在UserDaoImpl中添加以下方法
@Override
public int getUserCount(Connection con, String username, int userRole) {
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;
    int count = 0;

    if (con != null){
        StringBuffer sql = new StringBuffer();
        sql.append("select count(1)  as count from smbms_user U,smbms_role R  where U.userRole = R.id");
        // 前面用Object数组存放占位参数，这里用ArrayList存放
        List<Object> list = new ArrayList<>();

        if (username != null && username.length() != 0){
            sql.append(" and U.userName like ?");
            list.add("'%" + username + "%'");
        }
        if (userRole > 0 && userRole < 4){
            sql.append(" and U.userRole = ?");
            list.add(userRole);
        }

        Object[] params = list.toArray();
        System.out.println("UserDaoImpl --> getUserCount : " + sql.toString());
        try {
            rs = BaseDao.execute(con,sql.toString(),preparedStatement,rs,params);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            if (rs.next()){
                count  = rs.getInt("count");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            BaseDao.release(con,preparedStatement,rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    return count;
}
```

```java
// 在UserServiceImpl中添加以下方法
@Override
public int getUserCount(String username,int userRole) {
    int count = 0;
    Connection con = BaseDao.getConnection();

    if (con != null){
        count = userDao.getUserCount(con,username,userRole);
    }

    try {
        BaseDao.release(con,null,null);
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }
    return count;
}
```

### 14.2 获取用户列表

代码逻辑和前面差不多：

```java
// 在UserDaoImpl中添加以下方法
@Override
public List<User> getUserList(Connection con, String username, int userRole, int currentPageNo, int pageSize) throws SQLException {
    List<User> userList  = new ArrayList<>();
    PreparedStatement preparedStatement = null;
    ResultSet rs = null;

    if (con != null){
        StringBuffer sql = new StringBuffer();
        sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");

        //建一个集合来存储参数
        List<Object> list = new ArrayList<>();
        if(username!=null){
            sql.append(" and u.userName like ?");
            list.add("%"+username+"%");//默认下标为0
        }
        if(userRole>0 & userRole<4){
            sql.append(" and u.userRole = ?");
            list.add(userRole);//默认下标为1
        }

        //在数据库中 分页使用limit startIndex,pageSize 总数
        //当前页 = (当前页-1)*页面大小
        sql.append(" order by u.creationDate DESC limit ?,?");
        currentPageNo = (currentPageNo-1)*pageSize;
        list.add(currentPageNo);
        list.add(pageSize);

        Object[] params = list.toArray();
        System.out.println("getUserList的语句"+sql.toString());

        try {
            rs =  BaseDao.execute(con,sql.toString(),preparedStatement,rs,params);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        while (rs.next()){
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUserCode(rs.getString("userCode"));
            user.setUserName(rs.getString("userName"));
            user.setGender(rs.getInt("gender"));
            user.setBirthday(rs.getDate("birthday"));
            user.setPhone(rs.getString("phone"));
            user.setUserRoleName(rs.getString("userRoleName"));
            user.setUserRole(rs.getInt("userRole"));

            userList.add(user);
        }

        try {
            BaseDao.release(con,preparedStatement,rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    return userList;
}
```

```java
// 在UserServiceImpl中添加以下方法
@Override
public List<User> getUserList(String username, int userRole, int currentPageNo, int pageSize) {
    List<User> userList = new ArrayList<>();
    Connection con = null;

    con = BaseDao.getConnection();

    if (con != null){
        try {
            userList = userDao.getUserList(con,username,userRole,currentPageNo,pageSize);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                BaseDao.release(con,null,null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    return userList;
}
```

### 14.3 获取用户角色列表（其实属于角色管理模块）

为了职责统一，把角色的操作单独放在一个包中，和POJO相对应。

代码逻辑和前面差不多：

```java
public class RoleDaoImpl implements RoleDao {

    @Override
    public List<Role> getRoleList(Connection con) throws SQLException {
        List<Role> roleList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        if (con != null){
            StringBuffer sql = new StringBuffer();
            sql.append("select * from smbms_role");
            Object[] params = {};
            rs = BaseDao.execute(con, sql.toString(), preparedStatement, rs,params);
            while(rs.next()){
                Role role = new Role();
                role.setId(rs.getInt("id"));
                role.setRoleName(rs.getString("roleName"));
                role.setRoleCode(rs.getString("roleCode"));
                roleList.add(role);
            }
            BaseDao.release(con,preparedStatement,rs);
        }

        return roleList;
    }
}
```

```java
public class RoleServiceImpl implements RoleService{
    //业务层调用持久层
    private RoleDao roleDao = null;
    public RoleServiceImpl(){
        this.roleDao =new RoleDaoImpl();
    }

    @Override
    public List<Role> getRoleList() {
        List<Role> roleList = new ArrayList<>();
        Connection con = null;

        con = BaseDao.getConnection();

        if (con !=  null){
            try {
                roleList = roleDao.getRoleList(con);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                try {
                    BaseDao.release(con,null,null);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }

        return roleList;
    }
}
```

### 14.4 获取用户显示的servlet

在UserServlet中新添方法。

```java
// 重难点
public void query(HttpServletRequest req,HttpServletResponse resp) {
    //从前端获取数据 userlist.jsp
    String queryUserName = req.getParameter("queryname");
    String temp = req.getParameter("queryUserRole");//值为0 、1、2、3
    String pageIndex = req.getParameter("pageIndex");
    int queryUserRole = 0;

    UserServiceImpl userService = new UserServiceImpl();
    RoleServiceImpl roleService = new RoleServiceImpl();

    // 设置当前页和页面用户数量
    int currentPageNo = 1;
    int pageSize = 5;

    if (queryUserName == null) {
        queryUserName = "";
    }
    if (temp != null && !temp.equals("")) {
        queryUserRole = Integer.parseInt(temp);
    }
    if (pageIndex != null) {
        currentPageNo = Integer.parseInt(pageIndex);
    }
    System.out.println("queryUserName : " + queryUserName + ", queryUserRole : " + queryUserRole);
    int totalCount = userService.getUserCount(queryUserName, queryUserRole);

    PageSupport pageSupport = new PageSupport();
    pageSupport.setCurrentPageNo(currentPageNo);
    pageSupport.setPageSize(pageSize);
    pageSupport.setTotalCount(totalCount);

    int totalPageCount = pageSupport.getTotalPageCount();

    //控制首页和尾页
    //如果页数小于1，就显示第一页  页数大于 最后一页就 显示最后一页
    if (currentPageNo < 1) {
        currentPageNo = 1;
    } else if (currentPageNo > totalPageCount) {
        currentPageNo = totalPageCount;
    }
    // 获取用户列表展示，传给前端展示
    List<User> userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
    req.getSession().setAttribute("userList",userList);
    for (User u : userList){
        System.out.println(u.getUserName());
    }

    List<Role> roleList = roleService.getRoleList();
    req.getSession().setAttribute("roleList",roleList);

    req.getSession().setAttribute("totalCount",totalCount);
    req.getSession().setAttribute("currentPageNo",currentPageNo);
    req.getSession().setAttribute("totalPageCount",totalPageCount);

    req.getSession().setAttribute("queryUserName",queryUserName);
    req.getSession().setAttribute("queryUserRole",queryUserRole);

    try {
        req.getRequestDispatcher("userlist.jsp").forward(req,resp);
    } catch (ServletException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

### 14.5 用户管理模块 子模块—添加用户功能

**一切的增删改操作都需要处理事务！**

先写完`dao`层的实现类：

```java
@Override
public int addUser(Connection con, User user) {
    int count = 0;
    PreparedStatement preparedStatement = null;

    if (con != null){
        String sql = "insert into smbms_user (userCode,userName,userPassword,gender,birthday,phone,address,userRole,createdBy,creationDate)values(?,?,?,?,?,?,?,?,?,?)";
        Object[] params ={user.getUserRole(),user.getUserName(),user.getUserPassword(),user.getGender(),user.getBirthday(),user.getPhone(),user.getAddress(),user.getUserRole(),user.getCreatedBy(),user.getCreationDate()};

        try {
            count = BaseDao.execute(con,sql,preparedStatement,params);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //try {
                //    BaseDao.release(con,preparedStatement,null);
                //} catch (SQLException throwables) {
                //    throwables.printStackTrace();
                //}
        }
    }

    return count;
}
```

再来写`service`层的实现类：注意开启事务！

```java
@Override
public boolean addUser(User user) {
    Connection conn = null;
    boolean flag = false;
    try {
        //获取数据库连接
        conn = BaseDao.getConnection();
        //开启JDBC事务管理
        conn.setAutoCommit(false);
        //Service层调用dao层的方法添加用户
        int updateRows = userDao.addUser(conn, user);
        conn.commit();
        if(updateRows > 0){
            flag = true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        conn.rollback();
    }finally {
        //释放连接
        //try {
        //    BaseDao.release(conn, null, null);
        //} catch (SQLException throwables) {
        //    throwables.printStackTrace();
        //}
        return flag;
    }
}
```

在可重用Servlet类中添加判断用户编码是否存在的方法。

```java
// 用户管理模块 子模块(验证用户编码是否已经存在)
public void ifExist(HttpServletRequest req, HttpServletResponse resp){
    //获取前端输入 的用户编码
    String userCode = req.getParameter("userCode");
    UserServiceImpl userService = new UserServiceImpl();
    Boolean flag = userService.getUser(userCode);

    //将结果存放在map集合中 让Ajax使用
    Map<String, String> resultMap = new HashMap<>();
    if (flag){
        resultMap.put("userCode","exist");
    }
    //上面已经封装好 现在需要传给Ajax 格式为json 所以我们得转换格式
    resp.setContentType("application/json");//将应用的类型变成json
    PrintWriter writer = null;
    try {
        writer = resp.getWriter();
    } catch (IOException e) {
        e.printStackTrace();
    }
    //JSONArray 阿里巴巴的JSON工具类 用途就是：转换格式
    writer.write(JSONArray.toJSONString(resultMap));
    writer.flush();
    writer.close();
}
```

添加用户BUG修复：

添加用户提交后，出现如下错误：

No operations allowed after connection closed。

解决方案：

把释放数据库连接的相关代码都注释掉。

**经验总结：之后牵涉到事务的代码都要这样处理！**

### 14.6 用户管理模块 子模块—删除用户功能

思路和前面类似，从底层往上写。

`Dao`层实现类：

```java
@Override
public int deleUser(Connection con, int userId) {
    int count = 0;
    PreparedStatement preparedStatement = null;

    if (con != null){
        String sql = "delete from smbms_user where id = ?";
        Object[] params = {userId};

        try {
            count = BaseDao.execute(con,sql,preparedStatement,params);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    return count;
}
```

`UserService`层实现类：

```java
@Override
public boolean deleUser(int userId) {
    boolean flag = false;
    Connection con  = BaseDao.getConnection();

    try {
        //开启JDBC事务管理
        con.setAutoCommit(false);
        //Service层调用dao层的方法添加用户
        int updateRows = userDao.deleUser(con, userId);
        con.commit();
        if(updateRows > 0){
            flag = true;
        }
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }

    return flag;
}
```

`Servlet`层：

```java
private void deleUser(HttpServletRequest req, HttpServletResponse resp) {
    //从前端获取 要删除的用户 的信息
    String userid = req.getParameter("uid");
    int delId = 0;
    //先转换
    try {
        delId = Integer.parseInt(userid);
    }catch (Exception e){
        e.printStackTrace();
        delId = 0;
    }
    //将结果存放在map集合中 让Ajax使用
    Map<String, String> resultMap = new HashMap<>();
    if(delId<=0){
        resultMap.put("delResult","notexist");
    }else {
        UserServiceImpl userService = new UserServiceImpl();
        if(userService.deleUser(delId)){
            resultMap.put("delResult","true");
        }else {
            resultMap.put("delResult", "false");
        }
    }
    //上面已经封装好 现在需要传给Ajax 格式为json 所以我们得转换格式
    resp.setContentType("application/json");//将应用的类型变成json
    PrintWriter writer = null;
    try {
        writer = resp.getWriter();
    } catch (IOException e) {
        e.printStackTrace();
    }
    //JSONArray 阿里巴巴的JSON工具类 用途就是：转换格式
    writer.write(JSONArray.toJSONString(resultMap));
    writer.flush();
    writer.close();
}
```

### 14.7 用户管理模块 子模块—修改用户信息功能

思路和前面类似，从底层往上写。

`Dao`层实现类：

这里增加一个getUserById方法来查询 旧数据 ，并显示在修改的页面中。

```java
@Override
public int modifyUser(Connection conn, User user) throws SQLException {
    int count = 0;
    PreparedStatement preparedStatement = null;

    if (conn != null){
        String sql = "update smbms_user set userName=?,"+
            "gender=?,birthday=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=? where id = ? ";
        Object[] params = {user.getUserName(),user.getGender(),user.getBirthday(),
                           user.getPhone(),user.getAddress(),user.getUserRole(),user.getModifyBy(),
                           user.getModifyDate(),user.getId()};

        try {
            count = BaseDao.execute(conn,sql,preparedStatement,params);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    return count;
}

@Override
public User getUserById(Connection con, String id) throws Exception {
    User user = null;
    PreparedStatement pstm = null;
    ResultSet rs = null;
    if(null != con){
        String sql = "select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id=? and u.userRole = r.id";
        Object[] params = {id};
        rs = BaseDao.execute(con, sql, pstm, rs, params);
        if(rs.next()){
            user = new User();
            user.setId(rs.getInt("id"));
            user.setUserCode(rs.getString("userCode"));
            user.setUserName(rs.getString("userName"));
            user.setUserPassword(rs.getString("userPassword"));
            user.setGender(rs.getInt("gender"));
            user.setBirthday(rs.getDate("birthday"));
            user.setPhone(rs.getString("phone"));
            user.setAddress(rs.getString("address"));
            user.setUserRole(rs.getInt("userRole"));
            user.setCreatedBy(rs.getInt("createdBy"));
            user.setCreationDate(rs.getTimestamp("creationDate"));
            user.setModifyBy(rs.getInt("modifyBy"));
            user.setModifyDate(rs.getTimestamp("modifyDate"));
            user.setUserRoleName(rs.getString("userRoleName"));
        }
        BaseDao.release(null, pstm, rs);
    }
    return user;
}
```

`UserService`层实现类：

```java
@Override
public boolean modifyUser(User user) {
    Connection connection = null;
    boolean flag = false;
    try {
        connection = BaseDao.getConnection();
        connection.setAutoCommit(false);
        if(userDao.modifyUser(connection,user) > 0)
            flag = true;
        connection.commit();
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return flag;
}


@Override
public User getUserById(String id) throws SQLException {
    User user = null;
    Connection connection = null;
    try{
        connection = BaseDao.getConnection();
        user = userDao.getUserById(connection,id);
    }catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
        user = null;
    }finally{
        BaseDao.release(connection, null, null);
    }
    return user;
}
```

`Servlet`层方法：

```java
private void findById(HttpServletRequest req, HttpServletResponse resp,String url) throws ServletException, IOException {
    //从前端获取 要修改的用户 的id
    String uid = req.getParameter("uid");

    if (uid != null && uid.length() != 0){
        UserServiceImpl userService = new UserServiceImpl();
        //查询要更改的用户信息
        User user = null;
        try {
            user = userService.getUserById(uid);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //将用户信息保存至 request中 让usermodify.jsp显示
        req.setAttribute("user",user);
        req.getRequestDispatcher(url).forward(req,resp);
    }
}

private void modifyUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    String id = request.getParameter("uid");
    String userName = request.getParameter("userName");
    String gender = request.getParameter("gender");
    String birthday = request.getParameter("birthday");
    String phone = request.getParameter("phone");
    String address = request.getParameter("address");
    String userRole = request.getParameter("userRole");

    User user = new User();
    user.setId(Integer.valueOf(id));
    user.setUserName(userName);
    user.setGender(Integer.valueOf(gender));
    try {
        user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
    } catch (ParseException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    user.setPhone(phone);
    user.setAddress(address);
    user.setUserRole(Integer.valueOf(userRole));
    user.setModifyBy(((User)request.getSession().getAttribute(Constant.USER_SESSION)).getId());
    user.setModifyDate(new Date());

    UserService userService = new UserServiceImpl();
    if(userService.modifyUser(user)){
        response.sendRedirect(request.getContextPath()+"/jsp/user.do?method=query");
    }else{
        request.getRequestDispatcher("usermodify.jsp").forward(request, response);
    }
}
```

### 14.8 用户管理模块 子模块—查看用户信息功能

思路和前面类似，从底层往上写。

这里直接利用修改用户用到的getUserById方法，之前修改用户用到两个方法，先查看原有用户信息，然后再修改用户信息。

这里就不用再写了。

只要用一个参数将修改用户信息和查看用户信息的操作分别重定向到对应的页面就行。

---

至此，用户的所有操作都已经实现了。

后面的订单管理和供应商管理等模块功能类似，这里就不再重复了，直接看源码。

## 15.项目上传至GitHub

登录GitHub新建一个仓库，不要勾选新建`Readme.md`文件。

找到项目所在文件夹，右键GIt打开。

然后根据提示在本地Git输入相应命令即可。

```bash
git init
git add .
git commit -m "first commit"
git branch -M master
git remote add origin git@github.com:grant1499/smbms_java_web.git
git push -u origin master
# 这些命令具体什么意思可以参考一下我的Linux系列笔记
```

就是一些简单的Git命令。

