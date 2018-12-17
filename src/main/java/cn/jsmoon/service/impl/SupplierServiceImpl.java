package cn.jsmoon.service.impl;


import cn.jsmoon.entity.Supplier;
import cn.jsmoon.repository.SupplierRepository;
import cn.jsmoon.service.SupplierService;
import cn.jsmoon.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 供应商Service实现类
 * @author Administrator
 *
 */
@Service("supplierService")
public class SupplierServiceImpl implements SupplierService {

	@Resource
	private SupplierRepository supplierRepository;


	@Override
	public List<Supplier> list(Supplier supplier, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
		Page<Supplier> pageSupplier=supplierRepository.findAll((Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
				search(supplier, root, query, cb),pageable);
		return pageSupplier.getContent();
	}

	@Override
	public Long getCount(Supplier supplier) {
		Long count=supplierRepository.count((Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
				search(supplier, root, query, cb));
		return count;
	}

	/**
	 * 供应商查询条件工具类
	 * @param supplier
	 * @param root
	 * @param query
	 * @param cb
	 * @return
	 */
	private Predicate search(Supplier supplier, Root<Supplier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicate=cb.conjunction();
		if(supplier!=null){
			if(StringUtil.isNotEmpty(supplier.getName())){
				predicate.getExpressions().add(cb.like(root.get("name"), "%"+supplier.getName()+"%"));
			}
		}
		return predicate;
	}

	@Override
	public void save(Supplier supplier) {
		supplierRepository.save(supplier);
	}

	@Override
	public void delete(Integer id) {
		supplierRepository.deleteById(id);
	}

	@Override
	public Supplier findById(Integer id) {
		return supplierRepository.findById(id).get();
	}

	@Override
	public List<Supplier> findByName(String name) {
		return supplierRepository.findByName(name);
	}
	


}
