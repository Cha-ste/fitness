/*
SQLyog Enterprise v12.08 (64 bit)
MySQL - 8.0.17 : Database - fitness
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`fitness` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `fitness`;

/*Table structure for table `apply` */

DROP TABLE IF EXISTS `apply`;

CREATE TABLE `apply` (
  `cid` int(11) DEFAULT NULL COMMENT '课程id',
  `sid` int(11) DEFAULT NULL COMMENT '会员id',
  `ctime` datetime DEFAULT NULL COMMENT '上课时间',
  `setTime` varchar(1) DEFAULT NULL COMMENT '设置上课时间标志',
  `appointment` int(11) DEFAULT NULL COMMENT '预约课程标志',
  `appointok` varchar(1) DEFAULT NULL COMMENT '确认预约标志',
  `clockin` varchar(1) DEFAULT NULL COMMENT '打卡标志',
  `prohibit` varchar(1) DEFAULT NULL COMMENT '禁用标志',
  `punch` int(11) DEFAULT NULL COMMENT '剩余打卡次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='报名表 ';

/*Data for the table `apply` */

insert  into `apply`(`cid`,`sid`,`ctime`,`setTime`,`appointment`,`appointok`,`clockin`,`prohibit`,`punch`) values (1,1,'2020-02-19 17:44:02','1',1,'1','0','0',12);

/*Table structure for table `coach` */

DROP TABLE IF EXISTS `coach`;

CREATE TABLE `coach` (
  `tid` int(11) NOT NULL AUTO_INCREMENT COMMENT '教练ID',
  `coach_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `phone` varchar(32) DEFAULT NULL COMMENT '点好',
  `birthday` date DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='教练表 ';

/*Data for the table `coach` */

insert  into `coach`(`tid`,`coach_name`,`password`,`sex`,`phone`,`birthday`) values (1,'晓梦','3edbf7ec92d368c5a9346578fbcdad07',0,'13566644455','1990-02-19'),(2,'雷同','3edbf7ec92d368c5a9346578fbcdad07',0,'1665544444','1994-07-21');

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `cid` int(11) DEFAULT NULL COMMENT '课程id',
  `sid` int(11) DEFAULT NULL COMMENT '会员id',
  `context` varchar(128) DEFAULT NULL COMMENT '评价内容'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='课程评价表 ';

/*Data for the table `comment` */

insert  into `comment`(`cid`,`sid`,`context`) values (1,1,'学完这个课程之后，感觉自己要起飞了');

/*Table structure for table `course` */

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `cid` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `tid` int(11) DEFAULT NULL COMMENT '教练ID',
  `cname` varchar(32) DEFAULT NULL COMMENT '课程名称',
  `cost` decimal(32,8) DEFAULT NULL COMMENT '报名费用',
  `location` varchar(64) DEFAULT NULL COMMENT '上课地址',
  `description` varchar(128) DEFAULT NULL COMMENT '课程介绍',
  `count` int(11) DEFAULT NULL COMMENT '已选人数',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='课程表 ';

/*Data for the table `course` */

insert  into `course`(`cid`,`tid`,`cname`,`cost`,`location`,`description`,`count`) values (1,1,'背部健美','800.00000000','xx广场一楼108室','啦啦',36),(2,2,'麒麟臂','600.00000000','xx广场一楼109','让男人靠得住',48);

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `nid` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息id',
  `title` varchar(32) DEFAULT NULL COMMENT '标题',
  `context` varchar(1024) DEFAULT NULL COMMENT '内容',
  `ptime` datetime DEFAULT NULL COMMENT '时间发布',
  PRIMARY KEY (`nid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息通告表 ';

/*Data for the table `message` */

insert  into `message`(`nid`,`title`,`context`,`ptime`) values (1,'重磅消息！！！','张家辉莅临现场做现场示范','2020-02-19 21:47:53');

/*Table structure for table `pdman_db_version` */

DROP TABLE IF EXISTS `pdman_db_version`;

CREATE TABLE `pdman_db_version` (
  `DB_VERSION` varchar(256) DEFAULT NULL,
  `VERSION_DESC` varchar(1024) DEFAULT NULL,
  `CREATED_TIME` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `pdman_db_version` */

insert  into `pdman_db_version`(`DB_VERSION`,`VERSION_DESC`,`CREATED_TIME`) values ('v0.0.0','默认版本，新增的版本不能低于此版本','2020-02-19 14:52:21');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '会员ID 唯一主键',
  `username` varchar(32) DEFAULT NULL COMMENT '姓名',
  `password` varchar(64) DEFAULT NULL COMMENT '密码 md5加密',
  `sex` int(11) DEFAULT NULL COMMENT '性别',
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `money` decimal(32,8) DEFAULT NULL COMMENT '余额',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会员表 会员表';

/*Data for the table `users` */

insert  into `users`(`sid`,`username`,`password`,`sex`,`phone`,`birthday`,`money`) values (1,'ocean','3edbf7ec92d368c5a9346578fbcdad07',0,'',NULL,'0.00000000'),(4,'member001','3edbf7ec92d368c5a9346578fbcdad07',0,'',NULL,'0.00000000'),(5,'member002','3edbf7ec92d368c5a9346578fbcdad07',0,'13588888888',NULL,'13000.00000000');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
