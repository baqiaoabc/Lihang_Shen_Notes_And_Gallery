email = 'example_key'

def copycat(lst1, lst2):
    """
    Write a function `copycat` that takes in two lists.
        `lst1` is a list of strings
        `lst2` is a list of integers

    It returns a new list where every element from `lst1` is copied the
    number of times as the corresponding element in `lst2`. If the number
    of times to be copied is negative (-k), then it removes the previous
    k elements added.

    Note 1: `lst1` and `lst2` do not have to be the same length, simply ignore
    any extra elements in the longer list.

    Note 2: you can assume that you will never be asked to delete more
    elements than exist


    >>> copycat(['a', 'b', 'c'], [1, 2, 3])
    ['a', 'b', 'b', 'c', 'c', 'c']
    >>> copycat(['a', 'b', 'c'], [3])
    ['a', 'a', 'a']
    >>> copycat(['a', 'b', 'c'], [0, 2, 0])
    ['b', 'b']
    >>> copycat([], [1,2,3])
    []
    >>> copycat(['a', 'b', 'c'], [1, -1, 3])
    ['c', 'c', 'c']
    """
    # my answer: partly correct
    # def copycat_helper(lst1, lst2, ans):
    #     if lst1 == [] or lst2 ==[]:
    #         return ans
    #     if lst2[0] < 0:
    #         ans = copycat_helper(lst1[1:],lst2[1:],ans[-lst2[0]:])
    #     else:
    #         ans = copycat_helper(lst1[1:],lst2[1:],ans + [lst1[0]] * lst2[0])
    #     return ans 
    # return copycat_helper(lst1, lst2, [])
    
    def copycat_helper(lst1, lst2, ans):
        if lst1 == [] or lst2 ==[]:
            return ans
        if lst2[0] >= 0:
            ans = ans + [lst1[0]] * lst2[0]
        else:
            # 这里之所以是对的，是因为题目中说的是removes the previous k elements added.
            # 即从ans的后几位开始删除
            ans = ans[:lst2[0]]
        return copycat_helper(lst1[1:],lst2[1:],ans) 
    return copycat_helper(lst1, lst2, [])

    # def copycat_helper(lst1, lst2, lst_so_far):
    #     if len(lst1) == 0 or len(lst2) == 0:
    #         return lst_so_far
    #     if lst2[0] >= 0:
    #         lst_so_far = lst_so_far + [lst1[0] for _ in range(lst2[0])]
    #     else:
    #         lst_so_far = lst_so_far[:lst2[0]]
    #     return copycat_helper(lst1[1:], lst2[1:], lst_so_far)
    # return copycat_helper(lst1, lst2, [])

# ORIGINAL SKELETON FOLLOWS

# def copycat(lst1, lst2):
#     """
#     Write a function `copycat` that takes in two lists.
#         `lst1` is a list of strings
#         `lst2` is a list of integers

#     It returns a new list where every element from `lst1` is copied the
#     number of times as the corresponding element in `lst2`. If the number
#     of times to be copied is negative (-k), then it removes the previous
#     k elements added.

#     Note 1: `lst1` and `lst2` do not have to be the same length, simply ignore
#     any extra elements in the longer list.

#     Note 2: you can assume that you will never be asked to delete more
#     elements than exist


#     >>> copycat(['a', 'b', 'c'], [1, 2, 3])
#     ['a', 'b', 'b', 'c', 'c', 'c']
#     >>> copycat(['a', 'b', 'c'], [3])
#     ['a', 'a', 'a']
#     >>> copycat(['a', 'b', 'c'], [0, 2, 0])
#     ['b', 'b']
#     >>> copycat([], [1,2,3])
#     []
#     >>> copycat(['a', 'b', 'c'], [1, -1, 3])
#     ['c', 'c', 'c']
#     """
#     def copycat_helper(______, ______, ______):
#         if ______:
#             return ______
#         if ______:
#             ______ = ______
#         else:
#             ______ = ______[:______]
#         return ______
#     return ______
