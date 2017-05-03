package lab4;

/**
 * CSCI C202
 * Programming Assignment 6 - TSP by Minimum Spanning Tree
 * Purpose: Write a program to solve the problem of finding the shortest path
 *          to travel using a stack to keep track of the cities and finding the
 *          shortest path immediately available from one city to another
 * 
 * @author Thomas Wiles
 * @version 1.0  5/3/17
 */

import java.util.*;
import java.io.*;

public class PA6 {
    
    int CITI;
    int[][] adjacency;
    int bestCost = Integer.MAX_VALUE;
    
    /**
     * constructor with one integer argument
     * @param N - the amount of cities to be traveled to
     */
    PA6 (int N) {
        CITI = N;
        adjacency = new int[N][N];
    } // constructor with arguments
    
    /**
     * fills the matrix adjacency with the distances from city to city using
     *     values from an external file
     */
    public void populateMatrix() {
        File f = new File("tsp" + CITI + ".txt");
        try {
            Scanner input = new Scanner(f);
            int value;
            for (int i = 0; i < CITI && input.hasNext(); i++) {
                for (int j = i; j < CITI && input.hasNext(); j++) {
                    if (i == j) {
                        adjacency[i][j] = 0;
                    } //if
                    else {
                        value = input.nextInt();
                        adjacency[i][j] = value;
                        adjacency[j][i] = value;
                    } //else
                } //for j
            } //for i
        } //try
        catch (Exception e) {
            e.printStackTrace();
        } //catch
    } //populateMatrix
    
    /**
     * calculates & prints the shortest paths between the cities
     */
    public void TSP() {
        int distance = 0;
        Stack<Integer> pathStack = new Stack<>();
        Boolean[] visitedCities = new Boolean[CITI];
        visitedCities[0] = true;
        for (int i = 1; i < visitedCities.length; i++) {
            visitedCities[i] = false;
        } //for
        pathStack.push(0);
        int closestCity = 0;
        boolean minFlag = false;
        System.out.print("0, ");
        while (!pathStack.empty()) {
            Integer currentCity = pathStack.peek();
            Integer minimum = Integer.MAX_VALUE;
            for (int i = 1; i < CITI; i++) {
                if (adjacency[currentCity][i] > 0 && !visitedCities[i]) {
                    if (adjacency[currentCity][i] < minimum) {
                        minimum = adjacency[currentCity][i];
                        closestCity = i;
                        minFlag = true;
                    } //if
                } //if
            } //for
            if (minFlag) {
                distance += adjacency[currentCity][closestCity];
                visitedCities[closestCity] = true;
                pathStack.push(closestCity);
                System.out.print(closestCity + ", ");
                minFlag = false;
                continue;
            } //if
            pathStack.pop();
        } //while
        System.out.println("");
        distance += adjacency[0][closestCity];
        System.out.println("Distance:  " + distance);
    } //TSP
    
    
    public static void main(String[] args) {
        String[] numberOfCities = {"12","13","14","15","16","19","29"};
        for (int i = 0; i < numberOfCities.length; i++) {
            long t1 = System.currentTimeMillis();
            System.out.println(" ****************");
            System.out.println("TSP for " + numberOfCities[i] + " cities.");
            System.out.println(" ****************");
            int n = Integer.parseInt(numberOfCities[i]);
            PA6 pa6 = new PA6(n);
            pa6.populateMatrix();
            pa6.TSP();
            long t2 = System.currentTimeMillis();
            long t = t2 - t1;
            System.out.println("Duration:  " + t/1000 + "." + t%1000 + " sec");
            System.out.println("\n\n\n");
        } //for
    } //main
    
} //class
