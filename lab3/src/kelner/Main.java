package kelner;

public class Main {
    public static void main(String[] args){
        Kelner kelner = new Kelner();
        Thread threads[] = new Thread[10];
        for (int i = 0; i < 10; i++){
            threads[i] = new Thread(new Para(i, kelner));
        }
        for (int i = 0; i < 10; i++){
            threads[i].start();
        }
    }

}
