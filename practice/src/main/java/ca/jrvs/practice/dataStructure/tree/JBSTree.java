package ca.jrvs.practice.dataStructure.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * A simplified BST implementation
 *
 * @param <E> type of object to be stored
 */
public class JBSTree<E> implements JTree<E> {

  /**
   * The comparator used to maintain order in this tree map
   * Comparator cannot be null
   */
  private final Comparator<E> comparator;

  private Node<E> root;
  /**
   * Create a new BST
   *
   * @param comparator the comparator that will be used to order this map.
   * @throws IllegalArgumentException if comparator is null
   */
  public JBSTree(Comparator<E> comparator) {
    if (comparator == null) {
      throw new IllegalArgumentException("Comparator cannot be null");
    }

    this.comparator = comparator;
    this.root = null;
  }

  /**
   * Insert an object into the BST.
   * Please review the BST property.
   *
   * @param object item to be inserted
   * @return inserted item
   * @throws IllegalArgumentException if the object already exists
   */
  @Override
  public E insert(E object) {
    if (root == null) {
      root = new Node<>(object, null);
      return object;
    }

    insertRecursive(root, object);
    return object;
  }

  private void insertRecursive(Node<E> root, E object) {
    int compare = comparator.compare(object, root.getValue());

    if (compare > 0) {
      if (root.getRight() == null) {
        root.right = new Node<>(object, root);
      } else {
        insertRecursive(root.right, object);
      }

    } else if (compare < 0) {
      if (root.getLeft() == null) {
        root.left = new Node<>(object, root);
      } else {
        insertRecursive(root.left, object);
      }

    } else {
      throw new IllegalArgumentException("The given object already exists in the tree");
    }
  }
  /**
   * Search and return an object, return null if not found
   *
   * @param object to be found
   * @return the object if exists or null if not
   */
  @Override
  public E search(E object) {
    if (root == null) {
      return null;
    } else {
      return searchRecursive(root, object);
    }
  }

  private E searchRecursive(Node<E> root, E object) {
    E returnObj = null;

    if (root != null) {
      int compare = comparator.compare(object, root.getValue());

      if (compare > 0) {
        returnObj = searchRecursive(root.getRight(), object);
      } else if (compare < 0) {
        returnObj = searchRecursive(root.getLeft(), object);
      } else {
        returnObj = object;
      }
    }

    return returnObj;
  }
  /**
   * Remove an object from the tree.
   *
   * @param object to be removed
   * @return removed object
   * @throws IllegalArgumentException if the object not exists
   */
  @Override
  public E remove(E object) {
    if (root == null) {
      throw new IllegalArgumentException("Cannot remove from an empty tree");
    }

    if (search(object) == null) {
      throw new IllegalArgumentException("Object does not exist in the tree");
    }

    return removeRecursive(root, object).getValue();
  }

  private Node<E> removeRecursive(Node<E> root, E object) {
    if (root == null) {
      return root;
    }

    int compare = comparator.compare(object, root.getValue());

    if (compare > 0) {
      root.right = removeRecursive(root.right, object);
    } else if (compare < 0) {
      root.left = removeRecursive(root.left, object);
    } else {
      if (root.left == null) {
        return root.right;
      } else if (root.right == null) {
        return root.left;
      }

      root.value = minValue(root.right);
      root.right = removeRecursive(root.right, root.value);
    }

    return root;
  }

  private E minValue(Node<E> root) {
    E minVal = root.value;

    while (root.left != null) {
      minVal = root.left.value;
      root = root.left;
    }

    return minVal;
  }
  /**
   * traverse the tree recursively
   *
   * @return all objects in pre-order
   */
  @Override
  public E[] preOrder() {
    return (E[]) preOrderRecursive(root).toArray();
  }

  private List<E> preOrderRecursive(Node<E> root) {
    List<E> nodeList = new ArrayList<>();

    if (root == null) {
      return nodeList;
    }

    nodeList.add(root.getValue());
    nodeList.addAll(preOrderRecursive(root.getLeft()));
    nodeList.addAll(preOrderRecursive(root.getRight()));

    return nodeList;
  }
  /**
   * traverse the tree recursively
   *
   * @return all objects in-order
   */
  @Override
  public E[] inOrder() {
    return (E[]) inOrderRecursive(root).toArray();
  }

  private List<E> inOrderRecursive(Node<E> root) {
    List<E> nodeList = new ArrayList<>();

    if (root == null) {
      return nodeList;
    }

    nodeList.addAll(inOrderRecursive(root.getLeft()));
    nodeList.add(root.getValue());
    nodeList.addAll(inOrderRecursive(root.getRight()));

    return nodeList;
  }
  /**
   * traverse the tree recursively
   *
   * @return all objects pre-order
   */
  @Override
  public E[] postOrder() {
    return (E[]) postOrderRecursive(root).toArray();
  }

  private List<E> postOrderRecursive(Node<E> root) {
    List<E> nodeList = new ArrayList<>();

    if (root == null) {
      return nodeList;
    }

    nodeList.addAll(postOrderRecursive(root.getLeft()));
    nodeList.addAll(postOrderRecursive(root.getRight()));
    nodeList.add(root.getValue());

    return nodeList;
  }
  /**
   * traverse through the tree and find out the tree height
   * @return height
   * @throws NullPointerException if the BST is empty
   */
  @Override
  public int findHeight() {
    if (root == null) {
      throw new NullPointerException("Tree is empty");
    }

    return findHeightRecursive(root);
  }

  private int findHeightRecursive(Node<E> root) {
    if (root == null) {
      return 0;
    } else {
      int leftHeight = findHeightRecursive(root.left);
      int rightHeight = findHeightRecursive(root.right);

      return Math.max(leftHeight, rightHeight) + 1;
    }
  }
  static final class Node<E> {

    E value;
    Node<E> left;
    Node<E> right;
    Node<E> parent;

    public Node(E value, Node<E> parent) {
      this.value = value;
      this.parent = parent;
    }

    public E getValue() {
      return value;
    }

    public void setValue(E value) {
      this.value = value;
    }

    public Node<E> getLeft() {
      return left;
    }

    public void setLeft(Node<E> left) {
      this.left = left;
    }

    public Node<E> getRight() {
      return right;
    }

    public void setRight(Node<E> right) {
      this.right = right;
    }

    public Node<E> getParent() {
      return parent;
    }

    public void setParent(Node<E> parent) {
      this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (!(o instanceof Node)) {
        return false;
      }
      Node<?> node = (Node<?>) o;
      return getValue().equals(node.getValue()) &&
          Objects.equals(getLeft(), node.getLeft()) &&
          Objects.equals(getRight(), node.getRight()) &&
          getParent().equals(node.getParent());
    }

    @Override
    public int hashCode() {
      return Objects.hash(getValue(), getLeft(), getRight(), getParent());
    }
  }

}