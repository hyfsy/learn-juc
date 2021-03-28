package com.hyf.juc.sample.future;

/**
 * @author baB_hyf
 * @date 2021/03/15
 */
public class Client {

    public static Data request(String payload) {
        FutureData futureData = new FutureData();
        new Thread(() -> {
            System.out.println("prepare data start");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            RealData realData = new RealData(payload);
            futureData.setRealData(realData);
            System.out.println("data prepare finish");
        }).start();

        return futureData;
    }
}
