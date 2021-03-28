package com.hyf.juc.sample.future;

/**
 * @author baB_hyf
 * @date 2021/03/15
 */
public class RealData implements Data {

    private String value;

    public RealData(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
