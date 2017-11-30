package com.quick.text.apache;

import org.apache.commons.text.similarity.CosineDistance;
import org.apache.commons.text.similarity.JaccardSimilarity;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/10/23
 * Time: 10:44
 * Description:
 */
public class TestMain {
    public static void main(String[] args) {
        // 计算jaccard相似系数
        JaccardSimilarity jaccardSimilarity = new JaccardSimilarity();
        double jcdsimilary1 = jaccardSimilarity.apply("我是一个帅哥", "帅哥不是我");
        System.out.println("jjcdsimilary1: " + jcdsimilary1);
        double jcdsimilary2 = jaccardSimilarity.apply("this is an apple", "this is an app");
        System.out.println("jjcdsimilary2: " + jcdsimilary2);
//
//         计算余弦相似度
        CosineDistance cosineDistance = new CosineDistance();
        System.out.println(cosineDistance.apply("我是 一个 帅哥", "帅哥 不是 我"));
        System.out.println(cosineDistance.apply("AB", "AB"));


    }

    private static Double roundValue(final Double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
