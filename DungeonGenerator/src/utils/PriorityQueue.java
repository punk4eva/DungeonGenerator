
package utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Function;

/**
 *
 * @author Adam Whittaker
 * @param <T> The Class of the element to be sorted.
 * 
 * Auto-sorts elements as they are added using itself as a comparator.
 */
public class PriorityQueue<T extends Object> extends LinkedList<T> implements Comparator<T>{

    
    private static final long serialVersionUID = 1543L;
    
    private Function<T, Double> enumerator;
    
    
    /**
     * Creates a new instance.
     * @param en The priority function.
     */
    public PriorityQueue(Function<T, Double> en){
        super();
        enumerator = en;
    }
    
    /**
     * Creates an instance.
     * @param clctn The collection to wrap.
     * @param en The priority function.
     */
    public PriorityQueue(Collection<? extends T> clctn, Function<T, Double> en){
        super(clctn);
        enumerator = en;
        sort();
    }
    
    
    /**
     * Creates an instance.
     * @param ary The array to wrap.
     * @param en The priority function.
     */
    public PriorityQueue(T[] ary, Function<T, Double> en){
        super(Arrays.asList(ary));
        enumerator = en;
        sort();
    }

    /**
     * Sorts this queue.
     */
    public final void sort(){
        super.sort(this);
    }

    @Override
    public int compare(T t, T t1){
        double comp = enumerator.apply(t);
        double comp1 = enumerator.apply(t1);
        return comp>comp1 ? 1 : comp<comp1 ? -1 : 0;
    }
    
    /**
     * Enumerates an object with the Comparator's
     * @param t The object to be enumerated.
     * @return The enumeration of that object.
     */
    public double enumerate(T t){
        return enumerator.apply(t);
    }
    
    @Override
    public boolean add(T element){
        for(int n=0;n<size();n++){
            if(compare(element, get(n))==-1){
                add(n, element);
                return true;
            }
        }
        super.add(element);
        return true;
    }
    
    public void setFunction(Function<T, Double> func){
        enumerator = func;
    }
    
}
