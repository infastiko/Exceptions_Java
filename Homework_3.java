import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Homework_3 {
    private static final String FILE_EXTENSION = ".txt";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Введите данные через пробел в формате: Фамилия Имя Отчество дата рождения(dd.mm.yyyy) номер телефона пол(f / m) ");
            String input = scanner.nextLine().trim();

            try {
                UserData userData = parseUserData(input);
                String filename = userData.getSurname() + FILE_EXTENSION;
                writeDataToFile(userData, filename);
                System.out.println("Данные успешно записаны в файл " + filename);
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            } catch (IOException e) {
                System.err.println("Ошибка записи в файл");
                e.printStackTrace();
            }
        }
    }

    private static UserData parseUserData(String input) {
        String[] inputData = input.split("\\s+");
        if (inputData.length != 6) {
            throw new IllegalArgumentException("Неверное количество данных");
        }

        String surname = inputData[0];
        String name = inputData[1];
        String patronymic = inputData[2];
        String dateOfBirth = inputData[3];
        long phoneNumber = parsePhoneNumber(inputData[4]);
        char gender = parseGender(inputData[5]);

        return new UserData(surname, name, patronymic, dateOfBirth, phoneNumber, gender);
    }

    private static long parsePhoneNumber(String phoneNumber) {
        try {
            return Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат номера телефона");
        }
    }

    private static char parseGender(String gender) {
        char genderChar = gender.charAt(0);
        if (genderChar != 'f' && genderChar != 'm') {
            throw new IllegalArgumentException("Неверное значение пола");
        }
        return genderChar;
    }

    private static void writeDataToFile(UserData userData, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            String dataLine = userData.getSurname() + " " + userData.getName() + " " + userData.getPatronymic()
                    + " " + userData.getDateOfBirth() + " " + userData.getPhoneNumber() + " " + userData.getGender();
            writer.write(dataLine);
            writer.newLine();
        }
    }

    private static class UserData {
        private final String surname;
        private final String name;
        private final String patronymic;
        private final String dateOfBirth;
        private final long phoneNumber;
        private final char gender;

        public UserData(String surname, String name, String patronymic, String dateOfBirth, long phoneNumber, char gender) {
            this.surname = surname;
            this.name = name;
            this.patronymic = patronymic;
            this.dateOfBirth = dateOfBirth;
            this.phoneNumber = phoneNumber;
            this.gender = gender;
        }

        public String getSurname() {
            return surname;
        }

        public String getName() {
            return name;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public long getPhoneNumber() {
            return phoneNumber;
        }

        public char getGender() {
            return gender;
        }
    }
}