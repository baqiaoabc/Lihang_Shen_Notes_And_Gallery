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
        Test the most_direct_path function
        A(S)-1-B(U)-2-F(S)
        |       |      |
        2       1      2
        |       |      |
        C(S)-3-D(S)-5-E(S)
        """
        self.graph = Graph()
        self.A = Vertex(True)
        self.B = Vertex(False)
        self.C = Vertex(True)
        self.D = Vertex(True)
        self.E = Vertex(True)
        self.F = Vertex(True)
        self.vertices = [self.A, self.B, self.C, self.D, self.E, self.F]

        self.graph.add_vertex(self.A)
        self.graph.add_vertex(self.B)
        self.graph.add_vertex(self.C)
        self.graph.add_vertex(self.D)
        self.graph.add_vertex(self.E)
        self.graph.add_vertex(self.F)

        self.edges = [
            self.graph.add_edge(self.A, self.B, 2),
            self.graph.add_edge(self.B, self.F, 2),
            self.graph.add_edge(self.F, self.E, 3),
            self.graph.add_edge(self.E, self.D, 2),
            self.graph.add_edge(self.D, self.C, 1),
            self.graph.add_edge(self.C, self.A, 1),
            self.graph.add_edge(self.B, self.D, 1)
        ]

    def test_most_direct_path(self):
        path = self.graph.most_direct_path(self.A, self.E, [self.C, self.F])

        print(self.A)
        print(self.B)
        print(self.C)
        print(self.D)
        print(self.E)
        print(self.F)

        assert_equal(path, [self.A, self.C, self.D, self.E], "Incorrect path returned")

        print("========================================")
        self.graph.remove_edge(self.A, self.B)
        self.graph.remove_edge(self.A, self.C)
        self.graph.remove_edge(self.C, self.D)
        self.graph.remove_edge(self.B, self.D)

        self.B.update_status(True)

        self.graph.add_edge(self.A, self.D,1)
        self.graph.add_edge(self.C, self.B,1)
        self.graph.add_edge(self.F, self.D,1)
        self.graph.add_edge(self.B, self.E,1)

        path = self.graph.most_direct_path(self.A, self.E, [self.C, self.F])

        print(self.A)
        print(self.B)
        print(self.C)
        print(self.D)
        print(self.E)
        print(self.F)
        assert_equal(path, [self.A, self.D, self.F, self.E], "Incorrect path returned")

        print("========================================")
        self.graph.remove_edge(self.A, self.D)
        path = self.graph.most_direct_path(self.A, self.E, [self.C, self.F])
        print(self.A)
        print(self.B)
        print(self.C)
        print(self.D)
        print(self.E)
        print(self.F)

        assert_equal(path, None, "Incorrect path returned")

        print("========================================")
        self.graph.add_edge(self.A, self.D,1)
        # self.graph.remove_vertex(self.F)
        self.graph.remove_vertex(self.D)
        self.graph.add_edge(self.A, self.B,1)
        path = self.graph.most_direct_path(self.A, self.E, [self.C, self.F])
        print(self.A)
        print(self.B)
        print(self.C)
        print(self.D)
        print(self.E)
        print(self.F)

        # assert_equal(path, [self.A, self.D, self.E, self.B, self.C, self.B, self.E], "Incorrect path returned")
        assert_equal(path, [self.A, self.B, self.F, self.E], "Incorrect path returned")




if __name__ == '__main__':
    unittest.main()