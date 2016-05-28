class Point {
    double x;
    double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() { //выводит точку в текстовом формате (x, y)
        return "("+this.x+", "+this.y+")";
    }
}
