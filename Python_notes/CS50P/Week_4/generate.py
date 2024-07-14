import random

coin = random.choice(["heads", "tails"])
print(coin)

# inclusively
number = random.randint(1,10)
print(number)

# 会shuffle list本身，本身不返回值
cards = ["j","q","k"]
after_s = random.shuffle(cards)
print(cards)
print(after_s)