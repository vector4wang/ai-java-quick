package com.quick.text.Test4Excel;


import com.quick.text.SimHash.SimHash;
import com.quick.text.Test4Excel.util.DateUtils;
import com.quick.text.Test4Excel.util.ExcelLogs;
import com.quick.text.Test4Excel.util.ExcelUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/10/23
 * Time: 11:21
 * Description:
 */
public class TestMain {
    private static SimHash simhash = new SimHash(4, 3);

    private final static double SOCIAL = 0.5;

    private final static double SOCIAL_NAME = 0.5;
    private final static double SOCIAL_BIRTHDAY = 0.2;
    private final static double SOCIAL_GENDER = 0.2;
    private final static double SOCIAL_HUKOU = 0.03;
    private final static double SOCIAL_DEGREE = 0.07;

    private final static double EDU = 0.25;
    private final static double EDU_SCHOOL = 0.5;
    private final static double EDU_MAJOR = 0.2;
    private final static double EDU_EDUSTART = 0.3;

    private final static double WORK = 0.25;
    private final static double WORK_COMPANY = 0.5;
    private final static double WORK_TITLE = 0.2;
    private final static double WORK_WORKSTART = 0.3;


    public static List<AnonymousModel> readExcel() throws Exception {

//        File f = new File("D:\\githubspace\\ai-java-quick\\quick-text-similarity\\src\\main\\resources\\test.xls");
        File f = new File("C:\\Users\\bd2\\Desktop\\yangben.xls");
        InputStream inputStream = new FileInputStream(f);
        ExcelLogs logs = new ExcelLogs();
        Collection<AnonymousModel> anonymousModelColls = ExcelUtil.importExcel(AnonymousModel.class, inputStream, "yyyy-MM-dd", logs, 0);

        List<AnonymousModel> anonymousModels = new ArrayList<>(anonymousModelColls);

        Map<String, List<AnonymousModel>> map = new HashMap<>();
        Map<String, Set<String>> keyMap = new HashMap<>();
        // 1、 根据人才id和姓名归类
        List<AnonymousModel> keyModelList = new ArrayList<>();
        for (AnonymousModel anonymousModel : anonymousModels) {
            String talentId = anonymousModel.getTalentId();
            String realName = anonymousModel.getRealName();

            Set<String> strings = keyMap.keySet();
            boolean isExist = false;
            String key = anonymousModel.getId();


            for (String item : strings) {
                Set<String> keyList = keyMap.get(item);

                if (keyList.contains(realName)) {
                    isExist = true;
                    key = item;
                    break;
                }

            }

            if (!isExist) {
                for (String item : strings) {
                    Set<String> keyList = keyMap.get(item);


                    if (keyList.contains(talentId)) {
                        isExist = true;
                        key = item;
                        break;
                    }
                }
            }

            if (isExist) {
                map.get(key).add(anonymousModel);
            } else {
                keyModelList.add(anonymousModel);
                List<AnonymousModel> anonymousModels1 = new ArrayList<>();
                anonymousModels1.add(anonymousModel);
                map.put(key, anonymousModels1);
                Set<String> keyListTmp = new HashSet<>();
                keyListTmp.add(talentId);
                keyListTmp.add(realName);
                keyMap.put(key, keyListTmp);
            }
        }


        // 将不同key里的相同姓名取出来


        // 样本结果
        LinkedList<AnonymousModelResult> anonymousModelResults = getAnonymousModelResults(map);
        export(anonymousModelResults, "匿名更新源原始特征规则得分结果");

        return null;
    }

    private static void export(LinkedList<AnonymousModelResult> result, String name) throws IOException {
        Map<String, String> map1 = new LinkedHashMap<>();
        map1.put("0", "key");
        map1.put("1", "talentId");
        map1.put("2", "id");
        map1.put("3", "realName");
        map1.put("4", "birthday");
        map1.put("5", "gender");
        map1.put("6", "huKou");
        map1.put("7", "degree");
        map1.put("8", "schoolName");
        map1.put("9", "majorName");
        map1.put("10", "eduStartDate");
        map1.put("11", "endDate");
        map1.put("12", "companyName");
        map1.put("13", "title");
        map1.put("14", "workStartDate");
        map1.put("15", "result");

        OutputStream out = new FileOutputStream(new File("C:\\Users\\bd2\\Desktop\\yangben_result.xls"));

        ExcelUtil.exportExcel(map1, result, out);
        out.close();
    }


