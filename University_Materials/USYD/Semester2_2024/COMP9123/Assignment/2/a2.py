class Node:
    children: []
    parent: 'Node'
    gold: int
    k_sum: int
    k: int

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
        # values = self.get_all_gold_values()
        # print(values)
        # self.k_sum = sum(values[:self.k])
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

    def add_child(self, node) -> None:
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
        self.gold = gold

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

def helper(n):
    if n.is_external():
        return n.gold
    else:
        value = n.gold
        for child in n.children:
            print(n.gold)
            value += helper(child)
        return value

def put(node_a: Node, node_b: Node) -> None:
    node_a.add_child(node_b)

# 1
#     2
#         5
#         5
#     3
#         3
#         4
root = Node(1,3)
node1 = Node(2,3)
node2= Node(5,3)
node3= Node(5,3)

node4 = Node(3,3)
node5= Node(3,3)
node6 = Node(4,3)
print("-----------------------")
root.add_child(node1)
root.add_child(node4)
node1.add_child(node2)
node1.add_child(node3)
node4.add_child(node5)
node4.add_child(node6)


print(root.k_sum)

