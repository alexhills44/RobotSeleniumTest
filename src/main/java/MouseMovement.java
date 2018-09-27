import org.openqa.selenium.interactions.Mouse;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Random;

public class MouseMovement {
    SeleniumMethods sl;
    Robot robot;
    int yOffset=77;//afairesi

    public MouseMovement(SeleniumMethods sl0){
        sl=sl0;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public Point getMousePosition() {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        return b;
    }

    public void cursorMoveMethod1(Point destinationPoint){
        try {
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
        Long scrolledPixels = sl.getScrolledY();
        int y=yd.intValue() + randomY - scrolledPixels.intValue();
        int x=xd.intValue() + randomX;
        Point returnPoint = new Point();
        returnPoint.setLocation(x,y);
        return returnPoint;
    }

    public void randomCursorMoveMethod1(String xpath) {
        cursorMoveMethod1(randomRectPoint(xpath));
    }

    public boolean elementOnScreen (String xpath) {
        boolean isDisplayed;
        int point = sl.getCoordinatesY(xpath);
        if (sl.getScrolledY()+500>point &&point>sl.getScrolledY()+50) {
            isDisplayed=true;
        }else {
            isDisplayed=false;
        }
        return isDisplayed;
    }

    public void scrollToView(String xpath) {
        moveMouseToMain();
        while(!elementOnScreen(xpath)) {
            try {
                robot.mouseWheel(100);
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        randomCursorMoveMethod1(xpath);
    }

    public void moveMouseToMain() {
        Random rand = new Random();
        robot.mouseMove(rand.nextInt(798)+1,rand.nextInt(598)+1);
    }

    public void onLeftClick() {
        Random rand=new Random();
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(rand.nextInt(300)+50);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }


}
