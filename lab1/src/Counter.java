public class Counter {
    int incValue;
    int decValue;
    Object o1 = new Object();
    Object o2 = new Object();



    public Counter(int value){
        this.incValue = 0;
        this.decValue = 0;
    }

    void incValue(){
        synchronized (o1) {
            incValue++;
        }
    }

    void decValue(){
        synchronized (o2) {
            decValue--;
        }
    }

}
