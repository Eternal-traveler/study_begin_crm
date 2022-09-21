package com.haoyang.workbench.service.customer;

import com.haoyang.workbench.pojo.Customer;

import java.util.List;
import java.util.Map;

/**
 * @author hao yang
 * @date 2022-08-11-20:22
 */
public interface CustomersService {
    List<Customer> queryCustomerByConditionForPage(Map<String, Object> map);

    int queryCountOfCustomerByCondition(Map<String, Object> map);

    int saveCreateCustomer(Customer customer);

    Customer queryCustomerById(String id);

    int saveEditCustomer(Customer customer);

    void deleteCustomer(String[] ids);

    Customer queryCustomerForDetailById(String id);

    List<String> queryCustomerNameByFuzzyName(String customerName);

    String queryCustomerIdByName(String customerName);
}
