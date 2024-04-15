HW_SOURCE_FILE=__file__


def pascal(row, column):
    """Returns a number corresponding to the value at that location
    in Pascal's Triangle.
    >>> pascal(0, 0)
    1
    >>> pascal(0, 5)	# Empty entry; outside of Pascal's Triangle
    0
    >>> pascal(3, 2)	# Row 4 (1 3 3 1), 3rd entry
    3
    >>> pascal(1,-1)
    0
    >>> pascal(-1, 1)
    0
    """
    "*** YOUR CODE HERE ***"
    if row < column or row < 0 or column < 0:
        return 0
    elif row == column or column == 0:
        return 1
    else:
        return pascal(row-1,column) + pascal(row-1,column-1)


def compose1(f, g):
    """"Return a function h, such that h(x) = f(g(x))."""
    def h(x):
        return f(g(x))
    return h

def repeated(f, n):
    """Return the function that computes the nth application of func (recursively!).

    >>> add_three = repeated(lambda x: x + 1, 3)
    >>> add_three(5)
    8
    >>> square = lambda x: x ** 2
    >>> repeated(square, 2)(5) # square(square(5))
    625
    >>> repeated(square, 4)(5) # square(square(square(square(5))))
    152587890625
    >>> repeated(square, 0)(5)
    5
    >>> from construct_check import check
    >>> # ban iteration
    >>> check(HW_SOURCE_FILE, 'repeated',
    ...       ['For', 'While'])
    True
    """
    "*** YOUR CODE HERE ***"
    # 2 base cases in total
    # accumulated from 0 to n
    if n ==0:
        return lambda n: n
    elif n == 1:
        return f
    else:
        return compose1(f,repeated(f,n-1))


def num_eights(x):
    """Returns the number of times 8 appears as a digit of x.

    >>> num_eights(3)
    0
    >>> num_eights(8)
    1
    >>> num_eights(88888888)
    8
    >>> num_eights(2638)
    1
    >>> num_eights(86380)
    2
    >>> num_eights(12345)
    0
    >>> from construct_check import check
    >>> # ban all assignment statements
    >>> check(HW_SOURCE_FILE, 'num_eights',
    ...       ['Assign', 'AugAssign'])
    True
    """
    "*** YOUR CODE HERE ***"
    # accumulated from determine the last digit to first digit
    if x == 8:
        return 1
    elif x < 10:
        return 0
    else:
        return num_eights(x%10) + num_eights(x//10)


def pingpong(n):
    """Return the nth element of the ping-pong sequence.

    >>> pingpong(8)
    8
    >>> pingpong(10)
    6
    >>> pingpong(15)
    1
    >>> pingpong(21)
    -1
    >>> pingpong(22)
    -2
    >>> pingpong(30)
    -2
    >>> pingpong(68)
    0
    >>> pingpong(69)
    -1
    >>> pingpong(80)
    0
    >>> pingpong(81)
    1
    >>> pingpong(82)
    0
    >>> pingpong(100)
    -6
    >>> from construct_check import check
    >>> # ban assignment statements
    >>> check(HW_SOURCE_FILE, 'pingpong', ['Assign', 'AugAssign'])
    True
    """
    "*** YOUR CODE HERE ***"
    # accumulated from 1 to n, so the recursion should be following
    # def helper(value, idx, direction):
    #     if idx == n:
    #         return value
    #     elif num_eights(idx) or idx % 8 == 0:
    #         if direction:
    #             return helper(value-1, idx+1, 0)
    #         else:
    #             return helper(value+1, idx+1, 1)
    #     else:
    #         if direction:
    #             return helper(value+1, idx+1, 1)
    #         else:
    #             return helper(value-1, idx+1, 0)
    # return helper(1,1,1)

    # if we begin from n to 1, we cannot know which direction we shouold 
    # begin with; for example, if n is 19, we donot know we should +1 or -1
    # at index 19; however, from 1 to n, we know the direction must begin 
    # with +1
    
    def helper(idx, direction):
        if idx == n:
            return direction
        if num_eights(idx) or idx % 8 == 0:
            return direction + helper(idx+1, direction*-1)
        else:
            return direction + helper(idx+1, direction)
    return helper(1,1)


def pingpong(n):
    """Return the nth element of the ping-pong sequence.

    >>> pingpong(8)
    8
    >>> pingpong(10)
    6
    >>> pingpong(15)
    1
    >>> pingpong(21)
    -1
    >>> pingpong(22)
    -2
    >>> pingpong(30)
    -2
    >>> pingpong(68)
    0
    >>> pingpong(69)
    -1
    >>> pingpong(80)
    0
    >>> pingpong(81)
    1
    >>> pingpong(82)
    0
    >>> pingpong(100)
    -6
    >>> from construct_check import check
    >>> # ban assignment statements
    >>> check(HW_SOURCE_FILE, 'pingpong', ['Assign', 'AugAssign'])
    True
    """
    "*** YOUR CODE HERE ***"
    # if we begin from n to 1, we cannot know which direction we shouold 
    # begin with; for example, if n is 19, we donot know we should +1 or -1
    # at index 19; however, from 1 to n, we know the direction begin with +1  
	
    # Method 1
    # def helper(value, idx, direction):
    #     if idx == n:
    #         return value
    #     elif num_eights(idx) or idx % 8 == 0:
    #         if direction:
    #             return helper(value-1, idx+1, 0)
    #         else:
    #             return helper(value+1, idx+1, 1)
    #     else:
    #         if direction:
    #             return helper(value+1, idx+1, 1)
    #         else:
    #             return helper(value-1, idx+1, 0)
    # return helper(1,1,1)

	# Method 2
    # def helper(idx, direction):
    #     if idx == n:
    #         return direction
    #     if num_eights(idx) or idx % 8 == 0:
    #         return direction + helper(idx+1, direction*-1)
    #     else:
    #         return direction + helper(idx+1, direction)
    # return helper(1,1)