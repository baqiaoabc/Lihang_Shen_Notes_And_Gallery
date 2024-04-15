order = {}

while True:
    try:
        item = input().upper()
        if item not in order:
            order[item] = 1
        else:
            order[item] += 1
    except EOFError:
        sorted(order.keys())
        for key in order:
            print(order[key], key)
        break