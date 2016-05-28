import java.text.DecimalFormat;
import java.util.ArrayList;

class Cluster {

    private ArrayList<Point> points = new ArrayList<>(); //точки кластера

    public Cluster() { //создаем кластер без точки
    }

    Cluster(Point point) { //создаем кластер с точкой
        points.add(point);
    }

    void addPoint(Point point) { //додавляем в кластер точку
        points.add(point);
    }

    ArrayList<Point> getPoints() { //кластер отдает список своих точек
        return points;
    }

    int getSize() { //класс отдает свой размер
        return points.size();
    }

    void addAll(ArrayList<Point> new_points) {
        points.addAll(new_points);
    }

    Point cg() { //центр тяжести кластера
        double x = 0, y = 0;
        for (Point p: points) {
            x += p.x;
            y += p.y;
        }
        x = x / points.size();
        y = y / points.size();
        return new Point(x, y);
    }

    double dispersion() { //дисперсия кластера
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

    String cgToString() { //выводит центр тяжести кластера в текстовом формате (x, y)
        DecimalFormat df = new DecimalFormat("#.###"); //Формат вывода дробных чисел
        Point cg = cg();
        return "("+df.format(cg.x)+", "+df.format(cg.y)+")";
    }
}