"""
Vertex Class
----------

This class represents an individual Vertex in a Graph. 

Each Vertex consists of the following properties:
    - is_safe: boolean indicating whether this Vertex is safe or not
    - edges: A list of Edge objects connected to this Vertex (adjlist)

The class also supports the following functions:
    - add_edge(Edge): Adds an edge to the Vertex. 
    - remove_edge(Edge): Removes an edge from the Vertex
    - get_edges(): Returns a list of all the Edges connected to the Vertex
    - update_status(is_safe): Updates the status of the Vertex to safe or not safe
    - get_is_safe(): Returns whether the Vertex is safe or not

Your task is to complete the following functions which are marked by the TODO comment.
You are free to add properties and functions to the class as long as the given signatures remain identical.
Good Luck!
"""


class Vertex():
    # These are the defined properties as described above
    is_safe: bool
    edges: 'set[Edge]'

    def __init__(self, is_safe: bool) -> None:
        """
        The constructor for the Vertex class.
        :param is_safe: Whether the Vertex is safe or not.
        """
        self.is_safe = is_safe
        self.edges = set()

        # need to update when we do remove_edge, add_edge, updatestatus
        self.unsafen = {} # unsafe neighbors
        self.safen = {} # safe neighbors
        self.parent = None
        self.parent2 = None
        self.cumw = 0
        

    def add_edge(self, edge: 'Edge') -> None:
        """
        Adds an edge to the given vertex.
        If adding the given edge would result in the graph no 
        longer being simple or the endpoint is invalid, do nothing.
        :param edge: The edge to add to this vertex.
        """
        # 只有在Edge initial的时候才会call
        # TODO Fill this in
        if self is edge.endpoints[0]:
            neighbor = edge.endpoints[1]
            current = edge.endpoints[0]
        else:
            # neighbor肯定不是self
            neighbor = edge.endpoints[0]
            # 这里current不一定是self
            current = edge.endpoints[1]
        # check self-loop cases + check edge includes self
        if current != neighbor and self is current:
            # check parallel cases; 如果self有该neighbor，那么不做任何操作，直接返回
            if self.safen.get(neighbor) or self.unsafen.get(neighbor):
                return
            # otherwise both vertex should add this edge
            neighbor.edges.add(edge)
            current.edges.add(edge)
            # update dict
            if neighbor.is_safe:
                current.safen[neighbor] = edge
            else:
                current.unsafen[neighbor] = edge
            if current.is_safe:
                neighbor.safen[current] = edge
            else:
                neighbor.unsafen[current] = edge


    def remove_edge(self, edge: 'Edge') -> None:
        """
        Removes the given edge from this vertex.
        If the edge is invalid or its endpoint does not exist, do nothing.
        :param vertex: The edge to remove.
        """
        # TODO Fill this in
        # 先获取edge的current和neighbor
        if self is edge.endpoints[0]:
            neighbor = edge.endpoints[1]
            current = edge.endpoints[0]
        else:
            # neighbor肯定不是self
            neighbor = edge.endpoints[0]
            # 这里current不一定是self
            current = edge.endpoints[1]
            
        # make current is self
        if self is current:
            # 更新2个edges
            current.edges.discard(edge)
            neighbor.edges.discard(edge)

            # 更新2个dict
            current.safen.pop(neighbor, None)
            current.unsafen.pop(neighbor, None)
            neighbor.safen.pop(current, None)
            neighbor.unsafen.pop(current, None)

    def get_edges(self) -> 'list[Edge]':
        """
        Returns the list of edges connected to the current node.
        :return: The list of edges connected to the current node.
        """
        return self.edges

    def update_status(self, is_safe: bool) -> None:
        """
        Updates the status of whether the vertex is safe or not.
        """
        # TODO Fill this in
        # 先更新current的status
        if is_safe == self.is_safe:
            return
        self.is_safe = is_safe

        #再更新所有neighbors的dict
        if is_safe:
            # 这里current 从 unsafe -> safe；所以要从neighbor的unsafen移动到safen
            for neighbor in self.safen:
                neighbor.unsafen.pop(self, None)
                neighbor.safen[self] = self.safen[neighbor]
            for neighbor in self.unsafen:
                # neighbor 是unsafe的
                neighbor.unsafen.pop(self, None)
                neighbor.safen[self] = self.unsafen[neighbor]
        else:
            for neighbor in self.safen:
                neighbor.safen.pop(self, None)
                neighbor.unsafen[self] = self.safen[neighbor]
            for neighbor in self.unsafen:
                neighbor.safen.pop(self, None)
                neighbor.unsafen[self] = self.unsafen[neighbor]

    def get_is_safe(self) -> bool:
        """
        Returns whether the vertex is safe or not.
        :return: True if the vertex is safe, False otherwise.
        """
        return self.is_safe
