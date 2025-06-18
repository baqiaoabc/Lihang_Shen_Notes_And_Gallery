# This is the function you need to implement
def find_best_code(question, model):
    """Predicts the SQL for a question by using a model to try all possible labels.

    Keyword arguments:
    question -- a string, the English question
    model -- a CodeModel, as defined in the Model question
    """
    scores = []
    max_score = 0
    for l in model.labels:
        current_score = model.get_score(question, l)
        if current_score > max_score:
            max_score = current_score
        scores.append([l,current_score])
        
    max_collection = []
    for score in scores:
        if score[1] == max_score:
            max_collection.append(score[0])
        
    max_collection.sort()
    label = max_collection[0]

    return label

        # If more than one have the same score then sort the labels using python's sort function
    # (e.g., best_options.sort()) and take the first (ie, sort them alphabetically).

    # scores = []
    # max_score = 0
    # for l in model.labels:
    #     current_score = model.get_score(question, l)
    #     if current_score > max_score:
    #         max_score = current_score
    #     scores.append([l,current_score])
    
    
    # max_collection = []
    # for label, score in scores:
    #     if score == max_score:
    #         max_collection.append(label)
        
    # max_collection.sort()
    # label = max_collection[0]

    # return label
