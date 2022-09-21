package com.haoyang.workbench.web.controller.Tran;

import com.haoyang.commons.constants.Constants;
import com.haoyang.commons.pojo.ReturnObject;
import com.haoyang.commons.utils.DateUtils;
import com.haoyang.commons.utils.UUIDUtils;
import com.haoyang.settings.pojo.DicValue;
import com.haoyang.settings.pojo.User;
import com.haoyang.settings.service.DicValueService;
import com.haoyang.settings.service.UserService;
import com.haoyang.workbench.pojo.*;
import com.haoyang.workbench.service.activity.ActivityService;
import com.haoyang.workbench.service.contacts.ContactsService;
import com.haoyang.workbench.service.customer.CustomersService;
import com.haoyang.workbench.service.tran.TranHistoryService;
import com.haoyang.workbench.service.tran.TranRemarkService;
import com.haoyang.workbench.service.tran.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author hao yang
 * @date 2022-08-11-20:31
 */
@Controller
@RequestMapping("/workbench/transaction")
public class TranController {

    private final UserService userService;

    private final TranService tranService;

    private final ActivityService activityService;

    private final ContactsService contactsService;

    private final CustomersService customerService;

    private final TranRemarkService tranRemarkService;

    private final TranHistoryService tranHistoryService;

    private final DicValueService dicValueService;

    public TranController(DicValueService dicValueService, UserService userService, TranService tranService, ActivityService activityService, ContactsService contactsService, CustomersService customersService, TranRemarkService tranRemarkService, TranHistoryService tranHistoryService) {
        this.dicValueService = dicValueService;
        this.userService = userService;
        this.tranService = tranService;
        this.activityService = activityService;
        this.contactsService = contactsService;
        this.customerService = customersService;
        this.tranRemarkService = tranRemarkService;
        this.tranHistoryService = tranHistoryService;
    }

    @RequestMapping("/index.do")
    public String index(HttpServletRequest request){
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("stageList", stageList);
        request.setAttribute("transactionTypeList", transactionTypeList);
        request.setAttribute("sourceList", sourceList);
        return "workbench/transaction/index";
    }

    /**
     * 分页查询操作
     * @param owner
     * @param name
     * @param customerId
     * @param stage
     * @param type
     * @param source
     * @param contactsId
     * @param pageNo
     * @param pageSize
     * @return 总页数以及数据
     */
    @RequestMapping("/queryTransactionByConditionForPage.do")
    @ResponseBody
    public Object queryTransactionByConditionForPage(String owner, String name, String customerId, String stage, String type,
                                                     String source, String contactsId, int pageNo, int pageSize) {
        // 封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("owner", owner);
        map.put("name", name);
        map.put("customerId", customerId);
        map.put("source", source);
        map.put("stage", stage);
        map.put("type", type);
        map.put("contactsId", contactsId);
        map.put("beginNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        // 查询
        List<Tran> tranList = tranService.queryTransactionByConditionForPage(map);
        int totalRows = tranService.queryCountOfTransactionByCondition(map);
        // 封装查询参数，传给前端操作
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("tranList", tranList);
        resultMap.put("totalRows", totalRows);
        return resultMap;
    }

    /**
     * 跳转到创建保存页面，传输参数
     * @param request request
     * @return 跳转
     */
    @RequestMapping("/toSavePage.do")
    public String toSavePage(HttpServletRequest request) {
        List<User> userList = userService.queryAllUsers();
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("userList",userList);
        request.setAttribute("stageList",stageList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        return "workbench/transaction/save";
    }

    /**
     * 获取市场活动源
     * @param activityName 市场活动名称
     * @return 市场活动集合
     */
    @RequestMapping("/queryActivityByFuzzyName.do")
    @ResponseBody
    public Object queryActivityByFuzzyName(String activityName) {
        List<Activity> activityList = activityService.queryActivityByFuzzyName(activityName);
        return activityList;
    }

    /**
     * 获取联系人名称
     * @param contactsName 联系人名称
     * @return 联系人集合
     */
    @RequestMapping("/queryContactsByFuzzyName.do")
    @ResponseBody
    public Object queryContactsByFuzzyName(String contactsName) {
        List<Contacts> contactsList = contactsService.queryContactsByFuzzyName(contactsName);
        return contactsList;
    }

    /**
     * 有问题
     * @param stageValue
     * @return
     * @throws UnsupportedEncodingException
     */
//    @RequestMapping("/getPossibilityByStage.do")
//    @ResponseBody
//    public Object getPossibilityByStage(String stageValue) throws UnsupportedEncodingException {
//        // 解析properties配置文件，根据阶段获取可能性
//        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
//        // 返回响应信息
//        return new String(bundle.getString("stageValue").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
//    }


    /**
     * 模糊查询客户全名
     * @param customerName
     * @return
     */
    @RequestMapping("/queryCustomerNameByFuzzyName.do")
    @ResponseBody
    public Object queryCustomerNameByFuzzyName(String customerName) {
        List<String> customerNames = customerService.queryCustomerNameByFuzzyName(customerName);
        return customerNames;
    }

    /**
     * 保存创建的交易信息
     * @param tran 交易信息
     * @param session session
     * @return 保存是否成功
     */
    @RequestMapping("/saveCreateTransaction.do")
    @ResponseBody
    public Object saveCreateTransaction(Tran tran, HttpSession session) {
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        // 封装参数
        tran.setId(UUIDUtils.getUUID());
        tran.setCreateTime(DateUtils.formatDateTime(new Date()));
        tran.setCreateBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            tranService.saveCreateTransaction(tran);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }

    @RequestMapping("/toEditPage.do")
    public String toEditPage(String id, HttpServletRequest request) {
        List<User> userList = userService.queryAllUsers();
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicValueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");
        Tran tran = tranService.queryTransactionById(id);
        // 解析properties配置文件，根据阶段获取可能性
//        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
//        String possibility = bundle.getString(dicValueService.queryDicValueById(tran.getStage()).getValue());
        // 存入request域中
        request.setAttribute("userList",userList);
        request.setAttribute("stageList",stageList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("tran", tran);
        request.setAttribute("possibility", null);
        return "workbench/transaction/edit";
    }

    /**
     * 这里还有
     * 根据id删除交易
     *保存修改交易
     */


    @RequestMapping("/toDetailPage.do")
    public String toDetailPage(String id, HttpServletRequest request) {
        // 调用service层方法，查询数据
        Tran tran = tranService.queryTranForDetailById(id);
        List<TranRemark> remarkList = tranRemarkService.queryTranRemarkForDetailByTranId(id);
        List<TranHistory> historyList = tranHistoryService.queryTranHistoryForDetailByTranId(id);
        //根据tran所处阶段名称查询可能性
//        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
//        String possibility = bundle.getString(tran.getStage());
        // 获取当前阶段阶段的stageNo
        String stageOrderNo = dicValueService.queryDicValueById(tranService.queryTransactionById(id).getStage()).getOrderNo();
        //把数据保存到request中
        request.setAttribute("tran",tran);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("historyList",historyList);
        request.setAttribute("possibility",null);
        request.setAttribute("stageOrderNo", stageOrderNo);
        // 调用service方法，查询交易所有的阶段
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        request.setAttribute("stageList",stageList);
        //请求转发
        return "workbench/transaction/detail";
    }

}
