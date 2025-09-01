package lab04;

public class Shapes {
    
    public static Shape max(ShapeComparator comp, Shape[] shapes){
        if(comp == null || shapes == null)
            throw new NullPointerException("comp or shapes cannot be null");
        else if(shapes.length == 0) return null;

        Shape temp = shapes[0];
        for(int i = 1; i < shapes.length; i++){
            if(comp.compare(shapes[i], temp) > 0)
                temp = shapes[i];
        }
        return temp;
    }

    public static void move(Movable[] movable, double dx, double dy){
        if(movable == null)
            throw new NullPointerException("movable cannot be null");         
        for(int i = 0; i < movable.length; i++){
            movable[i].move(dx, dy);
        }
    }

    public static void resize(Shape[] shapes, double factor){
        if(shapes == null)
            throw new NullPointerException("shapes cannot be null");   
        for(int i = 0; i < shapes.length; i++){
            shapes[i].resize(factor);
        }           
    }

    public static double area(Shape[] shapes){
        if(shapes == null)
            throw new NullPointerException("shapes cannot be null");
        double sum = 0;
        for(int i = 0; i < shapes.length; i++){
            sum += shapes[i].area();
        }    
        return sum;    
    }
}
