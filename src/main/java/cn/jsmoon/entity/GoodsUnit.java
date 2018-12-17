package cn.jsmoon.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品单位实体
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name="t_goodsunit")
public class GoodsUnit {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 编号
	
	@Column(length=10)
	private String name; // 商品单位名称


	
	
	
}
