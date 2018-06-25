聚类算法
========

****
### Author: Ping
### E-mail: 2496458210@qq.com
****
## 目录
* [chapter01 Kmeans](https://github.com/Liping0202/Clustering-algorithm/tree/master/Kmeans)
	* [Kmeans](#chapter01-kmeans)
	* Kmeans算法分析
* [chapter02 离群点改进](#chapter02-离群点改进)
	* [Kmedoids](https://github.com/Liping0202/Clustering-algorithm/tree/master/Kmedoids)
	* [局部离群因子检测方法LOF]()
* [chapter09]()  
	* 
***
---
___

聚类算法
-------
原型聚类又称划分方法，给定一个含n个数据的集合，使用划分方法构建数据的K个分区。大部分划分方法是基于距离的，所以只能发现球类簇。
普遍采用流行的启发式算法，如K均值和k-中心点算法，渐进的提高聚类质量，逼近局部最优解。其基本特点:发现球状互斥的簇、基于距离、用均值或中心点代表簇的中心、对中小规模数据有效。

chapter01 Kmeans
-------
* ### Kmeans  
	Kmeans算法原理：    
	>>(1) 对于给定的数据集，首先随机选取k个数据作为初始簇中心。  
	>>(2) 遍历数据集，找到距离最近的中心点，加入该簇。  
	>>(3) 求平均值更新k个簇的中心点。  
	>>(4) 重复(2)(3)直到所有的中心点不再发生改变或运行次数达到阈值。
  	
	算法的时间复杂度为O(nkt),其中n是数据总数，k是簇数，t是迭代次数。	
    
* ### Kmeans算法分析 
	>(1) 不能保证Kmeans算法收敛于全局最优解，常常终止于局部最优解，依赖于初始簇中心的随机选择。  
	>(2) 算法需要人工输入参数k的值，k值取值的不同，导致聚类结果的质量不同。  
	>(3) 算法的距离计算量为欧几里得距离，将形成球状或者类球状的簇，不能够发现非凸状的簇。  
	>(4) 算法对噪声和离群点敏感，异常数据会严重影响聚类结果的质量。  
	>(5) 算法的时间复杂度为O(nkt)，对于大数据集，算法是可伸缩的，但是计算量大，效率低下。  
	>(6) 标称属性无法计算均值，标称属性无法使用Kmeans算法。  
	>(7) 该算法是无监督的学习式聚类方法，当处理有初始类的数据时，需要一种半监督半学习式的处理模式，如果任算法自由运算，聚类结果很可能得不到用户的肯定。  

	接下来的章节将对Kmeans算法进行改进。
	
-----
chapter02 离群点改进
-------
* ### Kmedoids 
Kmeans算法对离群点十分敏感，因为离群点远离大多数数据，当分配到一个簇时，严重扭曲簇的均值。Kmedoids算法
在进行中心点选择时，不再依靠均值而是考虑用非中心点替换中心点，看是否能够提高聚类质量。  
*  **Kmedoids算法原理：**   
	(1) 对于给定的数据集，首先随机选取k个数据作为初始簇中心。  
	(2) 遍历数据集，找到距离最近的中心点，加入该簇。  
	(3) 各簇中，计算每个数据点距簇内其他数据点的绝度误差和，误差最小的点作为新的中心点。  
	(4) 重复(2)(3)直到新的中心点集与原中心点集相同。
  	
*  **Kmeans算法分析：**  当存在噪声与异常点时，Kmeans算法鲁棒性更好。Kmeans算法的时间复杂度为O(k(n-k)^2)，当n和k较大时，计算开销相当大，远高于Kmeans算法。     
	
-----
* ### 局部离群因子检测方法LOF  
一种基于距离的异常检测算法LOF通过剔除无效数据和异常数据来对数据进行预处理。
*  **LOF算法原理：**   
	(1)对于数据集中每个数据点p，计算数据点p与其他数据点o的欧几里得距离。    
	(2)对欧几里得距离进行排序，计算该数据的第k距离$k-distance(p)$以及第K领域$N_k(p)$。  
	(3)计算每个数据的可达距离$reach−distancek(p,o)$及可达密度$lrd_k(p)$,  
	其中$reach−distancek(p,o)$=max{$k−distance(o)$,$d(p,o)$}，
  	$$lrd_k(p)=\frac{|N_k(p)|}{\sum_{o∈N_k(p)}reach-dist_k(p,o)}$$
	(4)计算每个数据的局部离群点因子$LOF_k(p)$,
$$LOF_k(p)=\frac{\sum_{o∈N_k(p)}lrd_k(o)}{|N_k(p)| lrd_k(p)}$$	
	(5)对每个点的局部离群点因子进行排序，输出。  
	若局部离群因子越接近1，说明p的其邻域点密度差不多，p可能和邻域同属一簇；
若局部离群因子越小于1，说明p的密度高于其邻域点密度，p为密集点；
若局部离群因子越大于1，说明p的密度小于其邻域点密度，p越可能是异常点。
 
*  **LOF算法的基本思想：**	通过比较每个点p和其邻域点的密度来判断该点是否为异常点，
如果点p的密度越低，越可能被认定是异常点，其中密度是通过点之间的距离来计算的。



