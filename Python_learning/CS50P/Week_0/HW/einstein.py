# implement a program in Python that prompts the user for mass as an integer (in kilograms) 
# and then outputs the equivalent number of Joules as an integer.
def main():
    m = int(input("m: "))

    print("E:",m*pow(300000000 ,2))

main()