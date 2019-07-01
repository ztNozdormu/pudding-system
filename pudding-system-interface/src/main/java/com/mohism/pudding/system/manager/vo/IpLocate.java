package com.mohism.pudding.system.manager.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Exrickx
 */
@Data
public class IpLocate implements Serializable {

    private String retCode;

    private City result;
}

