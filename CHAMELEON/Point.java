package CHAMELEON;
/**
 * �������
 *
 */
public class Point{
	//�����id��,id��Ψһ
	int id;
	Integer x;
	Integer y;
	//�Ƿ��Ѿ������ʹ�
	boolean isVisited;
	
	public Point(String id, String x, String y){
		this.id = Integer.parseInt(id);
		this.x = Integer.parseInt(x);
		this.y = Integer.parseInt(y);
	}
	
	/**
	 * ���㵱ǰ�����ƶ���֮���ŷʽ����
	 * 
	 */
	public double ouDistance(Point p) {
		double distance = 0;
		distance = (this.x - p.x) * (this.x - p.x) + (this.y - p.y)
				* (this.y - p.y);
		distance = Math.sqrt(distance);
 
		return distance;
	}
	
	/**
	 * �ж�2��������Ƿ�Ϊ�����
	 * 
	 */
	public boolean isTheSame(Point p) {
		boolean isSamed = false;
 
		if (this.x == p.x && this.y == p.y) {
			isSamed = true;
		}
 
		return isSamed;
	}
}

