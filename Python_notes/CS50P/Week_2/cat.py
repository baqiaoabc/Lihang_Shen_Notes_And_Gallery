i = 3
while i != 0:
    print("meow")
    i -= 1

for _ in [0,1,2]:
    print("bia")

for i in range(0,4,2):
    print(i)

print(3*"meow\n",end="")

while True:
    n = int(input("what is n? "))
    if n > 0:
        break

while True:
    n = int(input("what is n? "))
    if n < 0:
        continue
    else:
        break