package com.hyf.juc.pattern.active;

/**
 * @author baB_hyf
 * @date 2020/12/24
 */
public class RealResult implements Result {

    private final Object resultValue;

    public RealResult(Object resultValue) {
        this.resultValue = resultValue;
    }

    @Override
    public Object getResultValue() {
        return this.resultValue;
    }

    @Override
    public String toString() {
        return resultValue.toString();
    }
}
