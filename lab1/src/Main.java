public class Main {

    public static void main(String[] args){
        try{
            Counter c = new Counter(0);
            DecThread dec = new DecThread(c);
            IncThread inc = new IncThread(c);
            Thread t1 = new Thread(dec);
            Thread t2 = new Thread(inc);

            long start = System.currentTimeMillis();

            t1.start();
            t2.start();
            t1.join();
            t2.join();

            long finish = System.currentTimeMillis();
            System.out.println(c.incValue + c.decValue);
            System.out.println(finish - start);
        } catch(InterruptedException e){
            System.out.println("whatever");
        }
    }
}
