package com.xiao.pharmacy.common.entity.api;

/**
 * @Author: xiaoxiongwen
 * @Date: 2023/09/14/17:12
 * @Description:
 */
public enum ResultCode {
    SUCCESS(200, "操作成功!", "操作成功!", "success", "common"),
    LOG_OUT(666, "登录状态已过期!", "登錄狀態已過期!", "Logon status has expired", "common"),
    FAILED(500, "操作失败!", "操作失敗!", "operation failure", "common"),

    ;
    private long code;
    private String message;
    private String hkMessage;
    private String enMessage;
    private String serverName;


    ResultCode(long code, String message, String hkMessage, String enMessage, String serverName) {
        this.code = code;
        this.message = message;
        this.hkMessage = hkMessage;
        this.enMessage = enMessage;
        this.serverName = serverName;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getHkMessage() {
        return hkMessage;
    }

    public String getServerName() {
        return serverName;
    }

    public String getEnMessage() {
        return enMessage;
    }

    public static ResultCode getResultCode(String resultCodeName) {
        try {
            return ResultCode.valueOf(resultCodeName);
        } catch (Exception e) {
            return null;
        }
    }
}
