public class Node {
    String key;
    Bucket pointer;

    /**
     * constructs the node
     * @param k the key
     * @param p the bucket
     */
    public Node(String k, Bucket p){
        key = k;
        pointer = p;
    }

    /**
     * gets the key
     * @return the key
     */
    public String getKey(){
        return key;
    }

    /**
     * gets the pointer
     * @return the pointer
     */
    public Bucket getPointer(){
        return pointer;
    }
    
    /**
     * sets the key
     * @param k new key
     */
    public void setKey(String k){
        key = k;
    }

    /**
     * sets the pointer
     * @param p the pointer
     */
    public void setPointer(Bucket p){
        pointer = p;
    }

    /**
     * returns stringify vers
     */
    public String toString(){
        return key+": "+ pointer.toString();
    }
}
