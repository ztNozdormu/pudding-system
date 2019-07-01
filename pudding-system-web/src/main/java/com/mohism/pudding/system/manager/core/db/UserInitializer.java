/**
 * Copyright 2018-2020 stylefeng & fengshuonan (sn93@qq.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mohism.pudding.system.manager.core.db;

import com.mohism.pudding.core.db.DbInitializer;
import com.mohism.pudding.system.manager.entity.SysUser;
import org.springframework.stereotype.Component;

/**
 * 用户表的初始化
 *
 * @author fengshuonan
 * @date 2018-07-30-上午9:29
 */
@Component
public class UserInitializer extends DbInitializer {

    @Override
    public String getTableInitSql() {
        return "CREATE TABLE `sys_user` (\n" +
                "  `USER_ID` bigint(20) NOT NULL COMMENT '主键id',\n" +
                "  `AVATAR` varchar(255) DEFAULT NULL COMMENT '头像',\n" +
                "  `ACCOUNT` varchar(45) DEFAULT NULL COMMENT '账号',\n" +
                "  `PASSWORD` varchar(45) DEFAULT NULL COMMENT '密码',\n" +
                "  `SALT` varchar(45) DEFAULT NULL COMMENT 'md5密码盐',\n" +
                "  `NAME` varchar(45) DEFAULT NULL COMMENT '名字',\n" +
                "  `BIRTHDAY` datetime DEFAULT NULL COMMENT '生日',\n" +
                "  `SEX` char(1) DEFAULT NULL COMMENT '性别（M：男 F：女）',\n" +
                "  `EMAIL` varchar(45) DEFAULT NULL COMMENT '电子邮件',\n" +
                "  `PHONE` varchar(45) DEFAULT NULL COMMENT '电话',\n" +
                "  `STATUS` int(11) DEFAULT NULL COMMENT '状态(1：启用  2：冻结  3：删除）',\n" +
                "  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',\n" +
                "  `UPDATE_TIME` datetime DEFAULT NULL COMMENT '更新时间',\n" +
                "  PRIMARY KEY (`USER_ID`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';";
    }

    @Override
    public String getTableName() {
        return "sys_user";
    }

    @Override
    public Class<?> getEntityClass() {
        return SysUser.class;
    }
}
