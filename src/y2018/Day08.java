/*
--- Day 8: Memory Maneuver ---

The sleigh is much easier to pull than you'd expect for something its weight. Unfortunately, neither you nor the Elves know which way the North Pole is from here.

You check your wrist device for anything that might help. It seems to have some kind of navigation system! Activating the navigation system produces more bad news: "Failed to start navigation system. Could not read software license file."

The navigation system's license file consists of a list of numbers (your puzzle input). The numbers define a data structure which, when processed, produces some kind of tree that can be used to calculate the license number.

The tree is made up of nodes; a single, outermost node forms the tree's root, and it contains all other nodes in the tree (or contains nodes that contain nodes, and so on).

Specifically, a node consists of:

    A header, which is always exactly two numbers:
        The quantity of child nodes.
        The quantity of metadata entries.
    Zero or more child nodes (as specified in the header).
    One or more metadata entries (as specified in the header).

Each child node is itself a node that has its own header, child nodes, and metadata. For example:

2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2
A----------------------------------
    B----------- C-----------
                     D-----

In this example, each node of the tree is also marked with an underline starting with a letter for easier identification. In it, there are four nodes:

    A, which has 2 child nodes (B, C) and 3 metadata entries (1, 1, 2).
    B, which has 0 child nodes and 3 metadata entries (10, 11, 12).
    C, which has 1 child node (D) and 1 metadata entry (2).
    D, which has 0 child nodes and 1 metadata entry (99).

The first check done on the license file is to simply add up all of the metadata entries. In this example, that sum is 1+1+2+10+11+12+2+99=138.

What is the sum of all metadata entries?

Your puzzle answer was 46962.
--- Part Two ---

The second check is slightly more complicated: you need to find the value of the root node (A in the example above).

The value of a node depends on whether it has child nodes.

If a node has no child nodes, its value is the sum of its metadata entries. So, the value of node B is 10+11+12=33, and the value of node D is 99.

However, if a node does have child nodes, the metadata entries become indexes which refer to those child nodes. A metadata entry of 1 refers to the first child node, 2 to the second, 3 to the third, and so on. The value of this node is the sum of the values of the child nodes referenced by the metadata entries. If a referenced child node does not exist, that reference is skipped. A child node can be referenced multiple time and counts each time it is referenced. A metadata entry of 0 does not refer to any child node.

For example, again using the above nodes:

    Node C has one metadata entry, 2. Because node C has only one child node, 2 references a child node which does not exist, and so the value of node C is 0.
    Node A has three metadata entries: 1, 1, and 2. The 1 references node A's first child node, B, and the 2 references node A's second child node, C. Because node B has a value of 33 and node C has a value of 0, the value of node A is 33+33+0=66.

So, in this example, the value of the root node is 66.

What is the value of the root node?

Your puzzle answer was 22633.
*/
package y2018;

import java.util.ArrayList;
import support.InputLoader;

class Node{
    static int totalPayload = 0;
    static int count = 0;
    static ArrayList<Node> nodes = new ArrayList<>();
    int payload, h1, h2;
    ArrayList<Integer> payloadArray;
    ArrayList<Node> childern;    
    int id;
    
    
    Node(){
        h1 = -1;
        h2 = -1;
        payload = 0;
        childern = new ArrayList<>();
        id = count++;
        payloadArray = new ArrayList<>();
        nodes.add(this);
    }
    
    @Override
    public String toString(){
        return Integer.toString(id);
    }
    
    int value(){
        int value = 0;
        if (childern.isEmpty()) {//nema potomky
            value = payload;
        } 
        else {
            for (int i = 0; i < h2; i++) {
                int index = payloadArray.get(i)-1;
                if (index < childern.size()) {
                    value += childern.get(index).value();
                }
            }
        }
            return value;
    }
}

public class Day08 {
    static String licString = new InputLoader("data/2018/day08.txt").getString().toString();
    //static String licString = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";
    static ArrayList<Node> stack = new ArrayList<>();
    
    
    public static void main(String[] args){
        
        String[] licInt = licString.split(" ");
        int[] lic = new int[licInt.length];
        for (int i = 0; i < lic.length; i++) lic[i] = Integer.parseInt(licInt[i]);

        int i = 0;
        Node root = new Node();
        stack.add(root);
        while (stack.size() > 0){
            Node node = stack.get(stack.size()-1);
            if (node.h1 == -1) { // jeste nema nactenou hlavicku - jeste jsem ho neprochazel
                node.h1 = lic[i];
                node.h2 = lic[i+1];
                i += 2;
                int childern = node.h1;
                if (childern == 0){ //kdyz nema potomky tak nacti payload
                    for (int j = i; j < i + node.h2; j++) {
                        node.payloadArray.add(lic[j]);
                        node.payload += lic[j];
                    }
                    Node.totalPayload += node.payload;
                    i += node.h2;
                    stack.remove(node);
                }
                else { // vytvor potomky a nahazej je na zasobnik
                    ArrayList<Node> newNodes = new ArrayList<>();
                    for (int j = 0; j < childern; j++) {
                        Node newNode = new Node();
                        node.childern.add(newNode);
                        newNodes.add(newNode);
                    }
                    for (int j = newNodes.size()-1; j >= 0; j--) {
                        stack.add(newNodes.get(j));
                    }

                }
            } // uz jsem ho prochazel, takze ted nasleduje payload
            else {
                for (int j = i; j < i + node.h2; j++) {
                    node.payloadArray.add(lic[j]);
                    node.payload += lic[j];
                }
                Node.totalPayload += node.payload;
                stack.remove(node);
                i += node.h2;
            }
        }
        System.out.println();
        System.out.println("Total payload: " + Node.totalPayload); //46962
        
        // ----------- part 2 ------------
        System.out.println(root.value()); //22633
    }
}

