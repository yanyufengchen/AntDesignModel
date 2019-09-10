-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `user` CASCADE;
CREATE TABLE `user`
(
	`id` INT NOT NULL AUTO_INCREMENT  COMMENT '主键',
	`name` VARCHAR(50) COMMENT '用户名称',
	`account` VARCHAR(50) COMMENT '域账号',
	`email` VARCHAR(50) COMMENT '邮箱',
	`phone` VARCHAR(50) COMMENT '电话',
	`create_time` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
	`update_time` TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
	CONSTRAINT `PK_user` PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='模型用户';
;
