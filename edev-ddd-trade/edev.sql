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
INSERT INTO `t_account` VALUES ('1000901', '10009', '846184.00', '2022-03-31 02:51:49', '2022-11-19 15:32:35');
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
INSERT INTO `t_address` VALUES ('1000100', '10001', '??????', '??????', '??????', '?????????', '?????????726???', '13300224466');
INSERT INTO `t_address` VALUES ('1000101', '10001', '??????', '??????', '??????', '?????????', '????????????410???', '13388990123');
INSERT INTO `t_address` VALUES ('1000200', '10002', '??????', '??????', '??????', '?????????', '?????????202???', '13422584349');

-- ----------------------------
-- Table structure for t_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_authority`;
CREATE TABLE `t_authority` (
  `id` int(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `available` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_authority
-- ----------------------------

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
INSERT INTO `t_customer` VALUES ('10001', '?????????', '???', '1979-10-01', '510110197910012312', '13388990123');
INSERT INTO `t_customer` VALUES ('10002', '?????????', '???', '1976-07-10', '510110197607103322', '13422584349');
INSERT INTO `t_customer` VALUES ('10003', '?????????', '???', '1985-12-05', '110100198512057777', '19216212345');
INSERT INTO `t_customer` VALUES ('10004', '?????????', '???', '1975-10-20', '110100197510207812', '13613671267');
INSERT INTO `t_customer` VALUES ('10005', '?????????', '???', '1987-11-15', '110110198711150001', '13613613666');
INSERT INTO `t_customer` VALUES ('10006', '????????????', '???', '1982-05-29', '110110198205290002', '18613629999');
INSERT INTO `t_customer` VALUES ('10007', '????????????', '???', '2002-07-11', '510210200207113882', '18934345656');
INSERT INTO `t_customer` VALUES ('10008', '????????????', '???', '2002-03-02', '510212200203027812', '13457772777');
INSERT INTO `t_customer` VALUES ('10009', '?????????', '???', '2000-04-21', '510212200004213344', '13656781234');
INSERT INTO `t_customer` VALUES ('10012', '?????????', '???', '1995-05-12', '110102199505123382', '13663100001');
INSERT INTO `t_customer` VALUES ('10013', '?????????', '???', '1992-10-13', '110102199210132828', '13279127912');
INSERT INTO `t_customer` VALUES ('10014', '????????????', '???', '1992-08-18', '410110199208182323', '19100225757');
INSERT INTO `t_customer` VALUES ('10015', '???????????????', '???', '1962-11-09', '410110196211093349', '13478692312');

-- ----------------------------
-- Table structure for t_distributor
-- ----------------------------
DROP TABLE IF EXISTS `t_distributor`;
CREATE TABLE `t_distributor` (
  `id` int(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `supplier_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_distributor
