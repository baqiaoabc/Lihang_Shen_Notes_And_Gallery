import torch
import string
import json
import re

'''
Data set for question split
'''

def normalize_whitespace(text):
    return re.sub(r'\s+', ' ', text).strip()

with open('atis.json', 'r', encoding='utf-8') as f:
    data = json.load(f)

train_data = {"inputs":[], "labels":[], "variables":[]}
test_data = {"inputs":[], "labels":[], "variables":[]}
dev_data = {"inputs":[], "labels":[], "variables":[]}
tags = []

for item in data:
    # Task 1: get the shortest sql query
    shortest_sqls = sorted(
                        item['sql'],
                        key=lambda x: (len(normalize_whitespace(x)), normalize_whitespace(x))
                    )
    shortest_sql = shortest_sqls[0]

    for sentence in item["sentences"]:
        # replace variable in input sentence and label
        real_sentence = normalize_whitespace(sentence["text"])
        for var in sentence["variables"].keys():
            real_sentence = real_sentence.replace(var, sentence["variables"][var])


        real_label = normalize_whitespace(shortest_sql)
        # Task 2: store training data
        if sentence["question-split"] == "train":
            train_data["inputs"].append(real_sentence)
            train_data["labels"].append(real_label)
            train_data["variables"].append(sentence["variables"])
        # Task 3: store testing data
        elif sentence["question-split"] == "test":
            test_data["inputs"].append(real_sentence)
            test_data["labels"].append(real_label)
            test_data["variables"].append(sentence["variables"])
        # Task 4: store dev data
        else:
            dev_data["inputs"].append(real_sentence)
            dev_data["labels"].append(real_label)
            dev_data["variables"].append(sentence["variables"])

print("===========train================")
print(train_data["inputs"][:5])
print(train_data["labels"][:5])
print("===========test================")
print(test_data["inputs"][:5])
print("===========dev================")
print(dev_data["inputs"][:5])

'''
Classification

First, we have a NER task to recognize all entity from input

Second, we need to according to input to predict template; we need to make sure 
input only have variable instead of real entity

Third, we need to combine them


Generation

'''