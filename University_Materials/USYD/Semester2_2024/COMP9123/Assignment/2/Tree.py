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
        :param node_a: The node to add the child to. parent
        :param node_b: The child to add to the node. child
        """
        # TODO Add the child to the node
        if self.root is None and node_a is None and node_b is not None:
            self.root = node_b
        elif (node_a is None) or (node_b is None):
            return
        else:
            node_a.add_child(node_b)

        while node_a.parent != None:
            node_a = node_a.parent
            node_a.update()

    # 有1个hidden task only和这个有关


    def move_subtree(self, node_a: Node, node_b: Node) -> None:
        """
        The subtree rooted at node_a is made into a child of node_b.
        If node_b already has children, your function should make node_a the last child.
        You must ensure that the k_sum property is correct for all nodes, after moving the subtree.
        You can assume that node_b isn't in the subtree of node_a and you don't have to check for this.
        :param node_a: The root of the subtree to move. child
        :param node_b: The node to add the subtree to. parent
        """
        # TODO Move the subtree rooted at node_a to the last child of node_b
        if node_a is None or node_b is None:
            return
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

def find_root(n):
    if n.parent is not None:
        return find_root(n.parent)
    else:
        return n


def print_tree(n, level=0):
    indent = " " * (level * 4)
    print(f"{indent}(gold={n.gold}, k_sum={n.k_sum})")
    for child in n.children:
        print_tree(child, level + 1)

# 1
#     2
#         5
#         5
#     3
#         3
#         4
root = Node(1,3)

node1 = Node(2,3)
node1_1= Node(5,3)
node1_2= Node(5,3)

node2 = Node(3,3)
node2_1= Node(3,3)
node2_2 = Node(4,3)

root.add_child(node1)
root.add_child(node2)
node1.add_child(node1_1)
node1.add_child(node1_2)
node2.add_child(node2_1)
node2.add_child(node2_2)

# 9
#    7
#       8
node_new1 = Node(9,3)
node_new1_1 = Node(7,3)
node_new1_1_1 = Node(8,3)
node_new1.add_child(node_new1_1)
node_new1_1.add_child(node_new1_1_1)
print_tree(node_new1,0)
# --------------------------------------------------------

tree = Tree(root)
print_tree(root,0)

# 1
#     2
#         5
#         5
#         3
#               3
#               4
tree.move_subtree(node2,node1)
print_tree(root,0)

# 1
#     2
#         5
#               9
#                   7
#                       8
#         5
#         3
#               3
#               4
tree.put(node1_1,node_new1)
print_tree(root,0)


children = node1_1.children
tree.melt_subtree(node1_1)
print_tree(root,0)

print(children[0].children)