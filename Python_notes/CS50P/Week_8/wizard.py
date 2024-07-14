# inheritance

class Wizard:
    def __init__(self,name):
        # 这里为了方便不用setter
        if not name:
            raise ValueError("Missing name")
        self.name = name


# means Student is a subclass of Wizard
class Student(Wizard):
    def __init__(self,name,house):
        # call Student's parent class Wizard
        super().__init__(name)
        self.house = house

# means Student is a subclass of Wizard
class Professor(Wizard):
    def __init__(self,name,subject):
        super().__init__(name)
        self.subject = subject


wizard = Wizard("Albus")
student = Student("Harry","Gryffindor")
professor = Professor("Severus", "Defense Against the Dark Arts")