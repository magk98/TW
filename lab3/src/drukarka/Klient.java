package drukarka;

public class Klient extends Thread {
    MonitorDrukarek monitorDrukarek;
    int number;

    Klient(MonitorDrukarek monitor, int number) {
        this.monitorDrukarek = monitor;
        this.number = number;
    }

    public void run() {
            try {
                Drukarka drukarka = monitorDrukarek.wezDrukarke();
                drukarka.print("Klient " + number + " drukuje na drukarce nr " + drukarka.numer);
                monitorDrukarek.oddajDrukarke(drukarka);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
