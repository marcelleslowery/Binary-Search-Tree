import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;

public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public BST() {
        root = null;
    }

    /**
     * Initializes the BST with the data in the collection. The data in the BST
     * should be added in the same order it is in the collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        root = null;
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else {
            for (T object : data) {
                this.add(object);
            }
        }

    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else if (root == null) {
            size++;
            root = new BSTNode<>(data);
        } else if (!this.contains(data)) {
            add(root, data);
        }
    }

    /**
     * Helper to add(T data), if data is not in tree
     * a new node is created and added
     *
     * @param current pointer node
     * @param data data to be added
     * @return the node containing data
     */
    private BSTNode<T> add(BSTNode<T> current, T data) {
        if (current == null) {
            size++;
            current = new BSTNode<>(data);
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(add(current.getLeft(), data));
        } else {
            current.setRight(add(current.getRight(), data));
        }
        return current;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else if (!this.contains(data)) {
            throw new java.util.NoSuchElementException("Data was not found!");
        } else {
            return remove(root, data);
        }
    }

    /**
     * Helper method to remove(T data), if data is found in the BST
     * it is removed and the BST is adjusted
     *
     * @param current pointer to parent node
     * @param data data to be found and node containing it removed
     * @return node to be removed from BST
     */
    private T remove(BSTNode<T> current, T data) {
        T retval = null;
        if (root.getData().equals(data)) {
            size--;
            retval = root.getData();
            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
            } else if (root.getRight() == null) {
                root = root.getLeft();
            } else if (root.getLeft() == null) {
                root = root.getRight();
            } else {
                root.setData(minVal(root));
            }
        } else if (current.getData().compareTo(data) > 0) {
            if (current.getLeft().getData().equals(data)) {
                size--;
                retval = current.getLeft().getData();
                if (current.getLeft().getLeft() == null
                        && current.getLeft().getRight() == null) {
                    current.setLeft(null);
                } else if (current.getLeft().getRight() == null) {
                    current.setLeft(current.getLeft().getLeft());
                } else if (current.getLeft().getLeft() == null) {
                    current.setLeft(current.getLeft().getRight());
                } else {
                    current.getLeft().setData(minVal(current.getLeft()));
                }
            } else {
                return remove(current.getLeft(), data);
            }
        } else if (current.getData().compareTo(data) < 0) {
            if (current.getRight().getData().equals(data)) {
                size--;
                retval = current.getRight().getData();
                if (current.getRight().getLeft() == null
                        && current.getRight().getRight() == null) {
                    current.setRight(null);
                } else if (current.getRight().getRight() == null) {
                    current.setRight(current.getRight().getLeft());
                } else if (current.getRight().getLeft() == null) {
                    current.setRight(current.getRight().getRight());
                } else {
                    current.getRight().setData(minVal(current.getRight()));
                }
            } else {
                return remove(current.getRight(), data);
            }
        }
        return retval;
    }

    /**
     * Finds the minimun successor of a node with two children
     * and returns the data to the node
     *
     * @param current pointer node
     * @return data from removed node
     */
    private T minVal(BSTNode<T> current) {
        BSTNode<T> parent = current.getRight();
        if (parent.getLeft() == null) {
            current.setRight(parent.getRight());
            return parent.getData();
        }
        while (parent.getLeft().getLeft() != null) {
            parent = parent.getLeft();
        }
        T retval = parent.getLeft().getData();
        parent.setLeft(parent.getLeft().getRight());
        return retval;
    }

    @Override
    public T get(T data) {
        T ret;
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else if (!this.contains(data)) {
            throw new java.util.NoSuchElementException("Data was not found!");
        } else {
            ret = get(root, data);
        }
        return ret;
    }

    /**
     * helper method to get(T data), find the node containing
     * the data given and return the data from the node
     *
     * @param current pointer node
     * @param data the data to be found in the BST
     * @return data contained in the node matching the param
     */
    private T get(BSTNode<T> current, T data) {
        if (current.getData().equals(data)) {
            return current.getData();
        } else if (current.getData().compareTo(data) > 0) {
            return get(current.getLeft(), data);
        } else {
            return get(current.getRight(), data);
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else {
            return contains(root, data);
        }
    }

    /**
     * helper method to contains(T data), checks to see if a node
     * contains the data given
     *
     * @param current pointer node
     * @param data the data to be found in the BST
     * @return whether or not data was found in the BST
     */
    private boolean contains(BSTNode<T> current, T data) {
        if (current == null) {
            return false;
        } else if (current.getData().compareTo(data) == 0) {
            return true;
        } else if (current.getData().compareTo(data) > 0) {
            return contains(current.getLeft(), data);
        } else {
            return contains(current.getRight(), data);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        return preorder(root);
    }

    /**
     * Helper method to preorder(), adds the data then checks left and right
     *
     * @param current pointer node
     * @return list containing the data from BST in preorder
     */
    private List<T> preorder(BSTNode<T> current) {
        List<T> preList = new ArrayList<>();
        if (current == null) {
            return preList;
        } else {
            preList.add(current.getData());
            preList.addAll(preorder(current.getLeft()));
            preList.addAll(preorder(current.getRight()));
        }
        return preList;
    }

    @Override
    public List<T> postorder() {
        return postorder(root);
    }

    /**
     * Helper method to postorder(),
     * checks the left then checks the right and adds the data
     *
     * @param current pointer node
     * @return list containing the data from BST in preorder
     */
    private List<T> postorder(BSTNode<T> current) {
        List<T> postList = new ArrayList<>();
        if (current == null) {
            return postList;
        } else {
            postList.addAll(postorder(current.getLeft()));
            postList.addAll(postorder(current.getRight()));
            postList.add(current.getData());
        }
        return postList;
    }

    @Override
    public List<T> inorder() {
        return inorder(root);
    }

    /**
     * Helper method to inorder(),
     * checks the left then add the data and then checks the right
     *
     * @param current pointer node
     * @return list containing the data from BST in preorder
     */
    private List<T> inorder(BSTNode<T> current) {
        List<T> inList = new ArrayList<>();
        if (current == null) {
            return inList;
        } else {
            inList.addAll(inorder(current.getLeft()));
            inList.add(current.getData());
            inList.addAll(inorder(current.getRight()));
        }
        return inList;
    }

    @Override
    public List<T> levelorder() {
        Queue<BSTNode<T>> nodeList = new LinkedList<>();
        List<T> levelList = new LinkedList<>();
        nodeList.add(root);
        BSTNode<T> current = nodeList.peek();
        while (!nodeList.isEmpty() && current != null) {
            current = nodeList.poll();
            levelList.add(current.getData());
            if (current.getLeft() != null) {
                nodeList.add(current.getLeft());
            }
            if (current.getRight() != null) {
                nodeList.add(current.getRight());
            }
        }
        return levelList;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public int height() {
        if (root == null) {
            return -1;
        }
        return height(root);
    }

    /**
     * Helper method to height() checks the height of left and right subtree
     *
     * @param current pointer node
     * @return max height from left and right subtree
     */
    private int height(BSTNode<T> current) {
        if (current == null) {
            return -1;
        } else {
            return Math.max(height(current.getLeft()),
                    height(current.getRight())) + 1;
        }
    }

    @Override
    public int depth(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null!");
        } else if (!this.contains(data)) {
            return -1;
        } else {
            return depth(root, data);
        }
    }

    /**
     * Helper to depth(T data) looks for the node containing data and returns
     * the distance from the root
     *
     * @param current pointer node
     * @param data the data to be searched for
     * @return how far down the BST the data is
     */
    private int depth(BSTNode<T> current, T data) {
        if (root == null) {
            return -1;
        } else if (current.getData().equals(data)) {
            return 1;
        } else if (current.getData().compareTo(data) > 0) {
            return depth(current.getLeft(), data) + 1;
        } else {
            return depth(current.getRight(), data) + 1;
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     * DO NOT USE IT IN YOUR CODE
     * DO NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        return root;
    }
}
