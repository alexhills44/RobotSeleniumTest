import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

import static java.awt.event.KeyEvent.*;


class MouseMovement {
    private SeleniumMethods sl;
    private Robot robot;
    private Random rand;
    private int yOffset= PropertiesHandler.getYOffset();
    private Rectangle winSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();

    // TODO : Move the mouse more random
    MouseMovement(SeleniumMethods sl0){
        rand = new Random();
        sl=sl0;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    // Left clicks and release the mouse button with a start delay of 30-60
    // NO DELAY NEEDED FOR THIS METHOD
    void onLeftClick() {
        randomDelay(300,600);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(rand.nextInt(300)+50);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
        }

    // Delays the program for min-max miliseconds
    void randomDelay(int min,int max) {
        try {
            int delayTime=rand.nextInt(max-1-min)+min;
            robot.delay(delayTime);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("randomDelay : min has to be smaller than max");
        }
    }

    // Takes a string splits in a char array and calls the type(method) to execute right button press
    void typeString(String string) {
        randomDelay(30,60);
        char[] charArray = string.toCharArray();
        for (char  c:charArray) {
            type(c);
            randomDelay(200,600);
        }
    }

    @SuppressWarnings("Duplicates")
    //Checks if the element is on the Screen , else scrolls by 30 with a delay of 500-1000ms
    //BETWEEN EACH SCROLL IT DELAYS 500-700MS
    void scrollToView(String xpath) {
        long x = -1;
        // multiply by -1 to change direction
        int directionScrolled=1;
        int scrollValue;
        // The higher the value the lower the speed
        int scrollSpeed =1;
        //while element isn't on screen
        while(!elementOnScreen(xpath)) {
            if(sl.getScrolledY()==x) {
                directionScrolled=directionScrolled*(-1);
                scrollSpeed = scrollSpeed*2;
            }
            scrollValue = ((rand.nextInt(130)/scrollSpeed)+1)*directionScrolled;
            x=sl.getScrolledY();
            robot.mouseWheel(scrollValue);
            randomDelay(500,700);
            if (scrollSpeed==128) {
                scrollSpeed=1;
            }
        }
        randomCursorMoveMethod1(xpath);
    }

    @SuppressWarnings("Duplicates")
    //Checks if the element is on the Screen , else scrolls by 30 with a delay of 500-1000ms FOR IFRAME
    void scrollToViewIFRAME(String xpath) {
        long x = -1;
        // multiply by -1 to change direction
        int directionScrolled=1;
        int scrollValue;
        // The higher the value the lower the speed
        int scrollSpeed =1;
        //while element isn't on screen
        while(!elementOnScreenIFrame(xpath)) {
            if(sl.getScrolledY()==x) {
                directionScrolled=directionScrolled*(-1);
                scrollSpeed = scrollSpeed*2;
            }
            scrollValue = ((rand.nextInt(130)/scrollSpeed)+1)*directionScrolled;
            x=sl.getScrolledY();
            robot.mouseWheel(scrollValue);
            randomDelay(500,700);
            if (scrollSpeed==128) {
                scrollSpeed=1;
            }
        }
        randomCursorMoveMethod1IFrame(xpath);
    }

    @SuppressWarnings("SameParameterValue")
    // Same as Scroll to view but checks the screen from 0-500 pixes
    void scrollToViewForZero(String xpath) {
        while(!elementOnScreenForZero(xpath)) {
            robot.mouseWheel(30);
            randomDelay(500,1000);
        }
        randomCursorMoveMethod1(xpath);
    }

    // Combines the randomRectPoint and the CursorMoveMethod1
    private void randomCursorMoveMethod1(String xpath) {
        cursorMoveMethod1(randomRectPoint(xpath));
    }

    // Combines the randomRectPoint and the CursorMoveMethod1 FOR IFRAME
    private void randomCursorMoveMethod1IFrame(String xpath) {
        cursorMoveMethod1IFrame(randomRectPoint(xpath));
    }

    // A method that presses Enter key
    void pressEnter() {
        randomDelay(30,60);
        robot.keyPress(KeyEvent.VK_ENTER);
        randomDelay(50,300);
        robot.keyRelease(KeyEvent.VK_ENTER);
        randomDelay(50,100);
    }

    // Moves the mouse at the Bar of the WebBrowser
    void moveMouseToMain() {
        robot.mouseMove(rand.nextInt(798)+1,rand.nextInt(yOffset));
    }

    // gets the current position of the mouse
    private Point getMousePosition() {
        PointerInfo a = MouseInfo.getPointerInfo();
        return a.getLocation();
    }

    // gets the starting point and the destination for the mouse and adds the YOffset value
    @SuppressWarnings("Duplicates")
    private void cursorMoveMethod1(Point destinationPoint){
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
                Thread.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // gets the starting point and the destination for the mouse and adds the YOffset value FOR IFRAME
    @SuppressWarnings("Duplicates")
    private void cursorMoveMethod1IFrame(Point destinationPoint){
        try {
            Point point=getMousePosition();
            Double xd = point.getX();
            Double yd = point.getY();
            int y=yd.intValue();
            int x=xd.intValue();
            Double xd1 = destinationPoint.getX()+ PropertiesHandler.getiFramePointX();
            Double yd1 = destinationPoint.getY()+ PropertiesHandler.getiFramePointY();
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
                Thread.sleep(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Gets the surface and coordinates for the element specified and return a point somewhere randomly on the element
    private Point randomRectPoint(String xpath)  {
        Point destinationPoint=sl.getCoordinates(xpath);
        Point point = sl.getElementSurface(xpath);
        Double hotSpot;
        Double pointX = point.getX()-4;
        int randomX = rand.nextInt(pointX.intValue()+1)+1;
        Double pointY = point.getY()-4;
        int randomY = rand.nextInt(pointY.intValue()+1)+1;
        int chances = rand.nextInt(100)+1;
        //60% chance
        if(chances>40) {
            //get point
            pointX = pointX/2;
            pointY = pointY/2;
            hotSpot = point.getX() / 4;
            randomX = rand.nextInt(pointX.intValue()) + hotSpot.intValue();
            hotSpot = point.getY() / 4;
            randomY = rand.nextInt(pointY.intValue()) + hotSpot.intValue();
        //30% chance
        }else if (chances>10) {
            pointX = pointX*0.7;
            pointY = pointY*0.7;
            hotSpot = point.getX()*0.15;
            randomX = rand.nextInt(pointX.intValue()) + hotSpot.intValue();
            hotSpot = point.getY() *0.15;
            randomY = rand.nextInt(pointY.intValue()) + hotSpot.intValue();
        }
        //10% chance leave as it is


        Double xd = destinationPoint.getX();
        Double yd = destinationPoint.getY();
        Long scrolledPixels = sl.getScrolledY();
        int y=yd.intValue() + randomY - scrolledPixels.intValue();
        int x=xd.intValue() + randomX;
        Point returnPoint = new Point();
        returnPoint.setLocation(x,y);
        return returnPoint;
    }

    // Checks if the element in the specified Xpath is on the web screen (1-500 pixels) and return boolean
    private boolean elementOnScreen (String xpath) {
        boolean isDisplayed;
        int point = sl.getCoordinatesY(xpath);
        isDisplayed = sl.getScrolledY() + winSize.height >= point && point >= sl.getScrolledY() + 1;
        return isDisplayed;
    }

    // Checks if the element in the specified Xpath is on the web screen (1-500 pixels) and return boolean FOR IFRAME
    private boolean elementOnScreenIFrame (String xPathIframe) {
        boolean isDisplayed;
        int point = PropertiesHandler.getiFramePointY()+sl.getCoordinatesY(xPathIframe);
        isDisplayed = sl.getScrolledY() + winSize.height >= point && point >= sl.getScrolledY() + 1;
        return isDisplayed;
    }

    // Checks if the element in the specified Xpath is on the web screen (0-500 pixels) and return boolean
    private boolean elementOnScreenForZero (String xpath) {
        boolean isDisplayed;
        int point = sl.getCoordinatesY(xpath);
        isDisplayed = sl.getScrolledY() + winSize.height >= point && point >= sl.getScrolledY();
        return isDisplayed;
    }

    // Runs the awt.Robot method keyPress and keyRelease with a delay 50-400ms and 50-100ms
    private void doType(int value) {
        randomDelay(50,200);
        robot.keyPress(value);
        randomDelay(30,50);
        robot.keyRelease(value);
    }

    // Runs the awt.Robot method keyPress and keyRelease with a delay 50-400ms and 50-100ms (For Capital and Special Chars)
    private void doType(int shift,int value) {
        randomDelay(50,200);
        robot.keyPress(shift);
        randomDelay(50,400);
        robot.keyPress(value);
        randomDelay(30,50);
        robot.keyRelease(value);
        randomDelay(30,50);
        robot.keyRelease(shift);
    }

    //Extremely large switch statement that contains values for every char contained
    // if it doesn't contain char then it throws an ERROR
    private void type(char character) {
        switch (character) {
            case 'a': doType(VK_A); break;
            case 'b': doType(VK_B); break;
            case 'c': doType(VK_C); break;
            case 'd': doType(VK_D); break;
            case 'e': doType(VK_E); break;
            case 'f': doType(VK_F); break;
            case 'g': doType(VK_G); break;
            case 'h': doType(VK_H); break;
            case 'i': doType(VK_I); break;
            case 'j': doType(VK_J); break;
            case 'k': doType(VK_K); break;
            case 'l': doType(VK_L); break;
            case 'm': doType(VK_M); break;
            case 'n': doType(VK_N); break;
            case 'o': doType(VK_O); break;
            case 'p': doType(VK_P); break;
            case 'q': doType(VK_Q); break;
            case 'r': doType(VK_R); break;
            case 's': doType(VK_S); break;
            case 't': doType(VK_T); break;
            case 'u': doType(VK_U); break;
            case 'v': doType(VK_V); break;
            case 'w': doType(VK_W); break;
            case 'x': doType(VK_X); break;
            case 'y': doType(VK_Y); break;
            case 'z': doType(VK_Z); break;
            case 'A': doType(VK_SHIFT, VK_A); break;
            case 'B': doType(VK_SHIFT, VK_B); break;
            case 'C': doType(VK_SHIFT, VK_C); break;
            case 'D': doType(VK_SHIFT, VK_D); break;
            case 'E': doType(VK_SHIFT, VK_E); break;
            case 'F': doType(VK_SHIFT, VK_F); break;
            case 'G': doType(VK_SHIFT, VK_G); break;
            case 'H': doType(VK_SHIFT, VK_H); break;
            case 'I': doType(VK_SHIFT, VK_I); break;
            case 'J': doType(VK_SHIFT, VK_J); break;
            case 'K': doType(VK_SHIFT, VK_K); break;
            case 'L': doType(VK_SHIFT, VK_L); break;
            case 'M': doType(VK_SHIFT, VK_M); break;
            case 'N': doType(VK_SHIFT, VK_N); break;
            case 'O': doType(VK_SHIFT, VK_O); break;
            case 'P': doType(VK_SHIFT, VK_P); break;
            case 'Q': doType(VK_SHIFT, VK_Q); break;
            case 'R': doType(VK_SHIFT, VK_R); break;
            case 'S': doType(VK_SHIFT, VK_S); break;
            case 'T': doType(VK_SHIFT, VK_T); break;
            case 'U': doType(VK_SHIFT, VK_U); break;
            case 'V': doType(VK_SHIFT, VK_V); break;
            case 'W': doType(VK_SHIFT, VK_W); break;
            case 'X': doType(VK_SHIFT, VK_X); break;
            case 'Y': doType(VK_SHIFT, VK_Y); break;
            case 'Z': doType(VK_SHIFT, VK_Z); break;
            case '`': doType(VK_BACK_QUOTE); break;
            case '0': doType(VK_0); break;
            case '1': doType(VK_1); break;
            case '2': doType(VK_2); break;
            case '3': doType(VK_3); break;
            case '4': doType(VK_4); break;
            case '5': doType(VK_5); break;
            case '6': doType(VK_6); break;
            case '7': doType(VK_7); break;
            case '8': doType(VK_8); break;
            case '9': doType(VK_9); break;
            case '-': doType(VK_MINUS); break;
            case '=': doType(VK_EQUALS); break;
            case '~': doType(VK_SHIFT, VK_BACK_QUOTE); break;
            case '!': doType(VK_SHIFT,VK_1); break;
            case '@': doType(VK_AT); break;
            case '#': doType(VK_NUMBER_SIGN); break;
            case '$': doType(VK_DOLLAR); break;
            case '%': doType(VK_SHIFT, VK_5); break;
            case '^': doType(VK_CIRCUMFLEX); break;
            case '&': doType(VK_AMPERSAND); break;
            case '*': doType(VK_ASTERISK); break;
            case '(': doType(VK_LEFT_PARENTHESIS); break;
            case ')': doType(VK_RIGHT_PARENTHESIS); break;
            case '_': doType(VK_UNDERSCORE); break;
            case '+': doType(VK_PLUS); break;
            case '\t': doType(VK_TAB); break;
            case '\n': doType(VK_ENTER); break;
            case '[': doType(VK_OPEN_BRACKET); break;
            case ']': doType(VK_CLOSE_BRACKET); break;
            case '\\': doType(VK_BACK_SLASH); break;
            case '{': doType(VK_SHIFT, VK_OPEN_BRACKET); break;
            case '}': doType(VK_SHIFT, VK_CLOSE_BRACKET); break;
            case '|': doType(VK_SHIFT, VK_BACK_SLASH); break;
            case ';': doType(VK_SEMICOLON); break;
            case ':': doType(VK_COLON); break;
            case '\'': doType(VK_QUOTE); break;
            case '"': doType(VK_QUOTEDBL); break;
            case ',': doType(VK_COMMA); break;
            case '<': doType(VK_SHIFT, VK_COMMA); break;
            case '.': doType(VK_PERIOD); break;
            case '>': doType(VK_SHIFT, VK_PERIOD); break;
            case '/': doType(VK_SLASH); break;
            case '?': doType(VK_SHIFT, VK_SLASH); break;
            case ' ': doType(VK_SPACE); break;
            default:
                throw new IllegalArgumentException("Cannot type character " + character);
        }
    }
    // TODO : this has to become better its pathetic
    void moveMouseToXIframe() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double x = screenSize.getWidth() * 95 / 100;
        PointerInfo a=MouseInfo.getPointerInfo();
        Point point=new Point();
        point.setLocation(x,a.getLocation().getY());
        cursorMoveMethod1(point);
    }
}
