class Student:
    # it limited the number of arguments for object Student
    # we store the data inside "self", it give access of current object
    # we can set the default value, then the default value will replace the missing arguments value
    # 此外，设置了default value的variable后面的variable必须也要有default value
    def __init__(self, name, house="3", patronus=None):
        if not name:
            # 设置返回error的具体信息，而不是generic error
            # 设置后返回的error看起来是这样的: ValueError: Missing name
            raise ValueError("Missing name")
        self.name = name 
        # it will call setter method for house attribute；
        # 如果我们这里写成self._house，那么就和name一样，都没有restriction了
        self.house = house
        self.patronus = patronus

    # 有了下面的function后，我们再print()一个Student object后就不会返回存储地址了
    def __str__(self):
        return "__str__" + self.name + self.house
    

    def charm(self):
        match self.patronus:
            case "Stag":
                return "🐴"
            case "Otter":
                return "🦦"
            case "Jack Russell terrier":
                return "🐶"
            case _:
                return "🪄"
    
    # Getter for "house" attribute, 注意这里的名字要和对应的attribute一样
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

    # 用来替代下面的get_student function。因为这样可以把和Student相关的function整合在一起
    # 我们可以call有“@classmethod”的method without creating a Student object
    # "cls" means current class
    @classmethod
    def get(cls):
        name = input("classMethodName: ")
        house = input("classMethodHouse: ")
        return cls(name, house)

def main():
    # Data type: tuple
    # comparing with list, tuple does not allowed to change (immutable)

    student = get_student()
    # 这里student.name是直接读取的student的attribute，而house是通过getter method获取的
    print(student.name, student.house)
    # 打印__str__ method
    print(student)
    # 打印charm method
    print(student.charm())

    # 通过classmethod来init一个Student object
    methodStudent = Student.get()
    print(methodStudent.name)


def get_student():
    # tuple
    # return (input("Name: "),input("House: "))
    
    # oop
    # Student() is an object, we can just leave Student as blank when we run student_1 code
    # student_1 = Student()
    # student_1.name = input("Name: ")
    # student_1.house = input("House: ")
    # return student_1


    # we can only run student_2 code when we customized the init method in Student object

    # after we do the customization, we can no longger use student_1 code since we need to put 
    # arguments inside Student()

    # !!! 当我们不用“Getter”和“Setter”密封Student object的时候，we can still directly manage student_3's 
    # attribute outside the object function, and it will not get Error;

    # below code called "construct call"
    student_2 = Student(input("Name: "), input("House: "), input("Patronus: "))
    
    # 可以看到，在右路default value之后，只输入一个argument不会报错
    student_3 = Student("no House")
    # 可以看到“name”在 without restriction的情况下，我们还是可以通过以下方法directly访问object的attribute
    # 而这种访问是不会再次经过__init__里面的判断的，通过设置名字为null而不报错看出
    student_3.name = None
    # 而house则不能directly, 因为这里调用的是house的setter；可以通过设置value为"not correct"报错看出
    student_3.house = "2"
    # Python比较特别，在使用student_3._house还是可以directly访问，并且不会报错
    student_3._house = "not correct house"
    # 可以看到即使__init__里面不包含notExist，我们仍然可以在外面给Student class添加上
    student_3.notExist = "incorrect"
    print("incorrect", student_3.notExist)
    return student_2


if __name__ == "__main__":
    main()