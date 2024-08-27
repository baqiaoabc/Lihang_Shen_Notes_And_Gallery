class Node():
    # These are the defined properties as described above
    children: list['Node']
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

        # We need to initialize ksum below
        self.k_sum = self.gold

    def update(self):
        self.highest_K_Value = [0] * self.k
        if self.parent is None:
            self.calculate_ksum(self.highest_K_Value)
            self.k_sum = sum(self.highest_K_Value)
        else:
            self.calculate_ksum(self.highest_K_Value)
            self.k_sum = sum(self.highest_K_Value)
            self.parent.update()

    def calculate_ksum(self, highestK):
        if self.gold > min(highestK):
            highestK.remove(min(highestK))
            highestK.append(self.gold)
        for child in self.children:
            child.calculate_ksum(highestK)
        while 0 in highestK:
            highestK.remove(0)


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
        self.gold = gold
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