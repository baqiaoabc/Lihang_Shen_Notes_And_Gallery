# # csv: common seperate value
# students = []
# with open("students.csv") as file:
#     for line in file:
#         name,house = line.rstrip().split(",")
#         student = {"name": name, "house": house}
#         students.append(student)

# def get_name(student):
#     return student["name"]

# for student in sorted(students, key=get_name):
#     # 这里用''是因为在外面用了""
#     print(f"{student['name']} is in {student['house']}")

# # lambda function take one parameter called student，然后我们需要这个lambda function返回student["name"]
# for student in sorted(students, key=lambda student: student["name"]):
#     print(f"{student['name']} is in {student['house']}")

# #====================================================================================================
import csv
# 上面的方法读不了下面格式，因为有3个comma；但是新的方法可以
# 它会自动识别分隔符，并把每一行按分隔符分好后输出
"""
Jim,"house1, room1"
Amy,house2
Ty,house0
Tim,house3
"""

students = []
with open("students.csv") as file:
    reader = csv.reader(file)
    for name,home in reader:
        students.append({"name": name, "house": home})
for student in sorted(students, key=lambda student: student["name"]):
    print(f"{student['name']} is in {student['house']}")

# 还以用csv.DictReader()读取下面的格式，即在第一行表明了dict的key有哪些
# 这样返回来的就是每一行为一个dict，这样做的好处是当我们列于列互换的时候，不需要修改很多代码
# 这种方法灵活性最高
print("=============================================================")
"""
name,home
Jim,"house1, room1"
Amy,house2
Ty,house0
Tim,house3
"""
students = []
with open("students.csv") as file:
    reader = csv.DictReader(file)
    # 这里的dict格式为[{"name": "Jim", "home": "house1, room1"}, ...]
    for row in reader:
        students.append(row)
for student in sorted(students, key=lambda student: student["name"]):
    print(f"{student['name']} is in {student['home']}")


# 注意，插入新行要标明newline=""，不然会隔行输出
# 原因为csv标准库中的writerow在写入文件时会加入'\r\n'作为换行符,也就是说会换行两次
name = input("what is your name? ")
home = input("what is your home? ")

# 这种方法的缺陷是无法随意更改添加时列的顺序，也就是说灵活性不高
with open("write_Student.csv","a",newline="") as file:
    writer = csv.writer(file)
    # 这里填入的应该是list，并且空格要写入正确
    writer.writerow([name, home])

with open("write_Student.csv","a",newline="\n") as file:
    writer = csv.DictWriter(file, fieldnames=["name","home"])
    writer.writeheader()
    writer.writerow({"home": home, "name": name})
