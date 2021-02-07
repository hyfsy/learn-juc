package com.hyf.juc.test.four;

/**
 * @author baB_hyf
 * @date 2020/12/29
 */
public enum Type {
    FANG_KUAI("方块"),
    MEI_HUA("梅花"),
    HEI_TAO("黑桃"),
    HONG_XIN("红心"),
    DA_KING("大王"),
    XIAO_KING("小王"),
    ;

    private String name;

    private Type(String name) {
        this.name = name;
    }

    public static Type getType(int type) {
        switch (type) {
            case 1:
                return Type.FANG_KUAI;
            case 2:
                return Type.MEI_HUA;
            case 3:
                return Type.HEI_TAO;
            case 4:
                return Type.HONG_XIN;
            default:
                throw new IllegalArgumentException("类型无效：" + type);
        }
    }

    public String getName() {
        return name;
    }
}
