package cn.jsmoon.repository;

import cn.jsmoon.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 商品Repository接口
 * @author Administrator
 *
 */
public interface GoodsRepository extends JpaRepository<Goods, Integer>,JpaSpecificationExecutor<Goods> {

	
	/**
	 * 查询某个类别下的所有商品
	 * @param typeId
	 * @return
	 */
	@Query(value="select * from t_goods where type_id=?1",nativeQuery=true)
	List<Goods> findByTypeId(int typeId);
	
	/**
	 * 获取最大的商品编码
	 * @return
	 */
	@Query(value="SELECT MAX(CODE) FROM t_goods",nativeQuery=true)
	String getMaxGoodsCode();
	
	/**
	 * 查询库存报警商品，实际库存小于库存下限的商品
	 * @return
	 */
	@Query(value="SELECT * FROM t_goods WHERE inventory_quantity<min_num",nativeQuery=true)
	List<Goods> listAlarm();
}
