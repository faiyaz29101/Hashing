import java.math.BigInteger;
import java.util.List;

public class HashTable {
    private int size;
    private int prime;
    private LinkedList[] list11;
    private LinkedList[] list12;
    private LinkedList[] list21;
    private LinkedList[] list22;
    private LinkedList[] list31;
    private LinkedList[] list32;
    private int collision11 = 0;
    private int collision12 = 0;
    private int collision21 = 0;
    private int collision22 = 0;
    private int collision31 = 0;
    private int collision32 = 0;
    private int probe11 = 0;
    private int probe12 = 0;
    private int probe21 = 0;
    private int probe22 = 0;
    private int probe31 = 0;
    private int probe32 = 0;
    private int input = 0;


    private void list_init(){
        list11 = new LinkedList[prime];
        list21 = new LinkedList[prime];
        list31 = new LinkedList[prime];
        list12 = new LinkedList[prime];
        list22 = new LinkedList[prime];
        list32 = new LinkedList[prime];
        for(int j=0; j<prime; j++){
            list11[j] = null;
        }
        for(int j=0; j<prime; j++){
            list21[j] = null;
        }
        for(int j=0; j<prime; j++){
            list31[j] = null;
        }
        for(int j=0; j<prime; j++){
            list12[j] = null;
        }
        for(int j=0; j<prime; j++){
            list22[j] = null;
        }
        for(int j=0; j<prime; j++){
            list32[j] = null;
        }
    }
    public HashTable(int n){
        size = n;
        if(n==1 || n==2 || n==3){
            prime = n;
            list_init();
            return;
        }
        else{
            while(true){
                if(n%2==0){
                    n++;
                }
                else{
                    for(int i=3; i<=n; i=i+2){
                        if(n%i == 0){
                            n=n+2;
                            break;
                        }
                        else if(i>=Math.sqrt(n)){
                            prime = n;
                            list_init();
                            return;
                        }
                    }
                }
            }
        }
    }

