def main():
    x = get_int("What is x? ")
    print(f"x is {x}")


def get_int(prompt):
    while True:
        try:
            return int(input(prompt))
        except ValueError:
            pass

main()
print("1")
print("2")

# while True:
#     try:
#         x = int(input("what is x? "))
#         break
#     except ValueError:
#         print("x is not an integer")
        
# print(f"x is {x}")