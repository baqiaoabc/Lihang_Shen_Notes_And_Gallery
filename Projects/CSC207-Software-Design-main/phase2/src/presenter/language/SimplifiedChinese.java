package presenter.language;

import presenter.language.Language;

public class SimplifiedChinese implements Language {
    @Override
    public String username(){
        return ("用户名：");
    };

    @Override
    public String password(){
        return ("密码: ");
    };

    @Override
    public String signUpAccount(){
        return ("免费注册账户！");
    };

    @Override
    public String ok(){
        return ("确认");
    };

    @Override
    public String exit(){
        return ("退出");
    };

    @Override
    public String titleLogin(){
        return ("欢迎来到最NB的会议");
    };

    @Override
    public String back() {
        return ("返回");
    }

    @Override
    public String titleSignUp() {
        return ("你正在注册!");
    }

    @Override
    public String language(){
        return("简体中文");
    }

    @Override
    public String selectLanguage(){
        return("选择语言！ ");
    }

    @Override
    public String fail() {
        return ("糟糕！ 好像有什么东西搞错了");
    }

    @Override
    public String messageTitle() {
        return ("消息");
    }

    @Override
    public String createAccountMessage() {
        return ("亲！你成功创建了一个账户呢！");
    }

    @Override
    public String succeedAddRoomMessage(){
        return ("创建房间成功");
    }
    @Override
    public String successful(){
        return ("成功！");
    }
    @Override
    public String failed(){
        return ("失败");
    }
    @Override
    public String failAddRoomMessage(){
        return("创建房间失败");
    }
    @Override
    public String succeedCancelEventMessage(){
        return("取消事件成功");
    }

    @Override
    public String failCancelEventMessage(){
        return("取消事件失败");
    }

    @Override
    public String succeedSignUpEventMessage(){
        return("报名事件成功");
    }

    @Override
    public String failSignUpEvent(){
        return("报名事件失败");
    }

    @Override
    public String succeedWaitEvent(){
        return("成功加入等待名单");
    }

    @Override
    public String failWaitEventMessage(){
        return("加入等待名单失败");
    }

    @Override
    public String succeedChangeEventMessage(){
        return("成功修改事件");
    }

    @Override
    public String failChangeEventMessage(){
        return("修改事件失败");
    }

    @Override
    public String succeedCreateUserMessage(){
        return("成功创建用户");
    }

    @Override
    public String failCreateUserMessage(){
        return("创建用户失败");
    }

    @Override
    public String succeedCreateEventMessage(){
        return("成功创建事件");
    }

    @Override
    public String failCreateEventMessage(){
        return("创建事件失败");
    }

    @Override
    public String succeedCancelEnrollmentMessage(){
        return("成功取消事件资格");
    }

    @Override
    public String failCancelEnrollmentMessage(){
        return("取消事件资格失败");
    }

    @Override
    public String succeedRemoveWaitMessage(){
        return("成功退出等待名单");
    }

    @Override
    public String failRemoveWaitMessage(){
        return("退出等待名单失败");
    }

    @Override
    public String succeedResetPasswordMessage(){
        return("成功设置新密码");
    }

    @Override
    public String failResetPasswordMessage(){
        return("设置新密码失败");
    }

    @Override
    public String succeedCreateAccountMessage(){
        return("成功创建账户");
    }

    @Override
    public String failCreateAccount(){
        return("创建账户失败");
    }

    @Override
    public String welcome(){
        return ("欢迎");
    }
    @Override
    public String myProfile(){
        return ("个人资料");
    }
    @Override
    public String logOut(){
        return ("登出");
    }
    @Override
    public String resetPass(){
        return ("重置密码");
    }
    @Override
    public String schedule(){
        return ("日程表");
    }
    @Override
    public String signUp(){
        return ("活动报名");
    }

    @Override
    public String signUpWait(){
        return ("加入活动的等待列表");
    }
    @Override
    public String signUpNowait(){
        return ("无需等待");
    }
    @Override
    public String viewMyEvent(){
        return ("我的所有活动");
    }
    @Override
    public String  viewMyWait(){
        return ("我的等待列表");
    }
    @Override
    public String  viewMyNoWait(){
        return ("我的非等待列表");
    }
    @Override
    public String  viewAllEvent(){
        return ("查看所有活动");
    }
    @Override
    public String  messenger(){
        return ("消息系统");
    }
    @Override
    public String  newPass(){
        return ("新密码");
    }

    @Override
    public String  withID(){
        return (",ID 为 ");
    }
    @Override
    public String  startAt(){
        return (" 开始于 ");
    }
    @Override
    public String  endAt(){
        return (" 结束于 ");
    }
    @Override
    public String  takePlace(){
        return (" 举办在 ");
    }
    @Override
    public String  whichIs(){
        return (" 是 ");
    }

