package thinkingInJava.chapter_14_classInfo.Test4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author zhaojiatao
 * @date 2018/9/12
 * 工厂方法设计模式：
 * 将对象的创建工作交给类自己去完成；工厂方法可以被多态地调用，从而为你创建恰当类型的对象。
 */
public class Test4 {

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            System.out.println(Part.createRandom());
        }
    }

}

interface Factory<T>{
    T create();
}


class Part{
    public String toString(){
        return getClass().getSimpleName();
    }

    static List<Factory<? extends Part>> partFactories=new ArrayList<>();

    static {
        /*partFactories.add(new FuelFilter.Factory());
        partFactories.add(new AirFilter.Factory());
        partFactories.add(new CabinAirFilter.Factory());
        partFactories.add(new OilFilter.Factory());

        partFactories.add(new FanBelt.Factory());
        partFactories.add(new GeneratorBelt.Factory());
        partFactories.add(new PowerSteeringBelt.Factory());*/
        Collections.addAll(partFactories,new FuelFilter.Factory(),new AirFilter.Factory(),new CabinAirFilter.Factory(),
        new OilFilter.Factory(),new FanBelt.Factory(),new GeneratorBelt.Factory(),new PowerSteeringBelt.Factory()
        );

    }

    private static Random rand=new Random(47);
    public static Part createRandom(){
        int n =rand.nextInt(partFactories.size());
        return partFactories.get(n).create();
    }


}


class Filter extends Part{}

class FuelFilter extends Filter{
    public static class Factory implements thinkingInJava.chapter_14_classInfo.Test4.Factory<FuelFilter> {
        @Override
        public FuelFilter create() {
            return new FuelFilter();
        }
    }
}

class AirFilter extends Filter{
    public static class Factory implements thinkingInJava.chapter_14_classInfo.Test4.Factory<AirFilter> {
        @Override
        public AirFilter create() {
            return new AirFilter();
        }
    }
}

class CabinAirFilter extends Filter{
    public static class Factory implements thinkingInJava.chapter_14_classInfo.Test4.Factory<CabinAirFilter> {
        @Override
        public CabinAirFilter create() {
            return new CabinAirFilter();
        }
    }
}

class OilFilter extends Filter{
    public static class Factory implements thinkingInJava.chapter_14_classInfo.Test4.Factory<OilFilter> {
        @Override
        public OilFilter create() {
            return new OilFilter();
        }
    }
}


class Belt extends Part{

}

class FanBelt extends Belt{
    public static class Factory implements thinkingInJava.chapter_14_classInfo.Test4.Factory<FanBelt> {
        @Override
        public FanBelt create() {
            return new FanBelt();
        }
    }
}

class GeneratorBelt extends Belt{
    public static class Factory implements thinkingInJava.chapter_14_classInfo.Test4.Factory<GeneratorBelt> {
        @Override
        public GeneratorBelt create() {
            return new GeneratorBelt();
        }
    }
}

class PowerSteeringBelt extends Belt{
    public static class Factory implements thinkingInJava.chapter_14_classInfo.Test4.Factory<PowerSteeringBelt> {
        @Override
        public PowerSteeringBelt create() {
            return new PowerSteeringBelt();
        }
    }
}













