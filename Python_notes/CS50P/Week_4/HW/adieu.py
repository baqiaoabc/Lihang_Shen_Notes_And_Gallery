# 不用inflect package的方法
# nameList = []
# while True:
#     try:
#         nameList.append(input("Name: "))
#     except EOFError:
#         print("Adieu, adieu, to ", end = '')
#         for name in nameList[:-1]:
#             print(name, end=', ')
#         if len(nameList) > 1:
#             print("and", nameList[-1])
#         else:
#             print(nameList[0])
#         break

import inflect

p = inflect.engine()
nameList = []
while True:
    try:
        nameList.append(input("Name: "))
    except EOFError:
        break

if len(nameList) > 1:
    front = p.join(nameList[:-1])
    print("Adieu, adieu, to", front, "and", nameList[-1])
else:
    print("Adieu, adieu, to", nameList[0])
