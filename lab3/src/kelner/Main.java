package kelner;
import java.util.LinkedList;

public class Main {

    public static void main(String [] args){

        int couplesNumber = 10;
        LinkedList<Guest> guests = new LinkedList<>();
        Waiter waiter = new Waiter(couplesNumber);

        for(int i = 0; i < couplesNumber; i++) {
            Couple couple = new Couple(i);
            for(int j = 0; j < 2; j++){
                Guest guest = new Guest(waiter, couple);
                guests.add(guest);
                guest.start();
            }
        }
        for(Guest guest: guests){
            try{
                guest.join();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }


    }
}
