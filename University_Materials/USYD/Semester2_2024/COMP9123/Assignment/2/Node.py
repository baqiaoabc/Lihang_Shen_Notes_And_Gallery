"""
Tree Node
----------

This class represents an individual Node in a Tree.

Each Node consists of the following properties:
	- children: List of child Nodes
	- parent: Pointer to the parent Node in the tree
	- gold: The gold at this point
	- k_sum: The sum of the k highest gold values in the subtree rooted at this node

The class also supports the following functions:
	- add_child(Node): Adds the given Node as a child
	- is_external(): Returns True if the Node is a leaf node
	- get_children(): Returns the children of this node
	- update_gold(int): Updates the gold value at this node to the given value
	- return_k_sum(): Returns the k_sum at this node

Your task is to complete the following functions which are marked by the TODO comment.
Note that your Node should automatically update the k_sum when the subtree changes (if branches change or gold values are updated!)
You are free to add properties and functions to the class as long as the given signatures remain identical
"""


class Node():
    # These are the defined properties as described above
    children: []
    parent: 'Node'
    gold: int
    k_sum: int
    k: int # between 1 and n

    def __init__(self, gold: int, k: int) -> None:
        """
        The constructor for the Node class.
        :param gold: The gold of the node.
        :param k: Value used to calculate k_sum.
        """
        # TODO Initialize the properties of the node
        self.gold = gold
        self.k = k
        self.children = []
        self.parent = None
        # because initialized node does not have children
        self.k_sum = self.gold

    def update(self):
        if self.parent is None:
            values = self.get_all_gold_values()
            self.k_sum = sum(values[:self.k])
        else:
            values = self.get_all_gold_values()
            self.k_sum = sum(values[:self.k])
            self.parent.update()

    def get_all_gold_values(self):
        # return all gold values in order
        collection = [self.gold]
        for child in self.children:
            collection.extend(child.get_all_gold_values())
        collection.sort()
        return collection[::-1]


    def add_child(self, node: 'Node') -> None:
        """
        Adds the given node as a child of the current node.
        The given node is guaranteed to be new and not a child of any other node.
        :param node: The node to add as the child
        """
        # TODO Add the given node as the right child of the current node
        self.children.append(node)
        node.parent = self
        self.update()


    def is_external(self) -> bool:
        """
        Returns True if the node is a leaf node.
        :return: True if the node is a leaf node.
        """
        return len(self.children) == 0


    def update_gold(self, gold: int) -> None:
        """
        Updates the gold of the current node.
        :param gold: The new gold of the node.
        """
        # TODO Update the gold of the node
        self.gold=gold
        self.update()


    def get_children(self):
        """
        Returns the children of the current node.
        :return: The children of the current node.
        """
        return self.children

    def return_k_sum(self) -> int:
        """
        Returns the k_sum of the current node.
        :return: The k_sum of the current node.
        """
        # TODO Return the k_sum of the node
        return self.k_sum
