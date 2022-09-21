package com.haoyang.workbench.web.controller.Clue;

import com.haoyang.commons.constants.Constants;
import com.haoyang.commons.pojo.ReturnObject;
import com.haoyang.commons.utils.DateUtils;
import com.haoyang.commons.utils.UUIDUtils;
import com.haoyang.settings.pojo.User;
import com.haoyang.workbench.pojo.ClueRemark;
import com.haoyang.workbench.service.clue.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author hao yang
 * @date 2022-08-10-10:32
 */

@Controller
@RequestMapping("/workbench/clue")
public class ClueRemarkController {
    private final ClueRemarkService clueRemarkService;

    public ClueRemarkController(ClueRemarkService clueRemarkService) {
        this.clueRemarkService = clueRemarkService;
    }

    /**
     * 保存创建的线索备注
     * @param clueRemark 前端传来的线索备注参数
     * @param session 当前页面的的session对象
     * @return 响应到前端端的信息
     */
    @RequestMapping("/saveCreateClueRemark.do")
    public @ResponseBody Object saveCreateClueRemark(ClueRemark clueRemark , HttpSession session){
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        clueRemark.setId(UUIDUtils.getUUID());
        clueRemark.setCreateTime(DateUtils.formatDateTime(new Date()));
        clueRemark.setCreateBy(user.getId());
        clueRemark.setEditFlag(Constants.REMARK_EDIT_FLAG_FALSE);
        ReturnObject returnObject = new ReturnObject();
        try {
            int res = clueRemarkService.saveCreateClueRemark(clueRemark);
            if(res > 0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setReturnData(clueRemark);
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
     * 删除线索备注
     * @param id 线索备注的id
     * @return 响应到前端端的信息
     */
    @RequestMapping("/deleteClueRemarkById.do")
    public @ResponseBody Object deleteClueRemarkById(String id , ClueRemark clueRemark){
        ReturnObject returnObject = new ReturnObject();
        try {
            int res = clueRemarkService.deleteClueRemarkById(id);
            if (res > 0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setReturnData(clueRemark);
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

    /**
     * 保存编辑的线索备注
     * @param clueRemark 线索备注
     * @param session 当前页面的session对象
     * @return 响应到前端端的信息
     */
    @RequestMapping("/saveEditClueRemark.do")
    public @ResponseBody Object saveEditClueRemark(HttpSession session , ClueRemark clueRemark){
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        clueRemark.setEditBy(user.getId());
        clueRemark.setEditTime(DateUtils.formatDateTime(new Date()));
        clueRemark.setEditFlag(Constants.REMARK_EDIT_FLAG_TRUE);
        ReturnObject returnObject = new ReturnObject();
        try {
            int res = clueRemarkService.saveEditClueRemark(clueRemark);
            if (res > 0){
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setReturnData(clueRemark);
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
}
