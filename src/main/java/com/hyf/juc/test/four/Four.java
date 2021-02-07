package com.hyf.juc.test.four;

import java.util.Arrays;
import java.util.Random;

/**
 * 斗地主发牌
 * 3个人， 一副牌(54: 2大小鬼, 13方片，13梅花，13红桃，13黑桃)
 * 13方片: 1 - 10, J, Q, K
 * 13梅花: 1 - 10, J, Q, K
 * 13红桃: 1 - 10, J, Q, K
 * 13黑桃: 1 - 10, J, Q, K
 * 留3张底牌（随机)
 * 3个人随机拿牌(发牌)
 *
 * @author baB_hyf
 * @date 2020/12/29
 */
public class Four {

    public static void main(String[] args) {

        Card[] cards = getCard();
        Card[][] alloc = alloc(cards);
        print(alloc);

    }

    public static Card[] getCard() {


        // 初始化牌


        Card[] cards = new Card[54];
        Card[] diPai = new Card[3];
        int typeNum = 4;
        int cardNumber = 13;

        int cursor = 0;
        for (int i = 1; i <= typeNum; i++) {
            for (int j = 1; j <= cardNumber; j++) {
                cards[cursor++] = new Card(Type.getType(i), Card.getNumber(j));
            }
        }
        cards[cursor++] = new Card(Type.DA_KING, "");
        cards[cursor++] = new Card(Type.XIAO_KING, "");


        // 抽取三张底牌


        Random random = new Random();
        int succeed = 3;
        for (int i = 0; i < succeed; i++) {
            int ooo = random.nextInt(cards.length);
            if (cards[ooo] == null) {
                continue;
            }

            diPai[i] = cards[ooo];
            cards[ooo] = null;
        }
        System.out.println(Arrays.toString(diPai));


        // 重新排列牌


        Card[] remain = new Card[51];
        int remainCursor = 0;
        for (int i = 0; i < cards.length; i++) {
            Card card = cards[i];
            if (card != null) {
                remain[remainCursor++] = card;
            }
        }

        return remain;
    }

    public static Card[][] alloc(Card[] cards) {

        int peopleNum = 3;
        int sizeNum = cards.length / peopleNum;
        Card[][] allocation = new Card[sizeNum][peopleNum];

        Random random = new Random();

        int i = cards.length - 1;
        while (i >= 0) {
            int size = random.nextInt(sizeNum);
            int people = random.nextInt(peopleNum);
            Card card = allocation[size][people];
            if (card == null) {
                allocation[size][people] = cards[i--];
            }
        }
        return allocation;
    }

    public static void print(Card[][] cards) {
        for (int i = 0; i < 3; i++) {
            System.out.print("[");
            for (int j = 0; j < cards.length; j++) {
                System.out.print(cards[j][i]);
            }
            System.out.println("]");
        }
        int length = cards.length;
    }
}
