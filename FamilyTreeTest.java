import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import static org.junit.Assert.*;

public class FamilyTreeTest {

    private CaseGenerator treeGenerator = new CaseGenerator();

    @Test
    public void test1() throws Exception {
        test(1);
    }

    @Test
    public void test2() throws Exception {
        treeGenerator.createRandomCase(2, 10);
        test(2);
    }

    @Test
    public void test3() throws Exception {
        treeGenerator.createRandomCase(3, 100);
        test(3);
    }

    @Test
    public void test4() throws Exception {
        //This one may take a while
        treeGenerator.createRandomCase(4, 1000L);
        test(4);
    }

    @Test
    public void test5() throws Exception {
        test(5);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File getOutFile(int num) throws IOException {
        File file = new File(String.format("files/out/out%d.txt", num));
        if(!file.exists())
            file.createNewFile();
        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File getCorrectOutFile(int num) throws IOException {
        File file = new File(String.format("files/correctout/out%d.txt", num));
        if(!file.exists())
            file.createNewFile();
        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File getInFile(int num) throws IOException {
        File file = new File(String.format("files/in/input%d.txt", num));
        if(!file.exists())
            file.createNewFile();
        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private File getQueryFile(int num) throws IOException {
        File file = new File(String.format("files/in/query%d.txt", num));
        if(!file.exists())
            file.createNewFile();
        return file;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void test(int testNum) throws Exception {
        File f = getOutFile(testNum);
        if(f.exists())
            f.delete();
        f.createNewFile();
        runCase(testNum);
        assertTrue(String.format("Output file %d is not created yet", testNum), f.exists());
        assertTrue(matchFiles(getCorrectOutFile(testNum), getOutFile(testNum)));
    }

    private void runCase(int fileNo){
        try {
            FamilyTree familyTree = new FamilyTree();
            familyTree.buildFamilyTree(String.format("files/in/input%d.txt",fileNo));
            familyTree.evaluate(String.format("files/in/query%d.txt", fileNo), String.format("files/out/out%d.txt", fileNo));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean matchFiles(File realFile, File outFile) {
        try {
            Path realPath = realFile.toPath(), outPath = outFile.toPath();
            long size = Files.size(realPath);
            if(size != Files.size(outPath)) {
                return false;
            }
            if (size < 4096)
                return Arrays.equals(Files.readAllBytes(realPath), Files.readAllBytes(outPath));

            try {
                InputStream is1 = Files.newInputStream(realPath);
                InputStream is2 = Files.newInputStream(outPath);
                int bufSize = 4096;
                byte[] br1 = new byte[bufSize];
                byte[] br2 = new byte[bufSize];
                int data;
                while ((data = is1.read(br1)) != -1) {
                    if (data != is2.read(br2) || !Arrays.equals(br1, br2))
                        return false;
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    private class CaseGenerator {

        private void createRandomCase(int testNum, long count) throws Exception {
            List<List<String>> familyLine = createRandomInput(testNum, count);
            createRandomQuery(testNum, familyLine);
            createCorrectOutFile(testNum);
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        private List<List<String>> createRandomInput(int testNum, long count) throws IOException {
            File inFile = getInFile(testNum);
            if(inFile.exists()){
                inFile.delete();
                inFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(inFile));
            RandomFamilyGenerator familyGenerator = new RandomFamilyGenerator();
            List<List<String>> familyLine = familyGenerator.getRandomFamilyLine(count);
            for(List<String> family: familyLine) {
                for(String member: family) {
                    writer.write(member);
                    writer.write(' ');
                }
                writer.write('\n');
            }
            writer.close();
            return familyLine;
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        private void createRandomQuery(int testNum, List<List<String>> familyLine) throws IOException {
            File inFile = getQueryFile(testNum);
            if(inFile.exists()){
                inFile.delete();
                inFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(inFile));
            int personCount = 0;
            for(List<String> list: familyLine) {
                personCount += list.size();
            }
            double chance = .2;
            int threshold = (int) (personCount * chance);
            SecureRandom random = RandomNameGenerator.getSecureRandom();
            Set<String> tried = new TreeSet<>();
            for(int i = 0; i < personCount; i++) {
                if(random.nextInt(personCount) < threshold) {
                    int family1Index = random.nextInt(familyLine.size()),
                        family2Index = random.nextInt(familyLine.size());
                    List<String> family1 = familyLine.get(family1Index),
                                family2 = familyLine.get(family2Index);
                    int index1 = random.nextInt(family1.size()),
                        index2 = random.nextInt(family2.size());
                    String name1 = family1.get(index1),
                            name2 = family2.get(index2);
                    String key = name1 + name2;
                    if(!name1.equals(name2) && !tried.contains(key)) {
                        writer.write(String.format("%s\t%s%n", name1, name2));
                        tried.add(key);
                    }
                }
            }

            writer.close();
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        private void createCorrectOutFile(int testNum) throws Exception {
            runCase(testNum);
            File correctOutFile = getCorrectOutFile(testNum);
            correctOutFile.delete();
            Files.copy(getOutFile(testNum).toPath(), correctOutFile.toPath());
            runCase(testNum);
            assertTrue(matchFiles(getCorrectOutFile(testNum), getOutFile(testNum)));
        }
    }
}