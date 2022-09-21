package com.haoyang.workbench.service.customer.Impl;

import com.haoyang.workbench.mapper.customer.CustomerMapper;
import com.haoyang.workbench.pojo.Customer;
import com.haoyang.workbench.service.customer.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hao yang
 * @date 2022-08-11-20:23
 */

@Service("customersService")
public class CustomersServiceImpl implements CustomersService {
    private final CustomerMapper customerMapper;

    public CustomersServiceImpl(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public List<Customer> queryCustomerByConditionForPage(Map<String, Object> map) {
        return null;
    }

    @Override
    public int queryCountOfCustomerByCondition(Map<String, Object> map) {
        return 0;
    }

    @Override
    public int saveCreateCustomer(Customer customer) {
        return 0;
    }

    @Override
    public Customer queryCustomerById(String id) {
        return null;
    }

    @Override
    public int saveEditCustomer(Customer customer) {
        return 0;
    }

    @Override
    public void deleteCustomer(String[] ids) {

    }

    @Override
    public Customer queryCustomerForDetailById(String id) {
        return null;
    }

    @Override
    public List<String> queryCustomerNameByFuzzyName(String customerName) {
        return customerMapper.selectCustomerNameByFuzzyName(customerName);
    }

    @Override
    public String queryCustomerIdByName(String customerName) {
        return null;
    }
}
