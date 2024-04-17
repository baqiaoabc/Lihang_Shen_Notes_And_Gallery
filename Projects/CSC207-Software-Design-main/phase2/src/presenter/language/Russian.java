package presenter.language;

public class Russian implements Language{
    @Override
    public String username(){
        return ("Имя пользователя: ");
    }

    @Override
    public String password(){
        return ("Пароль: ");
    }

    @Override
    public String signUpAccount(){
        return ("Зарегистрируйтесь сейчас бесплатно! ");
    }

    @Override
    public String ok(){
        return ("ОК");
    }

    @Override
    public String exit(){
        return ("Выйти");
    }

    @Override
    public String titleLogin(){
        return ("Добро пожаловать на самую лучшую конференцию! ");
    }

    @Override
    public String back() {
        return ("Назад");
    }

    @Override
    public String titleSignUp() {
        return ("Вы зарегистрированы");
    }

    @Override
    public String language(){
        return ("Русский");
    }

    @Override
    public String selectLanguage(){
        return("Выберите язык ");
    }

    @Override
    public String fail() {
        return ("Упсб кажется, что-то пошло не так. Попробуйте еще раз");
    }

    @Override
    public String messageTitle() {
        return ("Сообщение");
    }

    @Override
    public String createAccountMessage() {
        return ("Вы создали аккаунт!");
    }

    @Override
    public String succeedAddRoomMessage() {
        return "Новая комната была создана успешно!";
    }

    @Override
    public String successful() {
        return "Удача!";
    }

    @Override
    public String failed() {
        return "Не удалось :(";
    }

    @Override
    public String failAddRoomMessage() {
        return "Комната уже существует или Ваш ввод неверен";
    }

    @Override
    public String succeedCancelEventMessage() {
        return "Событие было отменено успешно!";
    }

    @Override
    public String failCancelEventMessage() {
        return "Вы не можете отменить событие";
    }

    @Override
    public String succeedSignUpEventMessage() {
        return "Вы успешно зарегстрировались на мероприятие!";
    }

    @Override
    public String failSignUpEvent() {
        return "Вы не можете зарегстрироваться на мероприятие!";
    }

    @Override
    public String succeedWaitEvent() {
        return "Вы зарегистрировалсь в лист ожидания";
    }

    @Override
    public String failWaitEventMessage() {
        return "Вы зарегистрировалсь в лист ожидания";
    }

    @Override
    public String succeedChangeEventMessage() {
        return "Вы изменили параметры мероприятия!";
    }

    @Override
    public String failChangeEventMessage() {
        return "Вы не можете поменять параметры мероприятия!";
    }

    @Override
    public String succeedCreateUserMessage() {
        return "Вы создали нового ппользователя!";
    }

    @Override
    public String failCreateUserMessage() {
        return "Вы не можете создать нового пользователя";
    }

    @Override
    public String succeedCreateEventMessage() {
        return "Вы создали новое мероприятие";
    }

    @Override
    public String failCreateEventMessage() {
        return "Вы не можете создать новое мероприятие";
    }

    @Override
    public String succeedCancelEnrollmentMessage() {
        return "Упс, вы удачно отменили ваше участие";
    }

    @Override
    public String failCancelEnrollmentMessage() {
        return "Вы не смогли отменить ваше участие";
    }

    @Override
    public String succeedRemoveWaitMessage() {
        return "Упс, вы усппешно отменили ваше место в листе ожидания";
    }

    @Override
    public String failRemoveWaitMessage() {
        return "Вы не смогли отменить ваше место в листе ожидания";
    }

    @Override
    public String succeedResetPasswordMessage() {
        return "У вас новый пароль!";
    }

    @Override
    public String failResetPasswordMessage() {
        return "Вы не можете поменять пароль";
    }

    @Override
    public String succeedCreateAccountMessage() {
        return "Вы создал аккаунт";
    }

    @Override
    public String failCreateAccount() {
        return "Вы не можете создать аккаунт";
    }

