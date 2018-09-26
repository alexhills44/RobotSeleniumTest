public class MainProgram implements Runnable{
    public MainProgram(){

    }
    public void run(){
        SeleniumMethods sl=new SeleniumMethods();
        sl.pageOpener("http://www.example.com");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        sl.onClick("/html/body/div/p[2]/a");
    }
}
