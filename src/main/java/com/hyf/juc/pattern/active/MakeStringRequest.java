package com.hyf.juc.pattern.active;

/**
 * @author baB_hyf
 * @date 2020/12/24
 * @see ActiveObject#makeString(int count, char fillChar)
 */
public class MakeStringRequest extends MethodRequest {

    private final int  count;
    private final char fillChar;

    public MakeStringRequest(Servant servant, FutureResult futureResult, int count, char fillChar) {
        super(servant, futureResult);
        this.count = count;
        this.fillChar = fillChar;
    }

    @Override
    public void execute() {
        Result result = super.servant.makeString(count, fillChar);
        futureResult.setResult(result);
    }
}
