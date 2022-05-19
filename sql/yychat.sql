/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : yychat

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 19/05/2022 14:44:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'mai', '123456');
INSERT INTO `user` VALUES (2, '1', '123456');
INSERT INTO `user` VALUES (5, '3', '123456');
INSERT INTO `user` VALUES (7, '4', '123456');
INSERT INTO `user` VALUES (10, '5', '123456');
INSERT INTO `user` VALUES (11, '2', '123456');
INSERT INTO `user` VALUES (12, '6', '123456');
INSERT INTO `user` VALUES (13, 'dddd', '123456');

-- ----------------------------
-- Table structure for userrelation
-- ----------------------------
DROP TABLE IF EXISTS `userrelation`;
CREATE TABLE `userrelation`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `masterUser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `slaveUser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `relation` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userrelation_ibfk_1`(`masterUser`) USING BTREE,
  INDEX `userrelation_ibfk_2`(`slaveUser`) USING BTREE,
  CONSTRAINT `userrelation_ibfk_1` FOREIGN KEY (`masterUser`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `userrelation_ibfk_2` FOREIGN KEY (`slaveUser`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userrelation
-- ----------------------------
INSERT INTO `userrelation` VALUES (1, 'mai', '1', 1);
INSERT INTO `userrelation` VALUES (2, 'mai', '3', 1);

SET FOREIGN_KEY_CHECKS = 1;
