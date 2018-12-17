package cn.jsmoon.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品类别实体
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name="t_goodstype")
public class GoodsType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 编号
	
	@Column(length=50)
	private String name; // 类别名称
	
	private Integer state; // 类别节点类型 1 根节点 0 叶子节点
	
	@Column(length=100)
	private String icon; // 图标
	
	private Integer pId; // 父菜单Id


}