    private long getUnicode(String s){
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<s.length(); i++) {
            sb.append((int)(s.charAt(i)));
        }
        //System.out.println(sb);
        if(sb.length()>18) {
            sb.delete(18, sb.length());
        }
        return Long.parseLong(sb.toString());
    }
    private int HashFunction1(String s){
        long l = getUnicode(s);
        l = l%prime;
        return (int)l;
    }
    private int HashFunction2(String s){
       // h(k) = floor (m * (k * c mod 1))
        long l = getUnicode(s);
        if(l>10000000){
            l=l%10000000;
        }
        l=l%prime;
        return (int)l;
    }
    private int DoubleHash1(String s, int i){
        long temp = (HashFunction1(s)+i*auxHash(s))%prime;
        return (int)temp;
    }
    private int DoubleHash2(String s, int i){
        long l = getUnicode(s);
        long temp = (HashFunction2(s)+i*auxHash(s))%prime;
        return (int)temp;
    }
    private int auxHash(String s){
        long l = getUnicode(s);
        StringBuffer sb = new StringBuffer();
        sb.append(l);
        String st = sb.toString();
        String[] nums = st.split("");
        int p = 0;
        for(String q: nums){
            p = p + Integer.parseInt(q);
        }
        return prime%p;
    }
   // customHash(k, i) = (Hash(k) + C1 × i × auxHash(k) + C2 × i^2) % N

    private int customHash1(String s, int i){
        long temp = (HashFunction1(s) + 214*i*auxHash(s) + 79*i*i) % prime;
        return (int)temp;
    }
    private int customHash2(String s, int i){
        long temp = (HashFunction2(s) + 214*i*auxHash(s) + 79*i*i) % prime;
        return (int)temp;
    }
    public boolean insert(String s, int x){
        boolean a = insert11(s,x);
        boolean b = insert12(s,x);
        boolean c = insert21(s,x);
        boolean d = insert22(s,x);
        boolean e = insert31(s,x);
        boolean f = insert32(s,x);
        input++;
        return (a&&b);
    }
    public boolean insert11(String s, int x){
        int n = HashFunction1(s);
        LinkedList l = list11[n];
        if(l == null){
            LinkedList temp = new LinkedList();
            temp.setKey(s);
            temp.setValue(x);
            list11[n] = temp;
            return true;
        }
        else {
            while (true) {
                collision11++;
                    if(l.getKey() == s){
                        return false;
                    }else{
                        if(l.getNext() != null){
                            l = l.getNext();
                        }
                        else{
                            LinkedList temp = new LinkedList();
                            temp.setKey(s);
                            temp.setValue(x);
                            l.setNext(temp);
                            return true;
                        }
                    }

            }
        }
    }
    public boolean insert12(String s, int x){
        int n = HashFunction2(s);
        LinkedList l = list12[n];
        if(l == null){
            LinkedList temp = new LinkedList();
            temp.setKey(s);
            temp.setValue(x);
            list12[n] = temp;
            return true;
        }
        else {
            while (true) {
                collision12++;
                if(l.getKey() == s){
                    return false;
                }else{
                    if(l.getNext() != null){
                        l = l.getNext();
                    }
                    else{
                        LinkedList temp = new LinkedList();
                        temp.setKey(s);
                        temp.setValue(x);
                        l.setNext(temp);
                        return true;
                    }
                }

            }
        }
    }
    public boolean insert21(String s, int x){
        int i = 0;
        int n = DoubleHash1(s, i);
        while(true){
            if(list21[n] == null){
                LinkedList temp = new LinkedList();
                temp.setKey(s);
                temp.setValue(x);
                list21[n] = temp;
                return true;
            }
            else if(list21[n].getValue() == -100){
                LinkedList temp = new LinkedList();
                temp.setKey(s);
                temp.setValue(x);
                list21[n] = temp;
                return true;
            }
            collision21++;
            i++;
            n = DoubleHash1(s, i);
            if(list21[n] != null) {
                if (list21[n].getKey().equals(s) && list21[n].getValue() != -100) {
                    return false;
                }
            }
        }
    }
    public boolean insert22(String s, int x){
        int i = 0;
        int n = DoubleHash2(s, i);
        while(true){
            if(list22[n] == null){
                LinkedList temp = new LinkedList();
                temp.setKey(s);
                temp.setValue(x);
                list22[n] = temp;
                return true;
            }
            else if(list22[n].getValue() == -100){
                LinkedList temp = new LinkedList();
                temp.setKey(s);
                temp.setValue(x);
                list22[n] = temp;
                return true;
            }
            collision22++;
            i++;
            n = DoubleHash2(s, i);
            if(list22[n] != null) {
                if (list22[n].getKey().equals(s) && list22[n].getValue() != -100) {
                    return false;
                }
            }
        }
    }
    public boolean insert31(String s, int x){
        int i = 0;
        int n = customHash1(s, i);
        while(true){
            if(list31[n] == null){
                LinkedList temp = new LinkedList();
                temp.setKey(s);
                temp.setValue(x);
                list31[n] = temp;
                return true;
            }
            else if(list31[n].getValue() == -100){
                LinkedList temp = new LinkedList();
                temp.setKey(s);
                temp.setValue(x);
                list31[n] = temp;
                return true;
            }
            collision31++;
            i++;
            n = customHash1(s, i);
            if(list31[n] != null) {
                if (list31[n].getKey().equals(s) && list31[n].getValue() != -100) {
                    return false;
                }
            }
        }
    }
    public boolean insert32(String s, int x){
        int i = 0;
        int n = customHash2(s, i);
        while(true){
            if(list32[n] == null){
                LinkedList temp = new LinkedList();
                temp.setKey(s);
                temp.setValue(x);
                list32[n] = temp;
                return true;
            }
            else if(list32[n].getValue() == -100){
                LinkedList temp = new LinkedList();
                temp.setKey(s);
                temp.setValue(x);
                list32[n] = temp;
                return true;
            }
            collision32++;
            i++;
            n = customHash2(s, i);
            if(list32[n] != null) {
                if (list32[n].getKey().equals(s) && list32[n].getValue() != -100) {
                    return false;
                }
            }
        }
    }


    public void printList(){
        printList11();
        System.out.println("\n\n");
        printList12();
        System.out.println("\n\n");
        printList21();
        System.out.println("\n\n");
        printList22();
        System.out.println("\n\n");
        printList31();
        System.out.println("\n\n");
        printList32();

    }
    public void printList11(){
        for(int i = 0; i<prime; i++){
            if(list11[i] != null){
                list11[i].print();
            }
        }
        System.out.println("collision for list11 "+collision11);
    }
    public void printList12(){
        for(int i = 0; i<prime; i++){
            System.out.println(i);
            if(list12[i] != null){
                list12[i].print();
            }
        }
        System.out.println("collision for list12 "+collision12);
    }
    public void printList21(){
        for(int i = 0; i<prime; i++){
            System.out.println(i);
            if(list21[i] != null){
                list21[i].print();
            }
        }
        System.out.println("collision for list21 "+collision21);
    }
    public void printList22(){
        for(int i = 0; i<prime; i++){
            System.out.println(i);
            if(list22[i] != null){
                list22[i].print();
            }
        }
        System.out.println("collision for list22 "+collision22);
    }
    public void printList31(){
        for(int i = 0; i<prime; i++){
            System.out.println(i);
            if(list31[i] != null){
                list31[i].print();
            }
        }
        System.out.println("collision for list31 "+collision31);
    }
    public void printList32(){
        for(int i = 0; i<prime; i++){
            System.out.println(i);
            if(list32[i] != null){
                list32[i].print();
            }
        }
        System.out.println("collision for list32 "+collision32);
    }
    public boolean find(String s){
        boolean a = find11(s);
        boolean b = find12(s);
        boolean c = find21(s);
        boolean d = find22(s);
        boolean e = find31(s);
        boolean f = find32(s);
        return (a && b && c && d && e && f);
    }
    public boolean find11(String s){
        int n = HashFunction1(s);
        probe11++;
        if(list11[n] == null){
            return false;
        }
        LinkedList l = list11[n];
        while(true){
            if(l.getKey().equals(s)){
                return true;
            }
            else{
                probe11++;
                if(l.getNext()!=null){
                    l = l.getNext();
                }
                else{
                    return false;
                }
            }
        }
    }
    public boolean find12(String s){
        int n = HashFunction2(s);
        probe12++;
        if(list12[n] == null){
            return false;
        }
        LinkedList l = list12[n];
        while(true){
            if(l.getKey().equals(s)){
                return true;
            }
            else{
                probe12++;
                if(l.getNext()!=null){
                    l = l.getNext();
                }
                else{
                    return false;
                }
            }
        }
    }
    public boolean find21(String s){
        int i = 0;
        int n = DoubleHash1(s, i);
        probe21++;
        if(list21[n] == null || list21[n].getValue() == -100){
            return false;
        }
        if(list21[n].getKey().equals(s)){
            return true;
        }
        else{
            while(true){
                probe21++;
                i++;
                n = DoubleHash1(s, i);
                if(list21[n] == null || list21[n].getValue() == -100){
                    return false;
                }else if(list21[n].getKey().equals(s)){
                    return true;
                }
            }
        }
    }
    public boolean find22(String s){
        int i = 0;
        int n = DoubleHash2(s, i);
        probe22++;
        if(list22[n] == null || list22[n].getValue() == -100){
            return false;
        }
        if(list22[n].getKey().equals(s)){
            return true;
        }
        else{
            while(true){
                probe22++;
                i++;
                n = DoubleHash2(s, i);
                if(list22[n] == null || list22[n].getValue() == -100){
                    return false;
                }else if(list22[n].getKey().equals(s)){
                    return true;
                }
            }
        }
    }
    public boolean find31(String s){
        int i = 0;
        int n = customHash1(s, i);
        probe31++;
        if(list31[n] == null || list31[n].getValue() == -100){
            return false;
        }
        if(list31[n].getKey().equals(s)){
            return true;
        }
        else{
            while(true){
                probe31++;
                i++;
                n = customHash1(s, i);
                if(list31[n] == null || list31[n].getValue() == -100){
                    return false;
                }else if(list31[n].getKey().equals(s)){
                    return true;
                }
            }
        }
    }
    public boolean find32(String s){
        int i = 0;
        int n = customHash2(s, i);
        probe32++;
        if(list32[n] == null || list32[n].getValue() == -100){
            return false;
        }
        if(list32[n].getKey().equals(s)){
            return true;
        }
        else{
            while(true){
                probe32++;
                i++;
                n = customHash2(s, i);
                if(list32[n] == null || list32[n].getValue() == -100){
                    return false;
                }else if(list32[n].getKey().equals(s)){
                    return true;
                }
            }
        }
    }
    public boolean delete(String s){
        boolean a = delete11(s);
        boolean b = delete12(s);
        boolean c = delete21(s);
        boolean d = delete22(s);
        boolean e = delete31(s);
        boolean f = delete32(s);
        return (a && b && c && d && e && f);
    }
    public boolean delete11(String s){
        int n = HashFunction1(s);
        if(list11[n] == null) {
            return false;
        }
        if(list11[n].getKey().equals(s)){
            if(list11[n].getNext()==null){
                list11[n] = null;
            }
            else{
                list11[n] = list11[n].getNext();
            }
            return true;
        }else{
            if(list11[n].getNext()==null){
                return false;
            }
            else{
                LinkedList l = new LinkedList();
                l = list11[n];
                while(true){
                    if(l.getNext() == null){
                        return false;
                    }
                    if(l.getNext().getKey().equals(s)){
                        l.getNext().setNext(l.getNext().getNext());
                        return true;
                    }
                    else{
                        l = l.getNext();
                    }
                }
            }
        }
    }
    public boolean delete12(String s){
        int n = HashFunction2(s);
        if(list12[n] == null) {
            return false;
        }
        if(list12[n].getKey().equals(s)){
            if(list12[n].getNext()==null){
                list12[n] = null;
            }
            else{
                list12[n] = list12[n].getNext();
            }
            return true;
        }else{
            if(list12[n].getNext()==null){
                return false;
            }
            else{
                LinkedList l = new LinkedList();
                l = list12[n];
                while(true){
                    if(l.getNext() == null){
                        return false;
                    }
                    if(l.getNext().getKey().equals(s)){
                        l.getNext().setNext(l.getNext().getNext());
                        return true;
                    }
                    else{
                        l = l.getNext();
                    }
                }
            }
        }
    }
    //(n + (auxHash(s) % prime)) % prime
    public boolean delete21(String s) {
        int i =0;
        int n = DoubleHash1(s, i);
        while(true) {
            if (list21[n] == null){
                return false;
            }
            else if(list21[n].getKey().equals(s) && list21[n].getValue() == -100){
                return false;
            }
            if(list21[n].getKey().equals(s)){
                list21[n].setValue(-100);
                return true;
            }
            i++;
            n = DoubleHash1(s, i);
        }
    }
    public boolean delete22(String s) {
        int i =0;
        int n = DoubleHash2(s, i);
        while(true) {
            if (list22[n] == null){
                return false;
            }
            else if(list22[n].getKey().equals(s) && list22[n].getValue() == -100){
                return false;
            }
            if(list22[n].getKey().equals(s)){
                list22[n].setValue(-100);
                return true;
            }
            i++;
            n = DoubleHash2(s, i);
        }
    }
    public boolean delete31(String s) {
        int i =0;
        int n = customHash1(s, i);
        while(true) {
            if (list31[n] == null){
                return false;
            }
            else if(list31[n].getKey().equals(s) && list31[n].getValue() == -100){
                return false;
            }
            if(list31[n].getKey().equals(s)){
                list31[n].setValue(-100);
                return true;
            }
            i++;
            n = customHash1(s, i);
        }
    }
    public boolean delete32(String s) {
        int i =0;
        int n = customHash2(s, i);
        while(true) {
            if (list32[n] == null){
                return false;
            }
            else if(list32[n].getKey().equals(s) && list32[n].getValue() == -100){
                return false;
            }
            if(list32[n].getKey().equals(s)){
                list32[n].setValue(-100);
                return true;
            }
            i++;
            n = customHash2(s, i);
        }
    }
    public void printReport(){
        System.out.println("HashTable size: "+size);
        System.out.println("Collision Resolution Method");
        double averageProbe11 = ((double)probe11)/((double)input);
        double averageProbe12 = ((double)probe12)/((double)input);
        double averageProbe21 = ((double)probe21)/((double)input);
        double averageProbe22 = ((double)probe22)/((double)input);
        double averageProbe31 = ((double)probe31)/((double)input);
        double averageProbe32 = ((double)probe32)/((double)input);
        System.out.println("Chaining           # of Collisions for Hash1: "+collision11+"   # of Collisions for Hash2: "+collision12);
        System.out.println("Chaining           Average Probes for Hash1: "+averageProbe11+"   Average Probes for Hash2: "+averageProbe12);
        System.out.println("Double Hashing     # of Collisions for Hash1: "+collision21+"   # of Collisions for Hash2: "+collision22);
        System.out.println("Double Hashing     Average Probes for Hash1: "+averageProbe21+"   Average Probes for Hash2: "+averageProbe22);
        System.out.println("Custom Hashing     # of Collisions for Hash1: "+collision31+"   # of Collisions for Hash2: "+collision32);
        System.out.println("Custom Hashing     Average Probes for Hash1: "+averageProbe31+"   Average Probes for Hash2: "+averageProbe32);
    }


}
