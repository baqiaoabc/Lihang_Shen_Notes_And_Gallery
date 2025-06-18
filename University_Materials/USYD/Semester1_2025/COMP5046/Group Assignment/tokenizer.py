
import spacy
from spacy.language import Language
from spacy.tokens import Doc
from spacy.tokenizer import Tokenizer
import torch

# Run this after installing spacy to download the language model
# python -m spacy download en_core_web_lg
nlp = spacy.load('en_core_web_lg')

# Create a custom component to merge entities
@Language.component("entity_merger")
def entity_merger(doc):
    """
    Custom component of the spacy nlp pipeline which merges geographical location entity tokens into a single token
    For example: 'New York' would noramlly be split into 2 tokens 'New' and 'York' but this will combine into a single 'New York' token
    This is implemented because city_name type variables could have the value 'New York' and for effective tagging we aim to keep the tokenisation scheme consistent to the dataset
    """
    # Iterate over the entities in reverse order (to avoid index issues when merging)
    with doc.retokenize() as retokenizer:
        for ent in reversed(list(doc.ents)):
            # Merge the entity tokens into one token
            if(ent.label_ in ["GPE"]):
                attrs = {"LEMMA": ent.text}
                retokenizer.merge(ent, attrs=attrs)
    return doc

# Add the custom component after NER
nlp.add_pipe("entity_merger", after="ner")

def whitespace_tokenizer(nlp):
    # Create a custom tokenizer that splits only on whitespace
    return Tokenizer(nlp.vocab, token_match=re.compile(r'\S+').match)

nlp.tokenizer = whitespace_tokenizer(nlp)

# Example
doc = nlp("I love New York")
for token in doc:
    print(token)

# Use this to get tensors for each token. Tensors are produced by CNN model which part of the spacy language model
# You could use this as input to the LSTM directly or use just tokens and create an nn.Embedding layer in the model before the LSTM layer
# This should generate a tensor of shape (4,96) -> 4 = number of token in sentence and 96 = embedding size
token_tensors = torch.tensor(doc.tensor, dtype=torch.float32)

print(f"token tensor shape: {token_tensors.size()}")