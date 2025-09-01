package lab04;

public class Circle implements Movable, Shape {
    
    private final static double minimumRadius = 1.0;

    private double radius = minimumRadius; //va inizializzato a valore minimi altrimenti triggera l'invariant
    //@ invariant radius >= minimumRadius

    private PointInterface anchor = new Point();


    //controlli di validazione
    public static double requireGeqMinimumRadius(double radius){
        if(radius < minimumRadius)
            throw new IllegalArgumentException("Invariant requireGeqMinimumRadius is not verified");
        return radius;
    }


    //costruttori (vuoto, normale, deep copy)
    public Circle(){

    }

    public Circle(double radius, PointInterface anchor){
        this.radius = Circle.requireGeqMinimumRadius(radius);
        this.anchor.move(anchor.getX(), anchor.getY());
    }

    public Circle(Circle other){
        this(other.radius, other.anchor);
    }

    
    //metodo factory
    public static Circle ofRadiusAnchor(double radius, PointInterface anchor){
        return new Circle(radius, anchor);
    }


    //metodi delle interfacce
    public double perimeter(){
        return 2*radius*Math.PI;
    }

    public double area(){
        return Math.PI*radius*radius;
    }

    @Override
    public void resize(double factor){
        radius = Circle.requireGeqMinimumRadius(radius*factor);
    }

    @Override
    public PointInterface getAnchorCopy(){
        return new Point(anchor);
    }

    @Override
    public void move(double dx, double dy){
        anchor.move(dx, dy);
    }
}

