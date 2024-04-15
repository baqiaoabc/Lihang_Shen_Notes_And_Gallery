ans = input("Greeting: ").lower().strip()

"""
if ans[:5] == "hello":
    print("$0")
elif ans[0:1] == "h":
    print("$20")
else:
    print("$100")
"""

if ans.startswith("hello"):
    print("$0")
elif ans.startswith("h"):
    print("$20")
else:
    print("$100")