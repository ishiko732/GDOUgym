-- MySQL dump 10.13  Distrib 8.0.27, for macos11 (x86_64)
--
-- Host: *    Database: GSM
-- ------------------------------------------------------
-- Server version	8.0.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table Announcement
--

DROP TABLE IF EXISTS Announcement;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Announcement (
  aid int NOT NULL AUTO_INCREMENT COMMENT '公告id',
  uid int DEFAULT NULL COMMENT '操作者',
  type varchar(16) DEFAULT NULL COMMENT '公告类型',
  content text NOT NULL COMMENT '公告内容',
  createDate timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  updateDate timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (aid),
  KEY AnnouncementUid_fk (uid),
  CONSTRAINT AnnouncementUid_fk FOREIGN KEY (uid) REFERENCES User (id)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view Announcement_type
--

DROP TABLE IF EXISTS Announcement_type;
/*!50001 DROP VIEW IF EXISTS Announcement_type*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW Announcement_type AS SELECT 
 1 AS type*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table Competition
--

DROP TABLE IF EXISTS Competition;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Competition (
  id int NOT NULL AUTO_INCREMENT COMMENT '赛事id',
  uid int NOT NULL COMMENT '申请人Id',
  name varchar(64) NOT NULL COMMENT '赛事名称',
  competition_time timestamp NOT NULL COMMENT '赛事时间',
  event_length int DEFAULT NULL COMMENT '赛事时长，按分钟计算',
  introduction text COMMENT '赛事简介',
  money decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '赛事注册费用',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '赛事创建时间',
  PRIMARY KEY (id),
  KEY Competition_User_id_fk (uid),
  CONSTRAINT Competition_User_id_fk FOREIGN KEY (uid) REFERENCES User (id)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3 COMMENT='赛事表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Competition_cancel
--

DROP TABLE IF EXISTS Competition_cancel;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Competition_cancel (
  id int NOT NULL AUTO_INCREMENT,
  cid int NOT NULL COMMENT '赛事id',
  uid int NOT NULL COMMENT '取消的用户',
  reasons varchar(256) DEFAULT '无' COMMENT '取消的理由',
  cancellation_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '取消的时间',
  PRIMARY KEY (id),
  KEY Cancellation_events_user_FK (uid),
  KEY Competition_cancel_Competition_id_fk (cid),
  CONSTRAINT Cancellation_events_user_FK FOREIGN KEY (uid) REFERENCES User (id),
  CONSTRAINT Competition_cancel_Competition_id_fk FOREIGN KEY (cid) REFERENCES Competition (id) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='赛事取消';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Competition_check
--

DROP TABLE IF EXISTS Competition_check;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Competition_check (
  id int NOT NULL AUTO_INCREMENT,
  cid int NOT NULL COMMENT '赛事id',
  uid int DEFAULT NULL COMMENT '审核人',
  reason varchar(256) DEFAULT '' COMMENT '审核理由',
  status char(16) NOT NULL DEFAULT '待审核' COMMENT '审核状态',
  create_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建审核时间',
  check_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '审核时间',
  PRIMARY KEY (id),
  KEY Competition_check_User_id_fk (uid),
  KEY Competition_check_Competition_id_fk (cid),
  CONSTRAINT Competition_check_Competition_id_fk FOREIGN KEY (cid) REFERENCES Competition (id) ON UPDATE CASCADE,
  CONSTRAINT Competition_check_User_id_fk FOREIGN KEY (uid) REFERENCES User (id),
  CONSTRAINT check_status CHECK ((status in (_utf8mb4'待审核',_utf8mb4'审核通过',_utf8mb4'审核未通过',_utf8mb4'审核取消',_utf8mb4'重新审核')))
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='赛事审核表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Competition_equipment
--

DROP TABLE IF EXISTS Competition_equipment;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Competition_equipment (
  id int NOT NULL AUTO_INCREMENT,
  cfid int NOT NULL COMMENT '赛事场地id',
  eid int NOT NULL COMMENT '器材id',
  number int NOT NULL DEFAULT '0' COMMENT '器材数量',
  PRIMARY KEY (id),
  KEY Competition_equipment_Equipment_id_fk (eid),
  KEY Competition_equipment_Competition_field_id_fk (cfid),
  CONSTRAINT Competition_equipment_Competition_field_id_fk FOREIGN KEY (cfid) REFERENCES Competition_field (id) ON UPDATE CASCADE,
  CONSTRAINT Competition_equipment_Equipment_id_fk FOREIGN KEY (eid) REFERENCES Equipment (id) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='赛事器材表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Competition_field
--

DROP TABLE IF EXISTS Competition_field;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Competition_field (
  id int NOT NULL AUTO_INCREMENT COMMENT '赛事场地Id',
  cid int NOT NULL COMMENT '赛事id',
  fcId int NOT NULL COMMENT '场地审核id',
  uid int DEFAULT NULL COMMENT '裁判id',
  introduction varchar(512) DEFAULT NULL COMMENT '裁判简介',
  time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY Com_set_UserInfo_uid_fk (uid),
  KEY Competition_field_ibfk_1 (fcId),
  KEY Competition_field_Competition_id_fk (cid),
  CONSTRAINT Com_set_UserInfo_uid_fk FOREIGN KEY (uid) REFERENCES User (id),
  CONSTRAINT Competition_field_Competition_id_fk FOREIGN KEY (cid) REFERENCES Competition (id),
  CONSTRAINT Competition_field_ibfk_1 FOREIGN KEY (fcId) REFERENCES Field_check (id)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='赛事场地表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Equipment
--

DROP TABLE IF EXISTS Equipment;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Equipment (
  id int NOT NULL AUTO_INCREMENT COMMENT '器材id',
  types char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '器材种类',
  name char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '器材名称',
  number int NOT NULL COMMENT '器材数量',
  price decimal(10,2) DEFAULT '0.00' COMMENT '器材价格',
  rentPrice decimal(10,2) DEFAULT '0.00' COMMENT '器材租用价格',
  maxRentTime int DEFAULT '5' COMMENT '器材最大租用时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COMMENT='Equipment修改maxRentTime属性';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Equipment_rent_standard
--

DROP TABLE IF EXISTS Equipment_rent_standard;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Equipment_rent_standard (
  erid int NOT NULL AUTO_INCREMENT COMMENT '器材租用标准ID',
  eid int NOT NULL COMMENT '器材ID',
  name varchar(255) DEFAULT NULL COMMENT '器材名称',
  price decimal(10,2) DEFAULT '0.00' COMMENT '器材租用费用元/h',
  maxRentTime int DEFAULT NULL COMMENT '最大租用时间',
  PRIMARY KEY (erid,eid),
  KEY eid (eid),
  CONSTRAINT Equipment_rent_standard_ibfk_1 FOREIGN KEY (eid) REFERENCES Equipment (id)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb3 COMMENT='修改price的属性decimal（10,0）为（10,2）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Field
--

DROP TABLE IF EXISTS Field;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Field (
  fid int NOT NULL AUTO_INCREMENT COMMENT '场地id',
  tid int NOT NULL COMMENT '场地类型',
  money decimal(10,2) NOT NULL COMMENT '收费标准',
  description varchar(50) DEFAULT NULL COMMENT '场地描述',
  num int DEFAULT NULL COMMENT '场地数量',
  is_del int DEFAULT '0' COMMENT '场地删除标识符',
  PRIMARY KEY (fid,tid),
  KEY tid (tid),
  CONSTRAINT Field_ibfk_1 FOREIGN KEY (tid) REFERENCES Field_type (tid)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COMMENT='场地表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Field_check
--

DROP TABLE IF EXISTS Field_check;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Field_check (
  id int NOT NULL AUTO_INCREMENT COMMENT '审核表id',
  time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  money decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '费用',
  status char(8) DEFAULT NULL COMMENT '审核状态',
  uid int DEFAULT NULL COMMENT '用户id',
  name varchar(255) DEFAULT NULL COMMENT '审核标题',
  card char(18) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '一卡通号码',
  PRIMARY KEY (id),
  KEY uid (uid),
  KEY Field_check_UserInfo_id_fk (card),
  CONSTRAINT Field_check_ibfk_1 FOREIGN KEY (uid) REFERENCES User (id)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3 COMMENT='预约审核表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Field_date
--

DROP TABLE IF EXISTS Field_date;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Field_date (
  id int NOT NULL AUTO_INCREMENT COMMENT '日期表id',
  inode int DEFAULT NULL COMMENT '索引，方便操作',
  date date DEFAULT NULL COMMENT '日期',
  fid int DEFAULT NULL COMMENT '场地id',
  PRIMARY KEY (id),
  KEY fid (fid),
  CONSTRAINT Field_date_ibfk_1 FOREIGN KEY (fid) REFERENCES Field (fid)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8mb3 COMMENT='日期表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Field_type
--

DROP TABLE IF EXISTS Field_type;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Field_type (
  tid int NOT NULL AUTO_INCREMENT COMMENT '场地类型id',
  type_name varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '场地类型名称',
  is_del int DEFAULT '0' COMMENT '场地类型删除标识符',
  PRIMARY KEY (tid)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='场地类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Fix_equipment
--

DROP TABLE IF EXISTS Fix_equipment;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Fix_equipment (
  fid int NOT NULL COMMENT '器材维修id',
  name varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '器材名称',
  number int DEFAULT NULL COMMENT '器材数量',
  type varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '器材种类',
  PRIMARY KEY (fid) USING BTREE,
  CONSTRAINT Fix_equipment_ibfk_1 FOREIGN KEY (fid) REFERENCES Equipment (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='为Fix_equipment表属性添加注释';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Fix_equipment_bill
--

DROP TABLE IF EXISTS Fix_equipment_bill;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Fix_equipment_bill (
  id int NOT NULL AUTO_INCREMENT COMMENT '器材维修账单id',
  eid int DEFAULT NULL COMMENT '器材id',
  number int DEFAULT NULL COMMENT '已维修的器材数量',
  price decimal(10,2) DEFAULT '0.00' COMMENT '单个器材的维修价格（器材价格/2）',
  fixDate date DEFAULT NULL COMMENT '器材维修日期',
  PRIMARY KEY (id),
  KEY eid (eid),
  CONSTRAINT Fix_equipment_bill_ibfk_1 FOREIGN KEY (eid) REFERENCES Equipment (id)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='修改price的默认值为0.00';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Order_item
--

DROP TABLE IF EXISTS Order_item;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Order_item (
  oid int NOT NULL AUTO_INCREMENT COMMENT '审核，具体安排关联表id',
  fcid int NOT NULL COMMENT '审核表id',
  time_id int NOT NULL COMMENT '时间安排表id',
  PRIMARY KEY (oid),
  KEY fcid (fcid),
  KEY time_id (time_id),
  CONSTRAINT Order_item_ibfk_1 FOREIGN KEY (fcid) REFERENCES Field_check (id),
  CONSTRAINT Order_item_ibfk_2 FOREIGN KEY (time_id) REFERENCES Time_arrange (time_id)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3 COMMENT='场地订单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Recycle_equipment
--

DROP TABLE IF EXISTS Recycle_equipment;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Recycle_equipment (
  reid int NOT NULL COMMENT '回收器材id',
  name varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '器材名称',
  number int DEFAULT NULL COMMENT '器材数量',
  type varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '器材种类',
  PRIMARY KEY (reid) USING BTREE,
  CONSTRAINT Recycle_equipment_Competition_id_fk FOREIGN KEY (reid) REFERENCES Equipment (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='修改Recycle_equipment表的外键';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Rent_equipment
--

DROP TABLE IF EXISTS Rent_equipment;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Rent_equipment (
  rid int NOT NULL AUTO_INCREMENT,
  eid int DEFAULT NULL,
  eName varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  uid int DEFAULT NULL,
  username varchar(255) DEFAULT NULL,
  rentTime int DEFAULT NULL,
  number int DEFAULT NULL,
  rentDate date DEFAULT NULL,
  status int DEFAULT '0' COMMENT '器材租用状态，0代表未归还，1代表归还',
  PRIMARY KEY (rid),
  KEY eid (eid),
  KEY uid (uid),
  CONSTRAINT Rent_equipment_ibfk_1 FOREIGN KEY (eid) REFERENCES Equipment (id),
  CONSTRAINT Rent_equipment_ibfk_2 FOREIGN KEY (uid) REFERENCES User (id)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='增加status属性，判断该器材是否归还';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table Time_arrange
--

DROP TABLE IF EXISTS Time_arrange;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE Time_arrange (
  time_id int NOT NULL AUTO_INCREMENT COMMENT '时间表id',
  start_time time NOT NULL COMMENT '开始时间',
  end_time time NOT NULL COMMENT '结束时间',
  inode int DEFAULT NULL COMMENT '索引，方便操作',
  status char(8) DEFAULT NULL COMMENT '状态',
  fdid int DEFAULT NULL COMMENT '日期表id',
  PRIMARY KEY (time_id),
  KEY fdid (fdid),
  CONSTRAINT Time_arrange_ibfk_1 FOREIGN KEY (fdid) REFERENCES Field_date (id)
) ENGINE=InnoDB AUTO_INCREMENT=2317 DEFAULT CHARSET=utf8mb3 COMMENT='时间安排表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table User
--

DROP TABLE IF EXISTS User;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE User (
  id int NOT NULL AUTO_INCREMENT,
  name varchar(16) NOT NULL COMMENT '用户名',
  password varchar(100) NOT NULL COMMENT '密码',
  role_id int NOT NULL COMMENT '用户类型',
  PRIMARY KEY (id),
  KEY UserRole_fk (role_id),
  KEY name__index (name),
  CONSTRAINT UserRole_fk FOREIGN KEY (role_id) REFERENCES role (id)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table UserInfo
--

DROP TABLE IF EXISTS UserInfo;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE UserInfo (
  id char(16) NOT NULL COMMENT '学工号',
  uid int DEFAULT NULL COMMENT '用户id',
  uname varchar(16) DEFAULT NULL COMMENT '用户名',
  truename varchar(64) DEFAULT NULL,
  sex char(2) DEFAULT NULL,
  phone char(11) DEFAULT NULL COMMENT '手机号',
  idCard char(18) DEFAULT NULL,
  birthday timestamp NULL DEFAULT NULL,
  class varchar(64) DEFAULT NULL,
  PRIMARY KEY (id),
  KEY UserInfo_name_fk (uname),
  KEY userInfo_FK (uid),
  CONSTRAINT userInfo_FK FOREIGN KEY (uid) REFERENCES User (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT UserInfo_name_fk FOREIGN KEY (uname) REFERENCES User (name) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT UserInfo_chk_1 CHECK ((sex in (_utf8mb3'男',_utf8mb3'女')))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户联系方式';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view competition_field_time
--

DROP TABLE IF EXISTS competition_field_time;
/*!50001 DROP VIEW IF EXISTS competition_field_time*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW competition_field_time AS SELECT 
 1 AS id,
 1 AS cid,
 1 AS fcId,
 1 AS uid,
 1 AS introduction,
 1 AS time,
 1 AS fid,
 1 AS startTime,
 1 AS endTime*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view competition_time
--

DROP TABLE IF EXISTS competition_time;
/*!50001 DROP VIEW IF EXISTS competition_time*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW competition_time AS SELECT 
 1 AS id,
 1 AS uid,
 1 AS name,
 1 AS competition_time,
 1 AS event_length,
 1 AS introduction,
 1 AS money,
 1 AS create_time,
 1 AS competition_end_time*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view new_announcement
--

DROP TABLE IF EXISTS new_announcement;
/*!50001 DROP VIEW IF EXISTS new_announcement*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW new_announcement AS SELECT 
 1 AS aid,
 1 AS uid,
 1 AS type,
 1 AS content,
 1 AS createDate,
 1 AS updateDate*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table permission
--

DROP TABLE IF EXISTS permission;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE permission (
  id int NOT NULL,
  pname varchar(15) DEFAULT NULL COMMENT '权限名称',
  pid int DEFAULT NULL COMMENT '权限值',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view referee_announcements
--

DROP TABLE IF EXISTS referee_announcements;
/*!50001 DROP VIEW IF EXISTS referee_announcements*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW referee_announcements AS SELECT 
 1 AS cid,
 1 AS cfId,
 1 AS fcId,
 1 AS uid,
 1 AS judgment,
 1 AS competition_name,
 1 AS field_name,
 1 AS introduction,
 1 AS starttime,
 1 AS endtime*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table role
--

DROP TABLE IF EXISTS role;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE role (
  id int NOT NULL AUTO_INCREMENT COMMENT '角色id',
  role varchar(20) NOT NULL,
  info varchar(10) DEFAULT NULL,
  permissions int DEFAULT '1' COMMENT '权限二进制',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view role_permissions
--

DROP TABLE IF EXISTS role_permissions;
/*!50001 DROP VIEW IF EXISTS role_permissions*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW role_permissions AS SELECT 
 1 AS id,
 1 AS role,
 1 AS permissions,
 1 AS cnt*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view user_role_permissions
--

DROP TABLE IF EXISTS user_role_permissions;
/*!50001 DROP VIEW IF EXISTS user_role_permissions*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW user_role_permissions AS SELECT 
 1 AS uid,
 1 AS name,
 1 AS rid,
 1 AS permissions,
 1 AS cnt*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view Announcement_type
--

/*!50001 DROP VIEW IF EXISTS Announcement_type*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=GSM@% SQL SECURITY DEFINER */
/*!50001 VIEW Announcement_type AS select distinct Announcement.type AS type from Announcement */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view competition_field_time
--

/*!50001 DROP VIEW IF EXISTS competition_field_time*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=GSM@% SQL SECURITY DEFINER */
/*!50001 VIEW competition_field_time AS select Competition_field.id AS id,Competition_field.cid AS cid,Competition_field.fcId AS fcId,Competition_field.uid AS uid,Competition_field.introduction AS introduction,Competition_field.time AS time,Fd.fid AS fid,timestamp(Fd.date,Ta.start_time) AS startTime,timestamp(Fd.date,Ta.end_time) AS endTime from (((Competition_field left join Order_item Oi on((Competition_field.fcId = Oi.fcid))) left join Time_arrange Ta on((Oi.time_id = Ta.time_id))) left join Field_date Fd on((Ta.fdid = Fd.id))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view competition_time
--

/*!50001 DROP VIEW IF EXISTS competition_time*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=GSM@% SQL SECURITY DEFINER */
/*!50001 VIEW competition_time AS select Competition.id AS id,Competition.uid AS uid,Competition.name AS name,Competition.competition_time AS competition_time,Competition.event_length AS event_length,Competition.introduction AS introduction,Competition.money AS money,Competition.create_time AS create_time,(Competition.competition_time + interval 60 minute) AS competition_end_time from Competition */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view new_announcement
--

/*!50001 DROP VIEW IF EXISTS new_announcement*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=GSM@% SQL SECURITY DEFINER */
/*!50001 VIEW new_announcement AS select Announcement.aid AS aid,Announcement.uid AS uid,Announcement.type AS type,Announcement.content AS content,Announcement.createDate AS createDate,Announcement.updateDate AS updateDate from ((select max(Announcement.aid) AS base_id,max(Announcement.createDate) AS createtime from Announcement group by Announcement.type) max_tmp left join Announcement on(((max_tmp.base_id = Announcement.aid) and (max_tmp.createtime = Announcement.createDate)))) order by Announcement.createDate,Announcement.updateDate,Announcement.aid */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view referee_announcements
--

/*!50001 DROP VIEW IF EXISTS referee_announcements*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=GSM@% SQL SECURITY DEFINER */
/*!50001 VIEW referee_announcements AS select Competition_field.cid AS cid,Competition_field.id AS cfId,Competition_field.fcId AS fcId,Competition_field.uid AS uid,UI.truename AS judgment,Competition.name AS competition_name,Fc.name AS field_name,Competition_field.introduction AS introduction,Competition.competition_time AS starttime,(Competition.competition_time + interval 60 minute) AS endtime from (((Competition_field left join Competition on((Competition_field.cid = Competition.id))) left join UserInfo UI on((Competition_field.uid = UI.uid))) left join Field_check Fc on((Competition_field.fcId = Fc.id))) where ((Competition_field.uid is not null) and ((Competition.competition_time + interval 60 minute) >= now())) order by Competition_field.cid,Competition_field.fcId */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view role_permissions
--

/*!50001 DROP VIEW IF EXISTS role_permissions*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=GSM@% SQL SECURITY DEFINER */
/*!50001 VIEW role_permissions AS select r.id AS id,r.role AS role,group_concat(permission.pname separator ',') AS permissions,count(permission.pname) AS cnt from (permission join role r on(((permission.pid & r.permissions) = permission.pid))) group by r.id */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view user_role_permissions
--

/*!50001 DROP VIEW IF EXISTS user_role_permissions*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=GSM@% SQL SECURITY DEFINER */
/*!50001 VIEW user_role_permissions AS select User.id AS uid,User.name AS name,rp.id AS rid,rp.permissions AS permissions,rp.cnt AS cnt from (User join (select r.id AS id,r.role AS role,group_concat(permission.pname separator ',') AS permissions,count(permission.pname) AS cnt from (permission join role r on(((permission.pid & r.permissions) = permission.pid))) group by r.id) rp on((User.role_id = rp.id))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-12 17:03:43
