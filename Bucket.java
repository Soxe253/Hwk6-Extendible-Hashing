public class Bucket {
    int bitDepth;
    int bSize;
    String[] tuples;
    String bAddress;
    int arrayPointer;

    /**
     * the constructor for the bucket
     * @param depth the depth of the bucket
     * @param addr the address
     * @param size the amount of strings that can be stored
     */
    public Bucket(int depth, String addr, int size){
        bitDepth = depth;
        bSize = size;
        tuples = new String[bSize];
        arrayPointer = 0;
        bAddress = addr;
    }

    /**
     * sets the depth of the bucket
     * @param depth the depth to be set
     */
    public void setDepth(int depth){
        bitDepth = depth;
    }

    /**
     * Sets the address of the bucket
     * @param addr the address to set
     */
    public void setBucketAddress(String addr){
        bAddress = addr;
    }

    /**
     * gets the address of the bucket
     * @return the address
     */
    public String getBAddr(){
        return bAddress;
    }

    /**
     * gets the depth 
     * @return the depth of the bucket
     */
    public int getDepth(){
        return bitDepth;
    }

    /**
     * increments the depth by 1
     */
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

    /**
     * deletes the specified string from the tuples list
     * @param num the string to delete
     * @return true if succeeds, false otherwise
     */
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

    //fix the tuples in the array
    public void fixTuples(){
        int open = -1;
        for(int i = 0; i < tuples.length; i++){
            if(tuples[i] == null){
                if(open == - 1){
                    open = i;
                }
                continue;
            }
            if(open != -1 && open != i){
                tuples[open] = tuples[i];
                tuples[i] = null;
                open = findNextOpenIndex(i + 1);
            }
        }
    }

    private int findNextOpenIndex(int start) {
        for (int i = start; i < tuples.length; i++) {
            if (tuples[i] == null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * returns a stringify vers of the bucket
     */
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
        return "Local("+bitDepth+")["+bAddress+"*] = " + result+"]";
        
    }
}
