import java.util.ArrayList;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        int[] ar = new int[3];
        ar[0] = 5000;
        ar[1] = 10000;
        ar[2] = 20000;
        for(int x : ar) {
            HashTable ht = new HashTable(x);
            RandomStringGenerator rs = new RandomStringGenerator(50, 10000);
            List<String> l = new ArrayList<String>();
            for (int i = 0; i < 1000; i++) {
                l.add(rs.getString());
            }
            for (int i = 0; i < 1000; i++) {
                ht.insert(l.get(i), i);
            }
            for (int i = 0; i < 1000; i++) {
                ht.find(l.get(i));
            }
            for (int i = 0; i < 1000; i++) {
                boolean b = ht.delete(l.get(i));
                if (!b) {
                    System.out.println(b);
                }
            }
            ht.printReport();
            System.out.println("\n\n\n");
        }

    }
}