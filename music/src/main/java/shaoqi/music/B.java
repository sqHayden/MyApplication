package shaoqi.music;

/**
 * Created by hayden on 18-6-26.
 */

public class B extends A{
    {
        System.out.println("B类的非静态代码块");
    }
    static
    {
        System.out.println("B类的静态代码块");
    }

    public B() {
        System.out.println("B类的构造函数");
    }

    @Override
    public void add() {

    }
}
