package com.hyf.juc.pattern.active;

/**
 * 接收异步消息的主动对象
 *
 * @author baB_hyf
 * @date 2020/12/20
 */
public interface ActiveObject {

    Result makeString(int count, char fillChar);

    void displayString(String text);
}
