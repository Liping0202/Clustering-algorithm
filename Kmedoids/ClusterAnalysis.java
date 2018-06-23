package Kmedoids;

import java.util.ArrayList;
public class ClusterAnalysis {
    private Cluster[] clusters;// �������
    private int iter;// ��������
    private ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();// ����������
    private int dimNum;//ά��
    //���캯��
    public ClusterAnalysis(int k, int iter, ArrayList<DataPoint> dataPoints,int dimNum) {
        clusters = new Cluster[k];// ��������
        for (int i = 0; i < k; i++) {
            clusters[i] = new Cluster("Cluster:" + i);
        } 
        this.iter = iter;
        this.dataPoints = dataPoints;
        this.dimNum=dimNum;
    }
    public int getIterations() {
        return iter;
    }
    public ArrayList<DataPoint>[] getClusterOutput() {
        ArrayList<DataPoint> v[] = new ArrayList[clusters.length];
        for (int i = 0; i < clusters.length; i++) {
            v[i] = clusters[i].getDataPoints();
        }
        return v;
    }
    private void setInitialMedoids(double[][] medoids) {
        for (int n = 0; n < clusters.length; n++) {//����k���أ�ÿ���ؽ��б���һ��
            Medoid medoid = new Medoid(medoids[n]);//��һ�����������Ϊ���ĵ�,����һ�����ĵ㹹��һ��Medoid����
            clusters[n].setMedoid(medoid);//����n�����ĵ�����Ϊmedoid
            medoid.setCluster(clusters[n]);//�����ĵ�medoid�Ĵض�Ӧ����n
        }
    }
    public void startAnalysis(double[][] medoids) {
        setInitialMedoids(medoids);//���ó�ʼ�����ĵ�
        double[][] newMedoids=medoids;
        double[][] oldMedoids=new double[medoids.length][this.dimNum];
        while(!isEqual(oldMedoids,newMedoids)){//�������ϲ����ʱ�������ĵ㷢���˸ı�
            for(int m = 0; m < clusters.length; m++){//ÿ�ε�����ʼ�������صĵ��ʼ��
                clusters[m].getDataPoints().clear();
            }
            for (int j = 0; j < dataPoints.size(); j++) {//�����������㻮�ֵ���������Ĵ���
                int clusterIndex=0;
                double minDistance=Double.MAX_VALUE;
                for (int k = 0; k < clusters.length; k++) {//�ж������������ĸ����
                    double eucDistance=dataPoints.get(j).testEuclideanDistance(clusters[k].getMedoid());
                    if(eucDistance<minDistance){
                        minDistance=eucDistance;
                        clusterIndex=k;
                    }
                }
                clusters[clusterIndex].addDataPoint(dataPoints.get(j)); //������������ӵ������
            }
            for(int m = 0; m < clusters.length; m++){
                clusters[m].getMedoid().calcMedoid();//���¼������ص��ʵ�
            }
            for(int i=0;i<medoids.length;i++){
                for(int j=0;j<this.dimNum;j++){
                    oldMedoids[i][j]=newMedoids[i][j];//֮ǰ�������ĵ㱻����Ϊ�����ĵ�
                }
            }
            for(int n=0;n<clusters.length;n++){
                newMedoids[n]=clusters[n].getMedoid().getDimensioin();//�µ����ĵ���ǰ�����¼���Ĵص��ʵ�õ�
            }
            this.iter++;
        }
    }
   
    private boolean isEqual(double[][] oldMedoids,double[][] newMedoids){
        boolean flag=false;
        for(int i=0;i<oldMedoids.length;i++){
            for(int j=0;j<oldMedoids[i].length;j++){
                if(oldMedoids[i][j]!=newMedoids[i][j]){
                    return flag;
                }
            }
        }
        flag=true;
        return flag;//��һ���㲻��ȣ����������ĵ㼯�ϲ���ȣ�ֻ���������ĵ㼯���е�Ԫ�ض���ȣ��ŷ���true��
    }
}
