package com.quick.text.CosineSimilarity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SimilarCos
{  
    /* 
     * 计算两个字符串的相似度（汉语的已经分好词的字符串），简单的余弦计算，未添权重 
     */  
     public static double getCosSimilar(String first, String second)  
     {  
        //创建向量空间模型，使用map实现，主键为词项，值为长度为2的数组，存放着对应词项在字符串中的出现次数  
         Map<String, int[]> vectorSpace = new HashMap<String, int[]>();
         int[] itemCountArray = null;//为了避免频繁产生局部变量，所以将itemCountArray声明在此  
           
         //以空格为分隔符，分解字符串  
         String strArray[] = first.split(" ");  
         for(int i=0; i<strArray.length; ++i)  
         {  
             if(vectorSpace.containsKey(strArray[i]))  
                 ++(vectorSpace.get(strArray[i])[0]);  
             else  
             {  
                 itemCountArray = new int[2];  
                 itemCountArray[0] = 1;  
                 itemCountArray[1] = 0;  
                 vectorSpace.put(strArray[i], itemCountArray);  
             }  
         }  
           
         strArray = second.split(" ");  
         for(int i=0; i<strArray.length; ++i)  
         {  
             if(vectorSpace.containsKey(strArray[i]))  
                 ++(vectorSpace.get(strArray[i])[1]);  
             else  
             {  
                 itemCountArray = new int[2];  
                 itemCountArray[0] = 0;  
                 itemCountArray[1] = 1;  
                 vectorSpace.put(strArray[i], itemCountArray);  
             }  
         }  
           
         //计算相似度  
         double vector1Modulo = 0.00;//向量1的模  
         double vector2Modulo = 0.00;//向量2的模  
         double vectorProduct = 0.00; //向量积  
         Iterator iter = vectorSpace.entrySet().iterator();
           
         while(iter.hasNext())  
         {  
             Map.Entry entry = (Map.Entry)iter.next();  
             itemCountArray = (int[])entry.getValue();  
               
             vector1Modulo += itemCountArray[0]*itemCountArray[0];  
             vector2Modulo += itemCountArray[1]*itemCountArray[1];  
               
             vectorProduct += itemCountArray[0]*itemCountArray[1];  
         }  
           
         vector1Modulo = Math.sqrt(vector1Modulo);  
         vector2Modulo = Math.sqrt(vector2Modulo);  
           
         //返回相似度  
        return (vectorProduct/(vector1Modulo*vector2Modulo));  
     }  
       
     /* 
      *  
      */  
     public static void main(String args[])  
     {  
//         String str1 = "a gold silver truck";
//         String str2 = "Shipment of gold damaged in a fire";
//         String str3 = "Delivery of silver arrived in a silver truck";
//         String str4 = "Shipment of gold arrived in a truck";
         String str5 = "我是 一个 帅哥";
         String str6 = "帅哥 是 我";
           
//         System.out.println(SimilarCos.getCosSimilar(str1, str2));
//         System.out.println(SimilarCos.getCosSimilar(str3, str4));
         System.out.println(SimilarCos.getCosSimilar(str5, str6));  
     }  
}