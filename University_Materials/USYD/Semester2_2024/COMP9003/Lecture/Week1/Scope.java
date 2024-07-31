public class Scope {
        public static void main(String[] args) {
                int a = 10;

                {
                        a = 30;
                }
                int b = 40;
        }
}
