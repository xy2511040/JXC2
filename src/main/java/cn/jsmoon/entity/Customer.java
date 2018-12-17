package cn.jsmoon.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 客户实体
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name="t_customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 编号
	
	@Column(length=200)
	private String name; // 客户名称
	
	@Column(length=50)
	private String contact; // 联系人
	
	@Column(length=50)
	private String number; // 联系电话
	
	@Column(length=300)
	private String address; // 联系地址
	
	@Column(length=1000)
	private String remarks; // 备注 

}
