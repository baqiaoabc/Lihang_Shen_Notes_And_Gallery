from collections import Counter

# This is the class you need to implement
class CodeModel:
    '''
    features means 
    "Tell" ... 

    list of features means
    ("Tell", "SELECT name FROM course")
    ("me", "SELECT name FROM course")
    ("the", "SELECT name FROM course")
    ("names", "SELECT name FROM course")
    ("of", "SELECT name FROM course")
    ("all", "SELECT name FROM course")
    ("the", "SELECT name FROM course")
    ("classes", "SELECT name FROM course")
    '''
    def __init__(self, labels, training_data):
        """Prepare the class member variables.
        Save the labels in self.labels and initialise all the weights to 0.

        Keyword arguments:
        labels -- a set of strings, each string is one SQL query
        training_data -- a list, each item is a tuple containing a question and an SQL query
        """
        # TODO
        self.labels = labels

        # Only create weights for the training data.
        # label means the sql query 
        self.weights = {}
        for question, label in training_data:
            features = self.get_features(question, label)
            for f in features:
                # tuple is immutable
                if f not in self.weights:
                    self.weights[f] = 0

        
    
    def get_features(self, question, label):
        """Produce a list of features for a specific question and label.
        
        Keyword arguments:
        question -- a string, an English question
        label -- a string, an SQL query
        """

        features = [(f,label) for f in question.split()]

        return features

    def get_score(self, question, label):
        """Calculate the model's score for a (question, label) pair.
        
        Keyword arguments:
        question -- a string, an English question
        label -- a string, an SQL query
        """
        # TODO
        features = self.get_features(question, label)
        score = 0
        for f in features:
            score += self.weights.get(f, 0)

        return score

    def update(self, question, label, change):
        """Modify the model.
        Changes all weights for features for the (question, SQL query) pair by the amount indicated.

        Keyword arguments:
        question -- a string, an English question
        label -- a string, an SQL query
        change -- an integer, how much to change the weights
        """
        # TODO
        # features = self.get_features(question, label)

        # for f in features:
        #     if f in self.weights:
        #         self.weights[f] += change
        features = self.get_features(question, label)
        feature_counts = Counter(features)  # 统计出现次数

        for f, count in feature_counts.items():
            if f in self.weights:
                self.weights[f] += change * count
            else:
                self.weights[f] = change * count

