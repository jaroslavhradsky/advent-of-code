/*
--- Day 7: The Sum of Its Parts ---

You find yourself standing on a snow-covered coastline; apparently, you landed a little off course. The region is too hilly to see the North Pole from here, but you do spot some Elves that seem to be trying to unpack something that washed ashore. It's quite cold out, so you decide to risk creating a paradox by asking them for directions.

"Oh, are you the search party?" Somehow, you can understand whatever Elves from the year 1018 speak; you assume it's Ancient Nordic Elvish. Could the device on your wrist also be a translator? "Those clothes don't look very warm; take this." They hand you a heavy coat.

"We do need to find our way back to the North Pole, but we have higher priorities at the moment. You see, believe it or not, this box contains something that will solve all of Santa's transportation problems - at least, that's what it looks like from the pictures in the instructions." It doesn't seem like they can read whatever language it's in, but you can: "Sleigh kit. Some assembly required."

"'Sleigh'? What a wonderful name! You must help us assemble this 'sleigh' at once!" They start excitedly pulling more parts out of the box.

The instructions specify a series of steps and requirements about which steps must be finished before others can begin (your puzzle input). Each step is designated by a single letter. For example, suppose you have the following instructions:

Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin.

Visually, these requirements look like this:


  -->A--->B--
 /    \      \
C      -->D----->E
 \           /
  ---->F-----

Your first goal is to determine the order in which the steps should be completed. If more than one step is ready, choose the step which is first alphabetically. In this example, the steps would be completed as follows:

    Only C is available, and so it is done first.
    Next, both A and F are available. A is first alphabetically, so it is done next.
    Then, even though F was available earlier, steps B and D are now also available, and B is the first alphabetically of the three.
    After that, only D and F are available. E is not available because only some of its prerequisites are complete. Therefore, D is completed next.
    F is the only choice, so it is done next.
    Finally, E is completed.

So, in this example, the correct order is CABDFE.

In what order should the steps in your instructions be completed?

Your puzzle answer was GLMVWXZDKOUCEJRHFAPITSBQNY.
--- Part Two ---

As you're about to begin construction, four of the Elves offer to help. "The sun will set soon; it'll go faster if we work together." Now, you need to account for multiple people working on steps simultaneously. If multiple steps are available, workers should still begin them in alphabetical order.

Each step takes 60 seconds plus an amount corresponding to its letter: A=1, B=2, C=3, and so on. So, step A takes 60+1=61 seconds, while step Z takes 60+26=86 seconds. No time is required between steps.

To simplify things for the example, however, suppose you only have help from one Elf (a total of two workers) and that each step takes 60 fewer seconds (so that step A takes 1 second and step Z takes 26 seconds). Then, using the same instructions as above, this is how each second would be spent:

Second   Worker 1   Worker 2   Done
   0        C          .        
   1        C          .        
   2        C          .        
   3        A          F       C
   4        B          F       CA
   5        B          F       CA
   6        D          F       CAB
   7        D          F       CAB
   8        D          F       CAB
   9        D          .       CABF
  10        E          .       CABFD
  11        E          .       CABFD
  12        E          .       CABFD
  13        E          .       CABFD
  14        E          .       CABFD
  15        .          .       CABFDE

Each row represents one second of time. The Second column identifies how many seconds have passed as of the beginning of that second. Each worker column shows the step that worker is currently doing (or . if they are idle). The Done column shows completed steps.

Note that the order of the steps has changed; this is because steps now take time to finish and multiple workers can begin multiple steps simultaneously.

In this example, it would take 15 seconds for two workers to complete these steps.

With 5 workers and the 60+ second step durations described above, how long will it take to complete all of the steps?

Your puzzle answer was 1105.
*/
package y2018;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import support.InputLoader;

class Point implements Comparable{
    private String id;
    private ArrayList<Point> prevs;
    private ArrayList<Point> nexts;

    public Point(String id) {
        this.id = id;
        this.prevs = new ArrayList<>();
        this.nexts = new ArrayList<>();
    }
    
    public void addNext(Point p){
        this.nexts.add(p);
    }
    
