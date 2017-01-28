import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LargeFileGenerator {

    private static final String CHARS = " ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static Random RANDOM = new Random();

    public static void main(String[] args) throws Exception {
        Integer numberOfUniquePhrases = Integer.parseInt(args[0]);
        Integer numberOfLines = Integer.parseInt(args[1]);
        Integer numberOfPhrasesPerLine = Integer.parseInt(args[2]);
        String delimiter = args[3];
        File outputFile = new File(args[4]);

        List<String> phrases = new ArrayList<>();
        for (int i = 0; i < numberOfUniquePhrases; i++) {
            phrases.add(randomString(75 + RANDOM.nextInt(50)));
        }

        Integer twentyPercent = (int) (phrases.size() * 0.2);
        Integer thirtyPercent = (int) (phrases.size() * 0.3);

        List<String> groupA = phrases.subList(0, twentyPercent);
        List<String> groupB = phrases.subList(twentyPercent, twentyPercent + thirtyPercent);
        List<String> groupC = phrases.subList(twentyPercent + thirtyPercent, phrases.size());

        PrintWriter printWriter = new PrintWriter(outputFile);

        for (int i = 0; i < numberOfLines; i++) {
            for (int j = 0; j < numberOfPhrasesPerLine - 1; j++) {
                String phrase = null;
                switch (j % 3) {
                    case 0:
                        phrase = groupA.get(RANDOM.nextInt(groupA.size()));
                        break;
                    case 1:
                        phrase = groupB.get(RANDOM.nextInt(groupB.size()));
                        break;
                    case 2:
                        phrase = groupC.get(RANDOM.nextInt(groupC.size()));
                        break;
                }

                printWriter.printf("%s%s", phrase, delimiter);
            }

            printWriter.println(groupA.get(RANDOM.nextInt(groupA.size())));
            printWriter.flush();
        }

        printWriter.close();
    }

    private static String randomString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            stringBuilder.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }

        return stringBuilder.toString();
    }

}