-- ----------------------------
INSERT INTO `t_distributor` VALUES ('10001', '?????????', 'distributor');
INSERT INTO `t_distributor` VALUES ('10002', '????????????', 'distributor');

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
INSERT INTO `t_inventory` VALUES ('30001', '9978', '2022-12-02 20:33:09');
INSERT INTO `t_inventory` VALUES ('30002', '10000', '2022-03-31 02:40:46');
INSERT INTO `t_inventory` VALUES ('30003', '10000', '2022-03-31 02:40:46');
INSERT INTO `t_inventory` VALUES ('30004', '9974', '2022-12-02 20:33:09');
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

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
INSERT INTO `t_order` VALUES ('10001', '10001', '1000100', '8958.00', '2020-12-28 17:07:19', '2022-12-02 22:28:53', 'CREATE');
INSERT INTO `t_order` VALUES ('10002', '10002', '1000200', '829.00', '2021-01-01 12:00:00', '2022-12-02 22:29:01', 'CREATE');
INSERT INTO `t_order` VALUES ('10003', '10003', '1000300', '165.80', '2021-02-20 13:01:00', '2022-12-02 22:29:05', 'PAYMENT');
INSERT INTO `t_order` VALUES ('10004', '10004', '1000400', '4999.00', '2008-05-01 00:00:00', '2022-12-02 22:29:08', 'PAYMENT');
INSERT INTO `t_order` VALUES ('10005', '10005', '1000500', '4325.00', '2010-04-01 12:00:00', '2022-12-02 22:29:12', 'PAYMENT');
INSERT INTO `t_order` VALUES ('10006', '10006', '1000600', '11598.00', '2010-10-12 13:20:17', '2022-12-02 22:29:14', 'CREATE');
INSERT INTO `t_order` VALUES ('10007', '10007', '1000700', '14688.00', '2012-04-22 00:00:00', '2022-12-02 22:29:17', 'CREATE');
INSERT INTO `t_order` VALUES ('10008', '10008', '1000800', '12286.00', '2012-05-10 00:00:00', '2022-12-02 22:29:21', 'CREATE');
INSERT INTO `t_order` VALUES ('10009', '10009', '1000900', '7799.00', '2012-06-01 00:00:00', '2022-12-02 22:29:24', 'PAYMENT');
INSERT INTO `t_order` VALUES ('10010', '10001', '1000100', '5000.00', '2019-12-29 00:00:00', '2022-12-02 22:29:28', 'CREATE');
INSERT INTO `t_order` VALUES ('10011', '10001', '1000100', '16600.00', '2019-12-29 00:00:00', '2022-12-02 22:29:32', 'CREATE');
INSERT INTO `t_order` VALUES ('10012', '10010', '1001000', '4790.00', '2012-07-12 00:00:00', '2022-12-02 22:29:35', 'PAYMENT');
INSERT INTO `t_order` VALUES ('10013', '10011', '1001100', '3999.00', '2012-08-08 09:30:00', '2022-12-02 22:29:37', 'PAYMENT');
INSERT INTO `t_order` VALUES ('10014', '10012', '1001200', '8288.00', '2012-09-13 10:00:10', '2022-12-02 22:29:41', 'PAYMENT');
INSERT INTO `t_order` VALUES ('10015', '10011', '1001101', '5598.00', '2012-10-01 13:23:00', '2022-12-02 22:29:44', 'CREATE');
INSERT INTO `t_order` VALUES ('10016', '10001', '1000101', '10688.00', '2012-11-21 18:34:01', '2022-12-02 22:29:47', 'PAYMENT');
INSERT INTO `t_order` VALUES ('10017', '10013', '1001300', '2599.00', '2012-12-31 00:00:00', '2022-12-02 22:29:50', 'PAYMENT');
INSERT INTO `t_order` VALUES ('10018', '10014', '1001400', '2599.00', '2013-01-13 00:00:00', '2022-12-02 22:29:53', 'PAYMENT');
INSERT INTO `t_order` VALUES ('10019', '10015', '1001500', '4999.00', '2013-02-28 23:59:59', '2022-12-02 22:29:56', 'CREATE');
INSERT INTO `t_order` VALUES ('10020', '10012', '1001201', '2380.00', '2014-06-30 20:00:00', '2022-12-02 22:29:59', 'CREATE');
INSERT INTO `t_order` VALUES ('10021', '10012', '1001201', '28.90', '2014-06-30 21:00:00', '2022-12-02 22:30:01', 'CREATE');

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
INSERT INTO `t_product` VALUES ('30001', 'Apple iPhone X 256GB ???????????? ??????????????????4G??????', '4000.00', '???', '20004', '??????', '/static/img/product1.jpg', '5600.00', '??????');
INSERT INTO `t_product` VALUES ('30002', 'Apple iPad ???????????? 2018?????????9.7??????', '12600.00', '???', '20004', '????????????', '/static/img/product2.jpg', '13000.00', '??????');
INSERT INTO `t_product` VALUES ('30003', 'Apple MacBook Pro 13.3????????????????????????2017???Core i5?????????/8GB??????/256GB?????? MupxT2CH/A???', '10688.00', '???', '20004', '???????????????', '/static/img/product3.jpg', '12999.00', '??????');
INSERT INTO `t_product` VALUES ('30004', 'Kindle Paperwhite?????????????????? ?????????????????? 6??????wifi ??????', '958.00', '???', '20002', '?????????', '/static/img/product4.jpg', '999.00', '??????');
INSERT INTO `t_product` VALUES ('30005', '?????????Microsoft??????Surface Pro ?????????????????????????????? 12.3?????????i5 8G?????? 256G?????????', '8288.00', '???', '20005', '???????????????', '/static/img/product5.jpg', '8888.00', '??????');
INSERT INTO `t_product` VALUES ('30006', 'Apple Watch Series 3???????????????GPS??? 42?????? ??????????????????????????? ????????????????????? MQL12CH/A???', '2799.00', '???', '20004', '????????????', '/static/img/product6.jpg', '2899.00', '??????');
INSERT INTO `t_product` VALUES ('30007', '?????? HUAWEI Mate 30E Pro 5G??????990E SoC?????? ???4000????????????????????? 8GB+256GB????????????????????????', '5799.00', '???', '20003', '??????', '/static/img/HUAWEI_Mate_30E Pro.jpg', '6000.00', '??????');
INSERT INTO `t_product` VALUES ('30008', 'HUAWEI WATCH GT2 ???????????? ?????????????????? ???????????????/????????????/????????????/???????????? ??????gt2 46mm ?????????', '1488.00', '???', '20003', '????????????', '/static/img/WATCH_GT2.jpg', '1500.00', '??????');
INSERT INTO `t_product` VALUES ('30009', '????????????MatePad Pro?????????+??????10.8????????????990???????????????????????????????????????????????????8G+256G WIFI', '4999.00', '???', '20003', '????????????', '/static/img/MatePad_Pro.jpg', '5500.00', '??????');
INSERT INTO `t_product` VALUES ('30010', '?????????????????? ????????????????????????????????? ?????????(??????????????????)', '82.90', '???', '20006', '??????', '/static/img/DDD.jpg', '84.00', '??????');
INSERT INTO `t_product` VALUES ('30011', '????????????????????????(??????????????????) [Implementing Domain-Driven Design]', '82.90', '???', '20006', '??????', '/static/img/Implementing_Domain-Driven_Design.jpg', '84.00', '??????');

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
  `id` int(20) NOT NULL,
  `available` char(1) DEFAULT NULL,
  `role_id` int(20) NOT NULL,
  `authority_id` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role_granted_authority
