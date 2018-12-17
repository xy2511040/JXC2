package cn.jsmoon.repository;

import cn.jsmoon.entity.GoodsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 商品类别Repository接口
 * @author Administrator
 *
 */
public interface GoodsTypeRepository extends JpaRepository<GoodsType, Integer> {

	
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	@Query(value="select * from t_goodstype where p_id=?1",nativeQuery=true)
	List<GoodsType> findByParentId(int parentId);
}
