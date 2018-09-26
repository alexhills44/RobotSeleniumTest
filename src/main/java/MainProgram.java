public class MainProgram implements Runnable{
    public MainProgram(){

    }
    public void run(){
        SeleniumMethods sl=new SeleniumMethods();
        sl.pageOpener("http://www.example.com");
        try {
            Thread.sleep(5000);
            sl.onClick("/html/body/div/p[2]/a");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
