package com.quick.logistic.sparkexample;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.classification.LogisticRegressionModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import java.io.IOException;

public class JavaLogisticRegressionSummaryExample {
    public static void main(String[] args) throws IOException {
        SparkConf conf = new SparkConf().setAppName("JavaLogisticRegressionSummaryExample").setMaster("local[3]");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(jsc);
        // Load training data
        DataFrame training = sqlContext.read().format("libsvm")
                .load("D:\\githubspace\\ai-java-quick\\quick-logistic\\src\\main\\resources\\sample_libsvm_data.txt");
        LogisticRegression lr = new LogisticRegression()
                .setLabelCol("label")
                .setFeaturesCol("features")
                .setMaxIter(40)
                .setRegParam(0.3)
                .setElasticNetParam(0.8);
        // Fit the model
        LogisticRegressionModel lrModel = lr.fit(training);
        lrModel.save("logisticModel");

        Vector dense = Vectors.dense(0.236, 0.23);


        double predict = lrModel.predict(dense);
        System.out.println(dense);

        // $example on$
        // Extract the summary from the returned LogisticRegressionModel instance trained in the earlier
        // example

//        indexToString.transform();

//        LogisticRegressionTrainingSummary trainingSummary = lrModel.summary();
//        // Obtain the loss per iteration.
//        double[] objectiveHistory = trainingSummary.objectiveHistory();
//        for (double lossPerIteration : objectiveHistory) {
//            System.out.println(lossPerIteration);
//        }
//        // Obtain the metrics useful to judge performance on test data.
//        // We cast the summary to a BinaryLogisticRegressionSummary since the problem is a binary
//        // classification problem.
//        BinaryLogisticRegressionSummary binarySummary =
//                (BinaryLogisticRegressionSummary) trainingSummary;
//        // Obtain the receiver-operating characteristic as a dataframe and areaUnderROC.
//        DataFrame roc = binarySummary.roc();
//        roc.show();
//        roc.select("FPR").show();
//        System.out.println(binarySummary.areaUnderROC());
//        // Get the threshold corresponding to the maximum F-Measure and rerun LogisticRegression with
//        // this selected threshold.
//        DataFrame fMeasure = binarySummary.fMeasureByThreshold();
//        double maxFMeasure = fMeasure.select(functions.max("F-Measure")).head().getDouble(0);
//        double bestThreshold = fMeasure.where(fMeasure.col("F-Measure").equalTo(maxFMeasure))
//                .select("threshold").head().getDouble(0);
//        lrModel.setThreshold(bestThreshold);
//        // $example off$
//        jsc.stop();
    }
}
