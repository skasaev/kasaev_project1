package tools;

import java.util.Comparator;
import java.util.List;

public class ListComparator implements Comparator<List<Float>> {

    @Override
    public int compare(List<Float> o1, List<Float> o2) {

        if (o1.get(0) > o2.get(0)) {
            return 1;
        } else if (o1.get(0) < o2.get(0)) {
            return -1;
        } else if (o1.get(1) > o2.get(1)) {
            return 1;
        } else if (o1.get(1) < o2.get(1)) {
            return -1;
        } else {
            return 0;
        }
    }
}
