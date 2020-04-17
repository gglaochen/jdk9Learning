package Flow.demo;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 启动类
 *
 * @author ChenHanLin 2020/4/17
 */
public class BootStrap {
    private static void demoSubscribe(DockerXDemoPublisher<Integer> publisher, String subscriberName) {
        DockerXDemoSubscriber<Integer> subscriber = new DockerXDemoSubscriber<>(3L, subscriberName);
        publisher.subscribe(subscriber);
    }

    public static void main(String[] args) {
        ExecutorService executorService = ForkJoinPool.commonPool();
        try (DockerXDemoPublisher<Integer> publisher = new DockerXDemoPublisher<>(executorService)) {
            demoSubscribe(publisher, "One");
            demoSubscribe(publisher, "Two");
            demoSubscribe(publisher, "Three");
            IntStream.range(1, 5).forEach(publisher::submit);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                executorService.shutdown();
                int shutdownDelaySec = 1;
                System.out.println("......等待 " + shutdownDelaySec + " 秒后结束服务....");
                executorService.awaitTermination(shutdownDelaySec, TimeUnit.SECONDS);
            } catch (Exception ex) {
                System.out.println("捕获到 execService.awaitTermination()方法的异常：" + ex.getClass().getName());
            } finally {
                System.out.println("调用 execService.shutdownNow()结束服务...");
                List<Runnable> l = executorService.shutdownNow();
                System.out.println("还剩 " + l.size() + " 个任务等待执行，服务已关闭");
            }
        }
    }
}
