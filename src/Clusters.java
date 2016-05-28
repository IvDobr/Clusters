
import java.text.DecimalFormat;
import java.util.*;

public class Clusters {

    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in); //штука, которая читает данные с консоли
        DecimalFormat df = new DecimalFormat("#.###"); //Формат вывода дробных чисел

        System.out.println("Введите количество точек:");
        int n = in.nextInt(); //берем количество точек

        System.out.println("Введите фи"); //От кластера к кластеру
        double phi = in.nextDouble(); //вводим фи

        System.out.println("Введите пси"); //От точки к кластеру
        double psi = in.nextDouble(); //вводим пси

        ArrayList<Point> points = new ArrayList<Point>(); //создаем массив всех точек
        System.out.println("Введите " + n + " точек (последовательно x y через enter или пробел)");

        for (int i = 0; i < n; i++) {
            points.add(new Point(in.nextDouble(), in.nextDouble())); //заполняем массив точек из консоли
        }

        ArrayList<Cluster> clasters = new ArrayList<Cluster>(); //создаем массив кластеров
        Cluster c1 = new Cluster(points.get(0)); //создаем первый кластер с первой точкой в нем
        clasters.add(c1); //добавляем этот кластер в массив

        for (int i = 1; i < n; i++) { //генеральцый цикл - перебираем точки со второй
            System.out.println("Итерация " + i);

            TreeMap<Integer, Double> treeMap = new TreeMap<Integer, Double>(); //массив номеров кластеров, расстояние к которым у точки меньше PSI

            for (int j = 0; j < clasters.size(); j++) { //перебираем все кластеры, а внутри берем у каждого центр тяжести
                System.out.println("В точке " + i + " расстояние до кластера " + j + ": " + df.format(distance(clasters.get(j).cg(), points.get(i))));
                if (distance(clasters.get(j).cg(), points.get(i)) < psi) { // рассмотриваем точку, записываем все кластеры расстояние до которых меньше пси
                    treeMap.put(j, distance(clasters.get(j).cg(), points.get(i)));
                }
            }

            if (!treeMap.isEmpty()) { //Проверяем, пустой ли массив с подходящими кластерами
                clasters.get(treeMap.firstKey()).addPoint(points.get(i)); //если не пустой, то добавляем точку в ближайший клвстер
                System.out.println("Мы выбрали расстояние: "+ df.format(treeMap.get(treeMap.firstKey())) + " и кладем точку в кластер " + treeMap.firstKey()); //ЛОГИ
            } else {
                System.out.println("Создаем новый кластер");
                Cluster newC = new Cluster(points.get(i)); //если пустой то создаем новый кластер с этой точкой
                clasters.add(newC);
            }

        } //генеральный цикл

        //Теперь задача: объединить кластеры, расстояние между которыми меньше PHI
        /** Описание работы: метод работает зацикленно, пока есть кластеры, расстояние между которыми меньше пси
         * Сначала алгоримт сравнивает растояния между кластерами: каждый с каждым. При этом запоминается только последняя найденная пара.
         * Затем эта пара объединяется, и поиск повторяется. Если близких кластеров нет, то значение переменной flag становится false и цикл останавливается.
         * Данная зацикленность необходими, потому что при слиянии кластеров условия могут поменяться и требуется новая проверка.
        */
        System.out.println("-----------------------------------------------");
        System.out.println("Начинаем процесс объединения кластеров после главного цикла" );
        boolean flag = true; //флаг, обозначающий надобность проверки кластеров - true - означает, что проверять надо
        while (flag) {
            int del1 = -1;
            int del2 = -1; //две переменные, в которые мы записывает номера объединяемых кластеров
            for (int j = 0; j < clasters.size() - 1; j++) {
                for (int k = j + 1; k < clasters.size(); k++) { //сравниваем кластеры каждый с каждым
                    System.out.println("Расстояние между кластерами " + j + " и " + k + " = " + df.format(distance(clasters.get(j).cg(), clasters.get(k).cg())));
                    if (distance(clasters.get(j).cg(), clasters.get(k).cg()) < phi) {
                        System.out.println("Мы желаем удалить кластер " + k + " поместив его точки в " + j);
                        del1 = j; //записываем пару кластеров
                        del2 = k;
                    }
                }
            }
            if ((-1 != del1) && (-1 != del2)) {
                System.out.println("Мы удаляем кластер " + del2 + " помещая его точки в " + del1);
                clasters.get(del1).addAll(clasters.get(del2).getPoints());
                clasters.remove(del2);
                System.out.println("Проверяем еще раз");
            } else {
                flag = false;
                System.out.println("Больше нет кластеров, расстояние между которыми меньше " + phi);
            }
        }

        //Вычислим критерий качества
        double d30k;
        double sum = 0;
        for (Cluster cl : clasters) {
            sum += cl.getSize()*cl.dispersion();
        }
        d30k = sum/points.size() + 30*clasters.size();

        System.out.println("-----------------------------------------------");
        System.out.println("Всего кластеров получилось: " + clasters.size());
        System.out.println("Критерий качества: " + df.format(d30k));
        for (Cluster ct: clasters) {
            System.out.println("Кластер имеет " + ct.getSize() + " точек, дисперсия кластера: " + df.format(ct.dispersion()));
        }
    }
}
