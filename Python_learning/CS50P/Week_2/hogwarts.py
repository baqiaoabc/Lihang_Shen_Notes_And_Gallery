# list
students = ["Hermione", "Harry", "Ron"]

print(students[0:2:2])

for student in students:
    print(student)

for i in range(len(students)):
    print(i+1,students[i])

# dict
other_students = {
    "Hermione": "Gryffindor",
    "Harry": "Gryffindor",
    "Ron": "Gryffindor",
    "Draco": "Slytherin"
}

for student in other_students:
    print(student, other_students[student], sep=", ")

# combine multiple information together

full_info = [
    {"name": "Hermione1", "house": "Gryffindor1", "patronus": "Otter1"},
    {"name": "Hermione2", "house": "Gryffindor2", "patronus": "Otter2"},
    {"name": "Hermione3", "house": "Gryffindor3", "patronus": "Otter3"},
    {"name": "Hermione4", "house": "Gryffindor4", "patronus": None}
]

for student in full_info:
    print(student["name"], student["house"], student["patronus"], sep = ", ")