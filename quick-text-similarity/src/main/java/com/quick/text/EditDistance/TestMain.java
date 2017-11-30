package com.quick.text.EditDistance;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/10/23
 * Time: 10:24
 * Description:
 */
public class TestMain {
    public static void main(String[] args) {
        String s = "BABY是个帅人", t = "BABY是个帅哥";
        int d = EditDistance.getEditDistance(s, t);
        System.out.println(d);
    }
}
