package cn.jsmoon.service.impl;


import cn.jsmoon.entity.Customer;
import cn.jsmoon.repository.CustomerRepository;
import cn.jsmoon.service.CustomerService;
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
 * 客户Service实现类
 * @author Administrator
 *
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

	@Resource
	private CustomerRepository customerRepository;


	@Override
	public List<Customer> list(Customer customer, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = PageRequest.of(page - 1, pageSize, direction, properties);
		Page<Customer> pageCustomer=customerRepository.findAll((Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
				search(customer, root, query, cb),pageable);
		return pageCustomer.getContent();
	}

	@Override
	public Long getCount(Customer customer) {
		Long count=customerRepository.count((Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) ->
				search(customer, root, query, cb));
		return count;
	}

	/**
	 * 供应商查询条件工具类
	 * @param customer
	 * @param root
	 * @param query
	 * @param cb
	 * @return
	 */
	private Predicate search(Customer customer, Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		Predicate predicate=cb.conjunction();
		if(customer!=null){
			if(StringUtil.isNotEmpty(customer.getName())){
				predicate.getExpressions().add(cb.like(root.get("name"), "%"+customer.getName()+"%"));
			}
		}
		return predicate;
	}

	@Override
	public void save(Customer customer) {
		customerRepository.save(customer);
	}

	@Override
	public void delete(Integer id) {
		customerRepository.deleteById(id);
	}

	@Override
	public Customer findById(Integer id) {
		return customerRepository.findById(id).get();
	}

	@Override
	public List<Customer> findByName(String name) {
		return customerRepository.findByName(name);
	}
	


}
