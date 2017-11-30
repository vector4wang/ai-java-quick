package com.quick.text.SimHash;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/10/23
 * Time: 10:20
 * Description:
 */
public class TestMain {
    public static void main(String[] args) {
        SimHash simhash = new SimHash(4, 3);

        Long l1 = simhash.calSimhash("我是一中国人");
        Long l2 = simhash.calSimhash("你妈妈是一个美国人");
        int hamming = simhash.hamming(l1, l2);
        System.out.println(hamming);
    }
}
