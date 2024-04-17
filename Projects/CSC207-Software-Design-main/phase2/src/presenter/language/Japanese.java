package presenter.language;

public class Japanese implements Language{

    private final String fail = "失敗しました。もう一度確認してください。";
    private final String permission = "権限がないかもしれません。";

    @Override
    public String username() {
        return "ユーザー名";
    }

    @Override
    public String password() {
        return "パスワード";
    }

    @Override
    public String signUpAccount() {
        return "アカウントを作成する";
    }

    @Override
    public String ok() {
        return "確認する";
    }

    @Override
    public String exit() {
        return "終了する";
    }

    @Override
    public String back() {
        return "戻る";
    }

    @Override
    public String titleLogin() {
        return "最高の会議へようこそ！";
    }

    @Override
    public String titleSignUp() {
        return "アカウントを作成中";
    }

    @Override
    public String language() {
        return "日本語";
    }

    @Override
    public String selectLanguage() {
        return "言語を選ぶ";
    }

    @Override
    public String fail() {
        return "失敗しました、もう一度確認してください。";
    }

    @Override
    public String messageTitle() {
        return "メール";
    }

    @Override
    public String createAccountMessage() {
        return "アカウントを作成しました";
    }

    @Override
    public String succeedAddRoomMessage() {
        return "新しい会場を作成しました";
    }

    @Override
    public String successful() {
        return "成功しました";
    }

    @Override
    public String failed() {
        return "失敗しました";
    }

    @Override
    public String failAddRoomMessage() {
        return "新しい会場の作成を" + fail;
    }

    @Override
    public String succeedCancelEventMessage() {
        return "新しいイベントを作りました。";
    }

    @Override
    public String failCancelEventMessage() {
        return "新しいイベントを作りません。もう一度確認してください。";
    }

    @Override
    public String succeedSignUpEventMessage() {
        return "イベントの申し込みを受け付きました。";
    }

    @Override
    public String failSignUpEvent() {
        return "イベントの申し込みを" + fail;
    }

    @Override
    public String succeedWaitEvent() {
        return "順番待ちリストの申し込みを受け付きました。";
    }

    @Override
    public String failWaitEventMessage() {
        return "順番待ちリストの申し込みを" + fail;
    }

    @Override
    public String succeedChangeEventMessage() {
        return "イベント情報を変更しました。";
    }

    @Override
    public String failChangeEventMessage() {
        return "イベント情報変更を" + fail + permission;
    }

    @Override
    public String succeedCreateUserMessage() {
        return "新しいユーザーを作りました。";
    }

    @Override
    public String failCreateUserMessage() {
        return fail + permission;
    }

    @Override
    public String succeedCreateEventMessage() {
        return "新しいイベントを作りました。";
    }

    @Override
    public String failCreateEventMessage() {
        return "新しいイベントの作成するのは" + fail;
    }

    @Override
    public String succeedCancelEnrollmentMessage() {
        return "イベントを辞めました。";
    }

    @Override
    public String failCancelEnrollmentMessage() {
        return "イベントを辞めるのは" + fail;
    }

    @Override
    public String succeedRemoveWaitMessage() {
        return "順番待ちリスをキャンセルしました。";
    }

    @Override
    public String failRemoveWaitMessage() {
        return "順番待ちリストをキャンセルする事を" + fail;
    }

    @Override
    public String succeedResetPasswordMessage() {
        return "パスワードをリセットしました。";
    }

    @Override
    public String failResetPasswordMessage() {
        return "パスワードをリセットのは" + fail;
    }

    @Override
    public String succeedCreateAccountMessage() {
        return "アカウントを作りました。";
    }

    @Override
    public String failCreateAccount() {
        return "アカウントを作成できないようです";
    }

    @Override
    public String welcome() {
        return "ようこそ";
    }

    @Override
    public String myProfile() {
        return "私のプロフィール";
    }

    @Override
    public String logOut() {
        return "ログアウト";
    }

    @Override
    public String resetPass() {
        return "パスワードを再設定する";
    }

    @Override
    public String schedule() {
        return "スケジュール";
    }

    @Override
    public String signUp() {
        return "イベント申し込み";
    }

    @Override
    public String signUpWait() {
        return "順番待ちリストに入る";
    }

    @Override
    public String signUpNowait() {
        return "待つ必要はありません！";
    }

    @Override
    public String viewMyEvent() {
        return "私のイベントを見る";
    }

    @Override
    public String viewMyWait() {
        return "順番待ち中のイベント";
    }

    @Override
    public String viewMyNoWait() {
        return "順番待ちませんのイベント";
    }

    @Override
    public String viewAllEvent() {
        return "すべてのイベントを見る";
    }

    @Override
    public String messenger() {
        return "メール";
    }

    @Override
    public String newPass() {
        return "新しいパスワード";
    }


    @Override
    public String withID() {
        return "IDは";
    }

    @Override
    public String startAt() {
        return "始まるのは";
    }

    @Override
    public String endAt() {
        return "終わるのは";
    }

    @Override
    public String takePlace() {
        return "会場は";
    }

    @Override
    public String whichIs() {
        return "これは";
    }

    @Override
    public String title() {
        return "テーマ：";
    }

    @Override
    public String duration() {
        return "期間：";
    }

    @Override
    public String startTime() {
        return "始まる時間：";
    }

    @Override
    public String endTime() {
        return "終了時間：";
    }

    @Override
    public String vIPStatus() {
        return "VIPイベント？：";
    }

    @Override
    public String waitinglist() {
        return "順番待ちリスト：";
    }

