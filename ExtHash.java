import java.lang.Math;
import java.util.Scanner;
public class ExtHash{
int globBitDepth;
Node[] list;
int keyLength;
int bucketSize;

    /**
     * constructor for the extendible hash table
     * @param kLength the length of the keys in the table
     * @param bSize the amount of strings stored in a bucket
     */
    public ExtHash(int kLength, int bSize){
        globBitDepth = 0;
        keyLength= kLength;
        bucketSize = bSize;
        list = new Node[1];
        Bucket bucket = new Bucket(0, "", bucketSize);
        Node node = new Node("", bucket);
        list[0] = node;
    }

    /**
     * Inserts a string into the table
     * @param num the binary string to be inserted
     * @return true if it succeeds, false if otherwise
     */
    public boolean insert(String num){
        if(search(num)){
            return false;
        }
        Node hit = findHit(num);

        while(hit.getPointer().add(num) == false){
            resize(hit);
            hit = findHit(num);
        }
        return true;
        
    }

    /**
     * Searches for a string in the table
     * @param bin the string to search for
     * @return true if its found, false otherwise
     */
    public boolean search(String bin){
        Node hit = findHit(bin);
        if(hit == null){
            return false;
        }
        else{
            Bucket bucket = hit.getPointer();
            for(int i = 0; i < bucket.tuples.length; i++){
                if(bucket.tuples[i] != null && bucket.tuples[i].equals(bin)){
                    return true;
                }
            }
        }
        return false;
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
        if(globBitDepth == target.getPointer().getDepth()){
            Bucket currBucket = target.getPointer();
            Bucket newBucket = new Bucket(currBucket.getDepth() + 1, currBucket.getBAddr(), bucketSize);//allocate new disk bloc
            globBitDepth++;
            
            Node[] tempList = new Node[(int) Math.pow(2, globBitDepth)];//double global directory
            //need to set the new buckets addr as the next bit and the curr bucket
            String tempAdd = currBucket.getBAddr();
            currBucket.incrementDepth();//increment depth
            currBucket.setBucketAddress(tempAdd + "0");
            newBucket.setBucketAddress(tempAdd + "1");
            rehashBuckets(currBucket, newBucket);//rehash the buckets

            for(int i = 0; i < tempList.length; i++){//fill new list with correct keys
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

            //update pointers in the new directory
            String newAddr = newBucket.getBAddr();
            int index = 0;
            for(int x = 0; x < list.length; x++){
                String currAddr = list[x].getPointer().getBAddr();
                currBucket = list[x].getPointer();
                if(currAddr.equals("01")){
                }
                for(int y = index; y < tempList.length; y++){
                    if(currAddr.equals(tempList[y].getKey().substring(0,currBucket.getDepth()))){
                        tempList[y].setPointer(currBucket);
                    }
                    else if(newAddr.equals(tempList[y].getKey().substring(0,newBucket.getDepth()))){
                        tempList[y].setPointer(newBucket);
                    }
                    else{
                        index = y;
                        break;
                    }
                }
            }
            list = tempList;
        }
        else{
            Bucket currBucket = target.getPointer();
            currBucket.incrementDepth();//increment depth
            Bucket newBucket = new Bucket(currBucket.getDepth(), currBucket.getBAddr(), bucketSize);//allocate new disk bloc
            //need to set the new buckets addr as the next bit and the curr bucket
            String tempAdd = currBucket.getBAddr();
            currBucket.setBucketAddress(tempAdd + "0");
            newBucket.setBucketAddress(tempAdd + "1");
            rehashBuckets(currBucket, newBucket);//rehash the buckets
            for(int w = 0; w < list.length; w++){
                if(currBucket.getBAddr().equals(list[w].getKey().substring(0,currBucket.getDepth()))){
                    list[w].setPointer(currBucket);
                }
                else if(newBucket.getBAddr().equals(list[w].getKey().substring(0,newBucket.getDepth()))){
                    list[w].setPointer(newBucket);
                }
            }

        }
        
    }

    //rehashes the two buckets given
    private void rehashBuckets(Bucket oldBucket, Bucket newBucket){
        String addr = newBucket.getBAddr();
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

    /**
     * returns a string version of the table
     */
    public String toString(){
        String result = "Global("+globBitDepth+")";
        for(int i = 0; i < list.length; i++){
            result = result + "\n" + list[i].toString();
        }
        return result;
    }

    public static void main(String[] args){
        if(args.length == 0){
            System.out.println("Usage: java ExtHash <block size> <key length>");
            return;
        }
        else if(Integer.parseInt(args[0]) < 1 ){
            System.out.println("Error: block size must be at least 1");
            return;
        }
        else if(Integer.parseInt(args[1]) < 1){
            System.out.println("Error: key length must be positive");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        ExtHash table = new ExtHash(Integer.parseInt(args[1]), Integer.parseInt(args[0]));
        String input = "";
        while(!input.equals("q")){
            input = scanner.nextLine();
            String bin = "";
            if(input.length() > 2){
                bin = input.substring(2, input.length());
            }
        if(bin.length() > table.keyLength){
            System.out.println("Error: key exceeds length " + table.keyLength);
            continue;
        }
            if(input.substring(0, 1).equals("i")){
                if(table.insert(bin)){
                    System.out.println("SUCCESS");
                }
                else{
                    System.out.println("FAILED");
                }
            }
            else if(input.substring(0, 1).equals("s")){
                if(table.search(bin)){
                    System.out.println(bin + " FOUND");
                }
                else{
                    System.out.println(bin + " NOT FOUND");
                }
            }
            else if(input.substring(0, 1).equals("p")){
                System.out.println(table.toString());
            }
        }
        scanner.close();
    }
}