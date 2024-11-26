public class Bucket {
    int bitDepth;
    String address;
    int bSize;
    String[] tuples;
    String bAddress;
    int arrayPointer;
    int numPointers;

    public Bucket(int depth, String addr, int size){
        bitDepth = depth;
        address = addr;
        bSize = size;
        tuples = new String[bSize];
        arrayPointer = 0;
        numPointers = 1;
        bAddress = address.substring(0, bitDepth) + "*";
    }

    public void setDepth(int depth){
        bitDepth = depth;
    }

    public void setAddr(String addr){
        address = addr;
        bAddress = address.substring(0, bitDepth) + "*";
    }

    public void setBucketAddress(String addr){
        bAddress = addr;
    }

    public String getBAddr(){
        return bAddress;
    }

    public int getNumPointers(){
        return numPointers;
    }

    public void setNumPointers(int num){
        numPointers = num;
    }

    public int getDepth(){
        return bitDepth;
    }

    public void incrementDepth(){
        bitDepth++;
    }

    /**
     * Adds the binary string to the buckets list
     * @param num the string to add
     * @return true if its added, false if the bucket is full and cant be added
     */
    public boolean add(String num){
        if(arrayPointer < tuples.length){
            tuples[arrayPointer] = num;
            arrayPointer++;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean delete(String num){
        for(int i = 0; i < tuples.length; i++){
            if(tuples[i] != null && tuples[i].equals(num)){
                tuples[i] = null;
                arrayPointer--;
                return true;
            }
        }
        return false;
    }

    public void fixTuples(){
        int open = 0;
        for(int i = 0; i < tuples.length; i++){
            if(tuples[i] == null){
                open = i;
                continue;
            }
            else if(open != i){
                tuples[open] = tuples[i];
                tuples[i] = null;
                open = i;
            }
        }
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
        String bin = "1001";
        int x = Integer.parseInt(bin, 2);
        System.out.println(x);
    }
    
}
