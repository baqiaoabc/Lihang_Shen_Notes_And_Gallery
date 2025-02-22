import IOHelpFunction

# file_path
input_file_path = ".\出库单测试.xlsx"
output_file_path = ".\\pc_system_setup_list.xlsx"


ip_d = [{'End User': 'TEST Leo', '分类': 'Laptop', '单号': 1238465, '固资号': 123011000653, '序列号': '12340539NB', 'End User Email': 'TEST@siemens-energy.com', '下发任务': 1}, {'End User': 'TEST YA JIE', '分类': 'Monitor', '单号': 1237244, '固资号': 123011000813, '序列号': '1234053ABC', 'End User Email': 'TEST..ext@siemens-energy.com', '下发任务': 1}]
IOHelpFunction.write_to_excel(input_file_path,ip_d)
IOHelpFunction.write_to_excel(output_file_path, [])