package com.haoyang.workbench.web.controller.Activity;

import com.haoyang.commons.constants.Constants;
import com.haoyang.commons.pojo.ReturnObject;
import com.haoyang.commons.utils.DateUtils;
import com.haoyang.commons.utils.HSSFUtils;
import com.haoyang.commons.utils.UUIDUtils;
import com.haoyang.settings.pojo.User;
import com.haoyang.settings.service.UserService;
import com.haoyang.workbench.pojo.Activity;
import com.haoyang.workbench.pojo.ActivityRemark;
import com.haoyang.workbench.service.activity.ActivityRemarkService;
import com.haoyang.workbench.service.activity.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author hao yang
 * @date 2022-08-02-16:38
 */

@Controller
@RequestMapping("/workbench/activity")
public class ActivityController {

    private final UserService userService;

    private final ActivityService activityService;

    private final ActivityRemarkService activityRemarkService;

    public ActivityController(UserService userService, ActivityService activityService, ActivityRemarkService activityRemarkService) {
        this.userService = userService;
        this.activityService = activityService;
        this.activityRemarkService = activityRemarkService;
    }


    /**
     * 显示市场活动界面
     * @param request 存放查询用户的作用域
     * @return 市场活动界面
     */
    @RequestMapping("/index.do")
    public String index(HttpServletRequest request){
        //调用servlet方法，来查询所有用户
        List<User> userList = userService.queryAllUsers();
        //把数据保存request中
        request.setAttribute("userList", userList);
        //请求转发到市场活动的主页面
        return "/workbench/activity/index";
    }

