from library import *

class Hardware:
    def __init__(self, input_file_path, output_file_path):
        '''Store the new tasks and old tasks in current instance and update the 
        new tasks to the output file.

        input_file_path -- the address of input file; notice that, the column name is different from output file
        output_file_path -- the address of output file
        
        Last edit by: Lihang Shen
        Last edit date: 2024-04-07
        '''
        self.files_pathes = {"input": input_file_path, "output": output_file_path}
        self.add_new_date()
        # 写代码测试用，后续在set_sender_emailPassword中根据用户第一次打开软件时更改
        # 后续删除
        self.email = {'email' : '', 'password': ''}
            
    @property
    def input_data(self):
        '''
        input_data getter; this is the parent method for Hardware child classes.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        return self._input_data
    
    @input_data.setter
    def input_data(self, file_path):
        '''
        input_data setter; this is the parent method for Hardware child classes.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        pass

    @property
    def output_data(self):
        '''
        output_data getter; this is the parent method for Hardware child classes.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        return self._output_data
    
    @output_data.setter
    def output_data(self, file_path):
        '''
        output_data setter; this is the parent method for Hardware child classes.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        pass

    def propertyNumber_and_serieNumber(self):
        '''Return the track number, last 3 digits of serial number, 
        and property number of all assigned tasks.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07
        '''
        number=[]
        for hardware in self.input_data:
            number.append(f'{hardware["单号"]} {hardware["序列号"][-3:]}: {hardware["固资号"]}')
        return number
    
    def add_new_date(self):
        '''Add new data from input file to instance variable and 
        update new tasks to output file. It will also clean all 
        finished old tasks.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07
        '''
        self.input_data = self.files_pathes
        self.output_data = self.files_pathes
        IOHelpFunction.write_to_excel(self.files_pathes['output'], self.output_data)

    def find_respective_hardware_info(self, input_msg):
        '''According to input return the info of respective hardware
        
        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        for hardware in self.output_data:
            if type(input_msg) == str and hardware['sub_序列号'][-3:] == input_msg or type(input_msg) == int and hardware['sub_trackNumber'] == input_msg:
                # print(hardware)
                return hardware
    
    def change_respective_hardware_info(self, input_msg, changed_key, changed_value):
        '''According to track number or last 3 digits of serial number to change field value.
        
        input_msg -- track number or last 3 digits of serial number
        changed_key -- the field you want to change. For example, "sub_name"
        changed_value -- the value you want to update for changed_key.
        extra_condition -- specify which kind of field we want to change. "" means directly change without following specific rules

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        for hardware in self.output_data:
            if type(input_msg) == str and hardware['sub_序列号'][-3:] == input_msg or type(input_msg) == int and hardware['sub_trackNumber'] == input_msg:
                hardware[changed_key] = changed_value
                # 测试用，后续删除下面一行代码
                print(self.output_data)
        # 每次改变信息都需要同步的更新到输出表
        IOHelpFunction.write_to_excel(self.files_pathes['output'], self.output_data)       

    def set_sender_emailPassword(self, email, password):
        '''Set or update the smtp email and password. The application will send msg using this account

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        self.email = {'email' : email, 'password': password}

    def send_email(self, receiver_email, subject, message):
        '''send email to specific receiver.

        receiver_email -- people who receive your email
        subject -- the subject line of the email
        message -- the content of email
        
        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        smtp_server = 'smtp.office365.com'
        smtp_port = 587

        msg = MIMEText(message, 'plain')
        msg['From'] = formataddr(('Sender Name', self.email['email']))
        msg['To'] = formataddr(('Receiver Name', receiver_email))
        msg['Subject'] = subject

        try:
            server = smtplib.SMTP(smtp_server,smtp_port)
            server.starttls()
            server.login(self.email['email'], self.email['password'])
            server.sendmail(self.email['email'], [receiver_email], msg.as_string())
            server.quit()
        except Exception as e:
            print('邮件发送失败:', str(e))

    def convert_to_output_format(self):
        '''Change the format of input_data to the format of output_data.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        converted_input=[]
        for hardware in self.input_data:
            converted_input.append(
                {
                'sub_name': hardware['End User'],
                'sub_contact_email': hardware['End User Email'],
                'sub_endUser_email': '',
                'sub_email_password': '',
                'sub_trackNumber': hardware['单号'],
                'sub_固资号': hardware['固资号'],
                'sub_序列号': hardware['序列号'],
                'sub_分类': hardware['分类'],
                'sub_status': 'Emailing',
                'sub_mailTrackNumber': 0
                }
            )
            # Task: 根据设备的种类，在第一次读取任务的同时发送对应的邮件
            # if hardware["分类"] == "Laptop":
            #     self.send_email(...)
            # elif hardware["分类"] == "Monitor":
            #     self.send_email(...)
        return converted_input
    
    def update_mailTrackNumber(self,input_msg,changed_value):
        '''Update mail track number for respective order.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        self.change_respective_hardware_info(input_msg, 'sub_mailTrackNumber',changed_value)

