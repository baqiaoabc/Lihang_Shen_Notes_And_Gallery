import random

while True:
    level = input("Level: ")
    if level.isnumeric() and int(level) > 0:
        break
    else:
        pass

ans = random.randint(1, int(level))

while True:
    guess = input("Guess: ")
    if not guess.isnumeric():
        pass
    else:
        guess = int(guess)
        if guess == ans:
            print("Just right!")
            break
        elif guess < ans:
            print("Too small!")
        else:
            print("Too large!")


# 用try except来做
# from random import randint

# while True:
#     try:
#         n = int(input("Level: "))
#         if n <= 0:
#             raise ValueError
#         break
#     except ValueError:
#         pass

# rand = randint(1, n)
# while True:
#     try:
#         guess = int(input("Guess: "))
#         if guess < rand:
#             print("Too small!")
#         elif guess > rand:
#             print("Too large!")
#         else:
#             print("Just right")
#             break
#     except ValueError:
#         pass
