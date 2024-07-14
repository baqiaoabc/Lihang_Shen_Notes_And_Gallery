# # constant var
# MEOWS = 3

# # 在python里面，constant var是可以更改的
# MEOWS = 4

# for _ in range(MEOWS):
#     print("meow!!")


# class Cat:
#     # Class variable
#     MEOWS = 3

#     def meow(self):
#         for _ in range(self.MEOWS):
#             print("meow")
    
# cat = Cat()
# cat.meow()

# ============================================================
# 需要先 pip install mypy
# 注意，规定变量类型只有在使用mypy才有效
# 此外mypy只会检查我们设定的变量type，并不会具体运行代码

# mypy 可以在我们给arg规定type后,返回具体是哪个arg不符合我们的规定
# 使用python "TypeError: 'str' object cannot be interpreted as an integer"
# 使用mypy error: "Argument 1 to "meow" has incompatible type "str"; expected "int"  [arg-type]"

# 设置arg的type，设置meow function的返回值type
def meow(n: int) -> None:
    """
    Meow n times.
    
    :param n: Number of times to meow
    :type n: int
    :raise TypeError: if n is not an int
    :return: A string of n meows, one per line
    :rtype: None
    """
    for _ in range(n):
       print("meow")

number = input("Number: ")
# 设置变量的type
test: int = input("Test: ")
result: str = meow(number)

