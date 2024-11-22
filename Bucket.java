public class Bucket {
    String bitDepth;
    String address;
    int bSize;
    String[] tuples;

    public Bucket(String depth, String addr, int size){
        bitDepth = depth;
        address = addr;
        bSize = size;
        tuples = new String[bSize];
    }

    public void setDepth(String depth){
        bitDepth = depth;
    }

    public void setAddr(String addr){
        address = addr;
    }
    
}
