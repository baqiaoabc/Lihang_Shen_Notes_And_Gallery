def main():
    n = int(input("What's n? "))
    for s in sheep(n):
        print(s)

# old
# Executing our code, you might try different numbers of sheep such as 10, 1000, 
# and 10000. What if you asked for 1000000 sheep, your program might completely hang 
# or crash. Because you have attempted to generate a massive list of sheep, 
# your computer may be struggling to complete the computation.
# 把数据存储下来并且一次性返回
# def sheep(n):
#     flock = []
#     for i in range(n):
#         flock.append("🐑" * i)
#     return flock



# new
# The yield generator can solve this problem by returning a small bit of the results at a time. 
# Notice how yield provides only one value at a time while the for loop keeps working.
# 及时返回每一次数据
def sheep(n):
    for i in range(n):
        yield "🐑" * i

if __name__ == "__main__":
    main()