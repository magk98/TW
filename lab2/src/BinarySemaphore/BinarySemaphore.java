package BinarySemaphore;

class BinarySemaphore {
    private boolean available = true;

    synchronized void V(){//get
        while (!this.available){
            try {
                this.wait();
            }
            catch (InterruptedException e){
                System.out.println("Error in acquiring");
            }
        }
        this.available = false;
    }

    synchronized void P() {//release
        if (!this.available) {
            this.available = true;
            this.notifyAll();
        }
    }

}

