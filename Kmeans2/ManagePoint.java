package Kmeans2;

import java.util.ArrayList;
import java.util.Map;
 
public class ManagePoint { 
// ��������֮��ľ���
	public double getDistance(Point p, Point q) {
		double dx = p.getX() - q.getX();
		double dy = p.getY() - q.getY();
		double dz = p.getZ() - q.getZ();
		double dw = p.getW() - q.getW();
		double distance = Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
		return distance;
	}

// �ж�ǰ�����������Ƿ���ͬ
	public boolean isEqual(Map<Point, ArrayList<Point>> lastCenterCluster,
			Map<Point, ArrayList<Point>> nowCenterCluster) {
		boolean contain = false;
		if (lastCenterCluster == null)
			return false;
		else {
			for (Point point : nowCenterCluster.keySet()) {
				contain = lastCenterCluster.containsKey(point);
			}
			if (contain)
				return true;
		}
		return false;
	}

// �����µ����� 
	public Point getNewCenter(ArrayList<Point> value) {
		double sumX = 0, sumY = 0, sumZ = 0, sumW = 0;
		for (Point point : value) {
			sumX += point.getX();
			sumY += point.getY();
			sumZ += point.getZ();
			sumW += point.getW();
		}
		System.out.println("�µ�����: (" + sumX / value.size() + "," + sumY
				/ value.size() + "," + sumZ / value.size() + "," + sumW
				/ value.size() + ")");
		Point point = new Point();
		point.setX(sumX / value.size());
		point.setY(sumY / value.size());
		point.setZ(sumZ / value.size());
		point.setW(sumW / value.size());
		return point;
	}
}


