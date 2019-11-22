#Hive笔记
##1.分组统计topN
###1.1 功能
统计一个商店每一天消费最多的人是谁？
###1.2 输入数据格式
	shop \t name \t date
###1.3 HQL实现
		SELECT *
		FROM (
			SELECT t.shop, t.date, t.name, t.cnt, row_number() OVER (PARTITION BY t.shop ORDER BY t.cnt DESC) AS 
				row_num
			FROM (
				SELECT shop, name, date, COUNT(*) AS cnt
				FROM shopping
				GROUP BY shop, name, date
			) t
		) t2
		WHERE t2.row_num < 2
###1.4 使用函数
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

		SELECT t.shop, t.date, t.name, t.cnt, 
			row_number() OVER (PARTITION BY t.shop ORDER BY t.cnt DESC) AS row_num
		FROM (
			SELECT shop, name, date, COUNT(*) AS cnt
			FROM shopping
			GROUP BY shop, name, date
		) t	

***输出***

	金虎便利 20191101 张三 3 1
	金虎便利 20191101 李四 1 2
	便利蜂 20191101 李四 2 1
	便利蜂 20191101 张三 1 2

对上述输出的结果根据row_num的值进行where条件判断，即可得到topN，`where row_num < 3`。

