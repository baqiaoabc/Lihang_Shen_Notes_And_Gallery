
# This is the function you need to implement


def main(filename, iterations, read_data, model_maker, learn, find_best_code, get_confusion_matrix, calculate_accuracy, calculate_macro_f1):
    """Trains and evaluates a model on some read_data

    Keyword arguments:
    filename -- a string, the location of a json file containing data
    iterations -- an integer, the number of iterations of training to do
    read_data -- a function, as defined in the Data question
    model_maker -- a class, as defined in the Model question

    ----------------------------------------------------------
    learn -- a function, as defined in the Learning question
    find_best_code -- a function, as defined in the Inference question
    get_confusion_matrix -- a function, as defined in the Confusion Matrix question
    calculate_accuracy -- a function, as defined in the Evaluation Metrics question
    calculate_macro_f1 -- a function, as defined in the Evaluation Metrics question
    """
# Read the data
    '''
    (
        {
        'train': [
            ('What are all the courses ?', 'SELECT name FROM course ;'),
            ('What are all the course codes ?', 'SELECT code FROM course ;')],
        'dev': [
            ('What are all the courses ?', 'SELECT name FROM course ;')],
        'test': [
            ('Please give me the names of courses .', 'SELECT name FROM course ;')]
        }
        ,
        {'SELECT name FROM course ;', 'SELECT code FROM course ;'}
    )
    '''
    data = read_data(filename)

# Create a model
    model = model_maker(data[1], data[0]["train"])

# Train the model by iterating over the training data. In each iteration, go through each example and run the learning function for it. At the end of each iteration, evaluate the model on the dev set.
    dev_scores = []
    for i in range(iterations):
        print("============iter " + str(i) + " complete===============")
        for question, answer in data[0]["train"]:
            learn(question, answer,model,find_best_code)
        dev_matrix = get_confusion_matrix(data[0]["dev"], model, find_best_code)
        temp = {}
        temp['accuracy'] = calculate_accuracy(dev_matrix,data[1])
        temp['macro-f1'] = calculate_macro_f1(dev_matrix,data[1])
        dev_scores.append(temp) 

# Evaluate the model on the test set.
    test_matrix = get_confusion_matrix(data[0]["test"], model, find_best_code)
    test_score = {}
    test_score['accuracy'] = calculate_accuracy(test_matrix,data[1])
    test_score['macro-f1'] = calculate_macro_f1(test_matrix,data[1])

# Return the dev scores and the final test score


    return dev_scores, test_score


from q0_data import read_data
from q1_model import *
from q2_inference import *
from q3_learning import *
from q4_confusion import *
from q5_metrics import * 

result = main("a2-data.json", 
     10, 
     read_data, 
     CodeModel, 
     learn, find_best_code, get_confusion_matrix, calculate_accuracy, calculate_macro_f1)

print(result)