import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ChenHanLin 2020/4/13
 */
public class main {
    public static void main(String[] args) {
        List<String> a = new ArrayList<>();
        a.add("1");
        Set<Integer> sets = Set.of(1, 2, 3);
        List<String> lists = Optional.ofNullable(sets).stream().map(String::valueOf).collect(Collectors.toList());
        System.out.println(lists);

        Integer sum1 = sets.parallelStream().reduce(Integer::sum).get();
        Integer sum2 = sets.parallelStream().reduce(0, Integer::sum);
        System.out.println("sum1" + sum1);
        System.out.println("sum2" + sum2);
    }
}


