students = [
    {"name": "Hermione", "house": "Gryffindor"},
    {"name": "Harry", "house": "Gryffindor"},
    {"name": "Ron", "house": "Gryffindor"},
    {"name": "Draco", "house": "Slytherin"},
]


def is_gryffindor(s):
    return s["house"] == "Gryffindor"

# filter() 用某一个返回True/False的function来过滤出所有为True的value并返回
gryffindors = filter(is_gryffindor, students)

for gryffindor in gryffindors:
    print(gryffindor["name"])



# enumerate
students = [1,2,3]
# old approach
for i in range(len(students)):
    print(i+1,students[i])
# new;即返回idx又返回值
for i,student in enumerate(students):
    print(i+1,student)