package kelner;

public class Para implements Runnable{
    private Kelner kelner;
    private int numer;

    public Para(int numer, Kelner kelner){
        this.numer = numer;
        this.kelner = kelner;
    }

    public void run(){
        kelner.zarzadzaj();
    }
}
