package kelner;

public class Guest extends Thread{

    public Waiter waiter;
    public Couple couple;

    public Guest(Waiter waiter, Couple couple){
        this.waiter = waiter;
        this.couple = couple;
    }

    public void run(){
            try {
                Thread.sleep((long) (Math.random() * 1000));
                waiter.takeTable(couple);
                Thread.sleep((long) (Math.random() * 1000));
                waiter.returnTable();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }

        }
}