    private static LinkedList<AnonymousModelResult> getAnonymousModelResults(Map<String, List<AnonymousModel>> map) {
        LinkedList<AnonymousModelResult> result = new LinkedList<>();
        Set<String> strings = map.keySet();

        for (String item : strings) {

            List<AnonymousModel> anonymousModelList = map.get(item);
            LinkedList<AnonymousModelResult> temp = new LinkedList<>();
            if (anonymousModelList.size() > 1) {

                // 选择合适的作为比对对象
                AnonymousModel originObj = anonymousModelList.get(0);
                for (AnonymousModel model : anonymousModelList) {
                    if (!StringUtils.isEmpty(model.getMajorName())
                            && !StringUtils.isEmpty(model.getCompanyName())
                            && !StringUtils.isEmpty(model.getRealName())
                            && !StringUtils.isEmpty(model.getSchoolName())
                            && !StringUtils.isEmpty(model.getTitle())
                            && model.getBirthday() != null
                            && model.getWorkStartDate() != null
                            && model.getEduStartDate() != null
                            && model.getGender() != 0
                            && model.getHuKou() != 0
                            && model.getDegree() != 0) {
                        originObj = model;
                        break;
                    }
                }
                AnonymousModelResult anonymousModelResult = new AnonymousModelResult(item, originObj);
                temp.add(anonymousModelResult);

                for (int i = 1; i < anonymousModelList.size(); i++) {
                    AnonymousModel anonymousModel = anonymousModelList.get(i);
                    temp.add(compare(item, originObj, anonymousModel)); //样本结果
//                    temp.add(getCompareScore(item, originObj, anonymousModel));
                }
                result.addAll(temp);
            }
        }
        return result;
    }

