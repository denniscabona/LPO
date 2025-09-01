package lab04;

// compares shapes by computing their areas

public class AreaComparator implements ShapeComparator {
    
    public int compare(Shape shape1, Shape shape2){
        if(shape1.area() > shape2.area()) return 1;
        else if(shape1.area() < shape2.area()) return -1;
        return 0;
    }
}
