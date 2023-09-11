import java.util.Set;
import java.util.Map;
import java.util.Queue;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Stack;
import org.objectweb.asm.commons.*;
import org.objectweb.asm.tree.*;

public class CFG {
    Set<Node> nodes = new HashSet<Node>();
    Map<Node, Set<Node>> edges = new HashMap<Node, Set<Node>>();

    static class Node {
	int position;
	MethodNode method;
	ClassNode clazz;

	Node(int p, MethodNode m, ClassNode c) {
	    position = p; method = m; clazz = c;
	}

	public boolean equals(Object o) {
	    if (!(o instanceof Node)) return false;
	    Node n = (Node)o;
	    return (position == n.position) &&
		method.equals(n.method) && clazz.equals(n.clazz);
	}

	public int hashCode() {
	    return position + method.hashCode() + clazz.hashCode();
	}

	public String toString() {
	    return clazz.name + "." +
		method.name + method.signature + ": " + position;
	}
    }

    public void addNode(int p, MethodNode m, ClassNode c) {
			Node node = new Node(p, m, c); 
			if (!nodes.contains(node)) {
				 nodes.add(node); 
				 edges.put(node, new HashSet<>()); 
				} 
    }

    public void addEdge(int p1, MethodNode m1, ClassNode c1,
			int p2, MethodNode m2, ClassNode c2) {
				Node n1 = new Node(p1, m1, c1); 
				Node n2 = new Node(p2, m2, c2); 
				if (!nodes.contains(n1)) { 
					addNode(p1, m1, c1); 
				} 
				if (!nodes.contains(n2)) { 
					addNode(p2, m2, c2); 
				} 
				edges.get(n1).add(n2);
    }
	
	public void deleteNode(int p, MethodNode m, ClassNode c) {

		Node node = new Node(p, m, c); 
		if (nodes.contains(node)) { 
			nodes.remove(node); 
			for (Node neighbor : edges.get(node)) { 
				edges.get(neighbor).remove(node); 
			} edges.remove(node); 
		}
    }
	
    public void deleteEdge(int p1, MethodNode m1, ClassNode c1,
			int p2, MethodNode m2, ClassNode c2) {
				Node n1 = new Node(p1, m1, c1); 
				Node n2 = new Node(p2, m2, c2); 
				if (nodes.contains(n1) && nodes.contains(n2)) { 
					edges.get(n1).remove(n2); 
				}
    }
	

    public boolean isReachable(int p1, MethodNode m1, ClassNode c1,
			       int p2, MethodNode m2, ClassNode c2) {
				Node n1 = new Node(p1, m1, c1); 
				Node n2 = new Node(p2, m2, c2); 
				if (!nodes.contains(n1) || !nodes.contains(n2)) { 
					return false; 
				} 
				Set<Node> visited = new HashSet<>(); 
				Queue<Node> queue = new LinkedList<>(); 
				queue.add(n1); 
				visited.add(n1); 
				while (!queue.isEmpty()) { 
					Node node = queue.poll(); 
					if (node == n2) { 
						return true; 
					} 
					for (Node neighbor : edges.get(node)) { 
						if (!visited.contains(neighbor)) { 
							queue.add(neighbor); visited.add(neighbor); 
						} 
					} 
				} 
				return false; 
			}
}
