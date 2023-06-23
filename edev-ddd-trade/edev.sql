/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50635
Source Host           : kubernetes:32306
Source Database       : edev

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2023-06-23 21:15:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_account
-- ----------------------------
DROP TABLE IF EXISTS `t_account`;
CREATE TABLE `t_account` (
  `id` int(20) NOT NULL,
  `customer_id` int(20) NOT NULL,
  `balance` decimal(20,2) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_account
-- ----------------------------
INSERT INTO `t_account` VALUES ('1000101', '10001', '100000.00', '2022-03-31 02:51:49', null);
INSERT INTO `t_account` VALUES ('1000201', '10002', '100000.00', '2022-03-31 02:51:49', null);
INSERT INTO `t_account` VALUES ('1000301', '10003', '100000.00', '2022-03-31 02:51:49', null);
INSERT INTO `t_account` VALUES ('1000401', '10004', '100000.00', '2022-03-31 02:51:49', null);
INSERT INTO `t_account` VALUES ('1000501', '10005', '100000.00', '2022-03-31 02:51:49', null);
INSERT INTO `t_account` VALUES ('1000601', '10006', '100000.00', '2022-03-31 02:51:49', null);
INSERT INTO `t_account` VALUES ('1000701', '10007', '100000.00', '2022-03-31 02:51:49', null);
INSERT INTO `t_account` VALUES ('1000801', '10008', '100000.00', '2022-03-31 02:51:49', null);
INSERT INTO `t_account` VALUES ('1000901', '10009', '846184.00', '2022-03-31 02:51:49', '2022-12-09 16:41:27');
INSERT INTO `t_account` VALUES ('1001201', '10012', '100000.00', '2022-03-31 02:51:49', null);
INSERT INTO `t_account` VALUES ('1001301', '10013', '100000.00', '2022-03-31 02:51:49', null);
INSERT INTO `t_account` VALUES ('1001401', '10014', '100000.00', '2022-03-31 02:51:49', null);
INSERT INTO `t_account` VALUES ('1001501', '10015', '100000.00', '2022-03-31 02:51:49', null);

-- ----------------------------
-- Table structure for t_address
-- ----------------------------
DROP TABLE IF EXISTS `t_address`;
CREATE TABLE `t_address` (
  `id` int(20) NOT NULL,
  `customer_id` int(20) DEFAULT NULL,
  `country` varchar(40) DEFAULT NULL,
  `province` varchar(40) DEFAULT NULL,
  `city` varchar(40) DEFAULT NULL,
  `zone` varchar(40) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_address
-- ----------------------------
INSERT INTO `t_address` VALUES ('1000100', '10001', '中国', '湖北', '武汉', '洪山区', '珞瑜路726号', '13300224466');
INSERT INTO `t_address` VALUES ('1000101', '10001', '中国', '湖北', '荆州', '沙市区', '北京西路410号', '13388990123');
INSERT INTO `t_address` VALUES ('1000200', '10002', '中国', '山东', '济南', '历下区', '山大路202号', '13422584349');

-- ----------------------------
-- Table structure for t_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_authority`;
CREATE TABLE `t_authority` (
  `id` int(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `authenticated` char(1) DEFAULT 'T',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_authority
-- ----------------------------
INSERT INTO `t_authority` VALUES ('50001', 'registerUser', 'T');
INSERT INTO `t_authority` VALUES ('50002', 'modifyUser', 'T');
INSERT INTO `t_authority` VALUES ('50003', 'removeUser', 'T');

-- ----------------------------
-- Table structure for t_customer
-- ----------------------------
DROP TABLE IF EXISTS `t_customer`;
CREATE TABLE `t_customer` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `identification` varchar(20) DEFAULT NULL,
  `phone_number` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10016 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_customer
-- ----------------------------
INSERT INTO `t_customer` VALUES ('10001', '李秋水', '女', '1979-10-01', '510110197910012312', '13388990123');
INSERT INTO `t_customer` VALUES ('10002', '逍遥子', '男', '1976-07-10', '510110197607103322', '13422584349');
INSERT INTO `t_customer` VALUES ('10003', '巫行云', '女', '1985-12-05', '110100198512057777', '19216212345');
INSERT INTO `t_customer` VALUES ('10004', '常青子', '男', '1975-10-20', '110100197510207812', '13613671267');
INSERT INTO `t_customer` VALUES ('10005', '关二锅', '男', '1987-11-15', '110110198711150001', '13613613666');
INSERT INTO `t_customer` VALUES ('10006', '诸葛村夫', '男', '1982-05-29', '110110198205290002', '18613629999');
INSERT INTO `t_customer` VALUES ('10007', '西门吹雪', '女', '2002-07-11', '510210200207113882', '18934345656');
INSERT INTO `t_customer` VALUES ('10008', '公孙无忌', '男', '2002-03-02', '510212200203027812', '13457772777');
INSERT INTO `t_customer` VALUES ('10009', '张一德', '男', '2000-04-21', '510212200004213344', '13656781234');
INSERT INTO `t_customer` VALUES ('10012', '赵子龙', '男', '1995-05-12', '110102199505123382', '13663100001');
INSERT INTO `t_customer` VALUES ('10013', '无涯子', '男', '1992-10-13', '110102199210132828', '13279127912');
INSERT INTO `t_customer` VALUES ('10014', '次仁顿珠', '男', '1992-08-18', '410110199208182323', '19100225757');
INSERT INTO `t_customer` VALUES ('10015', '娜扎买买提', '女', '1962-11-09', '410110196211093349', '13478692312');

-- ----------------------------
-- Table structure for t_distributor
-- ----------------------------
DROP TABLE IF EXISTS `t_distributor`;
CREATE TABLE `t_distributor` (
  `id` int(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `introduce` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `range` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_distributor
-- ----------------------------
INSERT INTO `t_distributor` VALUES ('20001', '国际商用机器公司(IBM)', '简称IBM（International Business Machines Corporation），总公司在纽约州阿蒙克市，1911年托马斯·约翰·沃森创立于美国，是全球最大的信息技术和业务解决方案公司', '北京市朝阳区奥运村街道北四环中路27号', '软件服务');
INSERT INTO `t_distributor` VALUES ('20002', '上海晨光文具股份有限公司(M&G)', '一家整合创意价值与服务优势的综合文具供应商,致力于让学习和工作更快乐、更高效。产品涵盖书写工具、学生文具、办公文具及其他相关产品', '上海市奉贤区金钱公路3469号3号楼', '文具、办公机械、活动用品组合装、组织计划类文具');
INSERT INTO `t_distributor` VALUES ('20003', '华为技术有限公司', '全球领先的ICT(信息与通信)基础设施和智能终端提供商,致力于把数字世界带入每个人、每个家庭、每个组织,构建万物互联的智能世界', '广东省深圳市龙岗区坂田华为基地', '通信技术、智能手机');
INSERT INTO `t_distributor` VALUES ('20004', '苹果公司(Apple Inc.)', '美国高科技公司，由史蒂夫·乔布斯、斯蒂夫·盖瑞·沃兹尼亚克和罗纳德·杰拉尔德·韦恩（Ron Wayne）等人于1976年4月1日创立', null, null);
INSERT INTO `t_distributor` VALUES ('20005', '微软公司(Microsoft)', '一家美国跨国科技企业，1975年4月4日创立，以研发、制造、授权和提供广泛的电脑软件服务业务为主', '北京市海淀区丹棱街5号', '软件服务');
INSERT INTO `t_distributor` VALUES ('20006', '文轩图书出版社', null, null, null);
INSERT INTO `t_distributor` VALUES ('20007', '德国西门子股份公司(SIEMENS AG)', null, null, null);
INSERT INTO `t_distributor` VALUES ('20008', '万利达集团有限公司', null, null, null);

-- ----------------------------
-- Table structure for t_inventory
-- ----------------------------
DROP TABLE IF EXISTS `t_inventory`;
CREATE TABLE `t_inventory` (
  `id` int(20) NOT NULL,
  `quantity` int(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_inventory
-- ----------------------------
INSERT INTO `t_inventory` VALUES ('30001', '9978', '2022-12-09 16:41:27');
INSERT INTO `t_inventory` VALUES ('30002', '10000', '2022-03-31 02:40:46');
INSERT INTO `t_inventory` VALUES ('30003', '10000', '2022-03-31 02:40:46');
INSERT INTO `t_inventory` VALUES ('30004', '9974', '2022-12-09 16:41:27');
INSERT INTO `t_inventory` VALUES ('30005', '10000', '2022-03-31 02:40:46');
INSERT INTO `t_inventory` VALUES ('30006', '10000', '2022-03-31 02:40:46');
INSERT INTO `t_inventory` VALUES ('30007', '10000', '2022-03-31 02:40:46');
INSERT INTO `t_inventory` VALUES ('30008', '10000', '2022-03-31 02:40:46');
INSERT INTO `t_inventory` VALUES ('30009', '10000', '2022-03-31 02:40:46');
INSERT INTO `t_inventory` VALUES ('30010', '10000', '2022-03-31 02:40:46');
INSERT INTO `t_inventory` VALUES ('30011', '10000', '2022-03-31 02:40:46');

-- ----------------------------
-- Table structure for t_journal_account
-- ----------------------------
DROP TABLE IF EXISTS `t_journal_account`;
CREATE TABLE `t_journal_account` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `account_id` int(20) DEFAULT NULL,
  `amount` decimal(20,2) DEFAULT NULL,
  `operation` varchar(50) DEFAULT NULL,
  `operate_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_journal_account
-- ----------------------------
INSERT INTO `t_journal_account` VALUES ('1', '1', '1000.00', 'topUp', '2022-12-02 01:07:51');
INSERT INTO `t_journal_account` VALUES ('2', '1', '1000.00', 'payoff', '2022-12-02 01:07:51');
INSERT INTO `t_journal_account` VALUES ('4', '1000901', '5916.00', 'payoff', '2022-12-02 17:19:06');
INSERT INTO `t_journal_account` VALUES ('5', '1000901', '5916.00', 'payoff', '2022-12-02 18:46:50');
INSERT INTO `t_journal_account` VALUES ('6', '1000901', '5916.00', 'refund', '2022-12-02 20:09:11');
INSERT INTO `t_journal_account` VALUES ('9', '1000901', '5916.00', 'refund', '2022-12-02 20:23:31');
INSERT INTO `t_journal_account` VALUES ('10', '1000901', '5916.00', 'payoff', '2022-12-02 20:29:12');
INSERT INTO `t_journal_account` VALUES ('11', '1000901', '5916.00', 'refund', '2022-12-02 20:33:09');
INSERT INTO `t_journal_account` VALUES ('17', '1', '1000.00', 'topUp', '2022-12-03 13:30:45');
INSERT INTO `t_journal_account` VALUES ('18', '1', '1000.00', 'payoff', '2022-12-03 13:30:46');
INSERT INTO `t_journal_account` VALUES ('19', '1', '1000.00', 'topUp', '2022-12-03 13:31:38');
INSERT INTO `t_journal_account` VALUES ('20', '1', '1000.00', 'payoff', '2022-12-03 13:31:39');
INSERT INTO `t_journal_account` VALUES ('22', '1', '1000.00', 'topUp', '2022-12-03 13:37:12');
INSERT INTO `t_journal_account` VALUES ('23', '1', '1000.00', 'payoff', '2022-12-03 13:37:13');
INSERT INTO `t_journal_account` VALUES ('25', '1', '1000.00', 'topUp', '2022-12-03 13:39:25');
INSERT INTO `t_journal_account` VALUES ('26', '1', '1000.00', 'payoff', '2022-12-03 13:39:26');
INSERT INTO `t_journal_account` VALUES ('29', '1', '1000.00', 'topUp', '2022-12-04 04:53:15');
INSERT INTO `t_journal_account` VALUES ('30', '1', '1000.00', 'payoff', '2022-12-04 04:53:15');
INSERT INTO `t_journal_account` VALUES ('32', '1', '1000.00', 'topUp', '2022-12-04 05:08:04');
INSERT INTO `t_journal_account` VALUES ('33', '1', '1000.00', 'payoff', '2022-12-04 05:08:04');
INSERT INTO `t_journal_account` VALUES ('34', '1', '1000.00', 'topUp', '2022-12-07 21:38:46');
INSERT INTO `t_journal_account` VALUES ('35', '1', '1000.00', 'payoff', '2022-12-07 21:38:46');
INSERT INTO `t_journal_account` VALUES ('37', '1', '1000.00', 'topUp', '2022-12-08 11:52:49');
INSERT INTO `t_journal_account` VALUES ('38', '1', '1000.00', 'payoff', '2022-12-08 11:52:49');
INSERT INTO `t_journal_account` VALUES ('40', '1', '1000.00', 'topUp', '2022-12-09 15:09:53');
INSERT INTO `t_journal_account` VALUES ('41', '1', '1000.00', 'payoff', '2022-12-09 15:09:53');
INSERT INTO `t_journal_account` VALUES ('43', '1', '1000.00', 'topUp', '2022-12-09 15:50:17');
INSERT INTO `t_journal_account` VALUES ('44', '1', '1000.00', 'payoff', '2022-12-09 15:50:17');
INSERT INTO `t_journal_account` VALUES ('46', '1', '1000.00', 'topUp', '2022-12-09 15:57:37');
INSERT INTO `t_journal_account` VALUES ('47', '1', '1000.00', 'payoff', '2022-12-09 15:57:37');
INSERT INTO `t_journal_account` VALUES ('48', '1000901', '5916.00', 'payoff', '2022-12-09 16:39:42');
INSERT INTO `t_journal_account` VALUES ('49', '1000901', '5916.00', 'refund', '2022-12-09 16:41:27');
INSERT INTO `t_journal_account` VALUES ('50', null, '5000.00', 'payoff', null);
INSERT INTO `t_journal_account` VALUES ('51', null, '3000.00', 'payoff', null);
INSERT INTO `t_journal_account` VALUES ('52', null, '5000.00', 'test', null);
INSERT INTO `t_journal_account` VALUES ('53', null, '3000.00', 'test', null);
INSERT INTO `t_journal_account` VALUES ('66', '1', '1000.00', 'topUp', '2023-01-31 01:28:39');
INSERT INTO `t_journal_account` VALUES ('67', '1', '1000.00', 'payoff', '2023-01-31 01:28:40');
INSERT INTO `t_journal_account` VALUES ('68', '1', '1000.00', 'topUp', '2023-01-31 01:30:29');
INSERT INTO `t_journal_account` VALUES ('69', '1', '1000.00', 'payoff', '2023-01-31 01:30:29');
INSERT INTO `t_journal_account` VALUES ('70', '1', '1000.00', 'topUp', '2023-01-31 01:32:53');
INSERT INTO `t_journal_account` VALUES ('71', '1', '1000.00', 'payoff', '2023-01-31 01:32:53');
INSERT INTO `t_journal_account` VALUES ('72', '1', '1000.00', 'topUp', '2023-01-31 01:43:46');
INSERT INTO `t_journal_account` VALUES ('73', '1', '1000.00', 'payoff', '2023-01-31 01:43:47');
INSERT INTO `t_journal_account` VALUES ('74', '1', '1000.00', 'topUp', '2023-01-31 01:46:57');
INSERT INTO `t_journal_account` VALUES ('75', '1', '1000.00', 'payoff', '2023-01-31 01:46:57');
INSERT INTO `t_journal_account` VALUES ('76', '1', '1000.00', 'topUp', '2023-01-31 09:27:11');
INSERT INTO `t_journal_account` VALUES ('77', '1', '1000.00', 'payoff', '2023-01-31 09:27:11');
INSERT INTO `t_journal_account` VALUES ('80', '1', '1000.00', 'topUp', '2023-01-31 11:30:46');
INSERT INTO `t_journal_account` VALUES ('81', '1', '1000.00', 'payoff', '2023-01-31 11:30:46');
INSERT INTO `t_journal_account` VALUES ('82', '1', '1000.00', 'topUp', '2023-01-31 18:35:08');
INSERT INTO `t_journal_account` VALUES ('83', '1', '1000.00', 'payoff', '2023-01-31 18:35:09');
INSERT INTO `t_journal_account` VALUES ('84', '1', '1000.00', 'topUp', '2023-02-01 22:57:41');
INSERT INTO `t_journal_account` VALUES ('85', '1', '1000.00', 'payoff', '2023-02-01 22:59:10');
INSERT INTO `t_journal_account` VALUES ('86', '1', '1000.00', 'topUp', '2023-02-01 23:02:00');
INSERT INTO `t_journal_account` VALUES ('87', '1', '1000.00', 'payoff', '2023-02-01 23:02:01');
INSERT INTO `t_journal_account` VALUES ('94', '1', '1000.00', 'topUp', '2023-02-02 11:42:28');
INSERT INTO `t_journal_account` VALUES ('95', '1', '1000.00', 'payoff', '2023-02-02 11:42:28');
INSERT INTO `t_journal_account` VALUES ('97', '1', '1000.00', 'topUp', '2023-02-02 11:47:16');
INSERT INTO `t_journal_account` VALUES ('98', '1', '1000.00', 'payoff', '2023-02-02 11:47:16');
INSERT INTO `t_journal_account` VALUES ('100', '1', '1000.00', 'topUp', '2023-02-07 11:08:33');
INSERT INTO `t_journal_account` VALUES ('101', '1', '1000.00', 'payoff', '2023-02-07 11:09:49');
INSERT INTO `t_journal_account` VALUES ('108', '1', '1000.00', 'topUp', '2023-02-20 21:04:55');
INSERT INTO `t_journal_account` VALUES ('109', '1', '1000.00', 'payoff', '2023-02-20 21:05:00');
INSERT INTO `t_journal_account` VALUES ('111', '1', '1000.00', 'topUp', '2023-02-20 21:15:59');
INSERT INTO `t_journal_account` VALUES ('112', '1', '1000.00', 'payoff', '2023-02-20 21:15:59');
INSERT INTO `t_journal_account` VALUES ('117', '1', '1000.00', 'topUp', '2023-02-20 21:45:44');
INSERT INTO `t_journal_account` VALUES ('118', '1', '1000.00', 'payoff', '2023-02-20 21:45:44');
INSERT INTO `t_journal_account` VALUES ('123', '1', '1000.00', 'topUp', '2023-02-20 23:36:58');
INSERT INTO `t_journal_account` VALUES ('124', '1', '1000.00', 'payoff', '2023-02-20 23:36:59');
INSERT INTO `t_journal_account` VALUES ('129', '1', '1000.00', 'topUp', '2023-02-20 23:40:43');
INSERT INTO `t_journal_account` VALUES ('130', '1', '1000.00', 'payoff', '2023-02-20 23:40:43');
INSERT INTO `t_journal_account` VALUES ('131', '1', '1000.00', 'topUp', '2023-02-21 11:24:19');
INSERT INTO `t_journal_account` VALUES ('132', '1', '1000.00', 'payoff', '2023-02-21 11:24:20');
INSERT INTO `t_journal_account` VALUES ('135', '1', '1000.00', 'topUp', '2023-02-21 11:27:35');
INSERT INTO `t_journal_account` VALUES ('136', '1', '1000.00', 'payoff', '2023-02-21 11:27:35');
INSERT INTO `t_journal_account` VALUES ('140', '1', '1000.00', 'topUp', '2023-02-21 14:21:04');
INSERT INTO `t_journal_account` VALUES ('141', '1', '1000.00', 'payoff', '2023-02-21 14:21:04');
INSERT INTO `t_journal_account` VALUES ('145', '1', '1000.00', 'topUp', '2023-05-21 20:17:51');
INSERT INTO `t_journal_account` VALUES ('146', '1', '1000.00', 'payoff', '2023-05-21 20:17:51');
INSERT INTO `t_journal_account` VALUES ('147', '1', '1000.00', 'topUp', '2023-05-22 09:45:41');
INSERT INTO `t_journal_account` VALUES ('148', '1', '1000.00', 'payoff', '2023-05-22 09:45:41');
INSERT INTO `t_journal_account` VALUES ('150', '1', '1000.00', 'topUp', '2023-05-22 10:06:51');
INSERT INTO `t_journal_account` VALUES ('151', '1', '1000.00', 'payoff', '2023-05-22 10:06:51');
INSERT INTO `t_journal_account` VALUES ('154', '1', '1000.00', 'topUp', '2023-05-22 10:33:28');
INSERT INTO `t_journal_account` VALUES ('155', '1', '1000.00', 'payoff', '2023-05-22 10:33:28');
INSERT INTO `t_journal_account` VALUES ('158', '1', '1000.00', 'topUp', '2023-05-28 07:26:56');
INSERT INTO `t_journal_account` VALUES ('159', '1', '1000.00', 'payoff', '2023-05-28 07:26:57');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` int(20) NOT NULL,
  `customer_id` int(20) DEFAULT NULL,
  `address_id` int(20) DEFAULT NULL,
  `amount` decimal(20,2) DEFAULT NULL,
  `order_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `flag` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES ('10001', '10001', '1000100', '8958.00', '2020-12-28 17:07:19', null, 'CREATE');
INSERT INTO `t_order` VALUES ('10002', '10002', '1000200', '829.00', '2021-01-01 12:00:00', null, 'CREATE');
INSERT INTO `t_order` VALUES ('10003', '10003', '1000300', '165.80', '2021-02-20 13:01:00', null, 'PAYMENT');
INSERT INTO `t_order` VALUES ('10004', '10004', '1000400', '4999.00', '2008-05-01 00:00:00', null, 'PAYMENT');
INSERT INTO `t_order` VALUES ('10005', '10005', '1000500', '4325.00', '2010-04-01 12:00:00', null, 'PAYMENT');
INSERT INTO `t_order` VALUES ('10006', '10006', '1000600', '11598.00', '2010-10-12 13:20:17', null, 'CREATE');
INSERT INTO `t_order` VALUES ('10007', '10007', '1000700', '14688.00', '2012-04-22 00:00:00', null, 'CREATE');
INSERT INTO `t_order` VALUES ('10008', '10008', '1000800', '12286.00', '2012-05-10 00:00:00', null, 'CREATE');
INSERT INTO `t_order` VALUES ('10009', '10009', '1000900', '7799.00', '2012-06-01 00:00:00', null, 'PAYMENT');
INSERT INTO `t_order` VALUES ('10010', '10001', '1000100', '5000.00', '2019-12-29 00:00:00', null, 'CREATE');
INSERT INTO `t_order` VALUES ('10011', '10001', '1000100', '16600.00', '2019-12-29 00:00:00', null, 'CREATE');
INSERT INTO `t_order` VALUES ('10012', '10010', '1001000', '4790.00', '2012-07-12 00:00:00', null, 'PAYMENT');
INSERT INTO `t_order` VALUES ('10013', '10011', '1001100', '3999.00', '2012-08-08 09:30:00', null, 'PAYMENT');
INSERT INTO `t_order` VALUES ('10014', '10012', '1001200', '8288.00', '2012-09-13 10:00:10', null, 'PAYMENT');
INSERT INTO `t_order` VALUES ('10015', '10011', '1001101', '5598.00', '2012-10-01 13:23:00', null, 'CREATE');
INSERT INTO `t_order` VALUES ('10016', '10001', '1000101', '10688.00', '2012-11-21 18:34:01', null, 'PAYMENT');
INSERT INTO `t_order` VALUES ('10017', '10013', '1001300', '2599.00', '2012-12-31 00:00:00', null, 'PAYMENT');
INSERT INTO `t_order` VALUES ('10018', '10014', '1001400', '2599.00', '2013-01-13 00:00:00', null, 'PAYMENT');
INSERT INTO `t_order` VALUES ('10019', '10015', '1001500', '4999.00', '2013-02-28 23:59:59', null, 'CREATE');
INSERT INTO `t_order` VALUES ('10020', '10012', '1001201', '2380.00', '2014-06-30 20:00:00', null, 'CREATE');
INSERT INTO `t_order` VALUES ('10021', '10012', '1001201', '28.90', '2014-06-30 21:00:00', null, 'CREATE');

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item` (
  `id` int(20) NOT NULL,
  `order_id` int(20) DEFAULT NULL,
  `product_id` int(20) DEFAULT NULL,
  `quantity` int(20) DEFAULT NULL,
  `price` decimal(20,2) DEFAULT NULL,
  `amount` decimal(20,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_order_item
-- ----------------------------
INSERT INTO `t_order_item` VALUES ('100010', '10001', '30003', '2', '4000.00', '8000.00');
INSERT INTO `t_order_item` VALUES ('100011', '10001', '30004', '1', '958.00', '958.00');
INSERT INTO `t_order_item` VALUES ('100020', '10002', '30010', '10', '82.90', '829.00');
INSERT INTO `t_order_item` VALUES ('100030', '10003', '30010', '1', '82.90', '82.90');
INSERT INTO `t_order_item` VALUES ('100031', '10003', '30011', '1', '82.90', '82.90');
INSERT INTO `t_order_item` VALUES ('100040', '10004', '30009', '1', '4999.00', '4999.00');
INSERT INTO `t_order_item` VALUES ('100050', '10005', '30013', '1', '2599.00', '2599.00');
INSERT INTO `t_order_item` VALUES ('100051', '10005', '30008', '1', '1488.00', '1488.00');
INSERT INTO `t_order_item` VALUES ('100052', '10005', '30015', '1', '238.00', '238.00');
INSERT INTO `t_order_item` VALUES ('100060', '10006', '30007', '2', '5799.00', '11598.00');
INSERT INTO `t_order_item` VALUES ('100070', '10007', '30001', '1', '4000.00', '4000.00');
INSERT INTO `t_order_item` VALUES ('100071', '10007', '30003', '1', '10688.00', '10688.00');
INSERT INTO `t_order_item` VALUES ('100080', '10008', '30007', '1', '5799.00', '5799.00');
INSERT INTO `t_order_item` VALUES ('100081', '10008', '30008', '1', '1488.00', '1488.00');
INSERT INTO `t_order_item` VALUES ('100082', '10008', '30009', '1', '4999.00', '4999.00');
INSERT INTO `t_order_item` VALUES ('100090', '10009', '30012', '1', '7799.00', '7799.00');
INSERT INTO `t_order_item` VALUES ('100110', '10011', '30001', '1', '4000.00', '4000.00');
INSERT INTO `t_order_item` VALUES ('100111', '10011', '30002', '1', '12600.00', '12600.00');
INSERT INTO `t_order_item` VALUES ('100120', '10012', '30004', '5', '958.00', '4790.00');
INSERT INTO `t_order_item` VALUES ('100130', '10013', '30014', '1', '3999.00', '3999.00');
INSERT INTO `t_order_item` VALUES ('100140', '10014', '30005', '1', '8288.00', '8288.00');
INSERT INTO `t_order_item` VALUES ('100150', '10015', '30006', '2', '2799.00', '5598.00');
INSERT INTO `t_order_item` VALUES ('100160', '10016', '30003', '1', '10688.00', '10688.00');
INSERT INTO `t_order_item` VALUES ('100170', '10017', '30013', '1', '2599.00', '2599.00');
INSERT INTO `t_order_item` VALUES ('100180', '10018', '30013', '1', '2599.00', '2599.00');
INSERT INTO `t_order_item` VALUES ('100190', '10019', '30009', '1', '4999.00', '4999.00');
INSERT INTO `t_order_item` VALUES ('100200', '10020', '30015', '10', '238.00', '2380.00');
INSERT INTO `t_order_item` VALUES ('100210', '10021', '30011', '1', '28.90', '28.90');

-- ----------------------------
-- Table structure for t_payment
-- ----------------------------
DROP TABLE IF EXISTS `t_payment`;
CREATE TABLE `t_payment` (
  `id` int(20) NOT NULL,
  `account_id` int(20) NOT NULL,
  `amount` decimal(20,2) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_payment
-- ----------------------------
INSERT INTO `t_payment` VALUES ('10001', '10102', '8958.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10002', '10103', '829.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10003', '10104', '165.80', 'payoff');
INSERT INTO `t_payment` VALUES ('10004', '10105', '4999.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10005', '10106', '4325.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10006', '10107', '11598.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10007', '10108', '14688.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10008', '10109', '12286.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10009', '10110', '7799.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10010', '10102', '5000.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10011', '10102', '16600.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10012', '10111', '4790.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10013', '10112', '3999.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10014', '10113', '8288.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10015', '10112', '5598.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10016', '10102', '10688.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10017', '10114', '2599.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10018', '10115', '2599.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10019', '10116', '4999.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10020', '10113', '2380.00', 'payoff');
INSERT INTO `t_payment` VALUES ('10021', '10113', '28.90', 'payoff');

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` int(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `unit` varchar(20) DEFAULT NULL,
  `supplier_id` int(20) DEFAULT NULL,
  `classify` varchar(100) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `original_price` decimal(10,2) DEFAULT NULL,
  `tip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES ('30001', 'Apple iPhone X 256GB 深空灰色 移动联通电信4G手机', '4000.00', '台', '20004', '手机', '/static/img/product1.jpg', '5600.00', '自营');
INSERT INTO `t_product` VALUES ('30002', 'Apple iPad 平板电脑 2018年新款9.7英寸', '12600.00', '台', '20004', '平板电脑', '/static/img/product2.jpg', '13000.00', '优惠');
INSERT INTO `t_product` VALUES ('30003', 'Apple MacBook Pro 13.3英寸笔记本电脑（2017款Core i5处理器/8GB内存/256GB硬盘 MupxT2CH/A）', '10688.00', '台', '20004', '笔记本电脑', '/static/img/product3.jpg', '12999.00', '秒杀');
INSERT INTO `t_product` VALUES ('30004', 'Kindle Paperwhite电纸书阅读器 电子书墨水屏 6英寸wifi 黑色', '958.00', '个', '20002', '电子书', '/static/img/product4.jpg', '999.00', '秒杀');
INSERT INTO `t_product` VALUES ('30005', '微软（Microsoft）新Surface Pro 二合一平板电脑笔记本 12.3英寸（i5 8G内存 256G存储）', '8288.00', '台', '20005', '笔记本电脑', '/static/img/product5.jpg', '8888.00', '优惠');
INSERT INTO `t_product` VALUES ('30006', 'Apple Watch Series 3智能手表（GPS款 42毫米 深空灰色铝金属表壳 黑色运动型表带 MQL12CH/A）', '2799.00', '块', '20004', '智能手表', '/static/img/product6.jpg', '2899.00', '自营');
INSERT INTO `t_product` VALUES ('30007', '华为 HUAWEI Mate 30E Pro 5G麒麟990E SoC芯片 双4000万徕卡电影影像 8GB+256GB青山黛全网通手机', '5799.00', '台', '20003', '手机', '/static/img/HUAWEI_Mate_30E Pro.jpg', '6000.00', '优惠');
INSERT INTO `t_product` VALUES ('30008', 'HUAWEI WATCH GT2 华为手表 运动智能手表 两周长续航/蓝牙通话/血氧检测/麒麟芯片 华为gt2 46mm 曜石黑', '1488.00', '块', '20003', '智能手表', '/static/img/WATCH_GT2.jpg', '1500.00', '自营');
INSERT INTO `t_product` VALUES ('30009', '华为平板MatePad Pro【键盘+笔】10.8英寸麒麟990游戏影音娱乐办公学习全面屏平板电脑8G+256G WIFI', '4999.00', '台', '20003', '平板电脑', '/static/img/MatePad_Pro.jpg', '5500.00', '优惠');
INSERT INTO `t_product` VALUES ('30010', '领域驱动设计 软件核心复杂性应对之道 英文版(异步图书出品)', '82.90', '本', '20006', '书籍', '/static/img/DDD.jpg', '84.00', '自营');
INSERT INTO `t_product` VALUES ('30011', '实现领域驱动设计(博文视点出品) [Implementing Domain-Driven Design]', '82.90', '本', '20006', '书籍', '/static/img/Implementing_Domain-Driven_Design.jpg', '84.00', '自营');

-- ----------------------------
-- Table structure for t_product_discount
-- ----------------------------
DROP TABLE IF EXISTS `t_product_discount`;
CREATE TABLE `t_product_discount` (
  `id` int(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `discount` decimal(5,4) DEFAULT NULL,
  `discount_type` varchar(20) DEFAULT NULL,
  `product_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product_discount
-- ----------------------------
INSERT INTO `t_product_discount` VALUES ('30001', '微软平板电脑笔记本打折', '2020-01-01 00:00:00', null, '0.8000', 'productDiscount', '30005');
INSERT INTO `t_product_discount` VALUES ('30002', '华为Mate 30E打折', '2020-01-01 00:00:00', null, '0.8800', 'productDiscount', '30007');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_role_granted_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_role_granted_authority`;
CREATE TABLE `t_role_granted_authority` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `available` char(1) DEFAULT 'T',
  `role_id` int(20) NOT NULL,
  `authority_id` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_granted_authority
-- ----------------------------

-- ----------------------------
-- Table structure for t_supplier
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE `t_supplier` (
  `id` int(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `supplier_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier
-- ----------------------------
INSERT INTO `t_supplier` VALUES ('20001', '国际商用机器公司(IBM)', 'distributor');
INSERT INTO `t_supplier` VALUES ('20002', '上海晨光文具股份有限公司(M&G)', 'distributor');
INSERT INTO `t_supplier` VALUES ('20003', '华为技术有限公司', 'distributor');
INSERT INTO `t_supplier` VALUES ('20004', '苹果公司(Apple Inc.)', 'distributor');
INSERT INTO `t_supplier` VALUES ('20005', '微软公司(Microsoft)', 'distributor');
INSERT INTO `t_supplier` VALUES ('20006', '文轩图书出版社', 'distributor');
INSERT INTO `t_supplier` VALUES ('20007', '德国西门子股份公司(SIEMENS AG)', 'distributor');
INSERT INTO `t_supplier` VALUES ('20008', '万利达集团有限公司', 'distributor');
INSERT INTO `t_supplier` VALUES ('40001', '华为专卖店（西直门店）', 'vendor');
INSERT INTO `t_supplier` VALUES ('40002', '华为专卖店（中关村店）', 'vendor');
INSERT INTO `t_supplier` VALUES ('40003', '苹果专卖店（望京店）', 'vendor');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_type` varchar(20) DEFAULT NULL,
  `expired` int(1) DEFAULT NULL,
  `locked` int(1) DEFAULT NULL,
  `credentials_expired` int(1) DEFAULT NULL,
  `disable` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_granted_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_user_granted_authority`;
CREATE TABLE `t_user_granted_authority` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `available` char(1) DEFAULT 'T',
  `user_id` int(20) NOT NULL,
  `authority_id` int(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_granted_authority` (`user_id`,`authority_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_granted_authority
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `available` char(1) DEFAULT 'T',
  `user_id` int(20) NOT NULL,
  `role_id` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for t_vendor
-- ----------------------------
DROP TABLE IF EXISTS `t_vendor`;
CREATE TABLE `t_vendor` (
  `id` int(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `distributor_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_vendor
-- ----------------------------
INSERT INTO `t_vendor` VALUES ('40001', '华为专卖店（西直门店）', '北京市西城区西直门', '20003');
INSERT INTO `t_vendor` VALUES ('40002', '华为专卖店（中关村店）', '北京市海淀区中关村南路', '20003');
INSERT INTO `t_vendor` VALUES ('40003', '苹果专卖店（望京店）', '北京市朝阳区望京西', '20004');

-- ----------------------------
-- Table structure for t_vip
-- ----------------------------
DROP TABLE IF EXISTS `t_vip`;
CREATE TABLE `t_vip` (
  `id` int(20) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `available` char(1) DEFAULT NULL,
  `coin` int(20) DEFAULT NULL,
  `vip_type` varchar(10) NOT NULL,
  `cashback` decimal(20,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_vip
-- ----------------------------
INSERT INTO `t_vip` VALUES ('10009', '2022-08-18 14:40:15', null, 'Y', '100000', 'golden', '4000.00');
INSERT INTO `t_vip` VALUES ('10012', '2022-08-18 14:40:15', '2022-08-18 14:42:12', 'Y', '100000', 'golden', '3000.00');
INSERT INTO `t_vip` VALUES ('10013', '2021-03-12 00:00:00', null, 'Y', '2000', 'silver', null);
INSERT INTO `t_vip` VALUES ('10014', '2021-12-09 00:00:00', null, 'Y', '1000', 'silver', null);

-- ----------------------------
-- Table structure for t_vip_discount
-- ----------------------------
DROP TABLE IF EXISTS `t_vip_discount`;
CREATE TABLE `t_vip_discount` (
  `id` int(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `begin_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `discount` decimal(5,4) DEFAULT NULL,
  `discount_type` varchar(20) DEFAULT NULL,
  `vip_type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_vip_discount
-- ----------------------------
INSERT INTO `t_vip_discount` VALUES ('1001', '金卡会员打折', '2020-01-01 00:00:00', null, '0.7500', 'vipDiscount', 'golden');
INSERT INTO `t_vip_discount` VALUES ('1002', '银卡会员打折', '2020-01-01 00:00:00', null, '0.9000', 'vipDiscount', 'silver');
