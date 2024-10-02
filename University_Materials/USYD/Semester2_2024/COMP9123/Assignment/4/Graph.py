from Vertex import Vertex
from Edge import Edge
from collections import deque
import heapq
import itertools

from collections import Counter
import copy
"""
Graph Class
----------

This class represents the Graph modelling our courier network. 

Each Graph consists of the following properties:
    - vertices: A list of vertices comprising the graph

The class also supports the following functions:
    - add_vertex(vertex): Adds the vertex to the graph
    - remove_vertex(vertex): Removes the vertex from the graph
    - add_edge(vertex_A, vertex_B, weight): Adds an edge between the two vertices
    - remove_edge(vertex_A, vertex_B): Removes an edge between the two vertices
    - exists_path(s, t): Returns if a valid path exists from s to t through only safe vertices
    - fastest_path(s, t, oasis): Returns the shortest path from s to t via the oasis that visits only safe vertices
    - most_direct_path(s, t, oases): Returns a path visiting the fewest vertices from s to t via any of the oases that visits only safe vertices

Your task is to complete the following functions which are marked by the TODO comment.
Note that your modifications to the structure of the Graph should be correctly updated in the underlying Vertex and Edge classes!
You are free to add properties and functions to the class as long as the given signatures remain identical.
"""
class DequeWrapper(deque):
    def __lt__(self, other):
        # 根据 deque 的长度进行比较
        return len(self) < len(other)

class ListWrapper(list):
    def __lt__(self, other):
        # 根据 list 的长度进行比较
        return len(self) < len(other)


