package com.hyf.juc.pattern.active;

/**
 * @author baB_hyf
 * @date 2020/12/24
 */
class ActiveObjectProxy implements ActiveObject {

    private final ScheduledThread scheduledThread;
    private final Servant         servant;

    public ActiveObjectProxy(ScheduledThread scheduledThread, Servant servant) {
        this.scheduledThread = scheduledThread;
        this.servant = servant;
    }

    @Override
    public Result makeString(int count, char fillChar) {
        FutureResult futureResult = new FutureResult();
        MakeStringRequest request = new MakeStringRequest(this.servant, futureResult, count, fillChar);
        scheduledThread.invoke(request);
        return futureResult;
    }

    @Override
    public void displayString(String text) {
        scheduledThread.invoke(new DisplayStringRequest(this.servant, text));
    }
}
