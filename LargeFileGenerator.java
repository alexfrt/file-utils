import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

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
        Map<String, Integer> phrasesUsage = new HashMap<>();

        for (int i = 0; i < numberOfUniquePhrases; i++) {
            String phrase = randomString(75 + RANDOM.nextInt(50));

            phrases.add(phrase);
            phrasesUsage.put(phrase, 0);
        }

        Integer twentyPercent = (int) (phrases.size() * 0.2);
        Integer thirtyPercent = (int) (phrases.size() * 0.3);

        List<String> groupA = phrases.subList(0, twentyPercent);
        List<String> groupB = phrases.subList(twentyPercent, twentyPercent + thirtyPercent);
        List<String> groupC = phrases.subList(twentyPercent + thirtyPercent, phrases.size());

        PrintWriter printWriter = new PrintWriter(outputFile);

        for (int i = 0; i < numberOfLines; i++) {
            String phrase = null;

            for (int j = 0; j < numberOfPhrasesPerLine - 1; j++) {
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

                phrasesUsage.put(phrase, phrasesUsage.get(phrase) + 1);
                printWriter.printf("%s%s", phrase, delimiter);
            }

            phrase = groupA.get(RANDOM.nextInt(groupA.size()));
            phrasesUsage.put(phrase, phrasesUsage.get(phrase) + 1);

            printWriter.println(phrase);
            printWriter.flush();
        }

        printWriter.close();

        LinkedHashMap<String, Integer> sortedPhrasesUsage = phrasesUsage.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        List<String> top10phrases = sortedPhrasesUsage.keySet().stream().collect(Collectors.toList()).subList(0, 10);
        for (String phrase : top10phrases) {
            System.out.println(String.format("Phrase: [%s]. Count: [%s]", phrase, phrasesUsage.get(phrase)));
        }
    }

    private static String randomString(int length) {
        StringBuilder stringBuilder = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            stringBuilder.append(CHARS.charAt(RANDOM.nextInt(CHARS.length())));
        }

        return stringBuilder.toString();
    }

}
