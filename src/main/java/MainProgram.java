import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public class MainProgram implements Runnable {

    SeleniumMethods sl;
    int yOffset=77;

    public MainProgram(){

    }

    public void screenCalibration() throws Exception{
        sl=new SeleniumMethods();
        sl.pageOpener("https://www.example.com");
        Robot robot=new Robot();
        int y= sl.getCoordinatesY("/html/body/div/p[2]/a");
        int y1=sl.getCoordinatesY("/html/body/div/p[2]/a");
        int x= sl.getCoordinatesX("/html/body/div/p[2]/a");

        while (sl.getText("/html/body/div/p[2]/a").equals("More information...")) {
            robot.mouseMove(x,y);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.delay(1000);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            System.out.println(y);
            y++;
            // save y so we can get it at startup
            //get distance
        }
        yOffset=y-y1;
    }

    public Point getMousePosition() {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        return b;
    }

    public void cursorMoveMethod1(Point destinationPoint){
        try {
            Robot robot=new Robot();
            Point point=getMousePosition();
            Double xd = point.getX();
            Double yd = point.getY();
            int y=yd.intValue();
            int x=xd.intValue();
            Double xd1 = destinationPoint.getX();
            Double yd1 = destinationPoint.getY();
            int y1=yd1.intValue();
            int x1=xd1.intValue();
            while (!(x==x1 && y==y1+yOffset)) {
                if(x<x1) {
                    x++;
                }else if(x>x1) {
                    x--;
                }
                if(y<y1+yOffset) {
                    y++;
                }else if (y>y1+yOffset) {
                    y--;
                }
                robot.mouseMove(x,y);
                Thread.sleep(5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Point randomRectPoint(String xpath) {
        Point destinationPoint=sl.getCoordinates(xpath);
        Point point = sl.getElementSurface(xpath);
        Random rand = new Random();
        Double pointX = point.getX()-2;
        int randomX = rand.nextInt(pointX.intValue())+1;
        Double pointY = point.getY()-2;
        int randomY = rand.nextInt(pointY.intValue())+1;
        Double xd = destinationPoint.getX();
        Double yd = destinationPoint.getY();
        int y=yd.intValue()+randomY;
        int x=xd.intValue()+randomX;
        Point returnPoint = new Point();
        returnPoint.setLocation(x,y);
        return returnPoint;
    }

    public void randomCursorMoveMethod1(String xpath) {
        cursorMoveMethod1(randomRectPoint(xpath));
    }

    public void run(){
        sl=new SeleniumMethods();
        sl.pageOpener("https://www.skroutz.gr/c/40/kinhta-thlefwna.html");
        //"/html/body/main/div[1]/section/div[2]/div/div/ul/li[1]/a"
        //sl.getElementSurface("/html/body/div[2]/ol/li[1]");
        randomCursorMoveMethod1("/html/body/main/div[1]/section/div[2]/div/div/ul/li[1]/a");
    }
}
