# Hive笔记
## 1.分组统计topN

### 1.1 功能
统计一个商店每一天消费最多的人是谁？
### 1.2 输入数据格式
	shop \t name \t date
### 1.3 HQL实现
```sql
	SELECT 
		*
	FROM (
		SELECT 
			t.shop, t.date, t.name, t.cnt, 
			row_number() OVER (PARTITION BY t.shop ORDER BY t.cnt DESC) AS row_num
		FROM (
			SELECT 
				shop, name, date, COUNT(*) AS cnt
			FROM 
				shopping
			GROUP BY 
				shop, name, date
		) t
	) t2
	WHERE t2.row_num < 2
```

- 统计一个人在一个商店一天内的所有消费次数
- 使用row_number()函数，根据商店进行partition，根据cnt进行逆序排序，得到排序后的每个商店每个人的消费次数
- 根据where选取消费前三的人


### 1.4 使用函数

> row_counter() over() 其实就是**窗口函数**

row_number() over (partition by A order by B) xxx

此函数的结果是根据A进行分组，reduce阶段按照B排序，给reduce中的每一行赋一个编号

**举例：**

***输入***

	金虎便利 张三 20191101
	金虎便利 李四 20191101
	金虎便利 张三 20191101
	金虎便利 张三 20191101
	便利蜂 张三 20191101
	便利蜂 李四 20191101
	便利蜂 李四 20191101

***SQL***

		SELECT 
			t.shop, t.date, t.name, t.cnt, 
			row_number() OVER (PARTITION BY t.shop ORDER BY t.cnt DESC) AS row_num
		FROM (
			SELECT 
				shop, name, date, COUNT(*) AS cnt
			FROM 
				shopping
			GROUP BY 
				shop, name, date
		) t	

***输出***

	金虎便利 20191101 张三 3 1
	金虎便利 20191101 李四 1 2
	便利蜂 20191101 李四 2 1
	便利蜂 20191101 张三 1 2

对上述输出的结果根据row_num的值进行where条件判断，即可得到topN，`where row_num < 3`。

# 2 窗口函数

## 2.1 格式

	聚合函数() over(partition by A order by B)

- partition by 和 order by 可以一起使用，也可以分开使用
- order by 可以加desc，降序排序

## 2.2 窗口函数和聚合函数的区别
- 部分聚合函数可以使用窗口函数
- 聚合函数的值是一个，窗口函数的值是每行一个值

**区别2举例**

***输入***

	金虎便利 张三 20191101
	金虎便利 李四 20191101
	金虎便利 张三 20191101
	金虎便利 张三 20191101
	便利蜂 张三 20191101
	便利蜂 李四 20191101
	便利蜂 李四 20191101

***聚合函数SQL***

	select shop, count(*) from shopping group by shop;

***输出***

	金虎便利 4
	便利蜂 3

***窗口函数SQL***

	select shop, name, count(*) over(paritition by shop) from shopping;

***输出***

	金虎便利 张三 4
	金虎便利 李四 4
	金虎便利 张三 4
	金虎便利 张三 4
	便利蜂 张三 3
	便利蜂 李四 3
	便利蜂 李四 3

## 2.3 其他窗口函数

**排序**

- rank() over()
- dense_rank() over()

这两个函数和row_number() over() 类似都是计算排序后的序号，row_number排序后，序号从1-n依次递增；rank排序后，排名相同的序号一样，同一排名有几个，后面序号就会跳过几次；dense_rank排序后，排名相同的序号一样，且后面的序号不跳跃。

***输入***

	shop    name datee   cnt
	金虎便利 张三 20191101 1
	金虎便利 李四 20191101 2
	金虎便利 王五 20191101 1


***SQL***
```sql
	select shop,name, datee, row_number() over(partition by shop order by cnt) as row_number, 
	rank() over(partition by shop order by cnt) as rank, dense_rank() over(partition by shop
	order by cnt) as dense_rank from shopping;
```

***输出***

shop   | name  |  date  | cnt  | row_number | rank | dense_rank
------ | ----- | ------ | ---- | ---------- | ---- | --------
金虎便利 | 张三 | 20191101 | 1 | 1 | 1 | 1
金虎便利 | 王五 | 20191101 | 1 | 2 | 1 | 1
金虎便利 | 李四 | 20191101 | 2 | 3 | 3 | 2

