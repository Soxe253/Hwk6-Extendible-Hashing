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
    
}
