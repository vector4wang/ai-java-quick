package com.quick.logistic.sparkexample;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class JavaLogisticRegressionWithElasticNetExample {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("JavaLogisticRegressionWithElasticNetExample").setMaster("local[3]");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(jsc);
// $example on$
// Load training data
        DataFrame training = sqlContext.read().format("libsvm")
                .load("D:\\githubspace\\ai-java-quick\\quick-logistic\\src\\main\\resources\\sample_libsvm_data.txt");
        LogisticRegression lr = new LogisticRegression()
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);
// Fit the model
        LogisticRegressionModel lrModel = lr.fit(training);
// Print the coefficients and intercept for logistic regression
        System.out.println("Coefficients: "
                + lrModel.coefficients() + " Intercept: " + lrModel.intercept());
// $example off$
        jsc.stop();
    }
}
