package cn.jsmoon.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * 商品实体
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name="t_goods")
public class Goods {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 编号
	
	@Column(length=50)
	private String code; // 商品编码
	
	@Column(length=50)
	private String name; // 商品名称
	
	@Column(length=50)
	private String model; // 商品型号
	
	@ManyToOne
	@JoinColumn(name="typeId")
	private GoodsType type; // 商品类别

	@Column(length=10)
	private String unit; // 商品单位
	
	private float lastPurchasingPrice; // 上次采购价格
	
	private float purchasingPrice; // 采购价格  成本价  假如价格变动 算平均值
	
	private float sellingPrice; // 出售价格
	
	private int inventoryQuantity; // 库存数量
	
	private int minNum; // 库存下限
	
	private int state; // 0 初始化状态 1 期初库存入仓库  2  有进货或者销售单据
	
	@Column(length=200)
	private String producer; // 生产厂商
	
	@Column(length=1000)
	private String remarks; // 备注
	
	@Transient
	private String codeOrName; // 查询用到 根据商品编码或者商品名称查询
	
	@Transient
	private int saleTotal; // 销售总数

}
