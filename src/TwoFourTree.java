public class TwoFourTree {
    private class TwoFourTreeItem {
        int values = 1;                             // Values is the number of values within the node Either 1,2, or 3.
        int value1 = 0;                             // always exists.
        int value2 = 0;                             // exists iff the node is a 3-node or 4-node.
        int value3 = 0;                             // exists iff the node is a 4-node.
        boolean isLeaf = true;
        
        TwoFourTreeItem parent = null;              // parent exists iff the node is not root.
        TwoFourTreeItem leftChild = null;           // left and right child exist iff the note is a non-leaf.
        TwoFourTreeItem rightChild = null;          
        TwoFourTreeItem centerChild = null;         // center child exists iff the node is a non-leaf 3-node.
        TwoFourTreeItem centerLeftChild = null;     // center-left and center-right children exist iff the node is a non-leaf 4-node.
        TwoFourTreeItem centerRightChild = null;

        // # of values + 1 = Name of the number node

        public boolean isTwoNode() { // Two node: 1 value within the node
            if(values == 1) return true;  
            return false;
        }

        public boolean isThreeNode() { // Three node: 2 values within the node
            if (values == 2) return true;
            return false;
        }

        public boolean isFourNode() { // Four node: 3 values within the node (Full)
            if(values == 3) return true;
            return false;
        }

        public boolean isRoot() { // Checks if the node is the root node
            if(parent == null) return true;
            return false;
        }

        public TwoFourTreeItem(int value1) { // Constructor for a two node. When 1 value is created on the spot.
            this.value1 = value1;
        }

        public TwoFourTreeItem(int value1, int value2) { // Constructor for a three node. When 2 value is created on the spot.
            this.value1 = value1;
            this.value2 = value2;
            values = 2;
        }

        public TwoFourTreeItem(int value1, int value2, int value3) { // Constructor for a four node. When 1 value is created on the spot.
            this.value1 = value1;
            this.value2 = value2;
            this.value3 = value3;
            values = 3;
        }

        private void printIndents(int indent) {
            for(int i = 0; i < indent; i++) System.out.printf("  ");
        }

        public void printInOrder(int indent) {
            if(!isLeaf) leftChild.printInOrder(indent + 1);
            printIndents(indent);
            System.out.printf("%d\n", value1);
            if(isThreeNode()) {
                if(!isLeaf) centerChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
            } else if(isFourNode()) {
                if(!isLeaf) centerLeftChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value2);
                if(!isLeaf) centerRightChild.printInOrder(indent + 1);
                printIndents(indent);
                System.out.printf("%d\n", value3);
            }
            if(!isLeaf) rightChild.printInOrder(indent + 1);
        }
    }

    TwoFourTreeItem root = null;

    // Adding a value to the tree
    public boolean addValue(int value) {
        return false;
    }

    // Searching for a value in the tree
    public boolean hasValue(int value) {
        return false;
    }

    // Deleting a value from the tree
    public boolean deleteValue(int value) {
        return false;
    }

    // Printing the tree in order
    public void printInOrder() {
        if(root != null) root.printInOrder(0);
    }

    public TwoFourTree() {

    }
}
