'''
Take away

(1) we need to drop NAs before we use CountVectorizer, otherwise, we may get 
AttributeError: 'float' object has no attribute 'lower'
(2)logistic regression only accecpt numeric data
(3) pandas.dataframe.explode() 的用法,即展平成1-D数据

(4) Is validation dataset used to do Grid search? Yes
(5) do we need to remove NA for test dataset? remove
(6) count_vect = CountVectorizer() 和 tfidf_transformer = TfidfTransformer() 需要
同时对train 和 validation 的X使用，并且使用的是同一个，否则数据处理部分就不一样了

'''
import json
import numpy as np
from sklearn.pipeline import Pipeline
import pandas as pd
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.linear_model import LogisticRegression
from sklearn import metrics
from sklearn.metrics import classification_report
from sklearn.feature_extraction.text import TfidfTransformer
#================STEP 1========================
# 读取训练数据
train_data = pd.read_json("mod-train.jsonl", lines=True)
train_data = train_data.explode(["messages", "sender_labels"]).dropna(subset=["messages", "sender_labels"])

# 展平消息列表，并保证每条消息是一个单独的字符串
train_message = train_data['messages'].explode().tolist()  # Flatten the list of messages
train_label = train_data['sender_labels'].explode().tolist()  # Flatten the list of labels
# 将标签转化为 0 和 1
train_label_flatten = [1 if label == True else 0 for label in train_label]
#=================STEP 2====================================
# 使用 CountVectorizer 将文本数据转换为特征向量
count_vect = CountVectorizer()
X_train_counts = count_vect.fit_transform(train_message)

tfidf_transformer = TfidfTransformer()
X_train_tfidf = tfidf_transformer.fit_transform(X_train_counts)
#=================STEP 3=====================
# 创建 Logistic Regression 模型并训练
clf = LogisticRegression(solver='saga', penalty=None, random_state=42)

# 模型训练完成，可以继续进行预测等后续步骤
clf.fit(X_train_tfidf, train_label_flatten)
#=============================================
# 处理测试集数据
test_df = pd.read_json("mod-validation.jsonl", lines=True)
test_data_exploded = test_df.explode(["messages", "sender_labels"]).dropna(subset=["messages", "sender_labels"])

X_test = test_data_exploded['messages'].explode().tolist() 
X_test_counts = count_vect.transform(X_test) # 不能使用fit_transform，因为会重新训练
X_test_tfidf = tfidf_transformer.transform(X_test_counts)

y_test = [1 if label else 0 for label in test_data_exploded["sender_labels"]]

# 生成预测结果
y_pred = clf.predict(X_test_tfidf)

# 输出分类报告
print(metrics.classification_report(y_test, y_pred))