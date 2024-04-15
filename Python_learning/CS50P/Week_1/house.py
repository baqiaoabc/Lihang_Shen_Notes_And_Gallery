name = input("what is your name? ")

match name:
    case: "Harry" | "Hermione" | "Ron":
        print("Gryffindor")
    case: "Draco"
        print("Slytherin")
    case _:
        print("who?")