public class Bucket {
    int bitDepth;
    String address;
    int bSize;
    String[] tuples;
    String bAddress;

    public Bucket(int depth, String addr, int size){
        bitDepth = depth;
        address = addr;
        bSize = size;
        tuples = new String[bSize];
        bAddress = address.substring(0, bitDepth) + "*";
    }

    public void setDepth(int depth){
        bitDepth = depth;
    }

    public void setAddr(String addr){
        address = addr;
    }

    public void setBucketAddress(String addr){
        bAddress = addr;
    }

    public String toString(){
        String result = "[";
        for(int i = 0; i < tuples.length; i++){
            if(i == (tuples.length - 1)){
                result = result + tuples[i];
            }
            else{
            result = result + tuples[i] + ", ";
            }
        }
        return "Local("+bitDepth+")["+bAddress+"] = " + result+"]";
        
    }

    public static void main(String[] args){
        Bucket bucket = new Bucket(1, "10", 2);
        bucket.tuples[0] = "1000";
        bucket.tuples[1] = "1010";
        System.out.println(bucket.toString());
    }
    
}
