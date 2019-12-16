# Spark快速大数据分析

## 键值对操作

> 主要介绍键值对函数的使用和背后的原理

Pair Rdd<key, value>

### 转化

#### 聚合

***combineByKey(createCombiner, mergeValue, mergeCombiners, partitioner)***

```wiki
分为三步：
①在每个分区第一次遇到新的key时，会为这个key新建一个累加器（调用createCombiner）
②在相同的分区，第二次以后，会调用mergeValue函数，将统一分区相同key的value进行合并
③最后将不同分许的累加器进行合并，调用mergeCombiners
```

其他的聚合操作，如reduceByKey等都是在combineByKey的基础上进行优化扩展的

#### 分组

***groupByKey***

#### 连接

内连接（join），左连接（leftOuterJoin），右连接（rightOuterJoin）

#### 排序

***sortByKey()，通过ascending指定升序还是降序排序***

可以自定义排序

### 行动

***countByKey(), collectAsMap(), lookup(key)***

### 分区

**为什么要进行分区**

```wiki
减少计算，提高性能
举例：join操作
从一万个用户信息里筛选指定的top5活跃的用户信息，常规的操做是：top5用户id left join 一万个用户。这样的操作底层是每次都要对10000个用户和top5的用户ID计算hash值，这样很消耗性能。可以提前将10000个用户hash分区，每次将top5用户id和已经分好区的10000个用户匹配，这样避免每次计算10000个用户的hash值
```

**如何获取RDD的分区方式**

***rdd.partitioner***

**从分区中获益的操作**

- cogroup()
- groupWith()
- join() , leftOuterJoin(), rightOuterJoin()
- groupByKey()
- reduceByKey()
- combineByKey()
- lookup()

**怎样自定义分区**

- 继承org.apache.spark.Partitioner类，实现三个方法
- numPartitions : Int：返回创建出来的分区数
- getPartition(key : Any) : Int：返回分区编号
- equals()：判断两个RDD分区是否相同

--- 

# Spark任务执行流程
名词解释

	Driver：负责任务的调度
	Executor：负责执行任务
	master：集群主节点，负责分配资源
	worker：集群数据节点，提供任务执行的环境
	Application：待执行的任务
	DAGSchedule：根据宽窄依赖形成的算子执行stage
	TaskSchedule：一个stage包括多个task

## Spark on Standalone

> 在独立的机器执行

1. 初始化SparkContext；主要包括DAGSchedule和TaskSchedule
2. Driver将Application的注册信息传给master，master根据注册信息在worker启动Executor
3. Executor会创建线程池，并将启动的Executor反向注册给Driver
4. DAGSchedule将不同的stage传给TaskSchedule
5. TaskSchedule将task分发给Executor执行
6. 任务执行结束，SparkContext向master注销

疑问：
- DAGSchedule和TaskSchedule是什么？
- 怎么生成DAGSchedule？
- 怎么生成TaskSchedule？

## Spark on yarn-client

> 通过yarn集群外的一台机器向yarn提交任务

1. client向ResourceManager申请启动ApplicationMaster，并初始化SparkContext
2. ResourceManager启动