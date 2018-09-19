package cn.jsmoon.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 角色菜单关联实体
 * @author: LTQ
 * @create: 2018-08-19 14:22
 **/
@Data
@Entity
@Table(name = "t_roleMenu")
public class RoleMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "roleId")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "menuId")
    private Menu menu;
}
