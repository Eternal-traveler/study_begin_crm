package com.haoyang.commons.pojo;

/**
 * // 返回给前端的 后端响应信息的封装类，用于发送给前端转为json字符串
 * @author hao yang
 */
public class ReturnObject {

    /**
     * 处理成功获取失败的标记,1----成功，2-----失败
     */
    private String code;

    /**
     *     提示信息
     */
    private String message;

    /**
     * 其他信息
     */
    private Object returnData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getReturnData() {
        return returnData;
    }

    public void setReturnData(Object returnData) {
        this.returnData = returnData;
    }
}
