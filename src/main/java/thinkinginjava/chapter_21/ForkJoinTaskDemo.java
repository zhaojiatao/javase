package thinkinginjava.chapter_21;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * @author zhaojiatao
 * @date 2019/3/12
 */
public class ForkJoinTaskDemo {
    static class CalculateSome extends RecursiveTask<Integer> {
        /**
         *
         */
        private static final long serialVersionUID = 174576842836225L;

        private static final int MAX_USE_SIZE = 20;
        private int[] arrTask;
        private int start;
        private int end;

        public CalculateSome(int[] arrTask, int start, int end) {
            this.arrTask = arrTask;
            this.start = start;
            this.end = end;
        }


        @Override
        protected Integer compute() {
            int sum = 0;
            if(end - start <= CalculateSome.MAX_USE_SIZE) {
                for (int i = start; i < end; i++) {
                    sum += arrTask[i];
                }
                System.out.println(Thread.currentThread().getName() + "compute ... " + sum);
                return sum;
            } else {
                System.out.println("----task separate----");
                int middle = (end + start) / 2;
                CalculateSome left = new CalculateSome(arrTask, start, middle);
                CalculateSome right = new CalculateSome(arrTask, middle, end);
                left.fork();
                right.fork();
                return sum = left.join() + right.join();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] arrTask = new int[1000];
        int sum = 0;
        for(int i=0; i< 1000; i++) {
            arrTask[i] = new Random().nextInt(100);
            sum += arrTask[i];
        }
        System.out.println(Runtime.getRuntime().availableProcessors());
        System.out.println("sum = " + sum);

        ForkJoinPool pool = new ForkJoinPool();
        Future<Integer> future = pool.submit(new CalculateSome(arrTask, 0, arrTask.length));
        Integer integer = future.get();
        System.out.println("fork/join result = " + integer);
        pool.shutdown();

    }
}
