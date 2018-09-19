package cn.jsmoon.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 用户角色关联实体
 * @author: LTQ
 * @create: 2018-08-19 14:22
 **/
@Data
@Entity
@Table(name = "t_userRole")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //自增ID

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;  //用户实体

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;  //角色实体


}
