/*
 * 文件名: Test.java
 * 版权: Copyright 2015 中星天下 Tech. Co. Ltd. All Rights Reserved.
 */
package doudizhu;

import java.util.ArrayList;
import java.util.List;

/**
 * <一句话功能描述>
 * <p>
 * <功能详细描述>
 * <p>
 * @author wangshiyan
 * @version [<版本号>, 2016年12月5日]
 * @see [<相关类>/<相关方法>]
 * @since [<产品>/<模块版本>]
 */
public class Test {
    public static void main(String[] args) {
        List<Card> cardList = CardUtil.randomCard(1);
        Card card = null;
        System.out.println("开始发牌");
        List<Card> list1 = new ArrayList<Card>();
        List<Card> list2 = new ArrayList<Card>();
        List<Card> list3 = new ArrayList<Card>();
        int every_size = cardList.size() / 3;// 每人多少张牌
        for (int i = 1; i <= 3; i++) {
            System.out.println("第" + i + "人牌：");
            for (int j = i * every_size - every_size; j < i * every_size; j++) {
                card = cardList.get(j);
                if (1 == i) {
                    list1.add(card);
                } else if (2 == i) {
                    list2.add(card);
                } else if (3 == i) {
                    list3.add(card);
                }
                
                System.out.print(card.getValue() + card.getSign() + " , ");
            }
            System.out.println("\n");
        }
        // 排序
        CardUtil.sort(list1, list2, list3);
        System.out.println("排序后");
        // 第1人排序后
        for (Card card2 : list1) {
            System.out.print(card2.getValue() + card2.getSign() + " , ");
        }
        System.out.println();
        // 第2人排序后
        for (Card card2 : list2) {
            System.out.print(card2.getValue() + card2.getSign() + " , ");
        }
        System.out.println();
        // 第3人排序后
        for (Card card2 : list3) {
            System.out.print(card2.getValue() + card2.getSign() + " , ");
        }
        
    }
    
}
