Amount_due = 50
while Amount_due > 0:
    print(f"Amount Due: {Amount_due}")
    insert_coin = int(input("Insert Coin: "))
    if insert_coin not in [5, 10, 25]:
        continue
    Amount_due -= insert_coin
print(f"Change Owed: {abs(Amount_due)}")