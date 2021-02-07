package com.hyf.juc.test.four;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public class Card {

    private Type   type;
    private String number;

    public static String getNumber(int number) {
        switch (number) {
            case 1:
                return "A";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            default:
                return String.valueOf(number);
        }
    }

    public Card(Type type, String number) {
        this.type = type;
        this.number = number;
    }

    @Override
    public String toString() {
        return "[" + type.getName() + number + ']';
    }
}
