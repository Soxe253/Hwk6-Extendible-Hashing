import java.lang.Math;
public class ExtHash{
int globBitDepth;
Node[] list;
int keyLength;
int bucketSize;

    public ExtHash(int kLength){
        globBitDepth = 0;
        keyLength= kLength;
        bucketSize = 0;
        list = new Node[1];
        Bucket bucket = new Bucket(0, "", bucketSize);
        Node node = new Node("", bucket);
        list[0] = node;
    }

    public void insert(String num){
        Node hit = findHit(num);
        if(hit == null){

        }
    }
    
    /**
     * checks to see if the binary strings key matches that in the table
     * @param num the binary string to be checked
     * @return the node if the key matches, null if not
     */
    private Node findHit(String num){
        String key = num.substring(0,globBitDepth);
        for(int i = 0; i < list.length; i++){
            if(list[i].getKey() == key){
                return list[i];
            }
        }
        return null;
    }

    /**
     * resizes the table and changes the variables
     */
    private void resize(){
        globBitDepth++;//increase global depth
        Node[] tempList = new Node[(int) Math.pow(2, globBitDepth)];
        //double the size of the list, and fill it into the new one
        for(int i = 0; i < tempList.length; i++){
           String key = Integer.toBinaryString(i).substring(0, globBitDepth);
           Bucket bucket = new Bucket(i, key, bucketSize);
           //having a problem right here need to know when to set local depth the same as global depth
           //or when to keep local depth the same, ask cathygpt?
        }
        list = tempList;
    }

    public String toString(){
        String result = "Global("+globBitDepth+")";
        for(int i = 0; i < list.length; i++){
            result = result + "\n" + list[i].toString();
        }
        return result;
    }

    public static void main(String[] args){
        ExtHash table = new ExtHash(4);
        System.out.println(table.toString());
        int x = 6;
        
    }
}