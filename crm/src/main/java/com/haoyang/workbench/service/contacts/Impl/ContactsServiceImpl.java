package com.haoyang.workbench.service.contacts.Impl;

import com.haoyang.workbench.mapper.contacts.ContactsMapper;
import com.haoyang.workbench.pojo.Contacts;
import com.haoyang.workbench.pojo.FunnelVO;
import com.haoyang.workbench.service.contacts.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hao yang
 * @date 2022-08-11-20:21
 */

@Service("contactsService")
public class ContactsServiceImpl implements ContactsService {
    private final ContactsMapper contactsMapper;

    public ContactsServiceImpl(ContactsMapper contactsMapper) {
        this.contactsMapper = contactsMapper;
    }

    @Override
    public List<Contacts> queryContactsByConditionForPage(Map<String, Object> map) {
        return null;
    }

    @Override
    public int queryCountOfContactsByCondition(Map<String, Object> map) {
        return 0;
    }

    @Override
    public void saveCreateContacts(Contacts contacts) {

    }

    @Override
    public Contacts queryContactsById(String id) {
        return null;
    }

    @Override
    public void saveEditContacts(Contacts contacts) {

    }

    @Override
    public void deleteContacts(String[] contactsIds) {

    }

    @Override
    public Contacts queryContactsForDetailById(String id) {
        return null;
    }

    @Override
    public List<Contacts> queryContactsByFuzzyName(String contactsName) {
        return contactsMapper.selectContactsByFuzzyName(contactsName);
    }

    @Override
    public List<FunnelVO> queryCountOfCustomerAndContactsGroupByCustomer() {
        return contactsMapper.selectCountOfCustomerAndContactsGroupByCustomer();
    }
}
