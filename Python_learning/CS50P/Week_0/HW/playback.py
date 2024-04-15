# implement a program in Python that prompts the user for input and then outputs that same input,
# replacing each space with ... (i.e., three periods).

def main():
    result = input("string you want to slow down: ")
    print("...".join(result.split()))

main()