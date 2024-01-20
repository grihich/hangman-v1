import java.util.*;

public class Main {

    static final String[] hangmanStates = {
            """
             +---+
             |   |
                 |
                 |
                 |
                 |
            =======    
            """,
            """
            +---+
             |   |
             O   |
                 |
                 |
                 |
            =======    
            """,
            """
            +---+
             |   |
             O   |
             |   |
                 |
                 |
            =======    
            """,
            """
             +---+
             |   |
             O   |
            /|   |
                 |
                 |
            =======    
            """,
            """
             +---+
             |   |
             O   |
            /|\\  |
                 |
                 |
            =======    
            """,
            """
             +---+
             |   |
             O   |
            /|\\  |
            /    |
                 |
            =======    
            """,
            """
             +---+
             |   |
             O   |
            /|\\  |
            / \\  |
                 |
            =======    
            """
    };

    static final int MAX_ERRORS_COUNT = hangmanStates.length - 1;

    static List<String> arr = new ArrayList<String>(Arrays.asList("почка", "собака", "кошка", "арбуз"));

    static Random rnd = new Random();

    static String hangMan(int errorsCount) {
        return hangmanStates[errorsCount];
    }
    static StringBuilder getWord() {
        return new StringBuilder(arr.get(rnd.nextInt(arr.size())));
    }

    static void showStartWindow() {
        System.out.println("Для начала игры введите любую строку. Для выхода введите 0");
    }

    static void showCurrentState(StringBuilder currentWord, int errorsCount) {

        String hangManState = hangMan(errorsCount);

        System.out.println("Количество ошибок: " + errorsCount);
        System.out.println("Состояние виселицы:");
        System.out.println(hangManState);
        System.out.println(currentWord);

    }


    public static void main(String[] args) {

        showStartWindow();

        Scanner in = new Scanner(System.in);
        String choice = in.next();

        GAME: while (!choice.equals("0")) {

            int errorsCount = 0;
            StringBuilder hiddenWord = getWord(); // загаданное слово
            StringBuilder currentWord = new StringBuilder(); // строка, выводимая пользователю

            for (int i = 0; i < hiddenWord.length(); i++) {
                currentWord.append('_'); // заполняем строку для пользователя вида ____
            }

            SYMBOL: while (currentWord.compareTo(hiddenWord) != 0 && errorsCount < MAX_ERRORS_COUNT) {


                boolean isCharInCurrentWord = false;
                boolean isCharInHiddenWord = false;

                System.out.print("Введите букву: ");
                String c = in.next(); // проверка на то, что ввели одну букву
                while (c.length() != 1) {
                    System.out.println("Некорректный ввод! Введите одну букву. Для выхода введите 0");
                    c = in.next();
                    if (c.equals("0")) {
                        break GAME;
                    }
                }

                // проверка, отгадал ли уже пользователь букву

                for (int i = 0; i < currentWord.length(); i++) {
                    if (currentWord.substring(i, i + 1).equals(c)) {
                        isCharInCurrentWord = true;
                        break;
                    }
                }

                if (isCharInCurrentWord) {
                    showCurrentState(currentWord, errorsCount);
                    continue SYMBOL;
                }

                for (int i = 0; i < hiddenWord.length(); i++) {
                    if (hiddenWord.substring(i, i + 1).equals(c)) {
                        currentWord.replace(i, i + 1, hiddenWord.substring(i, i + 1));
                        isCharInHiddenWord = true;
                    }
                }

                if (!isCharInHiddenWord) {
                    errorsCount += 1;
                }

                if (currentWord.compareTo(hiddenWord) == 0) {
                    showCurrentState(currentWord, errorsCount);
                    System.out.println("Вы победили! Для продолжения введите любую строку, для выхода - 0");
                    choice = in.next();
                }

                else if (errorsCount == MAX_ERRORS_COUNT) {
                    showCurrentState(currentWord, errorsCount);
                    System.out.println("Вы проиграли! Для продолжения введите любую строку, для выхода - 0");
                    choice = in.next();
                }

                else {
                    showCurrentState(currentWord, errorsCount);
                }

            }


        }

    }
}
