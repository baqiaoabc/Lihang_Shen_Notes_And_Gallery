students = [
    {"name": "Hermione", "house": "Gryffindor"},
    {"name": "Harry", "house": "Gryffindor"},
    {"name": "Ron", "house": "Gryffindor"},
    {"name": "Draco", "house": "Slytherin"},
    {"name": "Padma", "house": "Ravenclaw"},
]

# new data type set
houses = set()
for student in students:
    houses.add(student["house"])

for house in sorted(houses):
    print(house)








class Student:
    def __init__(self, house="3"):
        # it will call setter method for house attribute；
        # 如果我们这里写成self._house，那么就没有setter中的restriction了
        self.house = house
        self.__private = 0

	# Getter for "house" attribute
    @property
    def house(self):
        # 这里带上_，不然的话self.house相当于call的是当前这个setter method，也就会形成recursive error
        return self._house
    
    # Setter
    # 好处是只用在一个地方进行restriction的设置；没有setter的话，我们可以直接在设置完了object的初始值后对
    # 它的attributes进行更改，而这种更改不会再次被检查
    @house.setter
    def house(self, house):
        if house not in ["1","2","3"]:
            raise ValueError("Invalid house")
        # 这里带上_，不然的话self.house相当于call的是当前这个setter method，也就会形成recursive error
        # 带上后表示的是当前object的house attribute
        self._house = house


    @property
    def private(self):
        return self.__private
    
    @private.setter
    def private(self,n):
        if n == 200:
            self.__private = n


# 这里初始化Student class, 但是因为不满足setter中的restriction，
# 所以此时还没有house attribute，可以通过紧接着输入
# print(student_3.house)报错看出
student_3 = Student("no House")
# 这里调用的是house的setter；可以通过设置value为"not correct"报错看出
student_3.house = "2"
# Python比较特别，在使用student_3._house还是可以directly访问，并且不会报错
student_3._house = "not correct house"

# 这是通过house的getter来访问值，通过返回了
print("house:", student_3.house)
# 这是没有通过getter，直接获取的house attribute的值
print("_house:", student_3._house)

# 调用的是setter对__private进行操作
student_3.private = 200
# 调用的是__private的getter
print("private:", student_3.private)
# 
# print("private:", student_3._private)