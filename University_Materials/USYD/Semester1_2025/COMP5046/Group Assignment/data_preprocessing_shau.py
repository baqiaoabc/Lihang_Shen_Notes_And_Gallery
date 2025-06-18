import json
import re
import unicodedata

def unicodeToAscii(s):
    # Convert a Unicode string 's' to plain ASCII.
    # This is done by first normalizing the string into its decomposed form using 'NFD',
    # which separates characters from their accents. Then, it filters out all nonspacing marks (Mn).
    return ''.join(
        c for c in unicodedata.normalize('NFD', s)
        if unicodedata.category(c) != 'Mn'
    )

def normalize_whitespace(text):
  return re.sub(r'\s+', ' ', text).strip()

def preprocess_sentence(s:str) -> str:
    """
    Preprocesses sentence text for consistency
    """
    s = s.strip()
    s = normalize_whitespace(s)
    s = unicodeToAscii(s)
    s = s.strip()
    return s

def preprocess_dataset(dataset_loc = "atis.json",split_type=None, split=['dev', 'test', 'train']):
    
    # Read Dataset JSON file
    with open(dataset_loc) as f:
        dataset_json = json.load(f)

    processed_dataset = []
    variable_names = set()
    sql_templates = set()

    for sample in dataset_json:
        processed_sample = {}

        # Preprocess sql queries
        sql = [preprocess_sentence(query) for query in sample['sql']]

        # All valid sql queries for this examples sorted by their length
        sql = sorted(sql, key=len)

        # Adds shorests sql template to the set of sql templates
        sql_templates.add(sql[0])
        

        # Dictionary for variables/placeholders metadata
        variables_metadata = sample["variables"]

        # Delete 'location' key from variables dictionary
        # variable_type_mapping = {var['name']:var['type'] for var in variables_metadata}
        for var in variables_metadata:
            # Add current variable to set of all possible variable names
            variable_names.add(var.get("name"))
            var.pop('location', None)
        # query split for this sample
        query_split = sample['query-split']

        # Skips sample if its not the specified split_type or split
        if(split_type == "query"):
            if(query_split not in split):
                continue

        # Process each sentence
        for sentence in sample['sentences']:
            # Skips sample if its not the specified split_type or split
            if(split_type == "question"):
                if(sentence['question-split'] not in split):
                    continue
            # variables/placeholder mapping dictionary
            variables = sentence['variables']

            # Sentence text with variables/placeholders
            text_with_vars = preprocess_sentence(sentence['text'])

            # Replacing variables/placeholders in current sentence and sql query with their values from the variables dictionary
            text_with_vars_replaced = text_with_vars
            sql_with_vars_replaced = sql

            # Replace sentence and all sql variables with their values
            for var in variables:
                text_with_vars_replaced = text_with_vars_replaced.replace(var,variables[var])
                sql_with_vars_replaced = [query.replace(var,variables[var]) for query in sql_with_vars_replaced]

            # Taggingg expected output
            sentence_var_tagging_labels = []
            for word in text_with_vars.split():
                if(word in variables):
                    sentence_var_tagging_labels.append(word)
                else:
                    sentence_var_tagging_labels.append("-")

            # Appends preprocessed dictionary of current sentence to the processesed_dataset list
            processed_dataset.append({
                "text_with_vars":text_with_vars,
                "text_with_vars_replaced":text_with_vars_replaced,
                "sentence_var_tagging_labels":sentence_var_tagging_labels,
                "vars_metadata":variables_metadata,
                "variables":variables,
                "sql_with_vars": sql,
                "shortest_sql_with_vars":sql[0],
                "sql_with_vars_replaced": sql_with_vars_replaced,
                "shortest_sql_with_vars_replaced":sql_with_vars_replaced[0],
                "query_split":sample['query-split'],
                "question_split":sentence['question-split']
            })
    
    return processed_dataset,variable_names,sql_templates
    
    
train,var_names,sql_templates = preprocess_dataset(dataset_loc="atis.json", split_type="question", split=["train"])

# var_names contains all unique variable names in the samples processed
print(list(var_names)[:5])

# sql_templates contains all unique SQL templates in the samples processed
print(list(sql_templates)[:2])

sample = train[1]
print(f"Sentence with variables:\n{sample['text_with_vars']}\n")
print(f"Sentence with variables replaced by their values:\n{sample['text_with_vars_replaced']}\n")
print(f"Variable tagging expected output:\n{sample['sentence_var_tagging_labels']}\n")
print(f"Variables mapping in sentence:\n{sample['variables']}\n")
print(f"Metadat about variables in sentence:\n{sample['vars_metadata']}\n")
print(f"Shortest SQL query with variables:\n{sample['shortest_sql_with_vars']}\n")
print(f"Shortest SQL query with variables replaced by their values:\n{sample['shortest_sql_with_vars_replaced']}\n")
print(f"Which query split is this sample part of?:\n{sample['query_split']}\n")
print(f"Which question split is this sample part of?:\n{sample['question_split']}\n")


