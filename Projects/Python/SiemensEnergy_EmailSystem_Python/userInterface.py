from objectClasses import *


def login_window():
    '''Let user input their email and smtp password.
    '''
    ###### Create window ######
    login_w = tk.Tk()
    ###### Get related resources ######
    eye_close = ImageTk.PhotoImage(Image.open('.\source\picture\eyeClose.jpg').resize((28,28)))
    eye_open = ImageTk.PhotoImage(Image.open('.\source\picture\eyeOpen.jpg').resize((28,28)))
    ###### Function for changing image button setting ######
    def show_or_hide_password():
        if Button['text'] == 'hide':
            Enter_value_2.config(show='')
            Button.config(text = 'show',image=eye_open)
        else:
            Enter_value_2.config(show='*')
            Button.config(text = 'hide',image=eye_close)
    ###### 登陆界面setting ######
    login_w.title('登录界面')
    login_w.geometry('750x500')

    label_1 = tk.Label(login_w, width=7,text='邮箱',compound='center')
    label_1.place(x=200,y=200)

    label_2 = tk.Label(login_w, width=7,text='smtp密码',compound='center')
    label_2.place(x=200,y=230)
    ###### Get email and smtp password ######
    global email, password
    email = tk.StringVar()
    password = tk.StringVar()

    Enter_value_1 = tk.Entry(login_w, textvariable=email)
    Enter_value_1.pack()
    Enter_value_1.place(x=310,y=200)
    ###### show argument用来隐藏密码 ######
    Enter_value_2 = tk.Entry(login_w,show='*',textvariable=password)
    Enter_value_2.pack()
    Enter_value_2.place(x=310,y=230)
   
    Button = tk.Button(login_w, image=eye_close, borderwidth=0, height=28, width=28, command=show_or_hide_password, text='hide')
    Button.pack(pady=10)
    Button.place(x=455,y=225)


    login_w.mainloop()

def functional_window():
    pass

if __name__ == "__main__":
    # file_path
    input_file_path = ".\出库单测试.xlsx"
    output_file_path = ".\\pc_system_setup_list.xlsx"

    # create object, and manipulate values according to request
    pc_object = Pc(input_file_path,output_file_path)
    # print(pc_object.output_data[0]['sub_status']=="")
    monitor_object = Monitor(input_file_path,output_file_path)
    # print(monitor_object.input_data)
    # pc_object.find_respective_hardware_info(1238465)
    # pc_object.change_respective_hardware_info(1238465,'sub_status', 'Done')
    # pc_object.send_email('1053200896@qq.com', 'Test', 'This is a test')
    login_window()