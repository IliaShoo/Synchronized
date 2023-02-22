import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        List<Thread> myThreads = new ArrayList<>();

        Runnable runnable = () -> {
            String result = generateRoute("RLRFR", 100);
            System.out.println(result);

            //подсчет R в строке
            int amountOfRLetter = 0;
            for (int i = 0; i < result.length(); i++) {
                if (result.charAt(i) == 'R') {
                    amountOfRLetter++;
                }
            }
            int amountOfFrequence = 1;
            synchronized (sizeToFreq) {
                if (!sizeToFreq.containsKey(amountOfRLetter)) {
                    sizeToFreq.put(amountOfRLetter, amountOfFrequence);
                } else {
                    amountOfFrequence++;
                    sizeToFreq.put(amountOfRLetter, amountOfFrequence);
                }
            }
        };

        for (int i = 0; i < 1000; i++) {
            myThreads.add(new Thread(runnable));
        }

        for (Thread thread : myThreads) {
            thread.start();
        }

        for (Thread thread : myThreads) {
            thread.join();
        }

        Map.Entry<Integer, Integer> maxEntry = null;

        for(Map.Entry<Integer, Integer> entry: sizeToFreq.entrySet()) {
            System.out.println(entry.getKey() + " встретилось " + entry.getValue() + " раза");
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        System.out.println("Самое частое количество повторений " + maxEntry.getKey() + " ("+ maxEntry.getValue() + ") раза");

    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

}