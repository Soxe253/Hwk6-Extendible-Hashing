public class Node {
    String key;
    Bucket pointer;

    public Node(String k, Bucket p){
        key = k;
        pointer = p;
    }

    public String getKey(){
        return key;
    }

    public Bucket getPointer(){
        return pointer;
    }
    
    public void setKey(String k){
        key = k;
    }

    public void setPointer(Bucket p){
        pointer = p;
    }

    public String toString(){
        return key+": "+ pointer.toString();
    }
    
    public static void main(String[] args){
        Bucket bucket = new Bucket(2, "11", 2);
        Node node = new Node("11", bucket);
        System.out.println(node.toString());
    }
}
