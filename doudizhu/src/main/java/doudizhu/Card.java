/*
 * 文件名: Card.java
 * 版权: Copyright 2015 中星天下 Tech. Co. Ltd. All Rights Reserved.
 */
package doudizhu;

/**
 * 牌对象
 * @author wangshiyan
 * @version [<版本号>, 2016年12月2日]
 * @see [<相关类>/<相关方法>]
 * @since [<产品>/<模块版本>]
 */
public class Card {
    
    /**
     * 牌面花色1、2、3、4（分别代表：黑、红、梅、方）
     */
    private int type;
    
    /**
     * 牌值3-17代表11、12、13、14、15、16、17代表J、Q、K、A、2、小王、大王
     */
    private int value;
    
    /**
     * 黑红梅方：♠♥♣♦
     */
    private String sign;
    
    /**
     * 创建牌
     * @param type 花色 0没有花色
     * @param value 值
     */
    public Card(int type, int value) {
        this.type = type;
        this.value = value;
        this.sign = getHuase(type);
    }
    
    private String getHuase(int type) {
        String huase = "";
        switch (type)
            {
                case 1:
                    huase = "♠";
                    break;
                case 2:
                    huase = "♥";
                    break;
                case 3:
                    huase = "♣";
                    break;
                case 4:
                    huase = "♦";
                    break;
                
                default:
                    break;
            }
        return huase;
    }
    
    public String getSign() {
        return sign;
    }
    
    public void setSign(String sign) {
        this.sign = sign;
    }
    
    public int getType() {
        return type;
    }
    
    public void setType(int type) {
        this.type = type;
    }
    
    public int getValue() {
        return value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }
    
}
