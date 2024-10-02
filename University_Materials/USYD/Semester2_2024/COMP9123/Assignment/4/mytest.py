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
        assert got[i] in expected, \
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
        """
        self.graph = Graph()

        self.A = Vertex(True)
        self.B = Vertex(True)
        self.C = Vertex(True)
        # self.D = Vertex(False)
        # self.E = Vertex(True)
        # self.F = Vertex(False)
        # self.G = Vertex(False)
        # self.H = Vertex(True)
        # self.I = Vertex(False)
        # self.J = Vertex(True)
        # self.vertices = [self.A, self.B, self.C, self.D, self.E, self.F, self.G, self.H, self.I, self.J]
        self.vertices = [self.A, self.B, self.C]

        self.graph.add_vertex(self.A)
        self.graph.add_vertex(self.B)
        self.graph.add_vertex(self.C)
        # self.graph.add_vertex(self.D)
        # self.graph.add_vertex(self.E)
        # self.graph.add_vertex(self.F)
        # self.graph.add_vertex(self.G)
        # self.graph.add_vertex(self.H)
        # self.graph.add_vertex(self.I)
        # self.graph.add_vertex(self.J)

        self.edges = [
            self.graph.add_edge(self.A, self.B, 1),
            # self.graph.add_edge(self.A, self.D, 2),
            # self.graph.add_edge(self.B, self.C, 3),
            # self.graph.add_edge(self.C, self.D, 4),
            # self.graph.add_edge(self.C, self.E, 5),
            # self.graph.add_edge(self.C, self.F, 3),
            # self.graph.add_edge(self.E, self.I, 7),
            # self.graph.add_edge(self.E, self.H, 6),
            # self.graph.add_edge(self.F, self.G, 2),
            # self.graph.add_edge(self.F, self.H, 1),
            # self.graph.add_edge(self.H, self.J, 7),
            self.graph.add_edge(self.B, self.C, 4)
        ]

    def test_graph_exists_path(self):
        """
        Test that we can travel from A to D
        """
        path = self.graph.fastest_path(self.A, self.B, self.C)
        assert_equal(path, [self.A, self.B, self.C, self.B], "Incorrect path returned")

        self.graph.remove_vertex(self.A)
        # path = self.graph.fastest_path(self.A, self.B, self.C)
        assert_equal(self.A in path, False, "Incorrect path returned")


if __name__ == '__main__':
    unittest.main()