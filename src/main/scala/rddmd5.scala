package com.destrys

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.Logging

import org.apache.spark.rdd.RDD
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.codec.digest.DigestUtils._
import org.apache.spark.HashPartitioner
import java.security.MessageDigest

class MD5Functions(rdd: RDD[String]) extends Logging with Serializable {

  def md5_partitioned(parts: Int = 100): String = {
    val partitioner = new HashPartitioner(parts) 
    val output = rdd.
               map(x => (x, 1)).
               repartitionAndSortWithinPartitions(partitioner).
               map(_._1).
               mapPartitions(x => Iterator(x.foldLeft(getMd5Digest())(md5))).
               map(x => new java.math.BigInteger(1, x.digest()).toString(16)).
               collect().
               sorted.
               foldLeft(getMd5Digest())(md5)
    val checksum = new java.math.BigInteger(1, output.digest()).toString(16)
    return(checksum)
  }

  def md5(prev: MessageDigest, in2: String): MessageDigest = {
    val b = in2.getBytes("UTF-8")
    prev.update(b, 0, b.length)
    prev
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
    text.md5_partitioned(10)

  }
}


