package com.mohism.pudding.system.manager.core.constants;

import lombok.Getter;

/**
 * 系统基础管理枚举类型
 *
 * @author real earth
 * @date 2019/06/26 15:49
 */
@Getter
public enum ManagerTypeEnum {

    BUSINESS(1, "业务类"), SYSTEM(2, "系统类"), BASIS(3, "基础类");

    private Integer code;

    private String name;

    ManagerTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
