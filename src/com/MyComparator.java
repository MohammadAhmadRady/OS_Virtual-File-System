package com;
import java.util.ArrayList;
import java.util.Comparator;

public class MyComparator implements Comparator <ArrayList<Integer>>  {
    /**
     * comparator class helps to compare the node
     * on the basis of one of its attribute.
     * Here we will be compared
     * on the basis of Probability values of the nodes.
     * @param x
     * @param y
     * @return
     */
    public int compare(ArrayList<Integer> x, ArrayList<Integer> y) {
        return x.size() - y.size();
    }
}