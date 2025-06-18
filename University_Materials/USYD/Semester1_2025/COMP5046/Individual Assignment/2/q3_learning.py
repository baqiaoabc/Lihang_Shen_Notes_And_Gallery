# This is the function you need to implement
def learn(question, answer, model, find_best_code):
    """Updates a model by predicting the SQL for a question and making a Perceptron update 

    Keyword arguments:
    question -- a string, the English question
    answer -- a string, the correct SQL query for this question 
    model -- a CodeModel, as defined in the Model question
    find_best_code -- a function, the one defined the Inference question
    """
    # TODO
    # prediction correct
        # Do nothing
    # prediction incorrect
        # correct feature weight += 1
            # get all features that would lead to correct answer, then add 1
            # we can do it by using "answer"
        # Incorrect feature weight -= 1
            # we can do it by using "prediction"
    prediction = find_best_code(question,model)

    if not answer == prediction:
        # In weight matrix, correct feature weight += 1
        model.update(question,answer,1)
        # In weight matrix, incorrect feature weight -= 1
        model.update(question,prediction,-1)



            