    private static AnonymousModelResult getCompareScore(String key, AnonymousModel originObj, AnonymousModel anonymousModel) {
        AnonymousModelResult result = new AnonymousModelResult(key, originObj);
        result.setTalentId(anonymousModel.getTalentId());
        result.setId(anonymousModel.getId());
        if (originObj.getRealName().equals(anonymousModel.getRealName())) {
            result.setRealName(1 * SOCIAL_NAME * SOCIAL + "");
        } else {
            if (getFirstName(originObj.getRealName()).equals(getFirstName(anonymousModel.getCompanyName()))) {
                result.setRealName(0.5 * SOCIAL_NAME * SOCIAL + "");
            } else {
                result.setRealName(0 * SOCIAL_NAME * SOCIAL + "");
            }
        }

        if (originObj.getBirthday() == null) {
            result.setBirthday(0 * SOCIAL * SOCIAL_BIRTHDAY + "");
        } else if (DateUtils.isEqualDate(originObj.getBirthday(), anonymousModel.getBirthday())) {
            result.setBirthday(1 * SOCIAL * SOCIAL_BIRTHDAY + "");
        } else {
            result.setBirthday(0 * SOCIAL * SOCIAL_BIRTHDAY + "");
        }

        if (originObj.getGender() == anonymousModel.getGender()) {
            result.setGender(1 * SOCIAL * SOCIAL_GENDER + "");
        } else {
            result.setGender(0 * SOCIAL * SOCIAL_GENDER + "");
        }

        if (originObj.getHuKou() == anonymousModel.getHuKou()) {
            result.setHuKou(1 * SOCIAL * SOCIAL_HUKOU + "");
        } else {
            result.setHuKou(0 * SOCIAL * SOCIAL_HUKOU + "");
        }

        if (originObj.getDegree() == anonymousModel.getDegree()) {
            result.setDegree(1 * SOCIAL * SOCIAL_DEGREE + "");
        } else {
            result.setDegree(0 * SOCIAL * SOCIAL_DEGREE + "");
        }

        if (StringUtils.isEmpty(originObj.getSchoolName())) {
            result.setSchoolName(0 * EDU * EDU_SCHOOL + "");
        } else if (StringUtils.isEmpty(anonymousModel.getSchoolName())) {
            result.setSchoolName(0 * EDU * EDU_SCHOOL + "");
        } else if (originObj.getSchoolName().toLowerCase().equals(anonymousModel.getSchoolName().toLowerCase())) {
            result.setSchoolName(1 * EDU * EDU_SCHOOL + "");
        } else {
            if (simHashDis(originObj.getSchoolName().toLowerCase(), anonymousModel.getSchoolName().toLowerCase()) < 16) {
                result.setSchoolName(0.5 * EDU * EDU_SCHOOL + "");
            } else {
                result.setSchoolName(1 * EDU * EDU_SCHOOL + "");
            }

        }

        if (StringUtils.isEmpty(originObj.getMajorName())) {
            result.setMajorName(0 * EDU * EDU_MAJOR + "");
        } else if (StringUtils.isEmpty(anonymousModel.getMajorName())) {
            result.setMajorName(0 * EDU * EDU_MAJOR + "");
        } else if (originObj.getMajorName().toLowerCase().equals(anonymousModel.getMajorName().toLowerCase())) {
            result.setMajorName(1 * EDU * EDU_MAJOR + "");
        } else {
            if (simHashDis(originObj.getMajorName().toLowerCase(), anonymousModel.getMajorName().toLowerCase()) < 16) {
                result.setMajorName(0.5 * EDU * EDU_MAJOR + "");
            } else {
                result.setMajorName(0 * EDU * EDU_MAJOR + "");
            }
        }

        if (originObj.getEduStartDate() == null) {
            result.setEduStartDate(0 * EDU * EDU_EDUSTART + "");
        } else if (DateUtils.isEqualDate(originObj.getEduStartDate(), anonymousModel.getEduStartDate())) {
            result.setEduStartDate(1 * EDU * EDU_EDUSTART + "");
        } else {
            result.setEduStartDate(0 * EDU * EDU_EDUSTART + "");
        }


        if (StringUtils.isEmpty(originObj.getCompanyName())) {
            result.setCompanyName(0 * WORK * WORK_COMPANY + "");
        } else if (StringUtils.isEmpty(anonymousModel.getCompanyName())) {
            result.setCompanyName(0 * WORK * WORK_COMPANY + "");
        } else if (originObj.getCompanyName().toLowerCase().equals(anonymousModel.getCompanyName().toLowerCase())) {
            result.setCompanyName(1 * WORK * WORK_COMPANY + "");
        } else {
            if (simHashDis(originObj.getCompanyName().toLowerCase(), anonymousModel.getCompanyName().toLowerCase()) < 16) {
                result.setCompanyName(0.5 * WORK * WORK_COMPANY + "");
            } else {
                result.setCompanyName(0 * WORK * WORK_COMPANY + "");
            }
        }

        if (StringUtils.isEmpty(originObj.getTitle())) {
            result.setTitle(0 * WORK * WORK_TITLE + "");
        } else if (StringUtils.isEmpty(anonymousModel.getTitle())) {
            result.setTitle(0 * WORK * WORK_TITLE + "");
        } else if (originObj.getTitle().toLowerCase().equals(anonymousModel.getTitle().toLowerCase())) {
            result.setTitle(1 * WORK * WORK_TITLE + "");
        } else {
            if (simHashDis(originObj.getTitle().toLowerCase(), anonymousModel.getTitle().toLowerCase()) < 16) {
                result.setTitle(0.5 * WORK * WORK_COMPANY + "");
            } else {
                result.setTitle(0 * WORK * WORK_COMPANY + "");
            }
        }

        if (originObj.getWorkStartDate() == null) {
            result.setWorkStartDate(0 * WORK * WORK_WORKSTART + "");
        } else if (DateUtils.isEqualDate(originObj.getWorkStartDate(), anonymousModel.getWorkStartDate())) {
            result.setWorkStartDate(1 * WORK * WORK_WORKSTART + "");
        } else {
            result.setWorkStartDate(0 * WORK * WORK_WORKSTART + "");
        }

        double score = Double.parseDouble(result.getRealName())
                + Double.parseDouble(result.getBirthday())
                + Double.parseDouble(result.getGender())
                + Double.parseDouble(result.getHuKou())
                + Double.parseDouble(result.getDegree())
                + Double.parseDouble(result.getSchoolName())
                + Double.parseDouble(result.getMajorName())
                + Double.parseDouble(result.getEduStartDate())
                + Double.parseDouble(result.getCompanyName())
                + Double.parseDouble(result.getTitle())
                + Double.parseDouble(result.getWorkStartDate());
        result.setResult(score + "");
        return result;
    }

