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
* [chapter02 划分聚类算法](#chapter02-划分聚类算法)
	* [Kmeans](https://github.com/Liping0202/Clustering-algorithm/tree/master/Kmeans)
	* [Kmeans算法分析](#chapter02-划分聚类算法)
* [chapter03 离群点改进](#chapter03-离群点改进)
	* [局部离群因子检测方法LOF](https://github.com/Liping0202/Clustering-algorithm/tree/master/LOF)
	* [Kmedoids](https://github.com/Liping0202/Clustering-algorithm/tree/master/Kmedoids)
	* [大型应用聚类CLARA](#chapter03-离群点改进)
	* [基于随机搜索的聚类大型应用CLARANS](#chapter03-离群点改进)
* [chapter04 k值选择](#chapter04-k值选择)  
	* [手肘法](#chapter04-k值选择)
* [chapter05 初始聚类中心的选择](#chapter05-初始聚类中心的选择)  
	* [尽可能远的k个点](#chapter05-初始聚类中心的选择) 
	* [Canopy算法](https://github.com/Liping0202/Clustering-algorithm/tree/master/canopy)
* [chapter06 球状簇](#chapter06-球状簇)  
	* [Kernel-Means](#chapter06-球状簇)
* [chapter07 标称属性](#chapter07-标称属性)  
	* [K-modes算法](#chapter07-标称属性)
	* [K-prototype算法](#chapter07-标称属性)
* [chapter08 局部收敛性](#chapter08-局部收敛性)  
	* [改变初始簇中心](#chapter08-局部收敛性)
	* [二分K-均值聚类算法](https://github.com/Liping0202/Clustering-algorithm/tree/master/BisectingKmeans)
* [chapter09 层次聚类法](#chapter09-层次聚类法)  
	* [层次聚类方法](#chapter09-层次聚类法)
	* [层次聚类算法的改进](#chapter09-层次聚类法)
* [chapter10 密度聚类法](#chapter10-密度聚类法)  
	* [密度聚类法](#chapter10-密度聚类法)
	* [Optics算法](#chapter10-密度聚类法)
	
***
---
___

chapter01 聚类算法的性能度量
-------
* ### 聚类算法  
聚类算法是将数据集中的样本划分为若干个不相交的子集，每个子集即为一个簇，
可用于寻找数据内在的分布结构，也可作为其它学习任务的前驱过程，来提炼数据。
聚类算法包含很多种，分为划分法、密度法、层次法、图论聚类算法、模型算法等。  

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
DBI指数：
$$DBI=\frac{1}{k}\sum_{i=1}^{k}max_{j≠i}\frac{avg(C_i)+avg(C_j)}{dcen(u_i,u_j)}$$    
Dunn指数：
$$DI=min_{j≠i}\{min_{j≠i}\frac{dmin(C_i,C_j)}{max_{1≤l≤k}diam(C_j)}\}$$   
DBI指数是用来衡量样本簇内部点的聚合度大小，簇内平均距离越小越好。
Dunn指数是比较样本簇之间聚合度大小，簇间距离越大越好。  

*  **距离度量：**   
聚类的相似度是通过距离来度量的，函数$dist()$是一个距离度量。  
给定样本：$x=\{x_1,x_2,...,x_n\}$,$y=\{y_1,y_2,...,y_n\}$,有序属性（表示确切的值）的距离公式有：  
	(1) 闵可夫斯基距离: $dist(x,y)=(\sum_{i=1}^{n}|x_i-y_i|^p)^{\frac{1}{p}}$  
	(2) 欧氏距离: p=2    
	(3) 哈曼顿距离: p=1    
无序属性：VDM距离

-----
chapter02 划分聚类方法
-------
划分方法又称原型聚类，给定一个含n个数据的集合，使用划分方法构建数据的K个分区。
大部分划分方法是基于距离的，所以只能发现球类簇。普遍采用流行的启发式算法，
如K均值和k-中心点算法，渐进的提高聚类质量，逼近局部最优解。
其基本特点:发现球状互斥的簇、基于距离、用均值或中心点代表簇的中心、对中小规模
数据有效。

* ### Kmeans  
	Kmeans算法原理：    
	(1) 对于给定的数据集，首先随机选取k个数据作为初始簇中心。  
	(2) 遍历数据集，找到距离最近的中心点，加入该簇。  
	(3) 求平均值更新k个簇的中心点。  
	(4) 重复(2)(3)直到所有的中心点不再发生改变或运行次数达到阈值。    	
	
	算法的时间复杂度为O(nkt),其中n是数据总数，k是簇数，t是迭代次数。
##### Kmeans算法的效果图：
![Kmeans效果图](https://github.com/Liping0202/Clustering-algorithm/blob/master/640.gif) 

    
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
  	
*  **Kmedoids算法分析：**  当存在噪声与异常点时，Kmedoids算法鲁棒性更好。Kmedoids算法的时间复杂度为O(k(n-k)^2)，当n和k较大时，计算开销相当大，远高于Kmeans算法。     

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

-----
chapter04 k值选择
-------
* ### 手肘法
手肘法的核心指标是SSE(误差平方和)，
$$SSE=\sum_{i=1}^{k}\sum_{p∈C_i}|p-m_i|^2$$  
其中，$C_i$是第i个簇，$p$是$C_i$中的样本点，$m_i$是$C_i$的中心点，
SSE是所有样本的聚类误差，代表了聚类效果的好坏。

手肘法的核心思想是：随着聚类数k的增大，样本划分会更加精细，每个簇的聚合程
度会逐渐提高，那么误差平方和SSE自然会逐渐变小。另外，当k小于适当聚类数时，
由于k的增大会大幅增加每个簇的聚合程度，故SSE的下降幅度会很大，而当k到适合
实聚类数时，再增加k所得到的聚合程度回报会迅速变小，所以SSE的下降幅度会骤减，
然后随着k值的继续增大而趋于平缓，也就是说SSE和k的关系图是一个手肘的形状，
而这个肘部对应的k值就是数据的最合适的聚类数k。
##### k与SSE的关系图：
![手肘法](https://github.com/Liping0202/Clustering-algorithm/blob/master/k.png)  
 
肘部对于的k值为4，故对于这个数据集的聚类而言，最佳聚类数应该选4。

* ### 轮廓系数法  
该方法的核心指标是轮廓系数，某个样本点$X_i$的轮廓系数定义如下：
$$S=\frac{b-a}{max(a,b)}$$
其中，$a$是$X_i$与同簇的其他样本的平均距离，称为凝聚度，$b$是$X_i$与最近簇中所有样本的平均距离，
称为分离度。而最近簇的定义是
$$C_j=arg {min_{C_k}\frac{1}{n}\sum_{p∈C_k}|p-X_i|^2}$$
其中$p$是某个簇$C_k$中的样本。最近簇就是用$X_i$到某个簇所有样本平均距离，
选择离$X_i$最近的一个簇作为最近簇。

求出所有样本的轮廓系数后求平均值就得到了平均轮廓系数。平均轮廓系数的取值范围为
[-1,1]，且簇内样本的距离越近，簇间样本距离越远，平均轮廓系数越大，
聚类效果越好。所以，平均轮廓系数最大的k便是最佳聚类数。

-----
chapter05 初始聚类中心的选择
-------
* ### 尽可能远的k个点 	
	首先随机选择一个点作为第一个初始类簇中心点，然后选择距离该点最远的那个点作为
第二个初始类簇中心点，然后再选择距离前两个点的最近距离最大的点作为第三个初始类簇
的中心点，以此类推，直至选出K个初始类簇中心点。  
该方法经过测试效果很好，用该方法确定初始类簇点后运行KMeans可以较好的分好k个类簇。

* ### Canopy算法
Canopy算法与传统的聚类算法不同，Canopy聚类最大的特点是不需要事先指定k值，
可以先用Canopy聚类对数据进行“粗”聚类得到k值，
然后使用得到的类簇中心点作为KMeans算法初始类簇中心点进行进一步“细”聚类，所以
使用Canopy+K-means这种形式聚类算法聚类效果较好。  
*  **Canopy算法原理：**  
	(1) 确定初始距离阈值T1、T2，T1>T2(T1、T2的设定根据用户的需要)。  
	(2) 在List中随机挑选一个数据点A，计算这个数据点到canopy的距离d。  
	(3) 根据(2)得到的距离d，把d小于T1的数据点标上弱标记并划到这个canopy中，把d小于T2的数据点标上强标记并从List中移除。  
	(4) 重复(2)(3),直到list为空，算法结束。  
	这里的canopy指要划分数据的中心点，以这个canopy为中心，T2为半径，形成一个小圆。
T1为半径，形成一个大圆。小圆范围内的数据点一定属于这个canopy，不能作为一个新的
canopy来划分数据。而小圆范围外，大圆范围内的数据可以作为新的canopy来划分数据。	
*  注意：参数T1、T2的调整  
T1过大会使许多点属于多个Canopy，造成各个簇的中心点间距离较近，各簇间区别不明显；
T2过大会增加标记数据点的数量，会减少簇个数，T2过小会增加簇的个数，增加计算时间。 
##### Canopy算法的效果图：
![canopy.py效果图](https://github.com/Liping0202/Clustering-algorithm/blob/master/canopy.png) 
	由Canopy算法后的效果图，大致可以得到k个中心点，同时确定了K-MEANS算法的K值的选取以及
初始Ｋ个中心点。

*  **Canopy+K-means算法原理：**  
	(1) 聚类最耗时的是计算数据点的相似性，Canopy选择简单且计算代价较低的方法计算
对象相似性，将相似的对象放在一个子集中，这个子集叫做Canopy ，通过一系列计算得到
若干Canopy，Canopy之间可以是重叠的，但不会存在某个数据点不属于任何Canopy的情况，
通过这一阶段对数据进行预处理。  
	(2) 在各个Canopy内使用K-means聚类方法，不属于同一Canopy的对象之间不进行相似性
计算。    
	算法分析：  
	① Canopy不要太大且Canopy间重叠的不要太多会减少后续计算相似性数据点的个数。  
	② 使用Canopy算法得到的Canopy个数可以作为Kmeans聚类算法的k值，减少了选择K的盲目性。

-----
chapter06 球状簇
-------
* ### Kernel-Means   
因为K-Means使用距离作为样本间相似性的度量，所以只能发现球状簇。
K-均值算法对于各类样本的边界是线性不可分以及类分布为非球形时，聚类效果较差。
参照支持向量机SVM的核函数，将数据集映射到另一个高维特征空间，
原先的线性不可分可以变为线性可分或者近似线性可分，然后在高维特征空间再进行聚类处理。
引入核函数更能突显出不同样本的差异。

-----
chapter07 标称属性
-------
* ### K-modes算法 
	K-modes算法与K-means算法的基本思路是相同的，但是K-means算法用数值属性间
距离均值来更新中心点，若属性为标称属性时，例如有n款手机，每款手机具有编号、
国家和颜色等属性，其均值没有定义，因此引入了K-Modes算法。用簇众数代替簇均值
来聚类标称数据，使用标称属性距离(离散点距离)代替Kmeans的欧式距离，采用基于
频率的方法来更新簇的众数。      
*  **K-modes算法原理：**
假设有N个样本，M个属性且全是离散的，簇的个数为k  
	(1) 随机确定k个聚类中心$C_1$,$C_2$....$C_k$，$C_i$是长度是M的向量，
$C_i=[C_{1i},C_{2i},...,C_{Mi}]$。  
	(2) 对于样本$x_j$(j=1,2,...,N)，分别比较其与k个中心之间的距离(距离为不同属性值的个数)。  
	(3) 将$x_j$划分到距离最小的簇，在全部的样本都被划分完后，重新确定簇中心，
簇中心点$C_i$中每个分量更新为簇i中的众数。    
	(4) 重复(2)(3)，直到各个簇中样本与各自簇中心距离之和不再降低，返回最后的聚类结果。    

* ### K-prototype算法  
K-Prototype算法是处理混合属性聚类的典型算法，结合K-Means与K-modes算法。
针对混合属性的聚类方法，数值属性采用K-means方法得到P1，分类属性采用K-modes
方法P2，那么聚类方法D=P1+a*P2，a是权重。若分类属性重要，则增加a，否则减少a。
另外，更新一个簇的中心的方法是结合K-Means与K-modes的更新。

-----
chapter08 局部收敛性
-------
* ### 改变初始簇中心
由于簇初始中心点的选择是随机的，很容易导致最后的聚类结果是局部最优的。一种有效的解决方法
是改变初始簇中心，多次调试。可以根据聚类结果来合并距离较近的中心点，使聚类结果的误差平方和
最小，达到全局最优。 
* ### 二分K-均值聚类算法
	二分k均值算法是k-means算法的一个变体，主要是为了改进k-means算法随机性，
选择初始质心的随机性可能造成聚类结果局部最优的问题，而Bisecting k-means算法受随机选择初始质心的影响比较小。
二分k-means算法可以加速k-means算法的执行速度，因为它的相似度计算减少，另外
能够克服k-means收敛于局部最优的缺点。  
二分k均值的核心思路是：将初始的一个簇一分为二计算出误差平方和最大的那个簇，
对他进行二分，重复上述过程直至切分的簇个数为k停止，实质是不断的对选中的簇做k=2的kmeans切分。  
*  **二分k均值算法原理：**  
	(1) 将待聚类数据集$D$作为一个簇$C_0$，即$C={C_0}$，输入需要进行二分试验次数m、
k-means聚类的基本参数。  
	(2) 取$C$中具有最大SSE的簇$C_p$，调用k-means聚类算法进行二分试验m次，每次运行kmeans算法取k=2，
将$C_p$分为2个簇$C_{i1}$、$C_{i2}$，一共得到m个二分结果集合$B=[B_1,B_2,…,B_m]$，
$B_i=[C_{i1},C_{i2}]$，其中$C_{i1}$和$C_{i2}$为每一次二分试验得到的2个簇。  
	(3) 计算集合B中每一个划分方法得到的2个簇的总SSE值，选择具有最小总SSE的二分方法得到的结果：$B_j=[C_{i1},C_{i2}]$，
	并将簇$C_{j1}$、$C_{j2}$加入到集合$C$，并将$C_p$从$C$中移除。  
	(4) 重复(2)(3)，直到得到k个簇。

-----
chapter09 层次聚类法
-------
* ### 层次聚类方法
层次聚类试图在不同层次上对数据集进行划分，从而形成树形的聚类结构，
数据集的划分可采用“自底向上”的聚合策略，也可以采用“自顶向下”的分拆策略。    
	不管是凝聚的还是分裂的方法，一个核心问题就是度量两个簇之间的距离。
4个比较广泛的簇间距离度量如下所示：    
最小距离：$dist_min (C_i,C_j)=min_{p∈C_i,p'∈C_j}\{|p-p'|\}$  
最大距离：$dist_max (C_i,C_j)=max_{p∈C_i,p'∈C_j}\{|p-p'|\}$  
均值距离：$dist_mean (C_i,C_j)=|m_i-m_j|$  
平均距离：$dist_avg (C_i,C_j)=\frac{1}{n_i n_j}\sum_{p∈C_i,p∈C_j}|p-p'|$  

层次聚类算法也是基于距离来衡量相似性的，衡量的是簇的相似性，旨在发现球形簇。    
	当使用最小距离来度量两个簇之间的距离时，被称为最近邻聚类算法。
当最近的两个簇之间的距离超过设定的阈值之后，迭代就会终止，成为单连接算法。  
	当时用最大距离来度量两个簇之间的距离时，被称为最远邻聚类算法。
如果最近的两个簇之间的最远距离大于某个设定的阈值，迭代终止，称为全连接算法。  
	最小/最大距离代表了簇间距离距离度量的两个极端，对离群点以及噪声点过分敏感，
使用均值距离/平均距离是一种折中的办法，可以克服离群点等的影响。
均值距离计算简单，但是平均距离既能处理数值数据也可以处理分类数据。
	
凝聚的层次聚类方法使用自底向上的策略，即刚开始每个点都认为是一个簇，
然后在迭代过程中，不断的合并直到满足某种条件。
在合并步骤中，它找出最相近的簇，并且合并他们，形成一个簇。
分裂的层次聚类方法使用自顶向下的策略，即把所有的对象都放到一个簇中开始。
不断向下划分，直到满足某种设定的条件。  

* ### 层次聚类方法的改进
传统的层次聚类方法比较简单，但是合并或分裂点选择困难， 一个有希望的改进方向是
将层级聚类和其他聚类技术进行集成，形成多阶段聚类。  
	CHAMELEON是一种两阶段聚类法。第一阶段把点分成很多小的簇；第二阶段根据相近程度合并这些小的簇。
	第一阶段采用K最邻近法，即把一个点和它最邻近的K个点连接起来。
第二阶段计算任意两个簇的互连性$RI$和紧密性$RC$，当两个指标都比较大时才合并这两个簇。  
相对互连度$$RI(C_i,C_j)=\frac{2*|EC(C_i,C_j)|}{|EC(C_i)|+|EC(C_j)|}$$   
相对紧密度$$RC(C_i,C_j)=\frac{(|C_i|+|C_j|)EC(C_i,C_j)}{|C_j|EC(C_i)+|C_i|EC(C_j)}$$  
$|C_i|$表示簇i内数据点的个数；$EC(C_i)$表示簇i内所有边的权重和；
$EC(C_i,C_j)$表示跨越两个簇的所有边的权重和.   
合并过程算法：  
1、给定度量函数如下minMetric。  
2、访问每个簇，计算他与邻近的每个簇的RC和RI，通过度量函数公式计算出值tempMetric。    
3、找到最大的tempMetric,如果最大的tempMetric超过阈值minMetric，
将簇与此值对应的簇合并。  
4、如果找到的最大的tempMetric没有超过阈值，则表明此聚簇已合并完成，
移除聚簇列表，加入到结果聚簇中。  
5、递归步骤2，直到待合并聚簇列表最终大小为空。  
 
CHAMELEON具有两个特点：(1)适合于高维数据的聚类。
(2)采用k-邻近图可以动态地捕捉邻域概念，在稠密区域邻域比较窄，
在稀疏区域邻域比较宽，这相比于DBSCAN中的全局邻域密度来说容易获得更自然的邻域。


-----
chapter10 密度聚类法
-------
* ### 密度聚类方法
划分和层次的聚类算法旨在发现球状簇，很难发现任意形状的簇。
基于密度的聚类算法假设聚类结构能够通过样本分布的紧密程度确定。
通常情况下，密度聚类算法从样本密度的角度进行考察样本之间的可连接性，
并既有可连接样本不断扩展聚类簇直到获得最终的聚类结果。  
基于密度聚类算法的主要特点是：发现任意形状的簇;处理噪声;一次扫描;
需要密度参数作为终止条件。  
	DBSCAN是一种著名的密度聚类算法，基于一组邻域参数（r,MinPts）来刻画样
本的紧密程度。说的通俗点就是以某个样本点为中心，以r为半径进行画圆，
在圆内的范围都是邻域范围。与K-MEANS相比，DBSCAN算法可以不必输入聚类簇数量K，且适合于
任意聚类簇的形状，但是进行聚类时使用了一个全局性的表征密度的参数，当空间聚类的
密度不均匀、聚类间距差相差很大时，聚类质量较差。   
为了克服全局参数这一缺点，引入了OPTICS聚类算法。OPTICS并不显式地产生聚类簇，
而是输出点排序。这个排序是所有分析对象的线性表。OPTICS聚类算法不需要用户提供特定的密度阈值。
簇排序可以用来提取基本的聚类信息，导出内在的聚类结构。 

* ### Optics算法
OPTICS聚类算法是基于密度的聚类算法，目标是将空间中的数据按照密度分布进行聚类，
OPTICS算法可以获得不同密度的聚类。因为OPTICS算法输出的是样本的一个有序队列，
从这个队列里面可以获得任意密度的聚类。    
OPTICS算法的难点在于维护核心点的直接可达点的有序列表。算法的计算过程如下：  
输入：数据样本D，初始化所有点的可达距离和核心距离为MAX，半径ε，和最少点数MinPts。  
1、建立两个队列，有序队列(核心点及该核心点的直接密度可达点)，
结果队列(存储样本输出及处理次序);    
2、如果D中数据全部处理完，则算法结束，否则从D中选择一个未处理且未核心对象的点，
将该核心点放入结果队列，该核心点的直接密度可达点放入有序队列，
直接密度可达点并按可达距离升序排列；    
3、如果有序序列为空，则回到步骤2，否则从有序队列中取出第一个点；  
①判断该点是否为核心点，不是则回到步骤3，是的话则将该点存入结果队列，
如果该点不在结果队列；  
②该点是核心点的话，找到其所有直接密度可达点，并将这些点放入有序队列，
且将有序队列中的点按照可达距离重新排序，如果该点已经在有序队列中且新的
可达距离较小，则更新该点的可达距离；    
③重复步骤3，直至有序队列为空。  