    public void addPrev(Point p){
        this.prevs.add(p);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Point> getPrevs() {
        return prevs;
    }

    public ArrayList<Point> getNexts() {
        return nexts;
    }
    
    @Override
    public String toString(){
        String tos = "";
        String froms = "";
        for (Point prev : prevs) froms += prev.getId();
        for (Point next : nexts) tos += next.getId();
        return id + " -> " + tos + "\n" + id + " <- " + froms;
    }

    @Override
    public int compareTo(Object t) {
        Point p = (Point)t;
        return this.getId().compareTo(p.getId());
    }
    
    @Override
    public int hashCode(){
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Point other = (Point) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
}

public class Day07 {
    static ArrayList<String> input = new InputLoader("data/2018/day07.txt").getList();
    static HashMap<String,Point> points = new HashMap<>();
    static SortedSet<Point> todo = new TreeSet<>();
    
    public static void main(String[] args) {

        // create graph 
        for (String rule : input) {
            String from = Character.toString(rule.charAt(5));
            String to = Character.toString(rule.charAt(36));
            Point fromP;
            Point toP;
            if (points.containsKey(from)) fromP = points.get(from);
            else {
                fromP = new Point(from);
                points.put(from, fromP);
            }
            if (points.containsKey(to)) toP = points.get(to);
            else {
                toP = new Point(to);
                points.put(to, toP);
            }
            fromP.addNext(toP);
            toP.addPrev(fromP);
        }

        // add points with no predecessor to todo
        for (String id : points.keySet()) if (points.get(id).getPrevs().isEmpty()) todo.add(points.get(id));
        String done = "";
        
// ----- part 1
/*
        while (!todo.isEmpty()){
            Point processing = todo.first();
            for (Point next: processing.getNexts()) {
                next.getPrevs().remove(processing);
                if (next.getPrevs().isEmpty()) todo.add(next);
            }
            processing.getNexts().clear();           
            todo.remove(processing);
            done += processing.getId();
        }
        System.out.println(done + "\n");
*/

        // ---------- part 2 --------------------
        ArrayList<Worker> workers = new ArrayList<>();
        for (int i = 1; i <=5; i++) workers.add(new Worker());
        
        int sec = 0;
        while (!todo.isEmpty() || Worker.workingCount > 0){
            System.out.printf("%4d       ",sec);
            for (Worker worker : workers){ 
                if (worker.task == null) // kdyz nema co delat
                    if (!todo.isEmpty()){ // a je prace
                        worker.setTask(todo.first()); //tak mu dej praci
                        todo.remove(todo.first());
                    }
                    else {}
                else if (worker.remTime > 0) worker.remTime--; // ma co delat
                else { // posledni krok
                    Point removing = worker.remTask();
                  for (Point next: removing.getNexts()) {
                        next.getPrevs().remove(removing);
                        if (next.getPrevs().isEmpty()) todo.add(next);
                    }
                    removing.getNexts().clear();   
                    done += removing.getId();
                    if (!todo.isEmpty()){ // a je prace
                        worker.setTask(todo.first()); //tak mu dej praci
                        todo.remove(todo.first());
                    }
                    else worker.task = null;
                }
                System.out.print(worker.getTaskId() + "      ");
            }
            System.out.print(done + "   ");
            for (Point p : todo) System.out.print(p.getId()); 
            System.out.println();
            sec++;
        }
        
        //
                

        
    }
}

class Worker{
    static int workingCount = 0;
    Point task;
    int remTime;

    public Worker() {
        this.task = null;
        this.remTime = 0;
    }

    public Point remTask() {
        workingCount--;
        return task;
    }

    public void setTask(Point task) {
        this.task = task;
        this.setRemTime(task.getId().charAt(0) - 'A' + 60);
        workingCount++;
    }

    public int getRemTime() {
        return remTime;
    }

    public void setRemTime(int remTime) {
        this.remTime = remTime;
    }
    
    public String getTaskId(){
        if (task==null) return ".";
        return task.getId();
    }
    
    
    
    
    


}