    @Override
    public String signUpQuestion() {
        return "サインアップする？";
    }

    @Override
    public String yes() {
        return "はい！";
    }

    @Override
    public String enterRoomNum() {
        return "追加したい新しい会場の名前を入力します。";
    }

    @Override
    public String enterRoomCapacity() {
        return "この会場の収容人数を入力してください。";
    }

    @Override
    public String create() {
        return "作成する";
    }

    @Override
    public String eventType() {
        return "イベントタイプ：";
    }

    @Override
    public String party() {
        return "パーティー";
    }

    @Override
    public String talk() {
        return "トーク";
    }

    @Override
    public String discussion() {
        return "パネルディスカッション";
    }

    @Override
    public String topic() {
        return "テーマ：";
    }

    @Override
    public String roomNum() {
        return "会場：";
    }

    @Override
    public String capacity() {
        return "最大参加者数：";
    }

    @Override
    public String no() {
        return "いいえ";
    }

    @Override
    public String selectSpeaker() {
        return "スピーカーの選択：";
    }

    @Override
    public String empty() {
        return "消します";
    }

    @Override
    public String userType() {
        return "ユーザータイプ：";
    }

    @Override
    public String speaker() {
        return "スピーカー";
    }

    @Override
    public String organizer() {
        return "主催者";
    }

    @Override
    public String attendee() {
        return "参加者";
    }

    @Override
    public String cancel() {
        return "キャンセル";
    }

    @Override
    public String rank() {
        return "ランク：";
    }

    @Override
    public String reallyQuestion() {
        return "本当に？？";
    }

    @Override
    public String change() {
        return "変更する：";
    }


    @Override
    public String viewTalks() {
        return "私のイベントを見る：";
    }

    @Override
    public String newUser() {
        return "新しいユーザー";
    }

    @Override
    public String newRoom() {
        return "新しい会場";
    }

    @Override
    public String newEvent() {
        return "新しいイベント";
    }

    @Override
    public String organizedEvent() {
        return "主催したイベント";
    }

    @Override
    public String changeEventSetting() {
        return "イベント設定を変更する";
    }

    @Override
    public String viewOrganizedEvent() {
        return "開催されたイベントを見る";
    }

    @Override
    public String eventId() {
        return "イベントID：";
    }

    @Override
    public String cancelled() {
        return "キャンセルしました";
    }

    @Override
    public String signedUp() {
        return "申し込みました";
    }

    @Override
    public String addSpeaker() {
        return "スピーカーを追加";
    }

    @Override
    public String newCapacity() {
        return "最大参加者数を変更";
    }

    @Override
    public String newStatus() {
        return "新しいステータス";
    }

    @Override
    public String send() {
        return "送信される";
    }

    @Override
    public String receivedMessages() {
        return "受信したメール";
    }

    @Override
    public String sentMessage(){
        return "送信されたメール";
    }

    @Override
    public String close() {
        return "閉じる";
    }

    @Override
    public String messengerOptions() {
        return "メッセンジャーオプション";
    }

    @Override
    public String viewMyMessages() {
        return "私のメールを見る";
    }

    @Override
    public String message() {
        return "メール";
    }

    @Override
    public String writeMessage() {
        return "ここに内容を書いてください";
    }

    @Override
    public String writeTitle() {
        return "メールのタイトルを入力してください";
    }

    @Override
    public String allAttendees() {
        return "すべての参加者";
    }

    @Override
    public String oneAttendee() {
        return "1人の参加者";
    }

    @Override
    public String sendTo() {
        return "このメールを誰に送信しますか";
    }

    @Override
    public String messageInformation() {
        return "メール情報";
    }

    @Override
    public String enterIdEvent() {
        return "イベントのIDを入力してください";
    }

    @Override
    public String enterIdSpeaker() {
        return "スピーカーのIDを入力してください";
    }

    @Override
    public String enterNameAttendee() {
        return "出席者のユーザー名を入力してください";
    }

    @Override
    public String writeNewMes() {
        return "新しいメールを書く";
    }

    @Override
    public String reply() {
        return "返信";
    }

    @Override
    public String writeMesHere() {
        return "ここにメールを書いてください";
    }

    @Override
    public String chooseTitle() {
        return "タイトルを選択してください";
    }

    @Override
    public String succeedSendMes() {
        return "メールを送信されました";
    }

    @Override
    public String enterTitleMes() {
        return "メールのタイトルを入力してください";
    }

    @Override
    public String mesAllSpeakers() {
        return "すべてのスピーカーにメールを送る";
    }

    @Override
    public String mesOneSpeaker() {
        return "1人のスピーカーにメールを送る";
    }

    @Override
    public String mesAllAttendees() {
        return "すべての参加者にメールを送る";
    }

    @Override
    public String mesOneAttendees() {
        return "1人の参加者にメールを送る";
    }

    @Override
    public String enterNameSpeaker() {
        return "スピーカーのユーザー名を入力します";
    }

    @Override
    public String mesAllAttendeesEvent() {
        return "私のイベントのすべての参加者にメールを送る";
    }

    @Override
    public String mesOneAttendeeEvent() {
        return "私のイベントの1人の参加者にメールを送る";
    }

    @Override
    public String receivedEmail() {
        return ("受信したメール");
    }

    @Override
    public String mesAllAttendeesInOneEvent() {
        return "私の一つのイベントをすべての参加者にメールを送る";
    }

    @Override
    public String withCapacity() {
        return "容量は";
    }

    @Override
    public String here() {
        return "こちら";
    }

    @Override
    public String event() {
        return "イベント";
    }
}
