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
    children: list['Node']
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
        # if self.parent is None:
        # 	self.children.append(node)
        # 	node.parent = self
        # 	self.update()
        # else:
        # 	self.children.append(node)
        # 	node.parent = self
        # 	self.update()
        # 	self.parent.update()
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


    def get_children(self) -> list['Node']:
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

#----------------------------------------------------------------------------------------------------------------------

from Node import Node

"""
Tree
----------

This class represents the Tree used to model our dwarven mine. 

Each Tree consists of the following properties:
	- root: The root of the Tree

The class also supports the following functions:
	- put(node_a, node_b): Adds node_b as the last child of node_a
	- move_subtree(node_a, node_b): Move node_a to the last child of node_b. Update k_sum
	- melt_subtree(node): Removes the subtree of the node and updates the node's gold with the sum of the gold in its subtree. Update k_sum

Your task is to complete the following functions which are marked by the TODO comment.
Note that your modifications to the structure of the tree should be correctly updated in the underlying Node class!
You are free to add properties and functions to the class as long as the given signatures remain identical.
"""


class Tree():
    # These are the defined properties as described above
    root: Node

    def __init__(self, root: Node = None) -> None:
        """
        The constructor for the Tree class.
        :param root: The root node of the Tree.
        """
        self.root = root

    def put(self, node_a: Node, node_b: Node) -> None:
        """
        Adds node_b as the last child of node_a.
        You are guranteed that the given node is not already part of the tree
        :param node_a: The node to add the child to.
        :param node_b: The child to add to the node.
        """
        # TODO Add the child to the node

        node_a.add_child(node_b)

    # 有1个hidden task only和这个有关


    def move_subtree(self, node_a: Node, node_b: Node) -> None:
        """
        The subtree rooted at node_a is made into a child of node_b.
        If node_b already has children, your function should make node_a the last child.
        You must ensure that the k_sum property is correct for all nodes, after moving the subtree.
        You can assume that node_b isn't in the subtree of node_a and you don't have to check for this.
        :param node_a: The root of the subtree to move.
        :param node_b: The node to add the subtree to.
        """
        # TODO Move the subtree rooted at node_a to the last child of node_b

        node_a.parent.children.remove(node_a)
        node_a.parent.update()
        node_b.add_child(node_a)


    # 有1个hidden task only和这个有关
    # self.put(node_b,node_a)




    def melt_subtree(self, node_a) -> None:
        """
        Removes the subtree rooted at node_a and updates the gold value of node_a with the sum of the gold in its (removed) subtree.
        You must ensure that the k_sum property is correct for all nodes, after removing the subtree and updating the gold value.
        """
        # TODO Remove the subtree, update the gold value of node_a and update k_sum values

        node_a.gold = helper(node_a)
        # node_a.children = []
        dele(node_a)
        node_a.update()

    # 没有hidden task only和这个相关；或者可能没有通过
    # 可能没有让所有被删除节点的parent为0？


# 7 - 2 = 5 个需要联动
# 有2个hidden task only和 move and melt 相关
# 有0个hidden task only和 put and melt 相关
# 有2个hidden task only和 put and move 相关
# 也就是说，剩下的一个要么是 put and melt 相关； 要么是所有联合使用；要么是只有melt

def helper(n):
    if n.is_external():
        return n.gold
    else:
        value = n.gold
        for child in n.children:
            value += helper(child)
        return value

def dele(n):
    for child in n.children:
        child.parent = None
        dele(child)
    n.children = []