-- ----------------------------

-- ----------------------------
-- Table structure for t_staff
-- ----------------------------
DROP TABLE IF EXISTS `t_staff`;
CREATE TABLE `t_staff` (
  `id` int(20) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_staff
-- ----------------------------

-- ----------------------------
-- Table structure for t_supplier
-- ----------------------------
DROP TABLE IF EXISTS `t_supplier`;
CREATE TABLE `t_supplier` (
  `id` int(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_supplier
-- ----------------------------
INSERT INTO `t_supplier` VALUES ('20001', '????????????????????????(IBM)');
INSERT INTO `t_supplier` VALUES ('20002', '????????????????????????????????????(M&G)');
INSERT INTO `t_supplier` VALUES ('20003', '????????????????????????');
INSERT INTO `t_supplier` VALUES ('20004', '????????????(Apple Inc.)');
INSERT INTO `t_supplier` VALUES ('20005', '????????????(Microsoft)');
INSERT INTO `t_supplier` VALUES ('20006', '?????????????????????');

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
INSERT INTO `t_user` VALUES ('10001', 'Johnwood', '123', 'staff', null, null, null, null);

-- ----------------------------
-- Table structure for t_user_granted_authority
-- ----------------------------
DROP TABLE IF EXISTS `t_user_granted_authority`;
CREATE TABLE `t_user_granted_authority` (
  `id` int(20) NOT NULL,
  `available` char(1) DEFAULT NULL,
  `user_id` int(20) NOT NULL,
  `authority_id` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_granted_authority
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(20) NOT NULL,
  `available` char(1) DEFAULT NULL,
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
  `supplier_type` varchar(20) DEFAULT NULL,
  `distributor_id` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_vendor
-- ----------------------------

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
