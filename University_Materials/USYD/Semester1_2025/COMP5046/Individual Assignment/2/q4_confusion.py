# This is the function you need to implement
def get_confusion_matrix(eval_data, model, find_best_code):
    """Creates a confusion matrix by predicting the SQL for a question and recording how the answer compares with the true answer 

    Keyword arguments:
    eval_data -- a list of tuples containing the English question and the true SQL query
    model -- a CodeModel, as defined in the Model question
    find_best_code -- a function, the one defined the Inference question
    """
    # TODO
    # confusion_matrix is: {(true answer: guess): count}
    # include all possible combination
    confusion_matrix = {}
    for true_label in model.labels:
        for predict_label in model.labels:
            key = (true_label, predict_label)
            if not key in confusion_matrix:
                confusion_matrix[key] = 0

    for question, true_label in eval_data:
        pred_label = find_best_code(question, model)
        confusion_matrix[(true_label,pred_label)] += 1
    

    return confusion_matrix
