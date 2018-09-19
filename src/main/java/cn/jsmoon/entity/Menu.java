package cn.jsmoon.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 菜单实体
 * @author: LTQ
 * @create: 2018-08-19 14:22
 **/
@Data
@Entity
@Table(name = "t_menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     //自增ID

    @Column(length = 50)
    private String name;    //菜单名

    @Column(length = 200)
    private String url;     //菜单地址

    private Integer state;  //菜单状态 节点类型 1根节点 0叶子节点

    @Column(length = 100)
    private String icon;    //菜单图标

    private Integer pId;    //父菜单Id



}
