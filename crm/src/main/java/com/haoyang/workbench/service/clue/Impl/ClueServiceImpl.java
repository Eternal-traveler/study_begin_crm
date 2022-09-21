package com.haoyang.workbench.service.clue.Impl;

import com.haoyang.commons.constants.Constants;
import com.haoyang.commons.utils.DateUtils;
import com.haoyang.commons.utils.UUIDUtils;
import com.haoyang.settings.pojo.User;
import com.haoyang.workbench.mapper.activityAndClue.ClueActivityRelationMapper;
import com.haoyang.workbench.mapper.clue.ClueMapper;
import com.haoyang.workbench.mapper.clue.ClueRemarkMapper;
import com.haoyang.workbench.mapper.contacts.ContactsMapper;
import com.haoyang.workbench.mapper.contacts.ContactsRemarkMapper;
import com.haoyang.workbench.mapper.contactsActivityRelation.ContactsActivityRelationMapper;
import com.haoyang.workbench.mapper.customer.CustomerMapper;
import com.haoyang.workbench.mapper.customer.CustomerRemarkMapper;
import com.haoyang.workbench.mapper.tran.TranMapper;
import com.haoyang.workbench.mapper.tran.TranRemarkMapper;
import com.haoyang.workbench.pojo.*;
import com.haoyang.workbench.service.clue.ClueService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hao yang
 * @date 2022-08-08-16:46
 */

@Service("clueService")
public class ClueServiceImpl implements ClueService {

    private final ClueMapper clueMapper;

    private final ClueRemarkMapper clueRemarkMapper;

    private final ClueActivityRelationMapper clueActivityRelationMapper;

    private final CustomerMapper customerMapper;

    private final ContactsMapper contactsMapper;

    private final CustomerRemarkMapper customerRemarkMapper;

    private final ContactsRemarkMapper contactsRemarkMapper;

    private final ContactsActivityRelationMapper contactsActivityRelationMapper;

    private final TranMapper tranMapper;

    private final TranRemarkMapper tranRemarkMapper;

    public ClueServiceImpl(ClueMapper clueMapper, ClueRemarkMapper clueRemarkMapper, ClueActivityRelationMapper clueActivityRelationMapper, CustomerMapper customerMapper, ContactsMapper contactsMapper, CustomerRemarkMapper customerRemarkMapper, ContactsRemarkMapper contactsRemarkMapper, ContactsActivityRelationMapper contactsActivityRelationMapper, TranMapper tranMapper, TranRemarkMapper tranRemarkMapper) {
        this.clueMapper = clueMapper;
        this.clueRemarkMapper = clueRemarkMapper;
        this.clueActivityRelationMapper = clueActivityRelationMapper;
        this.customerMapper = customerMapper;
        this.contactsMapper = contactsMapper;
        this.customerRemarkMapper = customerRemarkMapper;
        this.contactsRemarkMapper = contactsRemarkMapper;
        this.contactsActivityRelationMapper = contactsActivityRelationMapper;
        this.tranMapper = tranMapper;
        this.tranRemarkMapper = tranRemarkMapper;
    }

    @Override
    public int saveCreateClue(Clue clue) {
        return clueMapper.insertClue(clue);
    }

    @Override
    public List<Clue> queryClueByConditionForPage(Map<String, Object> map) {
        return clueMapper.selectClueByConditionForPage(map);
    }

    @Override
    public int queryCountOfClueByCondition(Map<String, Object> map) {
        return clueMapper.selectCountOfClueByCondition(map);
    }

