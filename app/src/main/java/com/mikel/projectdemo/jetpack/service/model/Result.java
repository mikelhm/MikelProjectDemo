package com.mikel.projectdemo.jetpack.service.model;

/**
 * Created by mikeluo on 2019/3/17.
 */

/**
 * 字段的定义 需要遵循 Json串
 * 例子：
 *
 * {"code":200,
 * "message":"成功!",
 * result":{"title":"读张忠献公谥册感叹",
 *         "content":"恩视韩兼赵，名均献与忠。|礼中非不极，勋外若为同。|三圣无多学，千年仅一翁。|犹怜公议在，拼泪乞秋风。
 * "      ,"authors":"杨万里"}}
 * @param <Poetry>
 */
public class Result<Poetry> {
    private int code;
    private String message;
    private Poetry result;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Poetry getData() {
        return result;
    }
}