    @Override
    public String welcome(){
        return ("Добро пожаловать");
    }
    @Override
    public String myProfile(){
        return ("Моя страница");
    }
    @Override
    public String logOut(){
        return ("Выйти");
    }
    @Override
    public String resetPass(){
        return ("Сбросить пароль");
    }
    @Override
    public String schedule(){
        return ("Расписане");
    }
    @Override
    public String signUp(){
        return ("Зарегистрироватьна мероприятие");
    }

    @Override
    public String signUpWait(){
        return ("Получить место в листе ожиданя!");
    }
    @Override
    public String signUpNowait(){
        return ("Вам не обязательно ждать!");
    }
    @Override
    public String viewMyEvent(){
        return ("Посмотреть мои Мероприятия");
    }
    @Override
    public String  viewMyWait(){
        return ("Мероприятия в списке ожидания");
    }
    @Override
    public String  viewMyNoWait(){
        return ("Мероприятия вне списка ожидания");
    }
    @Override
    public String  viewAllEvent(){
        return ("Посмотреть все мероприятия");
    }
    @Override
    public String  messenger(){
        return ("Сообщениия");
    }
    @Override
    public String  newPass(){
        return ("Новые пароль");
    }

    @Override
    public String  withID(){
        return (", с идентифкатором ");
    }
    @Override
    public String  startAt(){
        return (" начинается в ");
    }
    @Override
    public String  endAt(){
        return (" заканчивается в ");
    }
    @Override
    public String  takePlace(){
        return (" проходит в ");
    }
    @Override
    public String  whichIs(){
        return (" который ");
    }

    @Override
    public String  title(){
        return ("Название: ");
    }
    @Override
    public String  duration(){
        return ("Длительность: ");
    }
    @Override
    public String  startTime(){
        return ("Время начала: ");
    }
    @Override
    public String  endTime(){
        return ("Время окончания: ");
    }
    @Override
    public String  vIPStatus(){
        return ("Золотой статус: ");
    }
    @Override
    public String  waitinglist(){
        return ("Лист ожидания: ");
    }
    @Override
    public String  signUpQuestion(){
        return ("Вы хотите зарегистрироваться?");
    }
    @Override
    public String  yes(){
        return ("Да!");
    }
    @Override
    public String  enterRoomNum(){
        return ("Введите номер комнаты, которую вы хотите добавить: ");
    }
    @Override
    public String  enterRoomCapacity(){
        return ("Введите вместмость помещения:");
    }
    @Override
    public String  create(){
        return ("Создать");
    }
    @Override
    public String  eventType(){
        return ("Тип мероприятия: ");
    }
    @Override
    public String  party(){
        return ("Вечеринка");
    }
    @Override
    public String  talk(){
        return ("Конференция");
    }
    @Override
    public String  discussion(){
        return ("Панельная дискуссия");
    }
    @Override
    public String  topic(){
        return ("Тема: ");
    }
    @Override
    public String  roomNum(){
        return ("Номер комнаты: ");
    }
    @Override
    public String  capacity(){
        return ("Вместимость: ");
    }
    @Override
    public String  no(){
        return ("Нет!");
    }
    @Override
    public String  selectSpeaker(){
        return ("Выбрать спикера: ");
    }
    @Override
    public String  empty(){
        return ("Пустой");
    }

    @Override
    public String  userType(){
        return ("Тип пользователя: ");
    }
    @Override
    public String  speaker(){
        return ("Спикер");
    }
    @Override
    public String  organizer(){
        return ("Органзатор");
    }
    @Override
    public String  attendee(){
        return ("Участники");
    }
    @Override
    public String  cancel(){
        return ("Отменить");
    }
    @Override
    public String  rank(){
        return ("Рейтинг: ");
    }
    @Override
    public String  reallyQuestion(){
        return ("Действительн?");
    }
    @Override
    public String  change(){
        return ("Изменить: ");
    }

