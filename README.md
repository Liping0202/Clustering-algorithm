聚类算法
========

****
### Author: Ping
### E-mail: 2496458210@qq.com
****
## 目录
* [chapter01 聚类算法的性能度量](#chapter01-聚类算法的性能度量)
	* [聚类算法](#chapter01-聚类算法的性能度量)
	* [聚类算法的性能度量](#chapter01-聚类算法的性能度量)
* [chapter02 Kmeans](https://github.com/Liping0202/Clustering-algorithm/tree/master/Kmeans)
	* [Kmeans](#chapter02-kmeans)
	* Kmeans算法分析
* [chapter03 离群点改进](#chapter03-离群点改进)
	* [局部离群因子检测方法LOF](https://github.com/Liping0202/Clustering-algorithm/tree/master/LOF)
	* [Kmedoids](https://github.com/Liping0202/Clustering-algorithm/tree/master/Kmedoids)
	* [大型应用聚类CLARA](#chapter03-离群点改进)
	* [基于随机搜索的聚类大型应用CLARANS](#chapter03-离群点改进)
* [chapter04 k值选择](#chapter04-k值选择)  
	* 
***
---
___

chapter01 聚类算法的性能度量
-------
* ### 聚类算法  
聚类算法是将数据集中的样本划分为若干个不相交的子集，每个子集即为一个簇，
可用于寻找数据内在的分布结构，也可作为其它学习任务的前驱过程，来提炼数据。

聚类算法包含很多种，分为划分法、密度法、层次法、图论聚类算法、模型算法等。本项目主要研究的是划分法，
划分方法又称原型聚类，给定一个含n个数据的集合，使用划分方法构建数据的K个分区。大部分划分方法是基于距离的，所以只能发现球类簇。
普遍采用流行的启发式算法，如K均值和k-中心点算法，渐进的提高聚类质量，逼近局部最优解。其基本特点:发现球状互斥的簇、基于距离、用均值或中心点代表簇的中心、对中小规模数据有效。
* ### 聚类算法的性能度量  
聚类算法的性能度量是用来评估聚类模型的好坏，并将其作为聚类过程的优化目标。
聚类的性能度量主要分为两种类型，外部指标：将聚类结果与某个“参考模型”进行比较，称为“外部指标”。
内部指标：直接考察聚类结果而不利用任何参考模型。
*  **外部指标：**  
对数据集$D$={$x_1$,$x_2$,...,$x_m$}假定通过聚类得到的簇划分为$C$={$C_1$,$C_2$,...,$C_k$}，
参考模型给出簇划分为$C'$={$C'_1$,$C'_2$,...,$C'_s$}，相应的，令$λ$与$λ'$分别表示与$C$和$C'$对应的簇标记向量。
有如下定义： 
$$a=|SS|,SS={(x_i,x_j)|λ_i=λ_j,λ'_i=λ'_j,i<j)}$$
$$b=|SD|,SD={(x_i,x_j)|λ_i=λ_j,λ'_i≠λ'_j,i<j)}$$ 
$$c=|DS|,DS={(x_i,x_j)|λ_i≠λ_j,λ'_i=λ'_j,i<j)}$$ 
$$a=|DD|,DD={(x_i,x_j)|λ_i≠λ_j,λ'_i≠λ'_j,i<j)}$$
其中集合$SS$中表示两个数据样本在$C$中属于相同簇，也同时在$C'$中属于相同簇。  
基于以上定义可以导出下面常用的聚类性能度量外部指标：  
+Jaccard系数：$JC=\frac{a} {a+b+c}$  
+FM指数：     $FMI=(\frac{a}{a+b}\frac{a}{a+c})^{\frac{1}{2}}$  
+Rand 指数：  $RI=\frac{2(a+d)}{m(m−1)}$  
上述外部指标结果均在[0,1]区间，值越大聚类效果越好。

*  **内部指标：**  
内部指标：核心就是类内的距离越小越好，类间距离越大越好。  
对于聚类结果的簇划分$C$={$C_1$,$C_2$,...,$C_k$}，定义：  
簇内平均距离：$avg(C)=\frac{2}{|C|(|C|-1)}\sum_{1≤i≤j≤|C|}dist(x_i,x_j)$     
簇内最远距离: $diam(C)=max_{1≤i≤j≤|C|}dist(x_i,x_j)$    
簇间最近距离: $dmin(C_i,C_j)=min_{x_i∈C_i,x_j∈C_j}dist(x_i,x_j)$  
簇间中心距离: $dcen(C_i,C_j)=dist(u_i,u_j)$  
基于以上定义，可以导出聚类性能度量的内部指标：  
DBI指数：$$DBI=\frac{1}{k}\sum_{i=1}^{k}max_{j≠i}\frac{avg(C_i)+avg(C_j)}{dcen(u_i,u_j)}$$    
Dunn指数：$$DI=min_{j≠i}\{min_{j≠i}\frac{dmin(C_i,C_j)}{max_{1≤l≤k}diam(C_j)}\}$$   
DBI指数是用来衡量样本簇内部点的聚合度大小，簇内平均距离越小越好。
Dunn指数是比较样本簇之间聚合度大小，簇间距离越大越好。  

*  **距离度量：**   
聚类的相似度是通过距离来度量的，函数$dist()$是一个距离度量。  
给定样本：$x=\{x_1,x_2,...,x_n\}$,$y=\{y_1,y_2,...,y_n\}$,有序属性（表示确切的值）的距离公式有：  
	(1) 闵可夫斯基距离: $dist(x,y)=(\sum_{i=1}^{n}|x_i-y_i|^p)^{\frac{1}{p}}$  
	(2) 欧氏距离: p=2    
	(3) 哈曼顿距离: p=1    
无序属性：VDM距离	

chapter02 Kmeans
-------
* ### Kmeans  
	Kmeans算法原理：    
	(1) 对于给定的数据集，首先随机选取k个数据作为初始簇中心。  
	(2) 遍历数据集，找到距离最近的中心点，加入该簇。  
	(3) 求平均值更新k个簇的中心点。  
	(4) 重复(2)(3)直到所有的中心点不再发生改变或运行次数达到阈值。    	
	
	算法的时间复杂度为O(nkt),其中n是数据总数，k是簇数，t是迭代次数。	
    
* ### Kmeans算法分析 
	(1) 不能保证Kmeans算法收敛于全局最优解，常常终止于局部最优解，依赖于初始簇中心的随机选择。  
	(2) 算法需要人工输入参数k的值，k值取值的不同，导致聚类结果的质量不同。  
	(3) 算法的距离计算量为欧几里得距离，将形成球状或者类球状的簇，不能够发现非凸状的簇。  
	(4) 算法对噪声和离群点敏感，异常数据会严重影响聚类结果的质量。  
	(5) 算法的时间复杂度为O(nkt)，对于大数据集，算法是可伸缩的，但是计算量大，效率低下。  
	(6) 标称属性无法计算均值，标称属性无法使用Kmeans算法。  
	(7) 该算法是无监督的学习式聚类方法，当处理有初始类的数据时，需要一种半监督半学习式的处理模式，如果任算法自由运算，聚类结果很可能得不到用户的肯定。  

	接下来的章节将对Kmeans算法进行改进。
	
-----
chapter03 离群点改进
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

-------
* ### Kmedoids 
Kmeans算法对离群点十分敏感，因为离群点远离大多数数据，当分配到一个簇时，严重扭曲簇的均值。Kmedoids算法
在进行中心点选择时，不再依靠均值而是考虑用非中心点替换中心点，看是否能够提高聚类质量。  
*  **Kmedoids算法原理：**   
	(1) 对于给定的数据集，首先随机选取k个数据作为初始簇中心。  
	(2) 遍历数据集，找到距离最近的中心点，加入该簇。  
	(3) 各簇中，计算每个数据点距簇内其他数据点的绝度误差和，误差最小的点作为新的中心点。  
	(4) 重复(2)(3)直到新的中心点集与原中心点集相同。
  	
*  **Kmedoids算法分析：**  当存在噪声与异常点时，Kmeans算法鲁棒性更好。Kmeans算法的时间复杂度为O(k(n-k)^2)，当n和k较大时，计算开销相当大，远高于Kmeans算法。     

-------
* ### 大型应用聚类 CLARA  
基于抽样的方法，使用数据集的一个随机样本，然后使用PAM方法计算最佳中心点。
CLARA由多个随机样本建立聚类。在一个随机样本上计算中心点的复杂度为O($k$$s^2$+$k(n-k)$)，
CLARA依赖于样本的大小。CLARA目的是在整个数据集上选取K个最佳中心点，
如果最佳的抽样中心点远离最佳的K个中心点，那么CLARA不能发现好的聚类。

-------
* ### 基于随机搜索的聚类大型应用 CLARANS
在数据集中随机选取k个对象作为当前中心点，随机选择一个当前中心点x和一个不是当前中心点的y进行替换，
看看是否能够改善绝对误差。CLARANS进行这样的随机搜索L次。
L步之后的中心点的集合被看做一个局部最优解。 
重复上述过程M次，返回最佳局部最优解作为最终的结果。

Kmeans聚类的效果评估方法是SSE，
是计算所有点到相应簇中心的距离均值，当然，k值越大 
SSE越小，我们就是要求出随着k值的变化SSE的变化规律，
找到SSE减幅最小的k值，这时k应该是相对比较合理的值。
SSE,即所有点到其所属簇中心的距离的平方和即误差的平方和

轮廓系数（silhouette coefficient）方法结合了凝聚度和分离度，
可以以此来判断聚类的优良性，其值在-1到+1之间取值，值越大表示聚类效果越好
	



