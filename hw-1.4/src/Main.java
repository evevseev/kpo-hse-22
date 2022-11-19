// Create bubble sort using Java

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = List.of(5, 2, 6, 1, -5, 2, 56);
        System.out.println(bubbleSort(list));
    }

    public static <T extends Comparable<T>> List<T> bubbleSort(Iterable<T> list) {
        List<T> result = new ArrayList<>();
        list.forEach(result::add);

        for (int i = 0; i < result.size(); i++) {
            for (int j = 0; j < result.size() - 1; j++) {
                if (result.get(j).compareTo(result.get(j + 1)) > 0) {
                    T temp = result.get(j);
                    result.set(j, result.get(j + 1));
                    result.set(j + 1, temp);
                }
            }
        }
        return result;
    }
}