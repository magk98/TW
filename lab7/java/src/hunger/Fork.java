package hunger;

class Fork {
    private boolean isFree;

    Fork(boolean isFree) {
        this.isFree = isFree;
    }

    void setFree(boolean free) {
        isFree = free;
    }

    boolean isFree() {
        return isFree;
    }
}