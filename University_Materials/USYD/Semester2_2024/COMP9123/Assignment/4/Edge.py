from Vertex import Vertex
"""
Edge Class
----------

This class represents an individual Edge in a Graph. 

Each Edge consists of the following properties:
    - weight: positive int indicating the travel time of this edge 
    - endpoints: A tuple of two Vertex objects connected to this Edge

The class also supports the following functions:
    - get_endpoints(): Returns the endpoints of the edge
	- update_weight(int): Updates the weight of the edge to the given integer
	- get_weight(): Return the weight of the edge

Your task is to complete the following functions which are marked by the TODO comment.
You are free to add properties and functions to the class as long as the given signatures remain identical.
Good Luck!
"""


class Edge():
	# These are the defined properties as described above
    weight: int
    endpoints: 'tuple[Vertex]'

    def __init__(self, endpoint1: Vertex, endpoint2: Vertex, weight: int) -> None:
        """
        The constructor for the Edge class.
        :param endpoint1: The first endpoint
		:param endpoint2: The second endpoint
		:param weight: The weight of the edge
        """
        # TODO
        self.weight = weight
        self.endpoints = (endpoint1,endpoint2)
        # I need to also update the endpoints' edge info
        # 使用Edge.add_edge会同时更新endpoint1和endpoint2的信息
        endpoint1.add_edge(self)


    def get_endpoints(self) -> tuple[Vertex]:
        """
        Return the endpoints
        """
        return self.endpoints

    def update_weight(self, weight: int) -> None:
        """
        Updates the weight of the edge. If it's not a positive integer, do nothing.
		:param weight: The new weight of the edge
        """
        # TODO Fill this in
        # check weight is indeed int
        if (type(weight) != int):
            return None
        if weight>=0:
            self.weight=weight

    def get_weight(self) -> int:
        """
        Returns the weight of the vertex.
        """
        return self.weight
    
