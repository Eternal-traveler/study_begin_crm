package com.haoyang.workbench.service.contacts;

import com.haoyang.workbench.pojo.Contacts;
import com.haoyang.workbench.pojo.FunnelVO;

import java.util.List;
import java.util.Map;

/**
 * @author hao yang
 * @date 2022-08-11-20:20
 */

public interface ContactsService {

    List<Contacts> queryContactsByConditionForPage(Map<String, Object> map);

    int queryCountOfContactsByCondition(Map<String, Object> map);

    void saveCreateContacts(Contacts contacts);

    Contacts queryContactsById(String id);

    void saveEditContacts(Contacts contacts);

    void deleteContacts(String[] contactsIds);

    Contacts queryContactsForDetailById(String id);

    /**
     * 通过姓名模糊查询联系人
     * @param contactsName 模糊姓名
     * @return 查询到的线索
     */
    List<Contacts> queryContactsByFuzzyName(String contactsName);

    List<FunnelVO> queryCountOfCustomerAndContactsGroupByCustomer();

}
