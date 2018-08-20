package cn.jsmoon.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * @program: Jxc
 * @description: 用户实体
 * @author: LTQ
 * @create: 2018-08-19 14:22
 **/
@Data
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     //自增ID

    @NotEmpty(message = "请输入用户名！！！")
    @Column(length = 50)
    private String userName;    //用户名

    @NotEmpty(message = "请输入密码！！！")
    @Column(length = 50)
    private String password;    //密码

    @Column(length = 50)
    private String trueName;    //真实姓名

    @Column(length = 1000)
    private String remarks;     //备注

}
