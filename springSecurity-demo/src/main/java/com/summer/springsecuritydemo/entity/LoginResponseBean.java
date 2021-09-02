package com.summer.springsecuritydemo.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author Summer_DM
 * @Summary TODO  用户登录失败，返回处理的实体
 * @Version 1.0
 * @Date 2021/8/26 下午 02:39
 **/
@Data
@ToString
public class LoginResponseBean implements Serializable {

    private static final long serialVersionUID = -7136537864183138269L;

    //报错提示
    private String msg;

}
