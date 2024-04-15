def main():
    # Ask user for their name
    # Remove whitespace from str and Capitalize user's name
    name = input("what's your name? ").strip().title()

    # Split user's name into first name and last name
    # first, last = name.split(" ")
    hello()
    hello(name)

    # Say hello to user
    print(f"fuck, {name}")
    
def hello(to="world"):
    print("hello, ", to)

main()