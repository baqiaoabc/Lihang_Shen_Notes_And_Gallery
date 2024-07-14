# msg = input("Input: ")
# res = []
# for cha in msg:
#     # 下面一行可以优化，通过 cha.lower() not in "aeiou"
#     if cha not in ['A', 'E', 'I', 'O', 'U', 'a', 'e', 'i', 'o', 'u']:
#         res += cha

# res = "".join(res)
# print(f"Output: {res}")


msg = input("Input: ")
res = "".join([ch for ch in msg if ch.lower() not in "aeiou"])
print(f"Output: {res}")