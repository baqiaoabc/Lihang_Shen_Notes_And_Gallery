def main():
    print_square(3)

def print_column(height):
    print("#\n" * height, end="")

def print_row(width):
    print("?" * width)

def print_square(size):
    for i in range(size):
        print("#" * size)
main()