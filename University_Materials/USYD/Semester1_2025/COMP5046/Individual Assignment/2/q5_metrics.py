# These are the functions you need to implement
def calculate_accuracy(confusion_matrix, labels):
    """Returns the accuracy based on the contents of a confusion matrix

    Keyword arguments:
    confusion_matrix -- a dictionary, as defined in the Confusion Matrix question
    labels -- a set of strings, all the possible labels
    """
    total = 0
    accurate_result = 0
    for key in confusion_matrix:
        if key[0] == key[1]:
            accurate_result += confusion_matrix[key]
        total += confusion_matrix[key]
    accuracy = accurate_result/total
    return accuracy

def calculate_precision(confusion_matrix, labels):
    """Returns a dict containing the precision for each label based on the contents of a confusion matrix

    Keyword arguments:
    confusion_matrix -- a dictionary, as defined in the Confusion Matrix question
    labels -- a set of strings, all the possible labels
    """
    # Precision is you predict is true and it is really true
    # per-SQL query precision, i.e. return a dict
    # correct predict / all prediction with that label
    precision_dictionary = {}
    for label in labels:
        # we calculate precision for each label one-by-one
        total = 0
        TP=0
        # key is (true, predict)
        for key in confusion_matrix:
            if key[1] == label and key[0] == key[1]:
                TP+= confusion_matrix[key]
                total += confusion_matrix[key]
            elif key[1] == label and key[0] != key[1]:
                total+=confusion_matrix[key]
        if total == 0:
            precision_dictionary[label] = 1.0
        else:
            precision_dictionary[label] = TP/total

    return precision_dictionary

def calculate_recall(confusion_matrix, labels):
    """Returns a dict containing the recall for each label based on the contents of a confusion matrix

    Keyword arguments:
    confusion_matrix -- a dictionary, as defined in the Confusion Matrix question
    labels -- a set of strings, all the possible labels
    """
    # correct predict / all real label with that label
    recall_dictionary = {}
    for label in labels:
        # we calculate precision for each label one-by-one
        total = 0
        TP=0
        # key is (true, predict)
        for key in confusion_matrix:
            if key[1] == label and key[0] == key[1]:
                TP+= confusion_matrix[key]
                total += confusion_matrix[key]
            elif key[0] == label:
                total+=confusion_matrix[key]
        if total == 0:
            recall_dictionary[label] = 1.0
        else:
            recall_dictionary[label] = TP/total
        
    return recall_dictionary

def calculate_macro_f1(confusion_matrix, labels):
    """Returns the Macro F-Score based on the contents of a confusion matrix

    Keyword arguments:
    confusion_matrix -- a dictionary, as defined in the Confusion Matrix question
    labels -- a set of strings, all the possible labels
    """
    # TODO
    macro_f1_list = []
    p= calculate_precision(confusion_matrix, labels)
    r= calculate_recall(confusion_matrix, labels)
    for label in labels:
            if p[label] + r[label] == 0:
                macro_f1_list.append(1.0)  # 避免 0/0，返回 1.0（符合 sklearn 处理方式）
            else:
                macro_f1 = 2*p[label]*r[label]/(p[label]+r[label])
            macro_f1_list.append(macro_f1)

    return sum(macro_f1_list)/len(macro_f1_list)





