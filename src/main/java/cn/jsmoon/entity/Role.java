package cn.jsmoon.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 角色实体
 * @author: LTQ
 * @create: 2018-08-19 14:22
 **/
@Data
@Entity
@Table(name = "t_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;     //自增ID

    @Column(length = 50)
    private String name;    //角色名

    @Column(length = 1000)
    private String remarks; //备注
}
