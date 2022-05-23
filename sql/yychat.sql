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

 Date: 23/05/2022 11:39:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `sender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '发送者',
  `receiver` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '接收者',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '内容',
  `sendTime` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 'mai', '5', '111', '2022-05-23 11:38:00');
INSERT INTO `message` VALUES (2, 'mai', '5', '111', '2022-05-23 11:38:01');

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
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `user` VALUES (14, '阿斯弗', '123456');

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
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userrelation
-- ----------------------------
INSERT INTO `userrelation` VALUES (1, 'mai', '1', 1);
INSERT INTO `userrelation` VALUES (2, 'mai', '3', 1);
INSERT INTO `userrelation` VALUES (3, 'mai', '4', 1);
INSERT INTO `userrelation` VALUES (4, 'mai', '5', 1);
INSERT INTO `userrelation` VALUES (5, 'mai', '6', 1);
INSERT INTO `userrelation` VALUES (6, 'mai', '阿斯弗', 1);

SET FOREIGN_KEY_CHECKS = 1;
