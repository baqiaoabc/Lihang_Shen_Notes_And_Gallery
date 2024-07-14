import sys
import csv


if len(sys.argv) < 3:
    sys.exit("Too few command-line arguments")
elif len(sys.argv) > 3:
    sys.exit("Too many command-line arguments")
elif not (sys.argv[1].endswith(".csv") and sys.argv[2].endswith(".csv")):
    sys.exit("Not a CSV file")

try:
    students = []
    # 这里可以一次性打开2个文件，并且共用一个for loop来减少代码长度
    with open(sys.argv[1]) as file:
        reader = csv.DictReader(file)
        for student in reader:
            first, last = student["name"].split(", ")
            new_Stu_Info = {"house": student["house"], "first": first, "last": last}
            students.append(new_Stu_Info)

    with open(sys.argv[2], "w", newline="") as file:
        writer = csv.DictWriter(file, fieldnames=["first", "last", "house"])
        writer.writeheader()
        for student in students:
            writer.writerow(
                {
                    "house": student["house"],
                    "first": student["first"],
                    "last": student["last"],
                }
            )

except FileNotFoundError:
    sys.exit(f"Could not read {sys.argv[1]}")
