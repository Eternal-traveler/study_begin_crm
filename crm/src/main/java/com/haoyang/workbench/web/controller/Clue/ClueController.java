package com.haoyang.workbench.web.controller.Clue;

import com.haoyang.commons.constants.Constants;
import com.haoyang.commons.pojo.ReturnObject;
import com.haoyang.commons.utils.DateUtils;
import com.haoyang.commons.utils.UUIDUtils;
import com.haoyang.settings.pojo.DicValue;
import com.haoyang.settings.pojo.User;
import com.haoyang.settings.service.DicValueService;
import com.haoyang.settings.service.UserService;
import com.haoyang.workbench.pojo.Activity;
import com.haoyang.workbench.pojo.Clue;
import com.haoyang.workbench.pojo.ClueActivityRelation;
import com.haoyang.workbench.pojo.ClueRemark;
import com.haoyang.workbench.service.activity.ActivityService;
import com.haoyang.workbench.service.activityAndClue.ActivityAndClueService;
import com.haoyang.workbench.service.clue.ClueRemarkService;
import com.haoyang.workbench.service.clue.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.soap.Addressing;
import java.util.*;

/**
 * @author hao yang
 * @date 2022-08-08-15:23
 */

@Controller
@RequestMapping("/workbench/clue")
public class ClueController {

    private final UserService userService;

    private final DicValueService dicValueService;
    
    private final ClueService clueService;

    private final ClueRemarkService clueRemarkService;

    private final ActivityService activityService;

    private final ActivityAndClueService activityAndClueService;

    public ClueController(UserService userService, DicValueService dicValueService, ClueService clueService, ClueRemarkService clueRemarkService, ActivityService activityService, ActivityAndClueService activityAndClueService) {
        this.userService = userService;
        this.dicValueService = dicValueService;
        this.clueService = clueService;
        this.clueRemarkService = clueRemarkService;
        this.activityService = activityService;
        this.activityAndClueService = activityAndClueService;
    }


    /**
     * 跳转到线索页面
     * @param request 当前页面跳转
     * @return 跳转页面
     */
    @RequestMapping("/index.do")
    public String index(HttpServletRequest request){
        // 查询线索模块中所有下拉列表中的动态数据
        //调用servlet方法，来查询所有用户
        List<User> users = userService.queryAllUsers();
        //线索的名称
        List<DicValue> appellation = dicValueService.queryDicValueByTypeCode("appellation");
        //线索的状态
        List<DicValue> clueState = dicValueService.queryDicValueByTypeCode("clueState");
        //线索的资源
        List<DicValue> source = dicValueService.queryDicValueByTypeCode("source");

        //疯转到request域中
        request.setAttribute("userList", users);
        request.setAttribute("appellationList", appellation);
        request.setAttribute("clueStateList", clueState);
        request.setAttribute("sourceList", source);
        return "workbench/clue/index";
    }

    /**
     * 保存创建的线索
     * @param clue 前端传来的参数
     * @param session 当前页面session对象
     * @return 发送给前端解析信息
     */
    @RequestMapping("/saveCreateClue.do")
    public @ResponseBody Object saveCreateClue(Clue clue,HttpSession session){
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateTime(DateUtils.formatDateTime(new Date()));
        clue.setCreateBy(user.getId());
        ReturnObject returnObject =  new ReturnObject();
        // 注意：查找一般不会出问题，但是增删改有可能会出问题，所以需要一个异常捕获机制，及时捕获异常
        try {
            // 保存创建的市场活动
            int res = clueService.saveCreateClue(clue);
            if(res > 0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                // 保存失败，服务器端出了问题，为了不影响顾客体验，最好不要直接说出问题
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后再试....");
            }
        }catch (Exception e){
            //抛出异常，返回错误信息
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后再试....");
        }
        return returnObject;
    }

    /**
     * 分页查询市场活动数据，响应给前端json对象
     * @param fullname 姓名
     * @param company 公司
     * @param phone 公司号码
     * @param source 来源
     * @param owner 所有者
     * @param mphone 手机号
     * @param state 状态
     * @param pageNo 起始页
     * @param pageSize 每个页面显示条数
     * @return 发送给前端解析信息
     */
    @RequestMapping("queryClueByConditionForPage.do")
    public @ResponseBody Object queryClueByConditionForPage(String fullname, String company, String phone, String source,
                                                            String owner, String mphone, String state, int pageNo, int pageSize){
        Map<String, Object> map = new HashMap<>();
        map.put("fullname",fullname);
        map.put("company", company);
        map.put("phone", phone);
        map.put("source",source);
        map.put("owner", owner);
        map.put("mphone", mphone);
        map.put("state", state);
        map.put("beginNo", (pageNo-1) * pageSize);
        map.put("pageSize", pageSize);
        List<Clue> clueList = clueService.queryClueByConditionForPage(map);
        //查询到的总条数
        int totalRows = clueService.queryCountOfClueByCondition(map);
        //进行封装参数，传回给前端
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("clueList", clueList);
        resultMap.put("totalRows", totalRows);
        return resultMap;
    }


