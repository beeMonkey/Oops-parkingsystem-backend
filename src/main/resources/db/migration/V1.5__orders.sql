create table orders(
id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
car_id varchar (50) not null ,
type varchar (20),
status varchar (20),
operation varchar (20),
boy_id BIGINT default null ,
parkinglot_id BIGINT default null
)ENGINE=InnoDB  DEFAULT CHARSET=utf8;
INSERT INTO `orders` VALUES (1, '粤A12345', '存车', '无人处理', '指派',null ,null );
INSERT INTO `orders` VALUES (2, '粤A88888', '存车', '无人处理', '指派',null ,null );
INSERT INTO `orders` VALUES (3, '粤C88653', '存车', '停取中', 'null',3 ,null );
INSERT INTO `orders` VALUES (4, '粤C52333', '存车', '停取中', 'null',3 ,1 );
INSERT INTO `orders` VALUES (5, '粤D68933', '存车', '停取中', 'null',3 ,1 );
INSERT INTO `orders` VALUES (6, '粤D66733', '存车', '停取中', 'null',3 ,1 );
-- INSERT INTO `orders` VALUES (2, 'car_2', '取车', '', '');
-- INSERT INTO `orders` VALUES (3, 'car_3', '存车', '无人处理', '指派');
-- INSERT INTO `orders` VALUES (4, 'car_4', '取车', '', '');
-- INSERT INTO `orders` VALUES (5, 'car_5', '存车', '无人处理', '指派');