import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.System.out;

public class RandomFamilyGenerator {
    private SecureRandom random;
    private RandomNameGenerator nameGenerator;

    public RandomFamilyGenerator() throws FileNotFoundException {
        random = new SecureRandom(new SecureRandom().generateSeed(8));
        nameGenerator = new RandomNameGenerator();
    }

    public List<String> getRandomFamily() throws Exception {
        List<String> members = new ArrayList<>();
        int familySize = random.nextInt(12) - 2;
        while(familySize < 3)
            familySize = random.nextInt(6);
        String familyName = nameGenerator.getNewLastName(false);
        for(; familySize > 0; familySize--) {
            //String name = String.format("%s-%s-%s", nameGenerator.getNewFirstName(), nameGenerator.getNewMiddleName(), familyName);
            String name = nameGenerator.getNewFirstName(false);
            members.add(name);
        }
        return members;
    }

    public List<List<String>> getRandomFamilyLine(long familyCount) {
        List<List<String>> families = new ArrayList<>();
        Set<Integer> alreadyDonor = new TreeSet<>();
        for(long i = 0; i < familyCount; i++) {
            try {
                List<String> family = getRandomFamily();
                if (!families.isEmpty() && family.size() > 2) {
                    int donorFamily = random.nextInt(families.size() - alreadyDonor.size());
                    while(alreadyDonor.contains(donorFamily)) {
                        donorFamily = random.nextInt(families.size());
                    }
                    alreadyDonor.add(donorFamily);
                    List<String> relatedFamily = families.get(donorFamily);
                    if (Math.random() <= 0.2) { // divorce
                        int donorIndex = random.nextInt(2);
                        int replaceIndex = random.nextInt(2);
                        family.set(replaceIndex, relatedFamily.get(donorIndex));
                        //out.println("Remarry");
                    } else if (Math.random() <= 0.90 && relatedFamily.size() > 2) { //kid marries new person
                        int donorIndex = 2 + random.nextInt(relatedFamily.size() - 2);
                        int replaceIndex = random.nextInt(2);
                        family.set(replaceIndex, relatedFamily.get(donorIndex));
                        if (Math.random() <= 0.10) { //incest is a test case (why so likely??)
                            int incestIndex = random.nextInt(relatedFamily.size() - 1);
                            while(incestIndex == donorIndex) {
                                incestIndex = random.nextInt(relatedFamily.size() - 1);
                            }
                            replaceIndex = Math.abs(replaceIndex - 1);
                            family.set(replaceIndex, relatedFamily.get(incestIndex));
                            //out.println("weird");
                        }
                        else {
                            //out.println("New marriage");
                        }
                    }
                    else {
                        //out.println("New people");
                    }
                }
                families.add(family);
            }
            catch(Exception e) {
                break;
            }
        }
        return families;
    }
}