    @Override
    public String  viewTalks(){
        return ("Посмотреть мои конференции");
    }
    @Override
    public String  newUser(){
        return ("Новый пользователь");
    }
    @Override
    public String  newRoom(){
        return ("Новая комната");
    }
    @Override
    public String  newEvent(){
        return ("Новое мероприятие");
    }
    @Override
    public String  organizedEvent(){
        return ("Организованные мероприятия");
    }
    @Override
    public String  changeEventSetting(){
        return ("Изменить параметры мероприятия");
    }
    @Override
    public String viewOrganizedEvent(){
        return ("Посмотреть организованные мероприятия");
    }
    @Override
    public String eventId(){
        return ("Введите идентификатор мероприятия: ");
    }

    @Override
    public String cancelled() {
        return ("Отменен"); // Waiting for Amina
    }

    @Override
    public String signedUp(){
        return ("Зарегистрированы");
    }

    @Override
    public String addSpeaker() {
        return ("Добавить/измен спикера");
    }

    @Override
    public String newCapacity() {
        return ("Добавить вместимость");
    }

    @Override
    public String newStatus() {
        return ("Новый статус");
    }


    @Override
    public String receivedEmail() {
        return ("Полученные сообщения");
    }

    @Override
    public String mesAllAttendeesInOneEvent() {
        return "Отправить сообщение всем участникам одного из моих мероприятий";
    }

    @Override
    public String send() {
        return ("Отправить");
    }

    @Override
    public String receivedMessages() {
        return ("Полученные сообщения");
    }

    @Override
    public String sentMessage(){
        return ("Отправленные сообщения");
    }

    @Override
    public String close() {
        return ("Закрыть");
    }

    @Override
    public String messengerOptions() {
        return ("Параметры сообщений");
    }

    @Override
    public String viewMyMessages() {
        return ("Посмотреть мои сообщения");
    }

    @Override
    public String message() {
        return ("Сообщение");
    }

    @Override
    public String writeMessage() {
        return ("Введите ваше сообщение");
    }

    @Override
    public String writeTitle() {
        return ("Введите наименование вашего сообщения");
    }

    @Override
    public String allAttendees() {
        return ("Всем участникам");
    }

    @Override
    public String oneAttendee() {
        return ("Одному участнику");
    }

    @Override
    public String sendTo() {
        return ("Кому вы хотите отправиить сообщение");
    }

    @Override
    public String messageInformation() {
        return ("Информация о сообщении");
    }

    @Override
    public String enterIdEvent() {
        return ("Введите идентификатор вашего мероприятия ");
    }

    @Override
    public String enterIdSpeaker() {
        return "Введите идентификатор громкоговорителя для отправки сообщения";
    }

    @Override
    public String enterNameAttendee() {
        return ("Введите имя пользователя");
    }

    @Override
    public String writeNewMes() {
        return ("Введите новое сообщение");
    }

    @Override
    public String reply() {
        return ("Ответить");
    }

    @Override
    public String writeMesHere() {
        return ("Введите ваше сообщение");
    }

    @Override
    public String chooseTitle() {
        return ("Выберите наименование");
    }

    @Override
    public String succeedSendMes() {
        return ("Ваше сообщение было отправлено успешно!");
    }

    @Override
    public String enterTitleMes() {
        return ("Введите наименование вашего сообщения");
    }

    @Override
    public String mesAllSpeakers() {
        return ("Написать сообщение всем спикерам");
    }

    @Override
    public String mesOneSpeaker() {
        return ("Написать сообщение спикеру");
    }

    @Override
    public String mesAllAttendees() {
        return ("Написать сообщение участнику");
    }

    @Override
    public String mesOneAttendees() {
        return ("Написать сообщение всем участникам");
    }

    @Override
    public String enterNameSpeaker() {
        return ("Введите имя спикера");
    }

    @Override
    public String mesAllAttendeesEvent() {
        return ("Написать сообщение всем участникам моего мероприятия");
    }

    @Override
    public String mesOneAttendeeEvent() {
        return ("Написать сообщение участнику моего мероприятия");
    }

    @Override
    public String withCapacity() {
        return "иметь Вместимость";
    }

    @Override
    public String here() {
        return "здесь";
    }

    @Override
    public String event() {
        return "мероприятия";
    }
}
