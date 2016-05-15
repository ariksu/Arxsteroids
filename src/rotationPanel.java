import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;

import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class rotationPanel extends JPanel {

    private static final long serialVersionUID = 117L;



    private static final int SIZE = 500;


    // point arrays which contain the points that are rotated around the center 
    Point[] points1;
    Point[] points2;
    Point[] points3;

    // The center of rotation  
    Point center;

    // the polygons being rotated
    Polygon poly1;
    Polygon poly2;
    Polygon poly3;

    // the angle of rotation
    double angle;


    Timer timer;
    long start;
    long sleepTime;
    static int runTime;





    public rotationPanel(){
        setSize(500,500);
        setBackground(Color.DARK_GRAY);
        setVisible(true);

        // time loop is set to run at fixed rate of 50 ms
        runTime = 50;

        start = 0;
        sleepTime = 0;
        timer = new Timer();
        angle = 0;


        // initializing the arrays (not neccesary)
        points1 = getOriginalPoints(1);
        points3 = getOriginalPoints(3);
        points2 = getOriginalPoints(2);

        // setting the rotation to the middle of the screen
        center = new Point(250,250);


        // start the looping
        mainloop();

    }

    public void mainloop(){
        start= System.currentTimeMillis();

        // rotate the points the spcified angle and store the rotated
        //points to the correct array
        rotatePointMatrix(getOriginalPoints(1),angle,points1);
        rotatePointMatrix(getOriginalPoints(2),angle,points2);
        rotatePointMatrix(getOriginalPoints(3),angle,points3);

        // Make the points into a polygon
        poly1 = polygonize(points1);
        poly2 = polygonize(points2);
        poly3 = polygonize(points3);

        // increase the angle by one degree, resulting to rotation in the longer run
        angle++;



        if (angle>=360){
            angle=0;
        }



        // restatring the sequence 
        repaint();

        sleepTime = runTime -(System.currentTimeMillis()-start);
        System.out.println("Looped. Sleeping for:" +sleepTime+"ms");

        if(sleepTime>0)
            timer.schedule(new loop(), sleepTime);
        else
            mainloop();

    }





    public void rotatePointMatrix(Point[] origPoints, double angle, Point[] storeTo){

        /* We ge the original points of the polygon we wish to rotate
         *  and rotate them with affine transform to the given angle. 
         *  After the opeariont is complete the points are stored to the 
         *  array given to the method.
        */
        AffineTransform.getRotateInstance
                (Math.toRadians(angle), center.x, center.y)
                .transform(origPoints,0,storeTo,0,5);


    }

    public Polygon polygonize(Point[] polyPoints){

        //a simple method that makes a new polygon out of the rotated points
        Polygon tempPoly = new Polygon();

        for(int  i=0; i < polyPoints.length; i++){
            tempPoly.addPoint(polyPoints[i].x, polyPoints[i].y);
        }

        return tempPoly;

    }


    public Point[] getOriginalPoints(int type){

        /* In this example the rotated "polygon" are stored in this method. 
         * The Point is that if we want to rotate a polygon constatnly/frequently
         * we cannot use the values of an already rotated polygon as this will 
         * lead to the polygon deforming severely after few translations due 
         * to the points being constantly rounded. So the trick is to save the
         * original Points of the polygon and always rotate that one to the new
         *  angle instead of rotating the same one again and again.
        */
        Point[] originalPoints = new Point[5];

        if(type == 2){

            originalPoints[0]= new Point(200, 100);
            originalPoints[1]= new Point(250, 50);
            originalPoints[2]= new Point(300, 100);
            originalPoints[3]= new Point(300, 400);
            originalPoints[4]= new Point(200, 400);



        }

        else if(type == 1){

            originalPoints[0]= new Point(210, 150);
            originalPoints[1]= new Point(250, 150);
            originalPoints[2]= new Point(250, 190);
            originalPoints[3]= new Point(230, 220);
            originalPoints[4]= new Point(210, 190);


        }
        else{

            originalPoints[0]= new Point(250, 300);
            originalPoints[1]= new Point(290, 300);
            originalPoints[2]= new Point(290, 340);
            originalPoints[3]= new Point(270, 370);
            originalPoints[4]= new Point(250, 340);

        }


        return originalPoints;

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.GRAY);
        g2d.fillPolygon(poly2);

        g2d.setColor(Color.yellow);
        g2d.fillPolygon(poly1);

        g2d.setColor(Color.yellow);
        g2d.fillPolygon(poly3);


        g2d.setColor(Color.WHITE);
        for(int  i=0; i < points1.length; i++){
            g2d.fillRect(points1[i].x-1, points1[i].y-1, 3, 3);
            g2d.fillRect(points3[i].x-1, points3[i].y-1, 3, 3);

        }

        g2d.setColor(Color.BLUE);
        g2d.fillOval(center.x-4, center.y-4, 8, 8);

        g2d.setColor(Color.yellow);
        g2d.drawString("Angle: "+angle, 10,450);

    }

    class loop extends TimerTask{


        public void run() {
            mainloop();

        }

    }


    public static void main(String[] args){
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new rotationPanel());
        f.setSize(500,500);
        f.setVisible(true);

    }

}