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
        Bucket bucket = new Bucket(0, "", bucketSize);
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
        System.out.println("target: "+ target.toString());
        globBitDepth++;//increase global depth
        Node[] tempList = new Node[(int) Math.pow(2, globBitDepth)];
        //double the size of the list, set its keys
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
            int index = 0;
            for(int i = 0; i < tempList.length; i++){
                Bucket bucket = list[index].getPointer();
                System.out.println("index/bucket: " + index + " "+ bucket.toString());
                System.out.println("i: " + i);
                if(target != null && target.getPointer() == bucket){
                    if(bucket.getDepth() < globBitDepth){
                        int totalPointers = bucket.getNumPointers();
                        
                    }
                        target = null;
                        // int depth;
                        // if(bucket.getNumPointers() > 1){
                        //     depth = bucket.getDepth();
                        // }
                        // else{
                        //     depth = globBitDepth;
                        // }
                        Bucket[] buckets = splitBucket(bucket, tempList[i].getKey(), depth);//split the target overflown bucket and redistribute 
                        Bucket newBucket = buckets[0];
                        Bucket oldBucket = buckets[1];
                        newBucket.setNumPointers(bucket.getNumPointers());
                        Node tempNode = new Node(tempList[i].getKey(), newBucket);//put the new bucket in for the current key
                        tempList[i] = tempNode;
                        i++;//now increase the marker on the loop to do the next bucket
                        String tempBin = Integer.toBinaryString(i);
                        if(i < globBitDepth){
                            for(int y = tempBin.length(); y < globBitDepth; y++){
                                tempBin = "0" + tempBin;
                            }
                        }
                        String key2 = tempBin.substring(0, globBitDepth);
                        buckets[1].setDepth(globBitDepth);
                        buckets[1].setAddr(key2);
                        Node tempNode2 = new Node(key2, oldBucket);//the old bucket was changed to hold the next keys in line
                        tempList[i] = tempNode2;
                        int totPointers = bucket.getNumPointers() * 2;
                        
                        // if(bucket.getNumPointers() > 1){//extra split for when there target is already split
                        //     i--;
                        //     int curr = i;
                        //     for(int y = curr; y < (curr+totPointers); y++){
                        //         System.out.println("y: "+y);
                        //         if(y < (curr + (totPointers/2))){
                        //             tempList[y].setPointer(newBucket);
                        //         }
                        //         else{
                        //             tempList[y].setPointer(oldBucket);
                        //         }
                        //         i++;
                        //     }
                        //     i--;
                        //     System.out.println("pointers: " + newBucket.getNumPointers());
                        //     index = index + newBucket.getNumPointers();
                        //     newBucket.setNumPointers(bucket.getNumPointers());
                        //     oldBucket.setNumPointers(bucket.getNumPointers());
                        //     continue;
                        // }
                        // index++;
                        // continue;//continue the loop
                }
                else{
                String add = bucket.getBAddr().substring(0,bucket.getBAddr().length()-1);
                System.out.println("add: "+add);
                int oldPointers = bucket.getNumPointers();
                System.out.println("opointers: " + oldPointers);
                for(int q = 0; q < tempList.length; q++){
                    //System.out.println("getkey: " +tempList[q].getKey());
                    if(add.equals(tempList[q].getKey().substring(0, add.length()))){
                        tempList[q].setPointer(bucket);
                        System.out.println("non split: " + tempList[q].toString());
                            i++;
                    }
                }
                bucket.setNumPointers(bucket.getNumPointers() * 2);
                i--;
                index = index + oldPointers;
                }
            }
        
        list = tempList;
    }

    private Bucket[] splitBucket(Bucket oldBucket, String addr, int depth){
        Bucket newBucket = new Bucket(depth, addr, bucketSize);
        for(int i = 0; i < oldBucket.tuples.length; i++){
            String temp = oldBucket.tuples[i];
            if(temp.substring(0, depth).equals(addr)){
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
        table.insert("0000");
        table.insert("1000");
        System.out.println(table.toString());
        table.insert("1100");
        System.out.println(table.toString());
        table.insert("1010");
        System.out.println(table.toString());
        table.insert("0001");
        System.out.println(table.toString());
        // table.insert("0100");
        // System.out.println(table.toString());
    }
}