import re

name = input("what is your name? ").strip()
# 用matches接受re.search接受返回值
matches = re.search(r"^(.+), *(.+)$", name)

# 有时后边直接加一个变量，而不是一个布尔表达式。该语句是在判断变量是否有值，
# 这个值必须是'非零非空'的值，该语句返回True或False。
if matches:
# 此外还可以这样写 if matches := re.search(r"^(.+), *(.+)$", name):

    # re.group的convention是第一个元素的idx是从1开始的
    name = matches.group(2) + " " + matches.group(1)


print(f"hello, {name}")