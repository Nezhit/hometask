package first;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
        smart();
    }
    public  static void vlob(){
        for (int i =0; i<100;i++){
            if(i%15==0){
                System.out.println("CodeInside");
            } else if (i%3==0) {
                System.out.println("Code");
            } else if (i%5==0) {
                System.out.println("Inside");
            }
            else {
                System.out.println(i);
            }
        }
    }
    public static void  smart(){
        //Object[] arr={1,2,3,4,5,6,7,8,9,10,11,12,13,14};
        //Arrays.stream(arr).filter(i->(int)i%3==0).forEach(System.out::println);
        IntStream.rangeClosed(1, 100)
                .mapToObj(i -> (i % 15 == 0) ? "CodeInside" :
                        (i % 3 == 0) ? "Code" :
                                (i % 5 == 0) ? "Inside" :
                                        Integer.toString(i))
                .forEach(System.out::println);
    }
}
