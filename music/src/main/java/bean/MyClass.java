package bean;

/**
 * Created by hayden on 18-5-22.
 */

public class MyClass {

    private Bean bean;

    public MyClass() {

    }

    public Bean getBean(){
        if(bean==null){
            bean = new Bean("aa");
        }
        return bean;
    }

    public String getString(){
        return bean.getTest();
    }
}
