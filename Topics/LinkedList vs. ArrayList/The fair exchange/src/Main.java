import java.util.ArrayList;
import java.util.LinkedList;

class ListOperations {
    public static void changeHeadsAndTails(LinkedList<String> linkedList, ArrayList<String> arrayList) {
        // write your code here
        var temp1 = linkedList.getFirst();
        linkedList.set(0, arrayList.get(0));
        arrayList.set(0, temp1);

        var temp2 = linkedList.getLast();
        linkedList.set(linkedList.size() - 1, arrayList.get(arrayList.size() - 1));
        arrayList.set(arrayList.size() - 1, temp2);
    }
}