    private static AnonymousModelResult compare(String key, AnonymousModel originObj, AnonymousModel anonymousModel) {
        AnonymousModelResult result = new AnonymousModelResult(key, originObj);
        result.setTalentId(anonymousModel.getTalentId());
        result.setId(anonymousModel.getId());
        if (originObj.getRealName().equals(anonymousModel.getRealName())) {
            result.setRealName(1 + "");
        } else {
            result.setRealName(simHashDis(originObj.getRealName(), anonymousModel.getRealName()) + "");
        }

        if (originObj.getBirthday() == null || DateUtils.diffYears(originObj.getBirthday()) > 40) {
            result.setBirthday(-1 + "");
        } else if (DateUtils.isEqualDate(originObj.getBirthday(), anonymousModel.getBirthday())) {
            result.setBirthday(1 + "");
        } else {
            result.setBirthday(0 + "");
        }

        if(originObj.getGender() == 0){
            result.setGender(-1 + "");
        }else if (originObj.getGender() == anonymousModel.getGender()) {
            result.setGender(1 + "");
        } else {
            result.setGender(0 + "");
        }

        if (originObj.getHuKou() == 0) {
            result.setHuKou(-1 + "");
        }else if (originObj.getHuKou() == anonymousModel.getHuKou()) {
            result.setHuKou(1 + "");
        } else {
            result.setHuKou(0 + "");
        }

        if (originObj.getDegree() == 0) {
            result.setDegree(-1 + "");
        }else if (originObj.getDegree() == anonymousModel.getDegree()) {
            result.setDegree(1 + "");
        } else {
            result.setDegree(0 + "");
        }

        if (StringUtils.isEmpty(originObj.getSchoolName())) {
            result.setSchoolName(-1 + "");
        } else if (StringUtils.isEmpty(anonymousModel.getSchoolName())) {
            result.setSchoolName(-1 + "");
        } else if (originObj.getSchoolName().toLowerCase().equals(anonymousModel.getSchoolName().toLowerCase())) {
            result.setSchoolName(1 + "");
        } else {
            result.setSchoolName(simHashDis(originObj.getSchoolName().toLowerCase(), anonymousModel.getSchoolName().toLowerCase()) + "");
        }

        if (StringUtils.isEmpty(originObj.getMajorName())) {
            result.setMajorName(-1 + "");
        } else if (StringUtils.isEmpty(anonymousModel.getMajorName())) {
            result.setMajorName(0 + "");
        } else if (originObj.getMajorName().toLowerCase().equals(anonymousModel.getMajorName().toLowerCase())) {
            result.setMajorName(1 + "");
        } else {
            result.setMajorName(simHashDis(originObj.getMajorName().toLowerCase(), anonymousModel.getMajorName().toLowerCase()) + "");
        }

        if (originObj.getEduStartDate() == null) {
            result.setEduStartDate(-1 + "");
        } else if (DateUtils.isEqualDate(originObj.getEduStartDate(), anonymousModel.getEduStartDate())) {
            result.setEduStartDate(1 + "");
        } else {
            result.setEduStartDate(0 + "");
        }

        if (originObj.getEndDate() == null) {
            result.setEndDate(-1 + "");
        } else if (DateUtils.isEqualDate(originObj.getEndDate(), anonymousModel.getEndDate())) {
            result.setEndDate(1 + "");
        } else {
            result.setEndDate(0 + "");
        }

        if (StringUtils.isEmpty(originObj.getCompanyName())) {
            result.setCompanyName(-1 + "");
        } else if (StringUtils.isEmpty(anonymousModel.getCompanyName())) {
            result.setCompanyName(-1 + "");
        } else if (originObj.getCompanyName().toLowerCase().equals(anonymousModel.getCompanyName().toLowerCase())) {
            result.setCompanyName(1 + "");
        } else {
            result.setCompanyName(simHashDis(originObj.getCompanyName().toLowerCase(), anonymousModel.getCompanyName().toLowerCase()) + "");
        }

        if (StringUtils.isEmpty(originObj.getTitle())) {
            result.setTitle(-1 + "");
        } else if (StringUtils.isEmpty(anonymousModel.getTitle())) {
            result.setTitle(0 + "");
        } else if (originObj.getTitle().toLowerCase().equals(anonymousModel.getTitle().toLowerCase())) {
            result.setTitle(1 + "");
        } else {
            result.setTitle(simHashDis(originObj.getTitle().toLowerCase(), anonymousModel.getTitle().toLowerCase()) + "");
        }

        if (originObj.getWorkStartDate() == null) {
            result.setWorkStartDate(-1 + "");
        } else if (DateUtils.isEqualDate(originObj.getWorkStartDate(), anonymousModel.getWorkStartDate())) {
            result.setWorkStartDate(1 + "");
        } else {
            result.setWorkStartDate(0 + "");
        }

        return result;


    }

