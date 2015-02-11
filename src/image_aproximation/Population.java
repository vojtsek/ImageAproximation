package image_aproximation;

import java.util.Collections;
import java.util.LinkedList;

/**
 *
 * @author Vojtech Hudecek serves to maintain the population
 */
public class Population {

    private LinkedList<IInd> individuals;
    private int pop_count, gen_count, survivors, fitness_prec;
    private double lambda;

    public Population(LinkedList<IInd> inds, int pc, double l, int s, int fitp) {
        individuals = inds;
        pop_count = pc;
        lambda = l;
        survivors = s;
        fitness_prec = fitp;
    }

    public void setLambda(double l) {
        lambda = l;
    }

    public void setSurvivors(int s) {
        survivors = s;
    }

    public void setFitnessP(int ftp) {
        fitness_prec = ftp;
    }

    /**
     *
     * @param max maximal number which should be returned
     * @return generates a number in range [0; max - 1] probability of each
     * number is driven by the exponential distribution
     */
    public int exp(int max) {
        double u = Math.random();
        u = Math.log(1 - u) / (-lambda);
        int gen = (int) (max * u);
        if (gen >= max) {
            gen = max - 1;
        }
        return gen;
    }

    /**
     *
     * @param rep whether repaint or not iterates through all the individuals
     * and creates a new generation
     */
    public synchronized void reproduce(boolean rep) {
        gen_count++;
        for (IInd i : individuals) {
            i.computeFitness(fitness_prec);
        }
        Collections.sort(individuals);
        int i = 0, r1, r2;
        IInd i1, i2;

        for (IInd ind : individuals) {
            if (i++ >= survivors) {
                r1 = exp(pop_count);
                while ((r2 = exp(pop_count)) == r1) ;
                i1 = individuals.get(r1);
                i2 = individuals.get(r2);
                ind.setContent(i1, i2);
            }
            ind.refresh();
            if (rep) {
                ind.repaint();
            }
        }

    }
}
