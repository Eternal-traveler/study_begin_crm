package com.haoyang.workbench.web.controller.Chart;

import com.haoyang.workbench.pojo.FunnelVO;
import com.haoyang.workbench.service.activity.ActivityService;
import com.haoyang.workbench.service.clue.ClueService;
import com.haoyang.workbench.service.contacts.ContactsService;
import com.haoyang.workbench.service.tran.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hao yang
 * @date 2022-08-11-20:29
 */

@Controller
public class ChartController {
    private final TranService tranService;

    private final ClueService clueService;

    private final ActivityService activityService;

    private final ContactsService contactsService;

    public ChartController(TranService tranService, ClueService clueService, ActivityService activityService, ContactsService contactsService) {
        this.tranService = tranService;
        this.clueService = clueService;
        this.activityService = activityService;
        this.contactsService = contactsService;
    }

    @RequestMapping("/workbench/chart/transaction/index.do")
    public String toTranIndex(){
        return "workbench/chart/transaction/index";
    }

    @RequestMapping("/workbench/chart/transaction/queryCountOfTranGroupByStage.do")
    @ResponseBody
    public Object queryCountOfTranGroupByStage(){
        List<FunnelVO> funnelVOList = tranService.queryCountOfTranGroupByStage();
        // 根据查询结果，返回响应信息
        return funnelVOList;
    }

    @RequestMapping("/workbench/chart/clue/index.do")
    public String toClueIndex(){
        return "workbench/chart/clue/index";
    }

    @RequestMapping("/workbench/chart/clue/queryCountOfClueGroupByStage.do")
    @ResponseBody
    public Object queryCountOfClueGroupByStage(){
        List<String> clueStage = clueService.queryClueStageOfClueGroupByClueStage();
        List<Integer> counts = clueService.queryCountOfClueGroupByClueStage();
        Map<String, Object> map = new HashMap<>();
        map.put("clueStage", clueStage);
        map.put("counts", counts);
        // 根据查询结果，返回响应信息
        return map;
    }

    @RequestMapping("/workbench/chart/activity/index.do")
    public String toActivityIndex(){
        return "workbench/chart/activity/index";
    }

    @RequestMapping("/workbench/chart/activity/queryCountOfActivityGroupByOwner.do")
    @ResponseBody
    public Object queryCountOfActivityGroupByOwner(){
        List<FunnelVO> funnelVOList = activityService.queryCountOfActivityGroupByOwner();
        // 根据查询结果，返回响应信息
        return funnelVOList;
    }

    @RequestMapping("/workbench/chart/customerAndContacts/index.do")
    public String toCustomerAndContactsIndex(){
        return "workbench/chart/customerAndContacts/index";
    }

    @RequestMapping("/workbench/chart/customerAndContacts/queryCountOfCustomerAndContactsGroupByCustomer.do")
    @ResponseBody
    public Object queryCountOfCustomerAndContactsGroupByCustomer(){
        List<FunnelVO> funnelVOList = contactsService.queryCountOfCustomerAndContactsGroupByCustomer();
        // 根据查询结果，返回响应信息
        return funnelVOList;
    }
}
