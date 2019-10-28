package drukarka;

public class Drukarka{
    public int numer;

    public Drukarka(int numer){
        this.numer = numer;
    }
    public void print(String tekst) throws InterruptedException {
        Thread.sleep((long) (Math.random() * 1000));
        System.out.println(tekst);
    }
}
