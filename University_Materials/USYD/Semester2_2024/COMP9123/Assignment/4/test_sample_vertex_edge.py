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


class SampleVertexTestCases(unittest.TestCase):
    """
    Sample testing functionality for the Vertex class
    Feel free to add your own tests here!
    """

    def setUp(self):
        """
        Set up the vertices to be used throughout the test
        This is a basic graph
        A(S)-1-B(U)
        |       |
        2       1
        |       |
        C(U)-3-D(S)
        """

        self.A = Vertex(True)
        self.B = Vertex(False)
        self.C = Vertex(False)
        self.D = Vertex(True)

        self.edges = [
            Edge(self.A, self.B, 1),
            Edge(self.A, self.C, 2),
            Edge(self.B, self.D, 1),
            Edge(self.C, self.D, 3)
        ]

    def test_vertex_construction(self):
        """
        Test that the sample graph is correctly constructed
        """
        assert_lists(self.A.get_edges(), [self.edges[0], self.edges[1]])
        assert_lists(self.B.get_edges(), [self.edges[0], self.edges[2]])
        assert_lists(self.C.get_edges(), [self.edges[1], self.edges[3]])
        assert_lists(self.D.get_edges(), [self.edges[2], self.edges[3]])
            
    def test_edge_construction(self):
        weights = [1,2,1,3]
        for i in range(len(self.edges)):
            assert_equal(self.edges[i].get_weight(), weights[i], "weight not set for edge"+str(i))
        assert_lists(self.edges[0].get_endpoints(), [self.A, self.B])
        assert_lists(self.edges[1].get_endpoints(), [self.A, self.C])
        assert_lists(self.edges[2].get_endpoints(), [self.B, self.D])
        assert_lists(self.edges[3].get_endpoints(), [self.C, self.D])

    def test_vertex_safe_status(self):
        """
        Test that the sample graph has nodes with correct safe status
        """
        assert_equal(self.A.get_is_safe(), True, "A is safe")
        assert_equal(self.B.get_is_safe(), False, "B is not safe")
        assert_equal(self.C.get_is_safe(), False, "C is not safe")
        assert_equal(self.D.get_is_safe(), True, "D is safe")

    def test_vertex_add_edge(self):
        """
        Test that an extra edge can be added to the sample graph
        """
        e = Edge(self.A, self.D, 23)

        assert_lists(self.A.get_edges(), [self.edges[0], self.edges[1], e])
        assert_lists(self.B.get_edges(), [self.edges[0], self.edges[2]])
        assert_lists(self.C.get_edges(), [self.edges[1], self.edges[3]])
        assert_lists(self.D.get_edges(), [self.edges[2], self.edges[3], e])

    def test_vertex_remove_edge(self):
        """
        Test that an edge can be removed from the sample graph
        """
        self.A.remove_edge(self.edges[1])
        self.C.remove_edge(self.edges[1])

        assert_lists(self.A.get_edges(), [self.edges[0]])
        assert_lists(self.B.get_edges(), [self.edges[0], self.edges[2]])
        assert_lists(self.C.get_edges(), [self.edges[3]])
        assert_lists(self.D.get_edges(), [self.edges[2], self.edges[3]])

    def test_vertex_update_status(self):
        """
        Test that the safe status can be correctly updated
        """
        self.B.update_status(True)
        self.C.update_status(True)

        assert_equal(self.A.get_is_safe(), True, "A is safe")
        assert_equal(self.B.get_is_safe(), True, "B is safe")
        assert_equal(self.C.get_is_safe(), True, "C is safe")
        assert_equal(self.D.get_is_safe(), True, "D is safe")
        
    def test_edge_update_weight(self):
        """
        Test that the weight of edges can be correctly updated
        """
        self.edges[0].update_weight(43)
        assert_equal(self.edges[0].get_weight(), 43, "Weight not updated")
        assert_equal(self.edges[0].weight, 43, "Weight not updated")

if __name__ == '__main__':
    unittest.main()