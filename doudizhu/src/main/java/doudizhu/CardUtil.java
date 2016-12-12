/*
 * 文件名: CardUtil.java
 * 版权: Copyright 2015 中星天下 Tech. Co. Ltd. All Rights Reserved.
 */
package doudizhu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 牌工具类
 * @author wangshiyan
 * @version [<版本号>, 2016年12月5日]
 * @see [<相关类>/<相关方法>]
 * @since [<产品>/<模块版本>]
 */
public class CardUtil {
    
    /**
     * 一副牌中数量
     */
    private static final int CARD_COUNT = 54;
    
    /**
     * 所有的牌
     */
    private static List<Card> listCards;
    
    /**
     * 初始化整副牌
     * @param size 几幅牌
     * @return
     */
    public static List<Card> initCard(int size) {
        listCards = new ArrayList<>(CARD_COUNT * size);
        for (int k = 0; k < size; k++) {
            // 初始化3-2的牌
            for (int i = 3; i <= 15; i++) {
                for (int j = 1; j < 5; j++) {
                    listCards.add(new Card(j, i));
                }
            }
            // 初始化大小王
            for (int i = 16; i <= 17; i++) {
                listCards.add(new Card(0, i));
            }
        }
        return listCards;
    }
    
    /**
     * 洗牌
     * @param size 几幅牌
     * @return
     */
    public static List<Card> randomCard(int size) {
        List<Card> cardList = initCard(size);// 初始化牌
        List<Card> newListCards = new ArrayList<Card>(CARD_COUNT * size);
        int index = 0;
        for (int i = 0; i < cardList.size();) {
            index = new Random().nextInt(cardList.size());
            newListCards.add(cardList.get(index));
            cardList.remove(index);
        }
        return newListCards;
        
    }
    
    /**
     * 排序已发放的牌
     * @param list 牌集合
     * @return
     */
    @SafeVarargs
    public static void sort(List<Card>... list) {
        for (List<Card> list2 : list) {
            Collections.sort(list2, new Comparator<Card>() {
                public int compare(Card c1, Card c2) {
                    if (c1.getValue() > c2.getValue()) {
                        return 1;
                    }
                    if (c1.getValue() == c2.getValue()) {
                        return 0;
                    }
                    return -1;
                }
            });
        }
        
    }
    
}
