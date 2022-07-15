package com.hyf.juc.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 评测题目：
 * <p>
 * 基于生产者消费者多线程模式的分组排序取top生产者消费者都使用多线程模式，生产者不断产生以下分学课数据，由消费者处理数据
 * <p>
 * 输出处理每条数据时的当前课目的平均分、top3的学生名和分数。考虑有无穷无尽的大量数据生产出来。
 * <p>
 * 张1 数学 50
 * 张1 语文 70
 * 张1 英语 60
 * 张2 数学 20
 * 张2 语文 40
 * 张2 英语 20
 * 张3 数学 50
 * 张3 语文 60
 * 张3 英语 55
 * 张4 数学 22
 * 张4 语文 11
 * 张4 英语 70
 *
 * @author baB_hyf
 * @date 2022/03/14
 */
public class TestScore {

    private static final String[] subjects   = new String[]{"语文", "数学", "英语"};
    private static final String   namePrefix = "张";
    private static final int      maxTop     = 3;

    private static final BlockingQueue<StudentScore> queue = new LinkedBlockingQueue<>();

    private static final StatisticsRefresher statisticsRefresher;

    // 消费速率
    private static final long produceRate = 300L;

    static {
        List<Statistic> statistics = new ArrayList<>();
        for (String subject : subjects) {
            statistics.add(new Statistic(subject));
        }
        statisticsRefresher = new StatisticsRefresher(statistics);
    }

    public static void main(String[] args) {
        TestScore boLe = new TestScore();
        boLe.consumer();
        boLe.producer();
    }

    public void consumer() {
        new Thread(() -> {
            try {
                while (true) {

                    StudentScore studentScore = queue.take();
                    System.out.println("接收信息：" + studentScore);

                    statisticsRefresher.refresh(studentScore);
                    statisticsRefresher.print(studentScore);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void producer() {
        new Thread(() -> {
            try {
                while (true) {

                    queue.offer(StudentScore.create());

                    if (produceRate > 0) {
                        Thread.sleep(produceRate);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public interface Subject {
        String getSubjectType();
    }

    public static class StatisticsRefresher {

        private final List<Statistic> statistics;

        public StatisticsRefresher(List<Statistic> statistics) {
            this.statistics = statistics;
        }

        public void refresh(StudentScore studentScore) {
            getCurrentStatistic(studentScore).refresh(studentScore);
        }

        public void print(StudentScore studentScore) {
            System.out.println(getCurrentStatistic(studentScore).toString());
        }

        public void printAll() {
            for (Statistic statistic : statistics) {
                System.out.println(statistic.toString());
            }
        }

        private Statistic getCurrentStatistic(StudentScore studentScore) {
            for (Statistic statistic : statistics) {
                if (statistic.getSubjectType().equals(studentScore.getSubjectType())) {
                    return statistic;
                }
            }

            throw new IllegalArgumentException("Cannot find statistics: " + studentScore.getSubjectType());
        }
    }

    public static class Statistic implements Subject {

        private String                   subjectType;
        private long                     count = 0L;
        private double                   avg   = 0.0;
        private LinkedList<StudentScore> topK  = new LinkedList<>();

        public Statistic(String subjectType) {
            this.subjectType = subjectType;
        }

        public void refresh(StudentScore studentScore) {
            refreshCountAndAvg(studentScore);
            refreshTop(studentScore);
        }

        private void refreshCountAndAvg(StudentScore studentScore) {

            // TODO ?
            if (count >= Long.MAX_VALUE) {
                throw new IllegalStateException("long limit");
            }

            count++;
            avg = avg + (studentScore.getScore() - avg) / count;
        }

        private void refreshTop(StudentScore scopeInfo) {

            // 初始空的，直接插入
            if (topK.isEmpty()) {
                topK.add(scopeInfo);
            }
            else {

                // 比较插入
                boolean added = false;
                for (int i = 0; i < topK.size(); i++) {
                    StudentScore topScore = topK.get(i);
                    if (topScore.getScore() < scopeInfo.getScore()) {
                        topK.add(i, scopeInfo);
                        added = true;
                        break;
                    }
                }

                // 没添加 & 没达到top数量
                if (!added && topK.size() < maxTop) {
                    topK.add(topK.size(), scopeInfo);
                }
                // 添加成功的情况，删除多余的
                else {
                    while (topK.size() > maxTop) {
                        topK.pollLast();
                    }
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(subjectType).append("平均分：").append(avg);
            int topKCount = Math.min(maxTop, topK.size());
            for (int i = 0; i < topKCount; i++) {
                sb.append("\n\r\t").append(topK.get(i));
            }
            return sb.toString();
        }

        @Override
        public String getSubjectType() {
            return subjectType;
        }
    }

    public static class StudentScore implements Subject {

        private static final Random        scoreRandom = new Random();
        private static final AtomicInteger nameTermIdx = new AtomicInteger(1);
        private static final AtomicInteger nameIdx     = new AtomicInteger(1);
        private static final AtomicInteger subjectIdx  = new AtomicInteger(0);

        private String name;
        private String subjectType;
        private int    score;

        public StudentScore(String name, String subjectType, int score) {
            this.name = name;
            this.subjectType = subjectType;
            this.score = score;
        }

        public static StudentScore create() {
            return new StudentScore(generateName(), generateSubject(), generateScore());
        }

        private static String generateName() {
            boolean add = nameTermIdx.getAndIncrement() % subjects.length == 0;
            if (add) {
                return namePrefix + nameIdx.getAndIncrement();
            }
            else {
                return namePrefix + nameIdx.get();
            }
        }

        private static String generateSubject() {
            return subjects[subjectIdx.getAndIncrement() % subjects.length];
        }

        private static int generateScore() {
            return scoreRandom.nextInt(100);
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        @Override
        public String toString() {
            return getName() + " " + getSubjectType() + " " + getScore();
        }

        @Override
        public String getSubjectType() {
            return subjectType;
        }
    }
}
