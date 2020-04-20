package com.build;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Build<T> {
    private final Supplier<T> instantiator;

    private List<Consumer<T>> modifiers = new ArrayList<>();
    
    public Build(Supplier<T> instantiator){
        this.instantiator = instantiator;
    }

    public static <T> Build<T> of(Supplier<T> instantiator){
        return new Build<>(instantiator);
    }

    /**
     * 设置一个属性
     * @param consumer
     * @param p1
     * @param <P1>
     * @return
     */
    public <P1> Build<T> with(Consumer1<T,P1> consumer,P1 p1){
       Consumer<T> c = instance ->consumer.accept(instance,p1);
       modifiers.add(c);
       return this;
    }

    /**
     * 设置2个属性
     * @param consumer
     * @param p1
     * @param p2
     * @param <P1>
     * @param <P2>
     * @return
     */
    public <P1,P2> Build<T> with(Consumer2<T,P1,P2> consumer,P1 p1,P2 p2){
        Consumer<T> c = instance->consumer.accept(instance,p1,p2);
        modifiers.add(c);
        return this;
    }

    /**
     * 设置3个属性
     * @param consumer
     * @param p1
     * @param p2
     * @param p3
     * @param <P1>
     * @param <P2>
     * @param <P3>
     * @return
     */
    public <P1,P2,P3> Build<T> with(Consumer3<T,P1,P2,P3>consumer,P1 p1,P2 p2,P3 p3){
        Consumer<T> c = instance->consumer.accept(instance,p1,p2,p3);
        modifiers.add(c);
        return this;
    }

    /**
     * 设置4个属性
     * @param consumer
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @param <P1>
     * @param <P2>
     * @param <P3>
     * @param <P4>
     * @return
     */
    public <P1,P2,P3,P4> Build<T> with(Consumer4<T,P1,P2,P3,P4> consumer,P1 p1,P2 p2,P3 p3,P4 p4){
        Consumer<T> c = instance->consumer.accept(instance,p1,p2,p3,p4);
        modifiers.add(c);
        return this;
    }

    public T build(){
        T value = instantiator.get();
        modifiers.forEach(modifier->modifier.accept(value));
        modifiers.clear();
        return value;
    }

    @FunctionalInterface
    public interface Consumer1<T,P1>{
        void accept(T t,P1 p1);
    }

    @FunctionalInterface
    public interface Consumer2<T,P1,P2>{
        void accept(T t,P1 p1,P2 p2);
    }

    @FunctionalInterface
    public interface Consumer3<T,P1,P2,P3>{
        void accept(T t,P1 p1,P2 p2,P3 p3);
    }

    @FunctionalInterface
    public interface Consumer4<T,P1,P2,P3,P4>{
        void accept(T t,P1 p1,P2 p2,P3 p3,P4 p4);
    }
    
}
