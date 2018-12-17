package cn.jsmoon.service;

import cn.jsmoon.entity.Goods;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

/**
 * 商品Service接口
 * @author Administrator
 *
 */
public interface GoodsService {

	/**
	 * 查询某个类别下的所有商品
	 * @param typeId
	 * @return
	 */
	List<Goods> findByTypeId(int typeId);
	
	/**
	 * 根据条件分页查询商品信息
	 * @param goods
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	List<Goods> list(Goods goods, Integer page, Integer pageSize, Direction direction, String... properties);

	/**
	 * 获取总记录数
	 * @param goods
	 * @return
	 */
	Long getCount(Goods goods);

	/**
	 * 根据商品编码或商品名称条件分页查询没有库存的商品信息
	 * @param codeOrName
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	List<Goods> listNoInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer pageSize, Direction direction, String... properties);

	/**
	 * 根据商品编码或商品名称条件分页查询没有库存的商品信息的总记录数
	 * @param codeOrName
	 * @return
	 */
	Long getCountNoInventoryQuantityByCodeOrName(String codeOrName);

	/**
	 * 根据商品编码或商品名称条件分页查询有库存的商品信息
	 * @param codeOrName
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	List<Goods> listHasInventoryQuantityByCodeOrName(String codeOrName, Integer page, Integer pageSize, Direction direction, String... properties);
	
	/**
	 * 根据商品编码或商品名称条件分页查询有库存的商品信息的总记录数
	 * @param codeOrName
	 * @return
	 */
	Long getCountHasInventoryQuantityByCodeOrName(String codeOrName);
	
	/**
	 * 获取最大的商品编码
	 * @return
	 */
	String getMaxGoodsCode();
	
	/**
	 * 根据id删除商品
	 * @param id
	 */
	void delete(Integer id);
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	Goods findById(Integer id);
	
	/**
	 * 添加或者修改商品信息
	 * @param goods
	 */
	void save(Goods goods);
	
	/**
	 * 查询库存报警商品，实际库存小于库存下限的商品
	 * @return
	 */
	List<Goods> listAlarm();
}
