package drukarka;

public class Main {

    public static void main(String[] args){
        MonitorDrukarek monitorDrukarek = new MonitorDrukarek();
        Thread threads[] = new Thread[10];
        for (int i = 0; i < 10; i++){
            threads[i] = new Thread(new Drukarka(monitorDrukarek));
        }
        for (int i = 0; i < 10; i++){
            threads[i].start();
        }
    }
}
