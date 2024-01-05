public class LinkedList {
    private String key;
    private int value;
    private LinkedList next = null;

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setNext(LinkedList next) {
        this.next = next;
    }

    public LinkedList getNext() {
        return next;
    }

    public String getKey() {
        return key;
    }
    public void print(){
        System.out.println(key);
        LinkedList temp = next;
        while(temp != null){
            System.out.println(temp.getKey()+" "+temp.getValue());
            temp = temp.getNext();
        }
    }
}
