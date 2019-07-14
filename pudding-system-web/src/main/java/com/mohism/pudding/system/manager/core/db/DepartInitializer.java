//package com.mohism.pudding.system.manager.core.db;
//
//
//import com.mohism.pudding.core.db.DbInitializer;
//import com.mohism.pudding.system.manager.entity.Depart;
//
///**
// * 组织机构表的初始化程序
// *
// * @author real earth
// * @date 2019-06-30-上午9:29
// */
//public class DepartInitializer extends DbInitializer {
//
//    @Override
//    public String getTableInitSql() {
////        return "CREATE TABLE `sys_dict` (\n" +
////                "  `DICT_ID` bigint(20) NOT NULL COMMENT '字典id',\n" +
////                "  `DICT_TYPE_CODE` varchar(255) NOT NULL COMMENT '字典类型编码',\n" +
////                "  `DICT_CODE` varchar(50) NOT NULL COMMENT '字典编码',\n" +
////                "  `DICT_NAME` varchar(255) NOT NULL COMMENT '字典名称',\n" +
////                "  `DICT_SHORT_NAME` varchar(255) DEFAULT NULL COMMENT '简称',\n" +
////                "  `DICT_SHORT_CODE` varchar(255) DEFAULT NULL COMMENT '字典简拼',\n" +
////                "  `PARENT_ID` bigint(20) NOT NULL COMMENT '上级代码id',\n" +
////                "  `STATUS` smallint(6) NOT NULL COMMENT '状态(1:启用,2:禁用)',\n" +
////                "  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',\n" +
////                "  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',\n" +
////                "  PRIMARY KEY (`DICT_ID`) USING BTREE\n" +
////                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='基础字典'";
//       return null;
//    }
//
//    @Override
//    public String getTableName() {
//        return "sys_depart";
//    }
//
//    @Override
//    public Class<?> getEntityClass() {
//        return Depart.class;
//    }
//}
