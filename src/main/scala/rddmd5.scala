package com.destrys

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.Logging

import org.apache.spark.rdd.RDD


class MD5Functions(rdd: RDD[String]) extends Logging with Serializable {

  def md5_partitioned(parts: Int = 100): Unit = {
    val tots = rdd.sortBy(x => x,numPartitions = parts).mapPartitions(x => Iterator(x.foldLeft(0)(md5))).collect()
    tots.foreach(println)
  }

  def md5(prev: Int, in2: String): Int = {
    prev + in2.length
  }
}

object RddMD5  {
  /**
    * 
    * 
    * 
    */
  
  implicit def rddToMD5Functions(rdd: RDD[String]) = new MD5Functions(rdd)

  def main(args: Array[String]) {
    
    // Configuration
    val conf = new SparkConf().setAppName("CatExample")  // Creates empty SparkConf and sets the AppName
    val sc = new SparkContext(conf)                      // Creates a new SparkContext

    val text = sc.textFile(args(0))                      // Reads a textfile, in local-mode the default is file:///
                                                         // in yarn-mode, the default is hdfs:///
    text.md5_partitioned(3)

  }
}


