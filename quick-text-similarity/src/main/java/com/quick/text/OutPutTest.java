package com.quick.text;

import com.quick.text.CosineSimilarity.Similarity;
import com.quick.text.EditDistance.EditDistance;
import com.quick.text.SimHash.SimHash;
import com.quick.text.Test4Excel.util.ExcelCell;
import com.quick.text.Test4Excel.util.ExcelUtil;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/10/23
 * Time: 14:02
 * Description:
 */
public class OutPutTest {
    public static void main(String[] args) throws Exception {
//        String schoolNames[] = {"阿尔第斯(北京)建筑规划顾问有限公司", "阿迩贝司机电技术(上海)有限公司", "阿酷餐饮管理集团", "阿里巴巴(中国)软件有限公司", "阿里巴巴(中国)网络技术有限公司", "阿里巴巴(中国)有限公司", "阿里巴巴集团", "阿里巴巴集团客户事业部", "阿里巴巴网络技术有限公司", "阿斯利康无锡贸易有限公司", "阿特拉斯.科普柯(上海)贸易有限公司", "阿文美弛轻型车系统有限公司", "埃迈诺冠商贸(上海)公司", "埃迈诺冠商贸(上海)有限公司", "埃派克森微电子(上海)有限公司", "埃梯梯流体技术有限公司"};
//        List<String> schoolList = Arrays.asList(schoolNames);
//        String majorNames[] = {"表演", "宾馆管理", "搏击、散打、射击", "材料成型及控制工程", "材料成型与控制", "材料化学", "材料科学与工程", "材料科学与工程(计算机", "材料类 -- 高分子材料加工机械设计(含机械设计和模具设计", "财会专业", "财务", "财务管理", "财务管理 经济学类(经济学/财政金融/会计", "财务管理(会计方向", "财务会计", "财政金融", "财政学", "采购与供应管理", "采矿工程", "采矿学、矿山压力及其岩层控制、安全原理、岩石力学、矿井通风与安全、安全生产管理、井巷工程、电工学、液压传动与采掘机械、矿山企业管理、采矿工程英语、测量学、矿山机械设备", "测控技术与仪器", "测控技术与仪器[仪器仪表类]"};
//        List<String> majorList = Arrays.asList(majorNames);

//        String titles[] = {"iOS软件开发工程师", "iOS研发工程师", "IT工程师", "IT技术/研发总监", "IT技术支持", "IT技术支持/桌面运维", "IT区域经理", "IT维护", "IT项目经理/主管", "IT项目执行/协调人员/项目实施", "IT运维工程师", "IT支持", "java 程序员", "JAVA+英语", "javaee软件工程师", "javaGIS开发", "java程序员", "Java初级开发工程师", "JAVA高级工程师", "JAVA高级开发工程师", "java高级软件工程师", "java高级软件开发工程师"};
//
//        List<String> titleList = Arrays.asList(titles);

//        SimHash simhash = new SimHash(4, 3);
//        for(int i=0;i<titleList.size();i++) {
//            for(int j=i;j<titleList.size();j++) {
//                if (!titleList.get(i).equals(titleList.get(j))) {
////                    Long l1 = simhash.calSimhash(titleList.get(i));
////                    Long l2 = simhash.calSimhash(titleList.get(j));
////                    System.out.println(titleList.get(i) + "--->" + titleList.get(j) + " = " + simhash.hamming(l1,l2));
////
////                    System.out.println(titleList.get(i) + "--->" + titleList.get(j) + " = " + EditDistance.getEditDistance(titleList.get(i), titleList.get(j)));
////
//                    Similarity similarity = new Similarity(titleList.get(i), titleList.get(j));
//                    System.out.println(titleList.get(i) + "--->" + titleList.get(j) + " = " + similarity.sim());
//                }
//            }
//        }

        List<String> list = IOUtils.readLines(new FileInputStream(new File("D:\\company\\alicia\\company_to_compare.txt")));
        System.out.println("string1\tstring2\tSimHash\teditDistance\tCosSim");
        List<SimMulti> simMultis = new ArrayList<>();

        for (String item : list) {
            String[] split = item.split(" | ");
            System.out.print(split[0]);
            System.out.println(split[2]);
            SimHash simhash = new SimHash(4, 3);
            Long l1 = simhash.calSimhash(split[0]);
            Long l2 = simhash.calSimhash(split[2]);
            int hamming = simhash.hamming(l1, l2);
            int editDistance = EditDistance.getEditDistance(split[0], split[2]);
            Similarity similarity = new Similarity(split[0], split[2]);
            double cosSim = similarity.sim();
            SimMulti simMulti = new SimMulti();
            simMulti.setS1(split[0]);
            simMulti.setS2(split[2]);
            simMulti.setHaming(hamming);
            simMulti.setEditDis(editDistance);
            simMulti.setCosSim(cosSim);
            simMultis.add(simMulti);
        }

        Map<String,String> map1 = new LinkedHashMap<>();
        map1.put("0","字符串1");
        map1.put("1","字符串2");
        map1.put("2","SimHash");
        map1.put("3","编辑距离");
        map1.put("4","余弦距离");

        File f=new File("D:/test.xls");
        OutputStream out =new FileOutputStream(f);
        ExcelUtil.exportExcel(map1,simMultis , out);
        out.close();
    }
}
class SimMulti{
    @ExcelCell(index = 0)
    private String s1;
    @ExcelCell(index = 1)
    private String s2;
    @ExcelCell(index = 2)
    private int haming;
    @ExcelCell(index = 3)
    private int editDis;
    @ExcelCell(index = 4)
    private double cosSim;

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public int getHaming() {
        return haming;
    }

    public void setHaming(int haming) {
        this.haming = haming;
    }

    public int getEditDis() {
        return editDis;
    }

    public void setEditDis(int editDis) {
        this.editDis = editDis;
    }

    public double getCosSim() {
        return cosSim;
    }

    public void setCosSim(double cosSim) {
        this.cosSim = cosSim;
    }
}
