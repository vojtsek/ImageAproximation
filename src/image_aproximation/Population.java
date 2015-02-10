/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image_aproximation;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vojcek
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

    public int exp(int max) {
        double u = Math.random();
        u = Math.log(1 -u) / (-lambda);
        int gen = (int) (max * u);
        if (gen >= max) gen = max - 1;
        return gen;
    }

    public synchronized void reproduce(boolean rep) {
        gen_count++;
        for(IInd i : individuals) {
            i.computeFitness(fitness_prec);
        }
        Collections.sort(individuals);
        int i = 0, size = individuals.size();
        IInd i1, i2;
        
        LinkedList<IInd> n_individuals = new LinkedList<>();
        for(IInd ind : individuals) {
           // if (i++ < survivors) continue;
            i1 = individuals.get(exp(pop_count));
            i2 = individuals.get(exp(pop_count));
            n_individuals.add(ind.setContent(i1, i2, false));
        }
        for(int j = 0; j < size; j++) {
            IInd ind = individuals.get(j);
            ind.setContent(n_individuals.get(j).getList());
            ind.refresh();
            ind.repaint();
        }
    }
}
