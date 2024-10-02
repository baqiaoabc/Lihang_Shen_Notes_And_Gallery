from Graph import Graph
from Vertex import Vertex
from Edge import Edge

import unittest


def assert_equal(got, expected, msg):
    """
    Simple assert helper
    """
    assert expected == got, \
        "[{}] Expected: {}, got: {}".format(msg, expected, got)


def assert_lists(got: 'list[Vertex]', expected: 'list[Vertex]'):
    """
    Assert two lists are equal not considering order
    """
    got = list(got)
    expected = set(expected)
    assert_equal(len(got), len(expected),
                 "Incorrect number of elements returned")
    for i in range(len(got)):
        assert got[i] in expected,\
            "Element {} not in expected list".format(got[i])

class SampleGraphTestCases(unittest.TestCase):
    """
    Sample testing functionality for the Graph class
    Feel free to add your own tests here!
    """

    def setUp(self):
        """
        Set up the vertices to be used throughout the test
        This is a basic graph. S is safe, U is unsafe
        A(S)-1-B(U)
        |       |
        2       1
        |       |
        C(U)-3-D(S)
        """
        self.graph = Graph()

        self.A = Vertex(True)
        self.B = Vertex(False)
        self.C = Vertex(False)
        self.D = Vertex(True)
        self.vertices = [self.A, self.B, self.C, self.D]

        self.graph.add_vertex(self.A)
        self.graph.add_vertex(self.B)
        self.graph.add_vertex(self.C)
        self.graph.add_vertex(self.D)

        self.edges = [
            self.graph.add_edge(self.A, self.B, 1),
            self.graph.add_edge(self.A, self.C, 2),
            self.graph.add_edge(self.B, self.D, 1),
            self.graph.add_edge(self.C, self.D, 3)
        ]


    def test_graph_construction(self):
        """
        Test that the sample graph is correctly constructed
        """
        assert_lists(self.vertices, self.graph.vertices)
        assert_lists(self.edges, self.graph.edges)
        assert_lists(self.A.get_edges(), [self.edges[0], self.edges[1]])
        assert_lists(self.B.get_edges(), [self.edges[0], self.edges[2]])
        assert_lists(self.C.get_edges(), [self.edges[1], self.edges[3]])
        assert_lists(self.D.get_edges(), [self.edges[2], self.edges[3]])
        weights = [1,2,1,3]
        for i in range(len(self.edges)):
            assert_equal(self.edges[i].get_weight(), weights[i], "weight not set for edge"+str(i))
        safe = [True,False,False,True]
        for i in range(len(self.vertices)):
            assert_equal(self.vertices[i].is_safe, safe[i], "safe not set for vertex"+str(i))
        
    def test_graph_add_edge(self):
        """
        Test that an extra edge can be added to the sample graph
        """
        self.edges.append(self.graph.add_edge(self.A, self.D, 5))
        assert_lists(self.A.get_edges(), [self.edges[0], self.edges[1], self.edges[4]])
        assert_lists(self.B.get_edges(), [self.edges[0], self.edges[2]])
        assert_lists(self.C.get_edges(), [self.edges[1], self.edges[3]])
        assert_lists(self.D.get_edges(), [self.edges[2], self.edges[3], self.edges[4]])

    def test_graph_remove_edge(self):
        """
        Test that an edge can be removed from the sample graph
        """
        self.graph.remove_edge(self.A, self.C)

        assert_lists(self.A.get_edges(), [self.edges[0]])
        assert_lists(self.B.get_edges(), [self.edges[0], self.edges[2]])
        assert_lists(self.C.get_edges(), [self.edges[3]])
        assert_lists(self.D.get_edges(), [self.edges[2], self.edges[3]])

    def test_graph_exists_path(self):
        """
        Test that we can travel from A to D
        """
        assert_equal(self.graph.exists_path(self.A, self.D), False, "Cannot reach it because B and C are not safe")
        self.edges.append(self.graph.add_edge(self.A, self.D, 5))
        assert_equal(self.graph.exists_path(self.A, self.D), True, "Now it can reach it through the new edge that was added")
        

    def test_fastest_path(self):
        """
        Test the fastest_path function
        A(S)-1-B(U)
        |       |
        2       1
        |       |
        C(S)-3-D(S)-3-E(S)
        """
        self.E = Vertex(True)
        self.graph.add_vertex(self.E)
        self.edges.append(self.graph.add_edge(self.D, self.E, 3))
        self.C.update_status(True)
        assert_equal(self.graph.fastest_path(self.A, self.E, self.D), [self.A, self.C, self.D, self.E], "Incorrect path returned")

        self.B.update_status(True)
        # self.graph.Dijkstra(self.A, self.D)
        # print(self.A.parent)
        # print(self.A.parent is None)
        # print(self.D.parent)
        # print(self.D.cumw)
        # print(self.C.cumw)
        # print(self.A.cumw)
        # print(self.B)
        # print(self.D.parent is self.B)
        assert_equal(self.graph.fastest_path(self.A, self.E, self.D), [self.A, self.B, self.D, self.E], "Incorrect path returned")



        self.edges.append(self.graph.add_edge(self.A, self.D, 1))
        # print(self.A.get_edges())
        # print(self.D.get_edges())
        assert_equal(self.graph.fastest_path(self.A, self.E, self.D), [self.A, self.D, self.E], "Incorrect path returned")
        self.B.update_status(False)
        self.C.update_status(False)
        self.graph.remove_edge(self.A, self.D)
        assert_equal(self.graph.fastest_path(self.A, self.E, self.D), None, "Incorrect path returned")
        
    def test_most_direct_path(self):
        """
        Test the most_direct_path function
        A(S)-1-B(U)-2-F(S)
        |       |      |
        2       1      2
        |       |      |
        C(S)-3-D(S)-5-E(S)
        """
        self.E = Vertex(True)
        self.graph.add_vertex(self.E)
        self.edges.append(self.graph.add_edge(self.D, self.E, 5))
        self.F = Vertex(True)
        self.graph.add_vertex(self.F)
        self.edges.append(self.graph.add_edge(self.B, self.F, 2))
        self.edges.append(self.graph.add_edge(self.F, self.E, 2))
        self.edges.append(self.graph.add_edge(self.A, self.D, 3))
        self.edges.append(self.graph.add_edge(self.D, self.F, 2))
        self.edges.append(self.graph.add_edge(self.B, self.E, 1))
        self.edges.append(self.graph.add_edge(self.B, self.C, 1))
        self.graph.remove_edge(self.D, self.C)
        self.graph.remove_edge(self.A, self.B)
        self.C.update_status(True)
        
        path = self.graph.most_direct_path(self.A, self.E, [self.C, self.F])

        print(self.A)
        print(self.B)
        print(self.C)
        print(self.D)
        print(self.E)
        print(self.F)


        assert_equal(path, [self.A, self.D, self.F, self.E], "Incorrect path returned")
        self.graph.add_edge(self.D, self.C, 23)
        path = self.graph.most_direct_path(self.A, self.D, [self.F, self.C])
        assert_equal(path, [self.A, self.C, self.D], "Incorrect path returned")
        self.B.update_status(True)
        path = self.graph.most_direct_path(self.A, self.D, [self.F, self.C])
        assert_equal(path, [self.A, self.C, self.D], "Incorrect path returned")
        path = self.graph.most_direct_path(self.A, self.E, [self.F, self.D, self.C])
        assert_equal(path, [self.A, self.D, self.E], "Incorrect path returned")
        
if __name__ == '__main__':
    unittest.main()