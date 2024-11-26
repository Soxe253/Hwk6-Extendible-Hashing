import java.lang.Math;
public class ExtHash{
int globBitDepth;
Node[] list;
int keyLength;
int bucketSize;

    public ExtHash(int kLength){
        globBitDepth = 0;
        keyLength= kLength;
        bucketSize = 2;
        list = new Node[1];
        Bucket bucket = new Bucket(0, "0", bucketSize);
        Node node = new Node("", bucket);
        list[0] = node;
    }

    public void insert(String num){
        if(num.length() > keyLength){
            System.out.println("Error: Key length exceeds " + keyLength);
            return;
        }
        System.out.println("Adding: " + num);
        Node hit = findHit(num);
        System.out.println(hit.toString());

        if(hit.getPointer().add(num) == false){
            resize(hit);
            hit = findHit(num);
            //System.out.println(hit.toString());
            //System.out.println(toString());
            hit.getPointer().add(num);
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
            if(list[i].getKey().equals(key)){
                return list[i];
            }
        }
        return null;
    }

    /**
     * resizes the table and changes the variables
     */
    private void resize(Node target){
        //Adding: 0000
// : Local(0)[*] = [null, null]
// Adding: 0001
// : Local(0)[*] = [0000, null]
// Adding: 0010
// : Local(0)[*] = [0000, 0001]
// bAdd: 0
// Global(0)
// : Local(1)[0*] = [0001, null]
//last run
        Bucket bucket = target.getPointer();
            bucket.incrementDepth();
            if(globBitDepth == 0){
                bucket.setAddr("0");
            }
            String bAdd = bucket.getBAddr().substring(0, bucket.getDepth());
            System.out.println("bAdd: " + bAdd);
            int newBin = Integer.parseInt(bAdd, 2);
            newBin++;
            String nBin = Integer.toBinaryString(newBin).substring(0,bucket.getDepth());
            Bucket newBucket = new Bucket(bucket.getDepth(), nBin, bucketSize);
        if(globBitDepth == bucket.getDepth()){
            globBitDepth++;
            Node[] tempList = new Node[(int) Math.pow(2, globBitDepth)];
            for(int i = 0; i < tempList.length; i++){
                String bin = Integer.toBinaryString(i);
                    if(bin.length() < globBitDepth){
                        for(int x = bin.length(); x < globBitDepth; x++){
                            bin = "0" + bin;
                        }
                    }
                    String key = bin.substring(0, globBitDepth);
                    Node node = new Node(key, null);
                    tempList[i] = node;
            }
            rehashBuckets(bucket, newBucket);
            for(int y = 0; y < list.length; y++){//double loop to iterate through each one in old list for the new list
                String bAddr = list[y].getPointer().getBAddr().substring(0,bucket.getDepth() -1);
                String newAddr = newBucket.getBAddr().substring(0,bucket.getDepth() -1);
                for(int x = 0; x < tempList.length; x++){
                    String key = tempList[x].getKey().substring(0,bucket.getDepth());
                    if(key.equals(bAddr)){//check if key to the local depth equals the bucket addr
                        tempList[x].setPointer(list[y].getPointer());
                    }
                    else if(key.equals(newAddr)){
                        tempList[x].setPointer(newBucket); 
                    }
                    else{
                        break;
                    }
                }
            }
        }
        else{
            rehashBuckets(bucket, newBucket);
            String oAdd = bucket.getBAddr().substring(0, bucket.getDepth());
            String nAdd = newBucket.getBAddr().substring(0, newBucket.getDepth());
            for(int t = 0; t < list.length; t++){
                if(list[t].getKey().equals(oAdd)){
                    list[t].setPointer(bucket);
                }
                else if(list[t].getKey().equals(nAdd)){
                    list[t].setPointer(newBucket);
                }
            }
        }
    }

    private void rehashBuckets(Bucket oldBucket, Bucket newBucket){
        String addr = newBucket.getBAddr().substring(0, newBucket.getBAddr().length()-1);
        for(int i = 0; i < oldBucket.tuples.length; i++){
            String temp = oldBucket.tuples[i];
            if(temp.substring(0, newBucket.getDepth()).equals(addr)){
                newBucket.add(temp);
                if(temp != null){
                oldBucket.delete(temp);
                }
            }
        }
        oldBucket.fixTuples();
        return;
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
        table.insert("0000");
        table.insert("0001");
        table.insert("0010");
        System.out.println(table.toString());
    }
}