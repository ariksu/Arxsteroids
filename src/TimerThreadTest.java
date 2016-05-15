/**
 * Created by Mike on 8/9/2015.
 */
public class TimerThreadTest {
    public static class ThreadStart implements Runnable{
        public void run(){

        }
    }

    public static void main (String[] args){
        long t0=System.nanoTime();
        for (int i = 0; i < 100; i++) {
            continue;
        }
        long t1=System.nanoTime();
        Thread[] threadholder = new Thread[1000];
        long t2=System.nanoTime();
        for (int i = 0; i < 100; i++) {
            threadholder[i] = new Thread(new ThreadStart());
            threadholder[i].start();
        }
        long t3=System.nanoTime();
        System.out.println("Empty cycle :"+(t1-t0));
        System.out.println("Thread cycle:"+(t3-t2));
        for (int i = 0; i < 100; i++) {
            if (threadholder[i].isAlive()==true)
                try {
                    threadholder[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
        long t4=System.nanoTime();
        for (int i = 0; i < 100; i++) {
            t1=i-t0;
        }
        long t5=System.nanoTime();
        System.out.println("Calc time:"+(((t5-t4)-1000)/100));

    }
}
