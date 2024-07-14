def main():
    dollars = dollars_to_float(input("How much was the meal? "))
    percent = percent_to_float(input("What percentage would you like to tip? "))
    tip = dollars * percent
    print(f"Leave ${tip:.2f}")


def dollars_to_float(d):
    # change price of meal to float, remove $ sign
    
    # return float(d[1:])
    return float(d.replace("$",""))

def percent_to_float(p):
    # change percentage to float, remove % sign

    # return float(p[:-1]) / 100
    return float(p.replace("%","")) / 100.0


main()