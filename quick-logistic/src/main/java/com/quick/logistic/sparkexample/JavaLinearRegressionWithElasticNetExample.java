package com.quick.logistic.sparkexample;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.regression.LinearRegression;
import org.apache.spark.ml.regression.LinearRegressionModel;
import org.apache.spark.ml.regression.LinearRegressionTrainingSummary;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class JavaLinearRegressionWithElasticNetExample {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("JavaLinearRegressionWithElasticNetExample").setMaster("local[3]");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(jsc);
        // $example on$
        // Load training data
        DataFrame training = sqlContext.read().format("libsvm")
                .load("data/mllib/sample_linear_regression_data.txt");
        LinearRegression lr = new LinearRegression()
                .setMaxIter(10)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);
        // Fit the model
        LinearRegressionModel lrModel = lr.fit(training);
        // Print the coefficients and intercept for linear regression
        System.out.println("Coefficients: "
                + lrModel.coefficients() + " Intercept: " + lrModel.intercept());
        // Summarize the model over the training set and print out some metrics
        LinearRegressionTrainingSummary trainingSummary = lrModel.summary();
        System.out.println("numIterations: " + trainingSummary.totalIterations());
        System.out.println("objectiveHistory: " + Vectors.dense(trainingSummary.objectiveHistory()));
        trainingSummary.residuals().show();
        System.out.println("RMSE: " + trainingSummary.rootMeanSquaredError());
        System.out.println("r2: " + trainingSummary.r2());
        // $example off$
        jsc.stop();
    }
}
