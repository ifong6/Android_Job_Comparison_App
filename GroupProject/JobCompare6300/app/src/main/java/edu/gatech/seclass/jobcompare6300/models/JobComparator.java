package edu.gatech.seclass.jobcompare6300.models;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/***
 * Simple Comparator class to pair a ComparisonSettings object with two Job objects
 */
public class JobComparator implements Comparator<Job> {

    protected ComparisonSettings settings;

    public JobComparator(ComparisonSettings settings) {
        this.settings = settings;
    }

    @Override
    public int compare(Job first, Job second) {
        // Deliberate change of ordering, we want the _reverse_ to be standard
        // i.e. Salary of $99,999 comes before salary of $1 in list
        return Double.compare(second.getRank(settings), first.getRank(settings));
    }

    @Override
    public Comparator<Job> reversed() {
        return Comparator.super.reversed();
    }

    @Override
    public Comparator<Job> thenComparing(Comparator<? super Job> other) {
        return Comparator.super.thenComparing(other);
    }

    @Override
    public <U> Comparator<Job> thenComparing(Function<? super Job, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return Comparator.super.thenComparing(keyExtractor, keyComparator);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<Job> thenComparing(Function<? super Job, ? extends U> keyExtractor) {
        return Comparator.super.thenComparing(keyExtractor);
    }

    @Override
    public Comparator<Job> thenComparingInt(ToIntFunction<? super Job> keyExtractor) {
        return Comparator.super.thenComparingInt(keyExtractor);
    }

    @Override
    public Comparator<Job> thenComparingLong(ToLongFunction<? super Job> keyExtractor) {
        return Comparator.super.thenComparingLong(keyExtractor);
    }

    @Override
    public Comparator<Job> thenComparingDouble(ToDoubleFunction<? super Job> keyExtractor) {
        return Comparator.super.thenComparingDouble(keyExtractor);
    }
}
