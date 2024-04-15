this_file = __file__


def make_adder_inc(a):
    """
    >>> adder1 = make_adder_inc(5)
    >>> adder2 = make_adder_inc(6)
    >>> adder1(2)
    7
    >>> adder1(2) # 5 + 2 + 1
    8
    >>> adder1(10) # 5 + 10 + 2
    17
    >>> [adder1(x) for x in [1, 2, 3]]
    [9, 11, 13]
    >>> adder2(5)
    11
    """
    "*** YOUR CODE HERE ***"
    def inner_adder(b):
        nonlocal a
        ans,a = a+b,a+1
        return ans
    return inner_adder


def make_fib():
    """Returns a function that returns the next Fibonacci number
    every time it is called.

    >>> fib = make_fib()
    >>> fib()
    0
    >>> fib()
    1
    >>> fib()
    1
    >>> fib()
    2
    >>> fib()
    3
    >>> fib2 = make_fib()
    >>> fib() + sum([fib2() for _ in range(5)])
    12
    >>> from construct_check import check
    >>> # Do not use lists in your implementation
    >>> check(this_file, 'make_fib', ['List'])
    True
    """
    "*** YOUR CODE HERE ***"
    # fib_num1 = 0
    # fib_num2 = 1
    # def fib():
    #     nonlocal fib_num1,fib_num2
    #     res = fib_num1
    #     fib_num1,fib_num2 = fib_num2, fib_num1+fib_num2
    #     return res
    # return fib
    f1, f2 = 1, 0
    def fib():
        nonlocal f1, f2
        f1, f2 = f2, f1 + f2
        return f1
    return fib


def insert_items(lst, entry, elem):
    """
    >>> test_lst = [1, 5, 8, 5, 2, 3]
    >>> new_lst = insert_items(test_lst, 5, 7)
    >>> new_lst
    [1, 5, 7, 8, 5, 7, 2, 3]
    >>> large_lst = [1, 4, 8]
    >>> large_lst2 = insert_items(large_lst, 4, 4)
    >>> large_lst2
    [1, 4, 4, 8]
    >>> large_lst3 = insert_items(large_lst2, 4, 6)
    >>> large_lst3
    [1, 4, 6, 4, 6, 8]
    >>> large_lst3 is large_lst
    True
    """
    "*** YOUR CODE HERE ***"
    # # 这里是生成新的list的办法
    # if lst == []:
    #     return []
    # else:
    #     if lst[0] == entry:
    #         return [lst[0]] + [elem] + insert_items(lst[1:],entry,elem)
    #     return [lst[0]] + insert_items(lst[1:],entry,elem)


    # idx_list = []
    # for idx in range(len(lst)):
    #     if lst[idx] == entry:
    #         idx_list.append(idx+1)
    # count = 0
    # for idx in idx_list:
    #     lst.insert(idx+count,elem)
    #     count+=1
    # return lst

    # index = 0
    # size = len(lst)
    # while index < size:
    #     if lst[index] == entry:
    #         lst.insert(index + 1, elem)
    #         index += 1
    #         size += 1
    #     index += 1
    # return lst

    idx = 0
    for _ in range(len(lst)):
        if lst[idx] == entry:
            lst.insert(idx + 1, elem)
            idx+=1
        idx+=1
    return lst