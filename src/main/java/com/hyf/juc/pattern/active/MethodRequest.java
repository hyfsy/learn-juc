package com.hyf.juc.pattern.active;

/**
 * 对应ActiveObject的每个方法
 *
 * @author baB_hyf
 * @date 2020/12/20
 */
public abstract class MethodRequest {

    protected final Servant      servant;
    protected final FutureResult futureResult;

    public MethodRequest(Servant servant, FutureResult futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }

    public abstract void execute();

}
