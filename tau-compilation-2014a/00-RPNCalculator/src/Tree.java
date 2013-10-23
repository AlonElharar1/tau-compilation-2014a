/**
 * @team Happy Tree Friends
 * 1. Sagi Katorza
 * 2. Assaf Krintza
 * 3. Nir Malbin
 */

import java.util.ArrayList;
import java.util.List;

public class Tree<T> {
	private Node<T> root;

	public Tree(T rootData) {
		root = new Node<T>();
		root.data = rootData;
		root.children = new ArrayList<Node<T>>();
	}

	public class Node<T> {
		private T data;
		private Node<T> parent;
		private List<Node<T>> children;
	}
}