聚类算法
========

****
### Author:Ping
### E-mail:2496458210@qq.com
****
## 目录
* [chapter01 Kmeans](https://github.com/Liping0202/Clustering-algorithm/tree/master/Kmeans)
	* [Kmeans](#chapter01-kmeans)
	* Kmeans算法分析
* [chapter03](https://github.com/Changzhisong/Introduction_to_Algorithms/tree/master/chapter03 "跳转到chapter03")
	* [渐进记号的定义](#渐进记号的定义)
	* [渐近记号Θ、Ο、Ω、o、ω之间的关系](#渐近记号ΘΟΩoω之间的关系) 
* [chapter09](https://github.com/Changzhisong/Introduction_to_Algorithms/tree/master/chapter09 "跳转到chapter09")  
	* 
***
---
___

chapter01 Kmeans
-------
* ### Kmeans 
	通过构建有序序列，在已经排序的序列中从后面向前扫描，找到相应的位置并插入。
		换句话说，每次从剩余数组中拎出一个元素，与有序数组从后往前对比，直到插入合
		适的位置，然后剩余数组缩小规模在循环插入。是一种原址排序算法（类似于抓牌后整理牌）  
![动画演示](https://github.com/Changzhisong/Introduction_to_Algorithms/blob/master/chapter02/Doc/Insertion-sort1.gif)  
* ### 选择排序【O($n^2$)】  
	找出数组A中最小的元素并将其与A[0]进行交换，接着，找出A中第二小的元素与A[1]
		交换。对A中前n-1个元素按该方式交换。  
![动画演示](https://github.com/Changzhisong/Introduction_to_Algorithms/blob/master/chapter02/Doc/Selection-Sort.gif)  

-----
