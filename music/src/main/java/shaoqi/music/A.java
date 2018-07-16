package shaoqi.music;

/**
 * Created by hayden on 18-6-26.
 */

public class A{

    public void add(){}

    public static String name;

        {
            System.out.println("A类的非静态代码块");
        }
//    A a = new A();
        static
        {
            System.out.println("A类的静态代码块");
        }

        public A() {
            System.out.println("A类的构造函数");
        }

    private static void testMethod(){
        System.out.println("testMethod");
    }
    public static void main(String[] args) {
        System.out.print(name);
    }
}
