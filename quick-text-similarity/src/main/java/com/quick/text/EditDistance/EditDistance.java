package com.quick.text.EditDistance;

/**
 * 编辑距离(删除，添加，替换 得到相等字符串所需次数)算法
 * s = "eeba", t="abac"
 * 使用一个二维数组记录所需编辑次数(s为纵向，t为横向)，
 * 0 1 2 3 4
 * 1 1 2 3 4
 * 2 2 2 3 4
 * 3 3 2 3 4
 * 4 3 3 2 3
 * 第二列为当t取一个字符a的时候，s依次为  ""、"e"、"ee"、"eeb"、"eeba"所需的编辑距离
 * 其余的类似
 * 以动态规划角度来看，以edit(i,j)来代表矩阵中的元素，其意义为i代表s取前i个字符，j代表t取前个字符
 * 如edit(2,2)代表s="ee"，t="ab"的编辑距离，在矩阵中为2（代码中还有一个0行）
 * edit(i,j)=minist(edit(i,j-1)+1, edit(i-1,j)+1, edit(i-1,j-1)+cost)
 * edit(2,2)=minist(edit(2,1)+1, edit(1,2)+1, edit(1,1)+cost)
 * 即取("ee","a")("e","ab")("ee","ab")三个编辑距离中的最小值
 * s.charAt(i-1)==t.charAt(j-1)时，cost=1
 */
public class EditDistance {


    //返回三者最小值
    private static int Minimum(int a, int b, int c) {
        int im = a < b ? a : b;
        return im < c ? im : c;
    }

    public static int getEditDistance(String s, String t) {
        int d[][]; // matrix  
        int n; // length of s  
        int m; // length of t  
        int i; // iterates through s  
        int j; // iterates through t  
        char s_i; // ith character of s  
        char t_j; // jth character of t  
        int cost; // cost  

        // Step 1  
        n = s.length();
        m = t.length();
        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }
        d = new int[n + 1][m + 1];

        // Step 2  
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Step 3  
        for (i = 1; i <= n; i++) {
            s_i = s.charAt(i - 1);
            // Step 4  
            for (j = 1; j <= m; j++) {
                t_j = t.charAt(j - 1);
                // Step 5  
                cost = (s_i == t_j) ? 0 : 1;
                // Step 6  
                d[i][j] = Minimum(d[i - 1][j] + 1, d[i][j - 1] + 1,
                        d[i - 1][j - 1] + cost);
            }
        }
        // Step 7  
//        print(d, m, n);
        return d[n][m];
    }

    public static void print(int d[][], int m, int n) {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.println();
        }
    }
}  
