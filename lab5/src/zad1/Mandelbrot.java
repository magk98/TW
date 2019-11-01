package zad1;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame implements Callable {

    static final int threadsQuantity = 8;
    private final int MAX_ITER = 100;
    private final double ZOOM = 150;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;
    private int threadNumber;

    public Mandelbrot(int threadNumber, BufferedImage I) {
        super("Mandelbrot Set");
        this.I = I;
        this.threadNumber = threadNumber;
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public Integer call(){
        for (int y = this.threadNumber; y < getHeight(); y += threadsQuantity){
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                I.setRGB(x, y, iter | (iter << 8));
            }
        }
        return 0;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(threadsQuantity);
        Set<Future> set = new HashSet<>();
        BufferedImage I = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);

        long startTime = System.currentTimeMillis();
        Mandelbrot m = null;
        for(int i = 0; i < threadsQuantity; i++){
            m = new Mandelbrot(i, I);
            Callable<Mandelbrot> callable = m;
            Future<Mandelbrot> future = pool.submit(callable);
            set.add(future);
        }
        try {
            for (Future<Mandelbrot> future: set)
                future.get();
        } catch(ExecutionException | InterruptedException e) {
            System.out.println("Whatever");
        }
        m.setVisible(true);

        long finishTime = System.currentTimeMillis();
        System.out.println(finishTime - startTime + " ms");
    }
}