class Graph():
    # These are the defined properties as described above
    vertices: 'set[Vertex]'
    edges: 'set[Edge]'

    def __init__(self) -> None:
        """
        The constructor for the Graph class.
        """
        self.vertices = set()
        self.edges = set()

    def add_vertex(self, vertex: Vertex) -> None:
        """
        Adds the given vertex to the graph.
        If the vertex is already in the graph or is invalid, do nothing.
        :param vertex: The vertex to add to the graph.
        """
        # TODO Fill this in
        # check vertex is indeed Vertex
        if not isinstance(vertex, Vertex) or len(vertex.edges) > 0:
            return None
        self.vertices.add(vertex)

    def remove_vertex(self, vertex: Vertex) -> None:
        """
        Removes the given vertex from the graph
        If the vertex is not in the graph or is invalid, do nothing.
        :param vertex: The vertex to remove from the graph.
        """
        # TODO Fill this in
        # check vertex is indeed Vertex
        
        # # make sure vertex is in the graph.vertices
        # if vertex in self.vertices:
        #     temp = []
        #     # update its neighbors' Edges; A--B--C --> A C
        #     for e in vertex.get_edges():
        #         temp.append(e)
        #     for e in temp:
        #         vertex.remove_edge(e)

        # remove vertex from the graph.vertices
        if not isinstance(vertex, Vertex):
            return
        self.vertices.discard(vertex)
        # remove edges from all neighbors of vertex 通过call Vertex.remove_edge
        for neighbor in list(vertex.safen.keys()):
            neighbor.remove_edge(vertex.safen[neighbor])
        for neighbor in list(vertex.unsafen.keys()):
            neighbor.remove_edge(vertex.unsafen[neighbor])


    def add_edge(self, vertex_A: Vertex, vertex_B: Vertex, weight: int) -> Edge:
        """
        Adds an edge between the two vertices.
        If adding the edge would result in the graph no longer being simple or the vertices are invalid, do nothing.
        :param vertex_A: The first vertex.
        :param vertex_B: The second vertex.
		:return: The edge object that was created
        """
        # TODO Fill this in
        # check vertex is indeed Vertex
        
        # # check there is not a self-loop
        if vertex_A is vertex_B or weight < 0 or type(weight) != int:
            return None

        # check vertex_A and B are inside Graph.vertices?
        # if vertex_A in self.vertices and vertex_B in self.vertices:
        if isinstance(vertex_A, Vertex) and isinstance(vertex_B, Vertex):
            # initialization include add Edge to both endpoints
            new = Edge(vertex_A, vertex_B, weight)

            # update graph.edges
            self.edges.add(new)
            return new

    def remove_edge(self, vertex_A: Vertex, vertex_B: Vertex) -> None:
        """
        Removes the edge between vertex A and vertex B, if it exists.
        If an existing edge does not exist or the vertices are invalid, do nothing.
        :param vertex_A: The first vertex.
        :param vertex_B: The second vertex.
        """
        # TODO Fill this in
        # 只检查vertex_A的edges?
        # 从A的neighbor移除B；从B的neighbor移除A；从graph移除edge
        # both vertices need to exist in graph.vertices
        # if not (vertex_A in self.vertices and vertex_B in self.vertices):
        #     return None
        if vertex_A is vertex_B:
            return None

        if isinstance(vertex_A, Vertex) and isinstance(vertex_B, Vertex):
            # delete = None
            # for e in self.edges:
            #     if vertex_A in e.get_endpoints() and vertex_B in e.get_endpoints():
            #         vertex_A.remove_edge(e)
            #         delete = e
            #         break
            # 检查A的neighbor，可以通过dict直接获得edge
            if vertex_B.is_safe:
                edge = vertex_A.safen[vertex_B]
            else:
                edge = vertex_A.unsafen[vertex_B]
            # 从edge的2个endpoints移除，包括edges和dict，可以直接使用Vertex.remove_edge达成
            vertex_A.remove_edge(edge)

            # 从graph中移除
            self.edges.remove(edge)

    def exists_path(self, s: Vertex, t: Vertex) -> bool:
        """
        Returns whether a path from the source to the destination that visits only safe vertices exists.
        You can assume that the source and the destination are distinct safe vertices.
        :param s: The source vertex.
        :param t: The destination vertex.
        :return: True if a valid path exists through safe vertices only, or False otherwise
        """
        # TODO Fill this in
        # only visit safe vertices
        seen_v = set()
        seen_v.add(s)
        # initialize current layer
        current_layer = deque([s])
        # stop when current layer is empty
        while current_layer:
            for _ in range(len(current_layer)):
                # remove the left element
                v = current_layer.popleft()
                for neighbor in v.safen:
                    if neighbor not in seen_v:
                        seen_v.add(neighbor)
                        current_layer.append(neighbor)
            if t in seen_v:
                return True
        return False

    def Dijkstra(self, s, t):
        for v in self.vertices:
            v.parent = None
            v.cumv=0
        seen_v = set()
        seen_v.add(s)
        cond = True
        current = deque()
        while cond:
            cond = False
            shortest = 1000
            for v in seen_v:
                for neighbor in v.safen:
                    if neighbor not in seen_v and v.cumw + v.safen[neighbor].weight < shortest:
                        shortest = v.cumw + v.safen[neighbor].weight
                        current.append(neighbor)
                        current.append(v)
                        cond = True
            if cond:
                parent=current.pop() # v
                child = current.pop() # neighbor
                child.parent = parent
                child.cumw = shortest
                seen_v.add(child)
                current.clear()
                if t is child:
                    return True


    def fastest_path(self, s: Vertex, t: Vertex, oasis: Vertex) -> 'list[Vertex]':
        """
        Returns a shortest path from the source to the destination via the oasis that visits
        only safe vertices, if one exists. Otherwise, returns None.
        You can assume that the source, destination, and the oasis are distinct safe vertices.
        :param s: The starting vertex
        :param t: The ending vertex
        :param oasis: The oasis vertex
        :return: A list of vertices which begins at s and ends at t, representing the fastest path.
        """
        # TODO Fill this in
        if not(isinstance(s, Vertex) and isinstance(t, Vertex) and isinstance(oasis, Vertex)):
            return

        path = deque()

        # s -> oasis
        if self.Dijkstra(oasis, s):
            current=s
            while current.parent is not None:
                path.append(current)
                current = current.parent
        else:
            return None

        # oasis -> t
        if self.Dijkstra(t, oasis):
            current = oasis
            # will stop at oasis
            while current.parent is not None:
                path.append(current)
                current = current.parent
            path.append(t)
        else:
            return None

        return list(path)


    def new_BFS(self, s):
        # clean all vertices' parent
        for v in self.vertices:
            v.parent = None

        seen_v = set()
        seen_v.add(s)
        # initialize current layer
        current_layer = deque([s])
        # stop when current layer is empty
        while current_layer:
            for _ in range(len(current_layer)):
                # remove the left element
                v = current_layer.popleft()
                for neighbor in v.safen:
                    if neighbor not in seen_v:
                        neighbor.parent = v
                        seen_v.add(neighbor)
                        current_layer.append(neighbor)

    def new_BFS2(self, s):
        # it will update BFS tree which root is s; and return layer: [[layer0], [layer1],...]

        # clean all vertices' parent
        for v in self.vertices:
            v.parent2 = None

        seen_v = set()
        seen_v.add(s)
        # initialize current layer
        current_layer = deque([s])
        # stop when current layer is empty
        while current_layer:
            for _ in range(len(current_layer)):
                # remove the left element
                v = current_layer.popleft()
                for neighbor in v.safen:
                    if neighbor not in seen_v:
                        neighbor.parent2 = v
                        seen_v.add(neighbor)
                        current_layer.append(neighbor)

    def most_direct_path(self, s: Vertex, t: Vertex, oases: list[Vertex]) -> 'list[Vertex]':
        """
        Returns a path that visits the fewest vertices, starting from the source and ending at
        the destination via at least one of the oases that visits only safe vertices, if one
        exists. Otherwise, returns None.
        You can assume that the source, destination, and all oases are distinct safe vertices
        :param s: The starting vertex
        :param t: The ending vertex
        :param oases: A list of oases vertices
        :return: A list of vertices which begins at s and ends at t, representing the most direct path.
        """
        # TODO Fill this in
        self.new_BFS(s)
        self.new_BFS2(t)
        pathes_heap = []

        for o in oases:
            # 确保oasis能到达s和t
            if o.parent is not None and o.parent2 is not None:
                path = deque()
                current = o
                # oasis -> s
                while current.parent is not None:
                    current=current.parent
                    path.appendleft(current)
                
                current = o
                # oasis -> t
                while current.parent2 is not None:
                    path.append(current)
                    current=current.parent2
                path.append(t)
                heapq.heappush(pathes_heap, DequeWrapper(path))
        if pathes_heap:
            return list(heapq.heappop(pathes_heap))
        return None


