import java.util.ArrayList;
import java.util.Random;

public class RandomStringGenerator {
    private int seed;
    private int count;
    private ArrayList<String> list;
    private Random r;
    public RandomStringGenerator(int s, int c){
        seed = s;
        count = c;
        list = new ArrayList<String>();
        String alphabet = new String();
        alphabet = "abcdefghijklmnopqrstuvwxyz";
        r = new Random(seed);
        String[] al = alphabet.split("");
        for(int i=0; i<count; i++){
            int length = r.nextInt(5, 11);
            StringBuffer sb = new StringBuffer();
            for(int j=0; j<length; j++){
                sb.append(al[r.nextInt(0, 26)]);
            }
            if(!list.contains(sb.toString())) {
                list.add(sb.toString());
            }else{
                i--;
            }
        }
    }
    public void print(){
        for(String x:list){
            System.out.println(x);
        }
    }
    public String getString(){
        int x = r.nextInt(0, list.size());
        String s = list.get(x);
        list.remove(x);
        return s;
    }

}
