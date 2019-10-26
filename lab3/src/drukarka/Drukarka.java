package drukarka;

public class Drukarka implements Runnable{
    private MonitorDrukarek monitorDrukarek;

    public Drukarka(MonitorDrukarek monitorDrukarek){
        this.monitorDrukarek = monitorDrukarek;
    }

    public void run(){
        this.monitorDrukarek.drukuj();
    }
}
