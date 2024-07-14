name = input("what's your name? ")

# "W" mode
file = open("name.txt", "a")
file.write(f"{name}\n")
file.close()

# 自动关闭file
with open("course.txt", "a") as file:
    file.write(f"{name}\n")

# read file, method 1
with open("name.txt", "r") as file:
    lines = file.readlines()
for line in lines:
    # 注意file里面每一行自带\n，因此如果这里print不做限制会出现两行空行
    # 可以通过rstrip method来解决
    print("hello" , line.rstrip())

# read file, method 2, 但是不推荐，因为不好sort
with open("name.txt", "r") as file:
    for line in sorted(file):
        print("hello" , line.rstrip())

# read file, method 3, r是default模式, 推荐，他是把file中的内容存储到了name list里面。这样后续改动很简单
names = []
with open("name.txt") as file:
    # 可以写成for line in sorted(file), 但是不推荐，因为如果有改动就需要该很多地方
    for line in file:
        names.append(line.rstrip())
for name in sorted(names):
    print(f"hello, {name}")