    @Override
    public void deleteClue(String[] ids) {
        // 删除线索备注
        clueRemarkMapper.deleteClueRemarkByClueId(ids);
        // 删除线索市场活动关联
        clueActivityRelationMapper.deleteClueActivityRelationByClueIds(ids);
        // 删除线索
        clueMapper.deleteClueByIds(ids);
    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    @Override
    public int saveEditClue(Clue clue) {
        return clueMapper.updateClue(clue);
    }

    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    /**
     * 线索转换功能的具体实现
     * @param map 封装的参数
     */
    @Override
    public void saveConvertClue(Map<String, Object> map) {
        String clueId = (String) map.get("clueId");
        User user = (User) map.get(Constants.SESSION_USER);
        String isCreateTran = (String) map.get("isCreateTran");
        //根据id查询线索信息
        Clue clue = clueMapper.selectClueById(clueId);
        // 把该线索中有关公司的信息转换到客户表中
        Customer customer = new Customer();
        customer.setId(UUIDUtils.getUUID());
        customer.setOwner(user.getId());
        customer.setName(clue.getCompany());
        customer.setWebsite(clue.getWebsite());
        customer.setPhone(clue.getPhone());
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.formatDateTime(new Date()));
        customer.setContactSummary(clue.getContactSummary());
        customer.setNextContactTime(clue.getNextContactTime());
        customer.setDescription(clue.getDescription());
        customer.setAddress(clue.getAddress());
        //新增用户//成功
        customerMapper.insertCustomer(customer);
        //把该线索中有关个人的信息转换到联系人表中
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtils.getUUID());
        contacts.setOwner(user.getId());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customer.getId());
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(DateUtils.formatDateTime(new Date()));
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());
        //新增联系人
        contactsMapper.insertContacts(contacts);

        // 根据clueId查询该线索下所有的备注
        List<ClueRemark> clueRemarkList = clueRemarkMapper.selectClueRemarkByClueId(clueId);
        // 如果线索备注非空，给客户和联系人添加该线索的所有备注
        if(clueRemarkList != null && clueRemarkList.size() > 0){
            // 遍历线索备注，封装客户备注和联系人备注
            CustomerRemark customerRemark = null;
            //可能会有多个线索备注，所以集合
            List<CustomerRemark> customerRemarkList = new ArrayList<>();

            ContactsRemark contactsRemark = null;
            List<ContactsRemark> contactsRemarkList = new ArrayList<>();
            for(ClueRemark clueRemark : clueRemarkList){
                customerRemark = new CustomerRemark();
                customerRemark.setId(UUIDUtils.getUUID());
                customerRemark.setNoteContent(clueRemark.getNoteContent());
                customerRemark.setCreateBy(clueRemark.getCreateBy());
                customerRemark.setCreateTime(clueRemark.getCreateTime());
                customerRemark.setEditBy(clueRemark.getEditBy());
                customerRemark.setEditTime(clueRemark.getEditTime());
                customerRemark.setEditFlag(clueRemark.getEditFlag());
                customerRemark.setCustomerId(customer.getId());
                customerRemarkList.add(customerRemark);

                contactsRemark = new ContactsRemark();
                contactsRemark.setNoteContent(clueRemark.getNoteContent());
                contactsRemark.setId(UUIDUtils.getUUID());
                contactsRemark.setCreateBy(clueRemark.getCreateBy());
                contactsRemark.setCreateTime(clueRemark.getCreateTime());
                contactsRemark.setEditBy(clueRemark.getEditBy());
                contactsRemark.setEditTime(clueRemark.getEditTime());
                contactsRemark.setEditFlag(clueRemark.getEditFlag());
                contactsRemark.setContactsId(contacts.getId());
                contactsRemarkList.add(contactsRemark);
            }
            customerRemarkMapper.insertCustomerRemarkByList(customerRemarkList);
            contactsRemarkMapper.insertContactsRemarkByList(contactsRemarkList);
        }
        // 查询所有线索和市场活动关联关系
        List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationMapper.selectClueActivityRelationByClueId(clueId);
        // 遍历线索和市场活动关联关系集合，封装到联系人和市场活动关系中
        if(clueActivityRelationList != null && clueActivityRelationList.size() > 0){
            ContactsActivityRelation contactsActivityRelation = null;
            List<ContactsActivityRelation> contactsActivityRelationList = new ArrayList<>();

            for(ClueActivityRelation clueActivityRelation : clueActivityRelationList){
                contactsActivityRelation = new ContactsActivityRelation();
                //封装参数
                contactsActivityRelation.setId(UUIDUtils.getUUID());
                contactsActivityRelation.setContactsId(contacts.getId());
                contactsActivityRelation.setActivityId(clueActivityRelation.getActivityId());

                contactsActivityRelationList.add(contactsActivityRelation);
            }
            contactsActivityRelationMapper.insertContactsActivityRelationByList(contactsActivityRelationList);
        }

        //如果需要添加交易
        if("true".equals(isCreateTran)){
            Tran tran = new Tran();
            tran.setId(UUIDUtils.getUUID());
            tran.setOwner(user.getId());
            tran.setMoney((String) map.get("money"));
            tran.setName((String) map.get("name"));
            tran.setExpectedDate((String) map.get("expectedDate"));
            tran.setCustomerId(customer.getId());
            tran.setStage((String) map.get("stage"));
            tran.setActivityId((String) map.get("activityId"));
            tran.setContactsId(contacts.getId());
            tran.setCreateBy(user.getId());
            tran.setCreateTime(DateUtils.formatDateTime(new Date()));
            //添加一条记录
            tranMapper.insertTran(tran);
            // 把线索的备注信息转换到交易备注表中一份
            if(clueRemarkList != null && clueRemarkList.size() > 0){
                List<TranRemark> tranRemarkList = new ArrayList<>();
                TranRemark tranRemark = null;
                for(ClueRemark clueRemark : clueRemarkList){
                    tranRemark = new TranRemark();
                    tranRemark.setNoteContent(clueRemark.getNoteContent());
                    tranRemark.setCreateBy(clueRemark.getCreateBy());
                    tranRemark.setId(UUIDUtils.getUUID());
                    tranRemark.setCreateTime(clueRemark.getCreateTime());
                    tranRemark.setEditBy(clueRemark.getEditBy());
                    tranRemark.setEditTime(clueRemark.getEditTime());
                    tranRemark.setEditFlag(clueRemark.getEditFlag());
                    tranRemark.setTranId(tran.getId());
                    tranRemarkList.add(tranRemark);
                }
                tranRemarkMapper.insertTranRemarkByList(tranRemarkList);
            }
            //将线索id封装成数组，这样好删除
            String[] clueIds = {clueId};
            // 删除线索备注
            clueRemarkMapper.deleteClueRemarkByClueId(clueIds);
            // 删除线索市场活动关联
            clueActivityRelationMapper.deleteClueActivityRelationByClueIds(clueIds);
            // 删除线索
            clueMapper.deleteClueByIds(clueIds);
        }
    }
    @Override
    public List<String> queryClueStageOfClueGroupByClueStage() {
        return clueMapper.selectClueStageOfClueGroupByClueStage();
    }

    @Override
    public List<Integer> queryCountOfClueGroupByClueStage() {
        return clueMapper.selectCountOfClueGroupByClueStage();
    }

}