class Pc(Hardware):
    @property
    def input_data(self):
        '''Input_data getter.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        return self._input_data
    
    @input_data.setter
    def input_data(self, file_path):
        '''Input_data setter. Will recognize new tasks from input table
        
        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        self._input_data = IOHelpFunction.get_pc_data(file_path["input"])

    @property
    def output_data(self):
        '''Output_data getter.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        return self._output_data
    
    @output_data.setter
    def output_data(self, file_path):
        '''Output_data setter.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        self._output_data = self.convert_to_output_format() + IOHelpFunction.get_outputTable_data(file_path["output"])

    def send_first_email(self):
        '''Send first email to all customers in the self.output_data whose sub_status == "" 
        which request them to provide their information.
        This method will only change order's status from "" to "Emailing".
        '''
        for order in self.output_data:
            if order["sub_status"] == "":
                self.update_status(order["sub_trackNumber"])

    def update_status(self,input_msg,mailNumber=None):
        '''Update the current status of process to next status and do the respective work.

        There are 4 status:
        1. sub_status == Emailing (initial status)
        2. sub_status == Setup System
        3. sub_status == Collecting 
            1. status == EMS Mailing
        4. sub_status == Done

        input_msg -- track number or last 3 digits of serial number
        mailNumber -- the mail track number; only need to fill it when we mail pc to customer

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        for pc in self.output_data:
            if type(input_msg) == str and pc['sub_序列号'][-3:] == input_msg or type(input_msg) == int and pc['sub_trackNumber'] == input_msg:
                if pc['sub_status']=='Emailing':
                    # task: accoring to user inputs to update sub_endUser_email，sub_email_password，sub_mailTrackNumber
                    pass
                elif True:
                    pass
                elif True:  
                    self.update_mailTrackNumber(input_msg,mailNumber)

    def update_emailPassword(self,input_msg,changed_value):
        '''Update the end user email password when we get them from customer.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        pass

    def update_endUser_email(self,input_msg,changed_value):
        '''Update the end user email when we get them from customer

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        pass

    def request_correct_password(self):
        '''Send email to customer again to request them send correct email password,
        and change the status of process back to "Emailing"

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        # task: will use Hardware.change_respective_hardware_info()
        pass



class Monitor(Hardware):
    @property
    def input_data(self):
        '''Input_data getter.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        return self._input_data
    
    @input_data.setter
    def input_data(self, file_path):
        '''Input_data setter.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        self._input_data = IOHelpFunction.get_monitor_data(file_path['input'])
        print(self.propertyNumber_and_serieNumber())
    
    @property
    def output_data(self):
        '''Output_data getter.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        return self._output_data
    
    @output_data.setter
    def output_data(self, file_path):
        '''Output_data setter.

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        self._output_data = self.convert_to_output_format() + IOHelpFunction.get_outputTable_data(file_path["output"])

    def update_status(self,input_msg,mailNumber=None):
        '''Change the current status of process to next step.

        There are 4 status:
        1. status == ""
        2. status == Emailing
        3. status == Collecting 
            1. status == Waiting for contract
        4. status == Done

        Last edit by: Lihang Shen
        Last edit date: 2024-04-07        
        '''
        pass




