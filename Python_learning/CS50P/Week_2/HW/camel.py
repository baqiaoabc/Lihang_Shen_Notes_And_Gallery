

# 我的答案, 没有小写驼峰，所以不正确
# word = input("camelCase: ")
# begin_idx = 0
# result = ""
# for end_idx in range(len(word)):
#     if word[end_idx].isupper():
#         result = result + word[begin_idx:end_idx] + "_"
#         begin_idx = end_idx
#     end_idx += 1
# result += word[begin_idx:end_idx]

# print(result)


# 其他答案
word = input("camelCase: ")
# snake = []

# for ch in word:
#     if ch.isupper():
#         snake += "_" + ch.lower()
#     else:
#         snake += ch
# snake = "".join(snake)
# print("snake_case:", snake)


# 这里的意思是，join后面括号中是一个List，比如 ['n', 'a', 'm', 'e'] 或者 ['n', 'a', 'm', 'e', '_h', 'i']
# 而join的作用就是把这些list中的character拼成一个string
snake = "".join(["_" + ch.lower() if ch.isupper() else ch for ch in word])
print("snake_case:", snake)
