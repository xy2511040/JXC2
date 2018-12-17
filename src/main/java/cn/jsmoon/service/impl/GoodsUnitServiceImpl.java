package cn.jsmoon.service.impl;


import cn.jsmoon.entity.GoodsUnit;
import cn.jsmoon.repository.GoodsUnitRepository;
import cn.jsmoon.service.GoodsUnitService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品单位Service实现类
 * @author Administrator
 *
 */
@Service("goodsUnitService")
public class GoodsUnitServiceImpl implements GoodsUnitService{

	@Resource
	private GoodsUnitRepository goodsUnitRepository;


	@Override
	public void save(GoodsUnit goodsUnit) {
		goodsUnitRepository.save(goodsUnit);
	}

	@Override
	public void delete(Integer id) {
		goodsUnitRepository.deleteById(id);
	}

	@Override
	public GoodsUnit findById(Integer id) {
		return goodsUnitRepository.findById(id).get();
	}

	@Override
	public List<GoodsUnit> listAll() {
		return goodsUnitRepository.findAll();
	}




}
