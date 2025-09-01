package lab04;

public class Rectangle implements Movable, Shape {

    private static final double minimumWidth = 1.0;
    private static final double minimumHeight= 1.5;
    
    private double width = minimumWidth; //va inizializzato a valore minimi altrimenti triggera l'invariant
    private double height = minimumHeight; //va inizializzato a valore minimi altrimenti triggera l'invariant
    //@ invariant wdith >= minimumWidth && height >= minimumHeight

    private PointInterface anchor = new Point();


    //controlli di validazione
    public static double requireGeqMinimumWidth(double width){
        if(width < minimumWidth)
            throw new IllegalArgumentException("Invariant requireGeqMinimumWidth not verified");
        return width;
    }

    public static double requireGeqMinimumHeight(double height){
        if(height < minimumHeight)
            throw new IllegalArgumentException("Invariant requireGeqMinimumHeight not verified");
        return height;
    }


    //costruttori (vuoto, normale, deep copy)
    public Rectangle(){

    }

    public Rectangle(double width, double height, PointInterface anchor){
        this.width = Rectangle.requireGeqMinimumWidth(width);
        this.height = Rectangle.requireGeqMinimumHeight(height);
        this.anchor.move(anchor.getX(), anchor.getY());
    }

    public Rectangle(Rectangle other){
        this(other.width, other.height, other.anchor);
    }
   
    //metodo factory
    public static Rectangle ofWidthHeightAnchor(double width, double height, PointInterface anchor){
        return new Rectangle(width, height, anchor);
    }


    //metodi delle interfacce
    public double perimeter(){
        return 2*(width+height);
    }

    public double area(){
        return width*height;
    }

    @Override
    public void resize(double factor){
        width = Rectangle.requireGeqMinimumWidth(width*factor);
        height = Rectangle.requireGeqMinimumHeight(height*factor);     
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