    @RequestMapping("/deleteClueByIds.do")
    public @ResponseBody Object deleteClueByIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        try {
            clueService.deleteClue(id);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后....");
        }
        return returnObject;
    }


    /**
     * 通过id查询线索
     * @param id 线索id
     * @return 线索活动信息
     */
    @RequestMapping("/queryClueById.do")
    public @ResponseBody Object queryClueById(String id){
        return clueService.queryClueById(id);
    }

    /**
     * 保存编辑的线索
     * @param clue 前端传来的线索参数
     * @param session 当前页面session对象
     * @return 发送给前端解析信息
     */
    @RequestMapping("/saveEditClue.do")
    public @ResponseBody Object saveEditClue(Clue clue , HttpSession session){
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        clue.setEditTime(DateUtils.formatDateTime(new Date()));
        clue.setEditBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try {
            int res = clueService.saveEditClue(clue);
            if(res > 0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                // 保存失败，服务器端出了问题，为了不影响顾客体验，最好不要直接说出问题
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                System.out.println("nihaoa1");
                returnObject.setMessage("系统繁忙，请稍后再试....");
            }
        }catch (Exception e){
            //抛出异常，返回错误信息
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后再试....");
        }
        return returnObject;
    }

    /**
     * 跳转到线索详情界面
     * @param id 线索id
     * @param request 发送的请求
     * @return 跳转界面
     */
    @RequestMapping("/detailClue.do")
    public String detailClue(String id , HttpServletRequest request){
//        查询对应id线索的信息
        Clue clue = clueService.queryClueForDetailById(id);
        //查询对应id的线索备注
        List<ClueRemark> clueRemarkList = clueRemarkService.queryClueRemarkForDetailByClueId(id);
        //相关联的市场活动信息
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);
        request.setAttribute("clue", clue);
        request.setAttribute("clueRemarkList", clueRemarkList);
        request.setAttribute("activityList",activityList);
        return "/workbench/clue/detail";
    }

    /**
     * 在线索详情页面绑定市场活动中通过市场活动名模糊查询市场活动
     * @param activityName 市场活动名
     * @param clueId 当前线索id
     * @return 查询到的市场活动集合
     */
    @RequestMapping("/queryActivityForDetailByNameAndClueId.do")
    public @ResponseBody Object queryActivityForDetailByNameAndClueId(String activityName, String clueId) {
        // 封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        System.err.println(clueId);
        List<Activity> activityList = activityService.queryActivityForDetailByNameAndClueId(map);
        return activityList;
    }

    /**
     * 保存市场活动和线索的绑定
     * @param activityId 市场活动id数组
     * @param clueId 线索id
     * @return 发送给前端解析信息
     */
    @RequestMapping("/saveBind.do")
    public @ResponseBody Object saveBind(String[] activityId, String clueId){
        // 封装参数
        ClueActivityRelation clueActivityRelation = null;
        List<ClueActivityRelation> clueActivityRelationList = new ArrayList<>();
        for (String actId : activityId) {
            clueActivityRelation = new ClueActivityRelation();
            clueActivityRelation.setActivityId(actId);
            clueActivityRelation.setClueId(clueId);
            clueActivityRelation.setId(UUIDUtils.getUUID());
            clueActivityRelationList.add(clueActivityRelation);
        }
        ReturnObject returnObject = new ReturnObject();
        try {
            int res = activityAndClueService.saveCreateClueActivityRelationByList(clueActivityRelationList);
            if (res > 0) {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                // 保存成功后查询所有市场活动id对应的市场活动，用于动态响应到前台页面
                List<Activity> activityList = activityService.queryActivityForDetailByIds(activityId);
                returnObject.setReturnData(activityList);
            } else {
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后重试...");
            }
        } catch (Exception e) { // 发生了某些异常，捕获后返回信息
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后重试...");
        }
        return returnObject;
    }

    /**
     * 解除线索和市场活动的绑定
     * @param clueActivityRelation 关联数据
     * @return int
     */
    @RequestMapping("/saveUnbind.do")
    public @ResponseBody Object saveUnbind(ClueActivityRelation clueActivityRelation){

        ReturnObject returnObject = new ReturnObject();
        try{
            int res = activityAndClueService.deleteClueActivityRelationByClueIdAndActivityId(clueActivityRelation);
            if(res > 0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后....");
        }
        return returnObject;
    }

    /**
     * 跳转到转换界面
     * @param id 当前线索id
     * @param request 请求
     * @return 前端界面
     */
    @RequestMapping("/toConvert.do")
    public String toConvert(String id , HttpServletRequest request){
        //根据id查询到convert页面所需数据
        Clue clue = clueService.queryClueForDetailById(id);
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");

        request.setAttribute("stageList", stageList);
        request.setAttribute("clue", clue);
        return "/workbench/clue/convert";
    }

    /**
     * 模糊查询市场活动
     * @param clueId 当前线索id
     * @param activityName 模糊查询市场活动名
     * @return 查询到的市场活动集合
     */
    @RequestMapping("/queryActivityForConvertByNameAndClueId.do")
    public @ResponseBody Object queryActivityForConvertByNameAndClueId(String activityName, String clueId) {
        // 封装参数
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        System.err.println(clueId);
        List<Activity> activityList = activityService.queryActivityForConvertByNameAndClueId(map);
        return activityList;
    }

    /**
     * 给线索转换按钮添加控制，点击转换按钮，删除一些内容，返回添加另一些内容
     * @param clueId 当前线索id
     * @param money 当前市场活动money
     * @param name 当前联系人
     * @param expectedDate 预期时间
     * @param stage 当前状态
     * @param activityId 市场活动
     * @param isCreateTran 是否创建了交易
     * @param session session
     * @return 是否成功转换
     */
    @RequestMapping("/convertClue.do")
    public @ResponseBody Object convertClue(String clueId, String money, String name, String expectedDate, String stage, String activityId, String isCreateTran, HttpSession session){
        Map<String, Object> map = new HashMap<>();
        map.put("clueId", clueId);
        map.put("money", money);
        map.put("name", name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId", activityId);
        map.put("isCreateTran", isCreateTran);
        map.put(Constants.SESSION_USER, session.getAttribute(Constants.SESSION_USER));
        ReturnObject returnObject = new ReturnObject();
        try{
            clueService.saveConvertClue(map);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后....");
        }
        return returnObject;
    }
}