    @Override
    public String  title(){
        return ("标题: ");
    }
    @Override
    public String  duration(){
        return ("时长: ");
    }
    @Override
    public String  startTime(){
        return ("开始时间: ");
    }
    @Override
    public String  endTime(){
        return ("结束时间: ");
    }
    @Override
    public String  vIPStatus(){
        return ("是否为VIP活动: ");
    }
    @Override
    public String  waitinglist(){
        return ("等待列表: ");
    }
    @Override
    public String  signUpQuestion(){
        return ("报名?");
    }
    @Override
    public String  yes(){
        return ("是!");
    }
    @Override
    public String  enterRoomNum(){
        return ("输入你想新建的房间号码: ");
    }
    @Override
    public String  enterRoomCapacity(){
        return ("输入新房间能容纳的最大人数:");
    }
    @Override
    public String  create(){
        return ("创建");
    }
    @Override
    public String  eventType(){
        return ("活动类型: ");
    }
    @Override
    public String  party(){
        return ("聚会");
    }
    @Override
    public String  talk(){
        return ("演讲");
    }
    @Override
    public String  discussion(){
        return ("论坛会");
    }
    @Override
    public String  topic(){
        return ("标题: ");
    }
    @Override
    public String  roomNum(){
        return ("房间号: ");
    }
    @Override
    public String  capacity(){
        return ("容量: ");
    }
    @Override
    public String  no(){
        return ("否!");
    }
    @Override
    public String  selectSpeaker(){
        return ("选择演讲者: ");
    }
    @Override
    public String  empty(){
        return ("清空");
    }

    @Override
    public String  userType(){
        return ("用户类型: ");
    }
    @Override
    public String  speaker(){
        return ("演讲者");
    }
    @Override
    public String  organizer(){
        return ("举办者");
    }
    @Override
    public String  attendee(){
        return ("参加者");
    }
    @Override
    public String  cancel(){
        return ("取消");
    }
    @Override
    public String  rank(){
        return ("排名: ");
    }
    @Override
    public String  reallyQuestion(){
        return ("真的确定么??");
    }
    @Override
    public String  change(){
        return ("改动: ");
    }

    @Override
    public String  viewTalks(){
        return ("查看我的演讲: ");
    }
    @Override
    public String  newUser(){
        return ("新的用户");
    }
    @Override
    public String  newRoom(){
        return ("新的房间");
    }
    @Override
    public String  newEvent(){
        return ("新活动");
    }
    @Override
    public String  organizedEvent(){
        return ("举办的活动");
    }
    @Override
    public String  changeEventSetting(){
        return ("改变活动设定");
    }
    @Override
    public String viewOrganizedEvent(){
        return ("查看举办的活动");
    }
    @Override
    public String eventId(){
        return ("会议代码");
    }

    public String cancelled(){
        return ("已取消");
    }
    public String signedUp(){
        return ("已报名");
    }

    @Override
    public String addSpeaker() {
        return ("添加/改变演讲者");
    }

    @Override
    public String newCapacity() {
        return ("新容量");
    }

    @Override
    public String newStatus() {
        return ("新状态");
    }

    @Override
    public String send() {
        return "发送";
    }

    @Override
    public String sentMessage() {
        return "已寄讯息";
    }

    @Override
    public String receivedMessages() {
        return "收到的消息";
    }

    @Override
    public String close() {
        return "关";
    }

    @Override
    public String messengerOptions() {
        return "信使选项";
    }

    @Override
    public String viewMyMessages() {
        return "查看我的讯息";
    }

    @Override
    public String message() {
        return "信息";
    }

    @Override
    public String writeMessage() {
        return "在这里写下您的讯息";
    }

    @Override
    public String writeTitle() {
        return "输入消息标题";
    }

    @Override
    public String allAttendees() {
        return "所有参加者";
    }

    @Override
    public String oneAttendee() {
        return "一位参加者";
    }

    @Override
    public String sendTo() {
        return "您想投放消息发送给谁";
    }

    @Override
    public String messageInformation() {
        return "留言信息";
    }

    @Override
    public String enterIdEvent() {
        return "输入事件的ID";
    }

    @Override
    public String enterIdSpeaker() {
        return "输入你想发送消息的演讲者";
    }

    @Override
    public String enterNameAttendee() {
        return "输入参加者的用户名";
    }

    @Override
    public String writeNewMes() {
        return "这里写新信息";
    }

    @Override
    public String reply() {
        return "回复";
    }

    @Override
    public String writeMesHere() {
        return "在这里写下您的讯息";
    }

    @Override
    public String chooseTitle() {
        return "选择你的标题";
    }

    @Override
    public String succeedSendMes() {
        return "您的信息已经成功发送";
    }

    @Override
    public String enterTitleMes() {
        return "输入邮件标题";
    }

    @Override
    public String mesAllSpeakers() {
        return "给所有演讲者发信息";
    }

    @Override
    public String mesOneSpeaker() {
        return "给一位演讲者留言";
    }

    @Override
    public String mesAllAttendees() {
        return "给所有参加者发消息";
    }

    @Override
    public String mesOneAttendees() {
        return "给一位参加者留言";
    }

    @Override
    public String enterNameSpeaker() {
        return "输入演讲者的用户名";
    }

    @Override
    public String mesAllAttendeesEvent() {
        return "向我的活动的所有参与者发送消息";
    }

    @Override
    public String mesOneAttendeeEvent() {
        return "向我的活动的一位参与者发送消息";
    }

    @Override
    public String receivedEmail() {
        return ("收到的电子邮件");
    }

    @Override
    public String mesAllAttendeesInOneEvent() {
        return "向参与活动的所有参加者发送消息";
    }

    @Override
    public String withCapacity() {
        return "占容量";
    }

    @Override
    public String here() {
        return "这里";
    }

    @Override
    public String event() {
        return "事件";
    }
}
