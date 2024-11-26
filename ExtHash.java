import java.lang.Math;
public class ExtHash{
int globBitDepth;
Node[] list;
int keyLength;
int bucketSize;

    public ExtHash(int kLength){
        globBitDepth = 0;
        keyLength= kLength;
        bucketSize = 3;
        list = new Node[1];
        Bucket bucket = new Bucket(0, "", bucketSize);
        Node node = new Node("", bucket);
        list[0] = node;
    }

    public void insert(String num){
        if(num.length() > keyLength){
            System.out.println("Error: Key length exceeds " + keyLength);
            return;
        }
        Node hit = findHit(num);

        if(hit.getPointer().add(num) == false){
            resize(hit);
            hit = findHit(num);
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
        System.out.println("key "+ key);
        for(int i = 0; i < list.length; i++){
            System.out.println(list[i].getKey());
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
        globBitDepth++;//increase global depth
        Node[] tempList = new Node[(int) Math.pow(2, globBitDepth)];
        //double the size of the list, and fill it into the new one
        for(int i = 0; i < tempList.length; i++){
           String key = Integer.toBinaryString(i).substring(0, globBitDepth);
           Bucket bucket = list[0].getPointer();
           if(target.getPointer() == bucket){
                Bucket[] buckets = splitBucket(bucket, key, i);//split the target overflown bucket and redistribute 
                Node tempNode = new Node(key, buckets[0]);//put the new bucket in for the current key
                tempList[i] = tempNode;
                i++;//now increase the marker on the loop to do the next bucket
                String key2 = Integer.toBinaryString(i).substring(0, globBitDepth);
                buckets[1].setDepth(globBitDepth);
                buckets[1].setAddr(key2);
                Node tempNode2 = new Node(key2, buckets[1]);//the old bucket was changed to hold the next keys in line
                tempList[i] = tempNode2;
                continue;//continue the loop
           }
           Bucket lastBucket = tempList[i-1].getPointer();
           String bAddress = lastBucket.getBAddr();
           //get the last bucket to see if current key still points there
           if(bAddress.substring(0, bAddress.length()-1) == key.substring(0, bAddress.length()-1)){
            Node tempNode = new Node(key, lastBucket);//yes this ket points to last bucket
            tempList[i] = tempNode;
           }
           else{//no it doesnt
            Node tempNode = new Node(key, bucket);
            tempList[i] = tempNode;
           }
        }
        list = tempList;
    }

    private Bucket[] splitBucket(Bucket oldBucket, String addr, int x){
        Bucket newBucket = new Bucket(globBitDepth, addr, bucketSize);
        for(int i = 0; i < oldBucket.tuples.length; i++){
            String temp = oldBucket.tuples[i];
            System.out.println(temp);
            if(temp.substring(0, globBitDepth).equals(addr)){
                System.out.println("add to new: " + temp);
                newBucket.add(temp);
                if(temp != null){
                oldBucket.delete(temp);
                }
            }
        }
        oldBucket.fixTuples();
        Bucket[] buckets = {newBucket, oldBucket};
        return buckets;
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
        table.insert("1000");
        table.insert("0000");
        table.insert("0100");
        table.insert("1100");
        System.out.println(table.toString());
        
    }
}