# IO related library
import pandas as pd
import openpyxl

def read_from_excel(file_path):
    '''Read excel file from file_path and return data's type is a list of dicts. 

    Last edit by: Lihang Shen
    Last edit date: 2024-04-07        
    '''
    # 把data按照list of dicts的格式读取出来
    # https://datascientyst.com/convert-dataframe-list-dictionaries-pandas/
    return pd.read_excel(file_path,header=0).to_dict('records')

def update_pc_dict_value(hardware_list,key,conditional_value=1,changed_value=0):
    '''Return all new assigned pc tasks and unassigned them in ouput table.

    Last edit by: Lihang Shen
    Last edit date: 2024-04-07  
    '''
    changed_hardware_list=[]
    for hardware in hardware_list:
        if hardware['分类'].lower() == 'laptop' and hardware[key] == conditional_value:
            hardware[key]=changed_value
            changed_hardware_list.append(hardware)
    return changed_hardware_list

def update_monitor_dict_value(hardware_list,key,conditional_value=1,changed_value=0):
    '''Return all new assigned monitor tasks and unassigned them in ouput table.

    Last edit by: Lihang Shen
    Last edit date: 2024-04-07  
    '''
    changed_hardware_list=[]
    for hardware in hardware_list:
        if hardware['分类'].lower() == 'monitor' and hardware[key] == conditional_value:
            hardware[key]=changed_value
            changed_hardware_list.append(hardware)
    return changed_hardware_list


def get_pc_data(file_path):
    '''Filter pc tasks from new tasks in input file.

    Last edit by: Lihang Shen
    Last edit date: 2024-04-07  
    '''
    pc_task = read_from_excel(file_path)
    if pc_task != []:
        if '分类' in pc_task[0]:
            # 重写Kou表中pc客户的'下发任务'为0
            pc_list = update_pc_dict_value(pc_task, '下发任务')
            write_to_excel(file_path,pc_task)
        else:
            pc_list=[hardware for hardware in pc_task if hardware['sub_分类'].lower() == 'laptop']
        return pc_list
    return []


def get_monitor_data(file_path):
    '''Filter monitor tasks from new tasks in input file.

    Last edit by: Lihang Shen
    Last edit date: 2024-04-07  
    '''
    monitor_task = read_from_excel(file_path)
    if monitor_task != []:
        if '分类' in monitor_task[0]:
            # 重写Kou表中monitor客户的'下发任务'为0
            monitor_list = update_monitor_dict_value(monitor_task, '下发任务')
            write_to_excel(file_path,monitor_task)
        else:
            monitor_list=[hardware for hardware in monitor_task if hardware['sub_分类'].lower() == 'monitor']
        return monitor_list
    return []

def get_outputTable_data(file_path):
    '''Get unfinished old task from output file.

    file_path -- output file location
    
    Last edit by: Lihang Shen
    Last edit date: 2024-04-07  
    '''
    hardwares_list = read_from_excel(file_path)
    return [hardware for hardware in hardwares_list if hardware['sub_status'] != 'Done']


def write_to_excel(file_path, dict_data):
    '''Write content to destinated file which locate in file_path

    file_path -- file location
    dict_date -- a list of dictionary data set

    Last edit by: Lihang Shen
    Last edit date: 2024-04-07  
    '''
    data = pd.DataFrame.from_dict(dict_data)
    data.to_excel(file_path, index=False)