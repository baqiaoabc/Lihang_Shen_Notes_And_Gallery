import random


class Hat:
    def __init__(self):
        # house is a class variable, we need not to initial it manually
        houses = ["Gryffindor", "Hufflepuff", "Ravenclaw", "Slytherin"]

    @classmethod
    # we use "cls" because "class" will collide with "class Hat"
    def sort(cls, name):
        print(name, "is in", random.choice(cls.houses))

# 当我们不需要instantiate a class object，可以用 classmethod 来处理共有的function 
# (像这里是重复用了houses variable)
# 类似与str method
Hat.sort("Harry")