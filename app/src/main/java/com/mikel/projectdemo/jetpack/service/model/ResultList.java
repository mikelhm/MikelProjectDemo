package com.mikel.projectdemo.jetpack.service.model;

import java.util.List;

/**
 * Created by mikeluo on 2019/3/17.
 * 字段的定义 需要遵循 Json串
 * 例子：
 *
 * {"code":200,
 * "message":"成功!",
 * "result":[{"title":"帝京篇十首 一","content":"秦川雄帝宅，函谷壮皇居。|绮殿千寻起，离宫百雉余。|连甍遥接汉，飞观迥凌虚。|云日隐层阙，风烟出绮疏。","authors":"太宗皇帝"},
 *          {"title":"帝京篇十首 二","content":"岩廊罢机务，崇文聊驻辇。|玉匣启龙图，金绳披凤篆。|韦编断仍续，缥帙舒还卷。|对此乃淹留，欹案观坟典。","authors":"太宗皇帝"},
 *          {"title":"帝京篇十首 三","content":"移步出词林，停舆欣武宴。|雕弓写明月，骏马疑流电。|惊雁落虚弦，啼猿悲急箭。|阅赏诚多美，于兹乃忘倦。","authors":"太宗皇帝"}]}
 */

public class ResultList<Poetry> {
    private int code;
    private String message;
    private List<Poetry> result;

    public List<Poetry> getData() {
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Poetry> getResult() {
        return result;
    }

    public void setResult(List<Poetry> result) {
        this.result = result;
    }
}
