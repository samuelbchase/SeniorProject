import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TopDown {

    public static void main(String[] args) throws Exception{
        //This code runs a single test of size 1024
        runTest(1024,1,0);

        //This code will run a series of 100,000 tests, not logging anything but a successful completion
        //message to the console
        for(int i = 0; i < 100000; i++) {
            runTest(1024,0,i);
        }
    }

    private static void runTest(int size,int print, int iter) throws Exception {
        ArrayList<vertex> graph = new ArrayList();
        //Creates blank graph of size size, and initializes them of degree 1
        for(int i = 0; i < size; i++) {
            vertex x = new vertex();
            graph.add(x);
        }
        //Randomly increments degrees up to max of 2n-2 (minus the # of vertices initialized as one)
        for(int i = 0; i < (size-1)*2 - size;i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0,size-1);
            graph.get(randomNum).increment();
        }
        //Prints the index & degree of vertices
        if(print == 1) {
            for(int i = 0; i < size; i++) {
                System.out.println("Vertex " + i + ": " + graph.get(i).initialDegree);
            }
        }

        //Runs algorithm
        ArrayList<Record> x = bottomUp(graph);
        if(print == 1) {
            //Prints all generated edges
            for(int i = 0; i < size-1; i++) {
                System.out.println(x.get(i).v1 + " -> " + x.get(i).v2);
            }
        }
        //If a degree constraint is not met, throw exception
        for(int i = 0; i < size; i++) {
            if(graph.get(i).degree < 0 || graph.get(i).initialDegree != graph.get(i).timesTouched){
                throw new Exception("Output does not match expected");
            }
        }
        System.out.println("Test " + iter + " completed Successfully");

    }

    private static ArrayList bottomUp(ArrayList<vertex> vertices) {
        ArrayList<Record> records = new ArrayList();
        for(int j = 0; j < vertices.size()-1;j++)
        {
            int maxIndex = -1;
            int max = 0;
            for(int i = 0; i < vertices.size(); i++) {
                if(vertices.get(i).getDegree() >= max) {
                    maxIndex = i;
                    max = vertices.get(i).getDegree();
                }
            }

            int minIndex = -1;
            int min = 5400000;
            for(int i = 0; i < vertices.size(); i++) {
                if(vertices.get(i).getDegree() < min && vertices.get(i).getDegree() > 0) {
                    minIndex = i;
                    min = vertices.get(i).getDegree();
                }
            }
            vertices.get(maxIndex).touch();
            vertices.get(minIndex).touch();
            Record x = new Record(minIndex,maxIndex);
            records.add(x);
        }
        return records;
    }
    public static class vertex {
        int degree;
        int initialDegree;
        int timesTouched;

        public vertex()
        {
            this.degree = 1;
            this.initialDegree = 1;
            this.timesTouched = 0;
        }
        public void increment() {
            this.degree++;
            this.initialDegree++;
        }
        public int getDegree() {
            return degree;
        }
        public void touch() {
            this.degree--;
            this.timesTouched++;
        }
    }
    public static class Record {
        int v1;
        int v2;
        public Record(int v1, int v2) {
            this.v1 = v1;
            this.v2 = v2;
        }
    }



}

