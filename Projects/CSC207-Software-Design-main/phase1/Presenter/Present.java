import java.io.IOException;

public class Present {
    public static void main(String[] args) throws IOException {
        ViewModel model = new ViewModel();
        Presenter p = new Presenter(model);
        p.Introduction();
        p.IsUserRegistered();
    }
}
