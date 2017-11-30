package com.quick.logistic.service;

import org.apache.spark.api.java.JavaDoubleRDD;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import scala.Serializable;
import scala.Tuple2;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created with IDEA
 * User: vector
 * Data: 2017/10/25
 * Time: 11:23
 * Description:
 */
@Service
public class LogisticService implements Serializable {

    @Autowired
    private transient JavaSparkContext sc;

    @PostConstruct
    public void runLogistic() throws IOException {
        test1();
//        test2();
//        test3();
//        File file = ResourceUtils.getFile("classpath:resume.txt");
//        List<String> list = IOUtils.readLines(new FileInputStream(file));
//        for (String item : list) {
//            String[] split = item.split(",");
//            String[] split1 = split[0].split(" ");
//            for(int i=0;i<split1.length;i++) {
//
//                System.out.print(new BigDecimal(split1[i]));
//            }
//            System.out.println();
//
//        }
    }

    private void test3() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:resume.txt");
        JavaRDD<String> data = sc.textFile(file.getAbsolutePath());
        JavaRDD<LabeledPoint> parsedData = data.map(line -> {
            String[] parts = line.split(",");
            double[] ds = Arrays.stream(parts[0].split(" "))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            return new LabeledPoint(Double.parseDouble(parts[1]), Vectors.dense(ds));
        }).cache();

        int numIterations = 50; //迭代次数
        LinearRegressionModel linearRegressionModel = LinearRegressionWithSGD.train(parsedData.rdd(), numIterations);
        RidgeRegressionModel ridgeRegressionModel = RidgeRegressionWithSGD.train(parsedData.rdd(), numIterations);
        LassoModel lassoModel = LassoWithSGD.train(parsedData.rdd(), numIterations);

        print(parsedData, linearRegressionModel);
        print(parsedData, ridgeRegressionModel);
        print(parsedData, lassoModel);

        //预测一条新数据方法
        double[] d = new double[]{1, 1, 1, 0, 1, 29, 31, 0, 0, 25, 32, 0};
        Vector v = Vectors.dense(d);

        System.out.println(linearRegressionModel.predict(v));
        System.out.println(ridgeRegressionModel.predict(v));
        System.out.println(lassoModel.predict(v));
    }

    private void test2() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:lpsa.txt");
        JavaRDD<String> data = sc.textFile(file.getAbsolutePath());
        JavaRDD<LabeledPoint> parsedData = data.map(line -> {
            String[] parts = line.split(",");
            double[] ds = Arrays.stream(parts[1].split(" "))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            return new LabeledPoint(Double.parseDouble(parts[0]), Vectors.dense(ds));
        }).cache();

        int numIterations = 50; //迭代次数
        LinearRegressionModel linearRegressionModel = LinearRegressionWithSGD.train(parsedData.rdd(), numIterations);
        RidgeRegressionModel ridgeRegressionModel = RidgeRegressionWithSGD.train(parsedData.rdd(), numIterations);
        LassoModel lassoModel = LassoWithSGD.train(parsedData.rdd(), numIterations);

        print(parsedData, linearRegressionModel);
        print(parsedData, ridgeRegressionModel);
        print(parsedData, lassoModel);

        //预测一条新数据方法
        double[] d = new double[]{1.0, 1.0, 2.0, 1.0, 3.0, -1.0, 1.0, -2.0};
        Vector v = Vectors.dense(d);

        System.out.println(linearRegressionModel.predict(v));
        System.out.println(ridgeRegressionModel.predict(v));
        System.out.println(lassoModel.predict(v));


    }

    public static void print(JavaRDD<LabeledPoint> parsedData, GeneralizedLinearModel model) {
        JavaPairRDD<Double, Double> valuesAndPreds = parsedData.mapToPair(point -> {
            double prediction = model.predict(point.features()); //用模型预测训练数据
            return new Tuple2<>(point.label(), prediction);
        });

        Double MSE = valuesAndPreds.mapToDouble((Tuple2<Double, Double> t) -> Math.pow(t._1() - t._2(), 2)).mean(); //计算预测值与实际值差值的平方值的均值
        System.out.println(model.getClass().getName() + " training Mean Squared Error = " + MSE);
    }

    private void test1() {
        //        traningModel();

        // 加载模型
        LinearRegressionModel sameModel = LinearRegressionModel.load(sc.sc(), "myModelPath");
        double[] d = new double[]{1.0, 1.0, 2.0, 1.0, 3.0, -1.0, 1.0, -2.0};

        Vector v = Vectors.dense(d);
        System.out.println(sameModel.predict(v));
    }

    private void traningModel() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:lpsa.txt");
        JavaRDD<String> data = sc.textFile(file.getAbsolutePath());
        //一、整理数据：把每一行原始的数据(num1, num2 num3...)转换成LabeledPoint(label, features)
        JavaRDD<LabeledPoint> parseData = data.filter(s -> {
            if (s.length() > 3) {
                return true;
            } else {
                return null;
            }
        }).map(line -> {
            String[] parts = line.split(",");
            String[] features = parts[1].split(" ");
            double[] v = new double[features.length];
            for (int i = 0; i < features.length - 1; i++) {
                v[i] = Double.parseDouble(features[i]);
            }
            return new LabeledPoint(Double.parseDouble(parts[0]), Vectors.dense(v));
        });
        parseData.cache();

        //迭代次数
        int numIterations = 100;

        //二、训练：用训练集和迭代次数为参进行模型训练
        final LinearRegressionModel model = LinearRegressionWithSGD.train(JavaRDD.toRDD(parseData), numIterations);

        //三、预测
        JavaRDD<Tuple2<Double, Double>> valuesAndPreds = parseData.map(point -> {
                    double prediction = model.predict(point.features());
                    return new Tuple2<>(prediction, point.label());
                }
        );


        //四、误差计算
        double MSE = new JavaDoubleRDD(valuesAndPreds.map(
                new Function<Tuple2<Double, Double>, Object>() {
                    @Override
                    public Object call(Tuple2<Double, Double> pair) {
                        return Math.pow(pair._1() - pair._2(), 2.0);
                    }
                }
        ).rdd()).mean();
        System.out.println("training MeanSquared Error = " + MSE);

        //五、保存
        model.save(sc.sc(), "myModelPath");
    }
}