    public static void main(String[] args) {
        try {
//            readExcel();

            matchResult();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void matchResult() throws IOException {
        File f = new File("C:\\Users\\bd2\\Desktop\\yangben_result.xls");
        InputStream inputStream = new FileInputStream(f);
        ExcelLogs logs = new ExcelLogs();
        Collection<AnonymousModelMatch> anonymousModelMatches = ExcelUtil.importExcel(AnonymousModelMatch.class, inputStream, "yyyy-MM-dd", logs, 0);
        List<AnonymousModelMatch> lists = new ArrayList<>(anonymousModelMatches);
        System.out.println(lists);
        Map<String, List<AnonymousModelMatch>> map = new HashMap<>();
        lists.forEach(item->{
            if (map.containsKey(item.getKey())) {
                map.get(item.getKey()).add(item);
            }else{
                List<AnonymousModelMatch> temp = new ArrayList<>();
                temp.add(item);
                map.put(item.getKey(), temp);
            }
        });
        Set<String> strings = map.keySet();
        List<AnonymousModelMatch> result = new ArrayList<>();
        for (String key : strings) {
            List<AnonymousModelMatch> modelMatches = map.get(key);
            AnonymousModelMatch orginObj = modelMatches.get(0);
            result.add(orginObj);
            for(int i=1;i<=modelMatches.size()-1;i++) {
                AnonymousModelMatch anonymousModelMatch = modelMatches.get(i);
                if (orginObj.getTalentId().equals(anonymousModelMatch.getTalentId())) {
                    anonymousModelMatch.setResult(1.0);
                } else {
                    anonymousModelMatch.setResult(0.0);
                }
                System.out.println(anonymousModelMatch);
                result.add(anonymousModelMatch);
            }
        }
        System.out.println(result.size());
        Map<String, String> map1 = new LinkedHashMap<>();
        map1.put("0", "key");
        map1.put("1", "talentId");
        map1.put("2", "id");
        map1.put("3", "realName");
        map1.put("4", "birthday");
        map1.put("5", "gender");
        map1.put("6", "huKou");
        map1.put("7", "degree");
        map1.put("8", "schoolName");
        map1.put("9", "majorName");
        map1.put("10", "eduStartDate");
        map1.put("11", "endDate");
        map1.put("12", "companyName");
        map1.put("13", "title");
        map1.put("14", "workStartDate");
        map1.put("15", "result");

        OutputStream out = new FileOutputStream(new File("C:\\Users\\bd2\\Desktop\\yangben_result_2.xls"));

        ExcelUtil.exportExcel(map1, result, out);
        out.close();

    }

    public static int simHashDis(String s1, String s2) {
        Long l1 = simhash.calSimhash(s1);
        Long l2 = simhash.calSimhash(s2);
        int hamming = simhash.hamming(l1, l2);
        return hamming;
    }

    public static String StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String   regEx  =  "[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static String getFirstName(String name) {
        if (StringUtils.isEmpty(name)) {
            return "";
        }
        String result = "";
        String[] Surname = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋", "沈", "韩", "杨", "朱", "秦", "尤", "许",
                "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜", "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎",
                "鲁", "韦", "昌", "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺", "倪", "汤", "滕", "殷",
                "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍", "余", "元", "卜", "顾", "孟", "平", "黄", "和",
                "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄", "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒",
                "屈", "项", "祝", "董", "梁", "杜", "阮", "蓝", "闵", "席", "季", "麻", "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭", "梅", "盛", "林", "刁", "钟",
                "徐", "邱", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝", "管", "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应",
                "宗", "丁", "宣", "贲", "邓", "郁", "单", "杭", "洪", "包", "诸", "左", "石", "崔", "吉", "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀",
                "羊", "于", "惠", "甄", "曲", "家", "封", "芮", "羿", "储", "靳", "汲", "邴", "糜", "松", "井", "段", "富", "巫", "乌", "焦", "巴", "弓", "牧", "隗", "山",
                "谷", "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲", "伊", "宫", "宁", "仇", "栾", "暴", "甘", "钭", "厉", "戎", "祖", "武", "符", "刘", "景",
                "詹", "束", "龙", "叶", "幸", "司", "韶", "郜", "黎", "蓟", "溥", "印", "宿", "白", "怀", "蒲", "邰", "从", "鄂", "索", "咸", "籍", "赖", "卓", "蔺", "屠",
                "蒙", "池", "乔", "阴", "郁", "胥", "能", "苍", "双", "闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申", "扶", "堵", "冉", "宰", "郦", "雍", "却",
                "璩", "桑", "桂", "濮", "牛", "寿", "通", "边", "扈", "燕", "冀", "浦", "尚", "农", "温", "别", "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹", "习",
                "宦", "艾", "鱼", "容", "向", "古", "易", "慎", "戈", "廖", "庾", "终", "暨", "居", "衡", "步", "都", "耿", "满", "弘", "匡", "国", "文", "寇", "广", "禄",
                "阙", "东", "欧", "殳", "沃", "利", "蔚", "越", "夔", "隆", "师", "巩", "厍", "聂", "晁", "勾", "敖", "融", "冷", "訾", "辛", "阚", "那", "简", "饶", "空",
                "曾", "毋", "沙", "乜", "养", "鞠", "须", "丰", "巢", "关", "蒯", "相", "查", "后", "荆", "红", "游", "郏", "竺", "权", "逯", "盖", "益", "桓", "公", "仉",
                "督", "岳", "帅", "缑", "亢", "况", "郈", "有", "琴", "归", "海", "晋", "楚", "闫", "法", "汝", "鄢", "涂", "钦", "商", "牟", "佘", "佴", "伯", "赏", "墨",
                "哈", "谯", "篁", "年", "爱", "阳", "佟", "言", "福", "南", "火", "铁", "迟", "漆", "官", "冼", "真", "展", "繁", "檀", "祭", "密", "敬", "揭", "舜", "楼",
                "疏", "冒", "浑", "挚", "胶", "随", "高", "皋", "原", "种", "练", "弥", "仓", "眭", "蹇", "覃", "阿", "门", "恽", "来", "綦", "召", "仪", "风", "介", "巨",
                "木", "京", "狐", "郇", "虎", "枚", "抗", "达", "杞", "苌", "折", "麦", "庆", "过", "竹", "端", "鲜", "皇", "亓", "老", "是", "秘", "畅", "邝", "还", "宾",
                "闾", "辜", "纵", "侴", "万俟", "司马", "上官", "欧阳", "夏侯", "诸葛", "闻人", "东方", "赫连", "皇甫", "羊舌", "尉迟", "公羊", "澹台", "公冶", "宗正",
                "濮阳", "淳于", "单于", "太叔", "申屠", "公孙", "仲孙", "轩辕", "令狐", "钟离", "宇文", "长孙", "慕容", "鲜于", "闾丘", "司徒", "司空", "兀官", "司寇",
                "南门", "呼延", "子车", "颛孙", "端木", "巫马", "公西", "漆雕", "车正", "壤驷", "公良", "拓跋", "夹谷", "宰父", "谷梁", "段干", "百里", "东郭", "微生",
                "梁丘", "左丘", "东门", "西门", "南宫", "第五", "公仪", "公乘", "太史", "仲长", "叔孙", "屈突", "尔朱", "东乡", "相里", "胡母", "司城", "张廖", "雍门",
                "毋丘", "贺兰", "綦毋", "屋庐", "独孤", "南郭", "北宫", "王孙"};
        List<String> list = Arrays.asList(Surname);
        for (int i = 0; i < name.length(); i++) {
            if (list.contains(name.charAt(i))) {
                result = String.valueOf(name.charAt(i));
                break;
            }
        }

        return result;

    }
}