    /**
     * 添加市场活动
     * @param activity
     * @param session
     * @return
     */
    @RequestMapping("/saveCreateActivity.do")
    public @ResponseBody Object saveCreateActivity(Activity activity, HttpSession session){
        //获取存放在session域中的User对象
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        // 从前端只传来六个参数，实际需要九个参数，封装需要的参数
        //主键id，自动创建主键id
        activity.setId(UUIDUtils.getUUID());
        //创建时间
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        //用户id（外键，一个用户可以创建多个市场活动）
        activity.setCreateBy(user.getId());
//        返回给前端的 后端响应信息的封装类
        ReturnObject returnObject =  new ReturnObject();
        // 注意：查找一般不会出问题，但是增删改有可能会出问题，所以需要一个异常捕获机制，及时捕获异常
        try {
            // 保存创建的市场活动
            int res = activityService.saveCreateActivity(activity);
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
     * @param name 名称
     * @param owner 所有者
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param pageNo 当前页码
     * @param pageSize 分页大小（每页数据量）
     * @return 封装查询的参数
     */
    @RequestMapping("/queryActivityByConditionForPage.do")
    public @ResponseBody Object queryActivityByConditionForPage(String name , String owner , String startDate , String endDate ,
                                                  int pageNo , int pageSize){
        //封装前端查询到的数据
        Map<String , Object> map = new HashMap<>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        // 分页计算起始数据的位置，从0页开始所以-1
        map.put("beginNo", (pageNo-1) * pageSize);
        map.put("pageSize", pageSize);
        //根据前端传来的数据进行查询
        // 查询当前分页要显示数据集合
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        //查询到的总条数
        int totalRows = activityService.queryCountOfActivityByCondition(map);
        //进行封装参数，传回给前端
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("activityList", activityList);
        resultMap.put("totalRows", totalRows);
        return resultMap;
    }

    /**
     * 删除市场活动把执行信息响应到前端
     * @param id 删除的市场活动id数组
     * @return 封装的查询参数
     */
    @RequestMapping("/deleteActivityByIds.do")
    public @ResponseBody Object deleteActivityByIds(String[] id){
        ReturnObject returnObject = new ReturnObject();
        //当系统删除成功，返回响应信息
        try {
            activityService.deleteActivity(id);
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            //抛出异常，返回错误信息
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后再试....");
        }
        return returnObject;
    }

    /**
     *通过id查询市场活动
     * @return 市场活动id
     */
    @RequestMapping("/queryActivityById.do")
    public @ResponseBody Object queryActivityById(String id){
        return activityService.queryActivityById(id);
    }

    /**
     * 修改市场活动
     * @param activity 要修改的市场活动
     * @return 修改的条数
     */
    @RequestMapping("/saveEditActivity.do")
    public @ResponseBody Object saveEditActivity(Activity activity,HttpSession session){
        User user = (User) session.getAttribute(Constants.SESSION_USER);

        //返回当前修改时间
        activity.setEditTime(DateUtils.formatDateTime(new Date()));
        //返回修改人的id
        activity.setEditBy(user.getId());
        ReturnObject returnObject = new ReturnObject();
        try{
            int res = activityService.saveEditActivity(activity);
            if(res > 0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统繁忙，请稍后再试....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统繁忙，请稍后再试....");
        }
        return returnObject;
    }


    //这个效率非常低，从内存写入磁盘，又从磁盘写入内存
//    @RequestMapping("/fileDownload.do")
//    public void fileDownload(HttpServletResponse response)throws Exception {
//        //1.设置响应类型
//        response.setContentType("application/octet-stream;charset=UTF-8");
//        //2.获取输入流
//        OutputStream out = response.getOutputStream();
//
//        //浏览器接收到响应信息之后，默认情况下，直接在显示窗口中打开响应信息，即使打不开，也会调用应用程序打开；只有实在打不开，才会激活文件下载窗口
//        //可以设置响应头文件，使浏览器接收到响应信息之后，直接激活文件下载窗口，即使能打开也不打开
//        response.addHeader("Content-Disposition", "attachment;filename=");
//
//        //3.读取excel文件（InputStream），把输入到浏览器（OutoutStream）
//        InputStream is = new FileInputStream("");
//        byte[] buff = new byte[256];
//        int len = 0;
//        while((len=is.read(buff)) != -1){
//            out.write(buff,0,len);
//        }
//        //关闭资源:原则上谁创建的谁关
//        is.close();
//        out.flush();
//    }


    @RequestMapping("/exportAllActivity.do")
    public @ResponseBody void exportAllActivity(HttpServletResponse response) throws Exception {
        //调用service层方法，查询所有的市场活动
        List<Activity> activityList = activityService.queryAllActivity();
        //创建exel文件，并且把activityList写入到excel文件中
        HSSFUtils.createExcelByActivityList(activityList, Constants.FILE_NAME_ACTIVITY, response);
    }


    @RequestMapping("/exportCheckedActivity.do")
    public @ResponseBody void exportCheckedActivity(String[] id,HttpServletResponse response) throws Exception {
        //这里已经调用了service方法，获取了所有市场活动集合
        List<Activity> activities = activityService.queryCheckedActivity(id);
        HSSFUtils.createExcelByActivityList(activities, Constants.FILE_NAME_ACTIVITY, response);
    }


//    @RequestMapping("/importActivity.do")
//    public @ResponseBody Object importActivity(@RequestParam("fileName") MultipartFile file, HttpSession session){
//        User user = (User) session.getAttribute(Constants.SESSION_USER);
//        ReturnObject returnObject = new ReturnObject();
//        try {
//            InputStream is = file.getInputStream();
//            HSSFWorkbook wb = new HSSFWorkbook(is);
//            // 根据wb获取HSSFSheet对象，封装了一页的所有信息
//            // 页的下标，下标从0开始，依次增加
//            HSSFSheet sheet = wb.getSheetAt(0);
//            // 根据sheet获取HSSFRow对象，封装了一行的所有信息
//            HSSFRow row = null;
//            HSSFCell cell = null;
//            Activity activity = null;
//            List<Activity> activityList = new ArrayList<>();
//            // sheet.getLastRowNum()：最后一行的下标
//            for(int i = 1; i <= sheet.getLastRowNum(); i++) {
//                // 行的下标，下标从0开始，依次增加
//                row = sheet.getRow(i);
//                activity = new Activity();
//                // 补充部分参数
//                activity.setId(UUIDUtils.getUUID());
//                activity.setOwner(user.getId());
//                activity.setCreateTime(DateUtils.formatDateTime(new Date()));
//                activity.setCreateBy(user.getId());
//                // row.getLastCellNum():最后一列的下标+1
//                for(int j = 0; j < row.getLastCellNum(); j++) {
//                    // 根据row获取HSSFCell对象，封装了一列的所有信息
//                    // 列的下标，下标从0开始，依次增加
//                    cell=row.getCell(j);
//                    // 获取列中的数据
//                    String cellValue = HSSFUtils.getCellValueForStr(cell.getCellType());
//                    if(j == 0) {
//                        activity.setName(cellValue);
//                    } else if(j == 1){
//                        activity.setStartDate(cellValue);
//                    } else if(j == 2){
//                        activity.setEndDate(cellValue);
//                    } else if(j == 3){
//                        activity.setCost(cellValue);
//                    } else if(j == 4){
//                        activity.setDescription(cellValue);
//                    }
//                }
//                //每一行中所有列都封装完成之后，把activity保存到list中
//                activityList.add(activity);
//            }
//            // 调用service层方法，保存市场活动
//            int res = activityService.saveActivityByList(activityList);
//            returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
//            returnObject.setReturnData(res);
//        } catch (Exception e){
//            e.printStackTrace();
//            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
//            returnObject.setMessage("系统繁忙，请稍后重试...");
//        }
//        return returnObject;
//    }


    @RequestMapping("/detailActivity.do")
    public String detailActivity(String id,HttpServletRequest request){
        //根据id查找对应的市场活动明细
        Activity activity = activityService.queryActivityForDetailById(id);
        //对应id的市场活动备注
        List<ActivityRemark> activityRemarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);

        request.setAttribute("activity", activity);
        request.setAttribute("activityRemarkList", activityRemarkList);

        return "workbench/activity/detail";
    }

}
