import java.util.ArrayList;

class Cluster {

    private ArrayList<Point> points = new ArrayList<Point>(); //точки кластера

    public Cluster() { //создаем кластер без точки
    }

    public Cluster(Point point) { //создаем кластер с точкой
        points.add(point);
    }

    public void addPoint(Point point) { //додавляем в кластер точку
        points.add(point);
    }

    public ArrayList<Point> getPoints() { //кластер отдает список своих точек
        return points;
    }

    public int getSize() { //класс отдает свой размер
        return points.size();
    }

    public void addAll(ArrayList<Point> new_points) {
        points.addAll(new_points);
    }

    public Point cg() { //центр тяжести кластера
        double x = 0, y = 0;
        for (Point p: points) {
            x += p.x;
            y += p.y;
        }
        x = x / points.size();
        y = y / points.size();
        return new Point(x, y);
    }

    public double dispersion() { //дисперсия кластера
        double y = 0;
        for (Point p: points) {
            y += p.y;
        }
        y = y / points.size();
        double summ = 0;
        for (Point p: points) {
            summ += Math.pow(p.y - y, 2);
        }
        return summ / points.size();
    }
}
