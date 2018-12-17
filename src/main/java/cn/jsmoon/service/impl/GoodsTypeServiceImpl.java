package cn.jsmoon.service.impl;


import cn.jsmoon.entity.GoodsType;
import cn.jsmoon.repository.GoodsTypeRepository;
import cn.jsmoon.service.GoodsTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品类别Service实现类
 * @author Administrator
 *
 */
@Service("goodsTypeService")
public class GoodsTypeServiceImpl implements GoodsTypeService {

	@Resource
	private GoodsTypeRepository goodsTypeRepository;

	@Override
	public List<GoodsType> findByParentId(int parentId) {
		return goodsTypeRepository.findByParentId(parentId);
	}

	@Override
	public void save(GoodsType goodsType) {
		goodsTypeRepository.save(goodsType);
	}

	@Override
	public void delete(Integer id) {
		goodsTypeRepository.deleteById(id);
	}

	@Override
	public GoodsType findById(Integer id) {
		return goodsTypeRepository.findById(id).get();
	}




}
