import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadTerminal extends Thread{

    static boolean stopProgram=false;

    /**
     * This Class controlls main program
     */
    ReadTerminal() {
    }
    //Σουηδία - Basketligan Γυναίκες---Χόμπσο Μπάσκετ Γυναικών---2---U 160.5

    @Override
    public void run() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Started MainProgram.class");
        ExecutorService x = Executors.newFixedThreadPool(1);
        x.execute(new MainProgram());
        while (scan.hasNext()) {
            String line = scan.nextLine();
            if (line.equals("stop")) {
                stopProgram=true;
            }
            try {
                String[] lineArray = line.split("---");
                @SuppressWarnings("unused")
                String test = lineArray[3]; // Try to get fourth and last element of array
                Main.tipList.add(line);
                Main.tipSendTime.add(System.nanoTime());
            }catch (ArrayIndexOutOfBoundsException e){
                Logger.logStringtoLogFile("Tip hasnt got proper structure! IT CANNOT BE PLAYED! ----> ReadTerminal()");
                System.out.println("Tip hasnt got proper structure! IT CANNOT BE PLAYED!");
            }

        }
    }

}
