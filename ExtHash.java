public class ExtHash{
int bitDepth;
Node[] list;
int keyLength;
int bucketSize;

    public ExtHash(int kLength){
        bitDepth = 0;
        keyLength= kLength;
        bucketSize = 0;
        list = new Node[1];
    }

    public static void main(String[] args){
        String[] test = {"hi", "hello"};
        System.out.println(test);
    }
}