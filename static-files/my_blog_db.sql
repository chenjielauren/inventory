/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`my_blog_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

use inventory;
DROP TABLE IF EXISTS `tb_item`;

CREATE TABLE `tb_item` (
  `item_number` int(6) NOT NULL AUTO_INCREMENT COMMENT '部件号',
  `item_desc` varchar(100) NOT NULL COMMENT '描述',
  `item_brand` varchar(20) NOT NULL COMMENT '品牌',
  `item_model` varchar(40) NOT NULL COMMENT '型号',
  `vendor` varchar(40)  NOT NULL DEFAULT '0' COMMENT '供应商',
  `vendor_PN` varchar(40)  NOT NULL DEFAULT '0' COMMENT '供应商_partnumber',
  `vmi` tinyint(4) NOT NULL DEFAULT '0' COMMENT '供应商库存',
  `safe_vmi` int(6)  NOT NULL DEFAULT '0' COMMENT '安全库存数',
  `expire_date` varchar(40)  NOT NULL  COMMENT '有效期',
  `item_unit` varchar(10)  NOT NULL DEFAULT '0' COMMENT '单位',
  `create_user` varchar(40) NOT NULL COMMENT '添加人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `modify_user` varchar(40) NOT NULL COMMENT '修改人',
  `modify_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`item_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `tb_item_vendor` (
  `vendor_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '供应商id',
  `vendor_name` varchar(50) NOT NULL COMMENT '供应商名称',
  `vendor_pn` varchar(50) NOT NULL COMMENT '供应商partnumber',
  `vendor_rank` int(11) NOT NULL DEFAULT '1' COMMENT '分类的排序值 被使用的越多数值越大',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`vendor_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `tb_blog_category` */

insert  into `tb_item_vendor`(`vendor_id`,`vendor_name`,`vendor_pn`,`vendor_rank`,`create_time`) values (1,' Babtec','OPFA0110',8,'2018-11-12 00:28:49');
insert  into `tb_item_vendor`(`vendor_id`,`vendor_name`,`vendor_pn`,`vendor_rank`,`create_time`) values (2,' Rudi Hutt Maschinenbau','OPFA0142',19,'2018-11-12 10:42:25');
insert  into `tb_item_vendor`(`vendor_id`,`vendor_name`,`vendor_pn`,`vendor_rank`,`create_time`) values (3,' CAMAS S.p.A','OPFA0161',22,'2018-11-12 10:43:21');
insert  into `tb_item_vendor`(`vendor_id`,`vendor_name`,`vendor_pn`,`vendor_rank`,`create_time`) values (1,' Babtec','OPFA0110',8,'2018-11-12 00:28:49');
insert  into `tb_item_vendor`(`vendor_id`,`vendor_name`,`vendor_pn`,`vendor_rank`,`create_time`) values (2,' Rudi Hutt Maschinenbau','OPFA0142',19,'2018-11-12 10:42:25');
insert  into `tb_item_vendor`(`vendor_id`,`vendor_name`,`vendor_pn`,`vendor_rank`,`create_time`) values (3,' CAMAS S.p.A','OPFA0161',22,'2018-11-12 10:43:21');


