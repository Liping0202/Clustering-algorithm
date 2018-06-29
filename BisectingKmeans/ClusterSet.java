package BisectingKmeans;
import java.util.List;

public class ClusterSet {//������һ���ؼ���
	private List<float[]> clu;
	private float[] center;
	private float erro;

	public void setErro(float erro) {
		this.erro = erro;
	}
	public float getErro() {
		return erro;
	}

	public void setClu(List<float[]> clu) {
		this.clu = clu;
	}
	public List<float[]> getClu() {
		return clu;
	}
	
	public void setCenter(float[] center) {
		this.center = center;
	}
	public float[] getCenter() {
		return center;
	}
	public static  float distance(float[] element, float[] center) {//�������������ݵ�֮��ľ���
		float distance = 0.0f;
		float x = element[0] - center[0];
		float y = element[1] - center[1];
		float z = x * x + y * y;
		distance = (float) Math.sqrt(z);
		return distance;
	}


//�������ƽ����׼��������     ��̬����
	public static float countRule( List<float[]> cluster,float[] center) {
		float jcF = 0;
		for (int j = 0; j < cluster.size(); j++) {
			jcF += ClusterSet.errorSquare(cluster.get(j), center);
			}
			return  jcF;
	}
	//���������ƽ���ķ���
		public static  float errorSquare(float[] element, float[] center) {
			float x = element[0] - center[0];
			float y = element[1] - center[1];
			float errSquare = x * x + y * y;
			return errSquare;
		}

	//��ӡ����
	public static  void printDataArray(List<float[]> dataArray) {
		for (int i = 0; i < dataArray.size(); i++) {
			System.out.println( "{"+ dataArray.get(i)[0] + "," + dataArray.get(i)[1] + "}");
		}
		System.out.println("===================================");
	}
}
