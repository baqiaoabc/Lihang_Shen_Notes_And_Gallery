def total(x,y,z):
    result = x+y*2+z
    return result

def f(*args_anyWordisOK, **kwargs):
    print("args:",args_anyWordisOK)
    print("kwags:",kwargs)
    

first, _ = "part1 part2".split(" ")
print(f"hello, {first}")

coins = [100,50,20]
# unpacked coins into 3 individuals arguments
# 貌似只能在输入arg到function时使用, 还可以在print时候使用
# 比如，print(*[1,2,3])
# >>> print(*[1,2,3])
# 1 2 3
print("list", total(*coins))


# unpacked dictionary
coins = {"y": 50, "x": 100, "z":20}
# unpacked the key of dictionary
# 比如这里的就是 y x z
print("*", total(*coins))
# unpacked the keys and responded value of dictionary; 
# 因此dictionary的key要和function中的args名字对应(顺序不用对应)；
# 比如这里就是 y=50 x=100 z=20
print("**",total(**coins))
print("normal case",total(x=100,y=50,z=20))
# 可以发现，在指定了args具体赋值后，我们传入参数的位置就不重要了
print("reverse case",total(y=50,z=20,x=100))


f(1,2,3)
f(x=1,y=2,z=3)
f(1,2,3,x=1,y=2,z=3)