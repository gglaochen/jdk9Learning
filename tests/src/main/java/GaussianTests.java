import org.apache.commons.math3.fitting.GaussianCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import java.util.stream.IntStream;

/**
 * 正态分布测试
 *
 * @author ChenHanLin 2020/8/4
 */
public class GaussianTests {
    public static void main(String[] args) {
        float u = 0;
        float v = 1;
        java.util.Random random = new java.util.Random();
        IntStream.range(0,10000).forEach(x->{
            System.out.println(Math.round((Math.sqrt(v) * random.nextGaussian() + u)));
        });
    }
}
