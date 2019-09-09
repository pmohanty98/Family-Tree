import java.io.File;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class RandomNameGenerator {
    private SecureRandom random;
    private List<String> first, middle, last;

    public RandomNameGenerator() throws FileNotFoundException {
        this(8);
    }

    public RandomNameGenerator(int seedByteLength) throws FileNotFoundException {
        random = new SecureRandom();
        random = new SecureRandom(random.generateSeed(seedByteLength));
        first = read("files/in/names_first.txt");
        middle = read("files/in/names_middle.txt");
        last = read("files/in/names_last.txt");
    }

    protected List<String> read(String filename) throws FileNotFoundException {
        List<String> names = new ArrayList<>();
        Scanner scan = new Scanner(new File(filename));
        while(scan.hasNextLine()) {
            names.add(scan.nextLine().trim());
        }
        return names;
    }

    public String getNewFullName() {
        try {
            return String.format("%s %s %s", getNewFirstName(), getNewMiddleName(), getNewLastName());
        }
        catch(Exception ignored) {}
        return null;
    }

    public String getNewName() {
        try {
            return String.format("%s %s", getNewFirstName(), getNewLastName());
        }
        catch(Exception ignored) {}
        return null;
    }

    public String getNewFirstName() throws Exception {
        if(first.isEmpty())
            throw new Exception("No more first names available.");
        return getNewName(first);
    }

    public String getNewFirstName(boolean replace) throws Exception {
        String name = getNewName(first);
        if(!replace)
            first.remove(name);
        return name;
    }

    public String getNewLastName(boolean replace) throws Exception {
        String name = getNewName(last);
        if(!replace)
            last.remove(name);
        return name;
    }

    public String getNewLastName() {
        return getNewName(last);
    }

    public String getNewMiddleName() {
        return getNewName(middle);
    }

    public String getNewName(List<String> names) {
        return names.get(getRandomIndex(names.size()));
    }

    public int getRandomIndex(int max) {
        return random.nextInt(max);
    }

    public static SecureRandom getSecureRandom() {
        return new SecureRandom();
    }

    public static int getSecureRandomInt(int bound) {
        SecureRandom random = getSecureRandom();
        random.setSeed(getSecureRandomSeed());
        return random.nextInt(bound);
    }

    public static byte[] getSecureRandomSeed() {
        return getSecureRandomSeed(8);
    }

    public static byte[] getSecureRandomSeed(int seedLength) {
        return getSecureRandom().generateSeed(seedLength);
    }
}
