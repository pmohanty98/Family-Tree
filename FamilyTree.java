import java.util.ArrayList;
import java.util.LinkedList;
import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;



class Pair<K, V> {
    /*
    The Pair class is intended to store key, value pairs. It'll be helpful
    for part 1.2 of the assignment.
    */
    public String key;
    public int value;

    public Pair(String key, int value) {
        this.key = key;
        this.value = value;
    }
}

class Djikstra_Table{

    public String vertex;
    public int distance;

    public Djikstra_Table(String vertex,int weight)
    {
        this.distance=weight;
        this.vertex=vertex;
    }
}

public class FamilyTree {

    String[] ans= new String[1000];
    int ans_index=0;

    String person;

    Queue<String> q = new LinkedList<>();


    ArrayList<LinkedList<String>> FamArrList;
    int index_FAL = 0;


       public void  Ancestor (String P1,ArrayList<String> A1) {

             for (int i = 0; i < FamArrList.size(); i++) {

                 for (int j = 1;  FamArrList.get(i)!=null && j<FamArrList.get(i).size()  ; j++) {

                     if (FamArrList.get(i).get(j).equals(P1) && !A1.contains(FamArrList.get(i).get(0))) {

                         A1.add(FamArrList.get(i).get(0));
                         Ancestor(FamArrList.get(i).get(0), A1);
                     }


                 }
             }
         }

    int weight = 0;
       int y=0;
   // int starting_parent_index=Index_returner(FamArrList,dest1);


    public int return_Min( int a, int b)
    {
        if(a==b)
            return a;

        else if(a>b)
            return b;

    return a;}

public ArrayList<String> AllMembers(ArrayList<LinkedList<String>> A1)
{
    ArrayList<String> A_Mem = new ArrayList();
    for(int i=0;i<FamArrList.size();i++)
    {
        for(int j=0; FamArrList.get(i)!=null && j<FamArrList.get(i).size();j++)
        {

            if(!A_Mem.contains(FamArrList.get(i).get(j)))
                A_Mem.add(FamArrList.get(i).get(j));
        }

    }
return A_Mem;}

    public int path_weight_finder( String start_node, String dest1, int weight, int dest_index) {


        ArrayList<Pair<String,Integer>> Child= new ArrayList<Pair<String, Integer>>();

        ArrayList<String> Visited= new ArrayList<>();

        Djikstra_Table[] D_T = new Djikstra_Table[1000];

        for (int i = 0; i < 1000; i++) {
            D_T[i] = new Djikstra_Table("",100000);
          }

        for (int i = 0; i < AllMembers(FamArrList).size(); i++) {

            D_T[i].vertex = AllMembers(FamArrList).get(i);

            if (D_T[i].vertex.equals(dest1))
                D_T[i].distance = 0;
        }
        int Child_index=0;
        for (int j = 0; j < 1000; j++) {
            if (start_node.equals(D_T[j].vertex))
            { Child_index = j;
            break;}
        }

        int Parent_index = 0;

        while (D_T[Child_index].distance==100000) {
            Visited.add(dest1);

            int start_index = Index_returner(FamArrList, dest1);

            for (int j = 0; j < 1000; j++) {
                    if (FamArrList.get(start_index).get(0).equals(D_T[j].vertex))
                        Parent_index = j;
            }

            for (int i = 1; i < FamArrList.get(start_index).size(); i++) {
                for (int j = 0; j < 1000; j++) {

                    if (D_T[j].vertex.equals(FamArrList.get(start_index).get(i))) {
                        D_T[j].distance = return_Min(D_T[j].distance, 1 + D_T[Parent_index].distance);

                        Pair<String, Integer> e = new Pair<>(D_T[j].vertex, D_T[j].distance);
                        Child.add(e);
                    }

                }
            }

            for (int i = 0; i < Child.size(); i++) {
                for (int j = i ; j < Child.size(); j++) {

                    if (Child.get(j).value < Child.get(i).value) {
                        Collections.swap(Child, i, j);
                    }
                }
            }

            for(int k=0;k<Child.size();k++)
            {

                if(Index_returner(FamArrList,Child.get(k).key)==-1) {
                    Child.remove(k);
                }
            }

            for(int k=0;k<Child.size();k++)
            {

                if(!Visited.contains(Child.get(k).key)) {
                    dest1 = Child.get(k).key;
                    break;
                }
            }
        }


    return D_T[Child_index].distance;
    }

      /*  public int path_weight_finder( String start_node, String dest1, int weight, int dest_index) {


             int destination_index=Index_returner(FamArrList, dest1);


             for (int i = 1; i < FamArrList.get(destination_index).size(); i++) {

                q.add(FamArrList.get(destination_index).get(i));

                 if (q.contains(person))
                 { y= weight;
                     return weight;}
             }


             while(Index_returner(FamArrList,q.peek())==-1)
                 q.remove();

             weight++;
             path_weight_finder(start_node,q.poll(),weight,dest_index);

             return weight;}
*/

    public static ArrayList< String> cloneList(ArrayList<String> list) {
        ArrayList<String> clone = new ArrayList<String>(list.size());
        for (String item: list) clone.add(item);
        return clone;
    }


         public void Lex_Order(ArrayList<String>Copy_Lex){

           for(int i=0;i<Copy_Lex.size();i++)
           {  for(int j=i;j<Copy_Lex.size();j++) {
               if (Copy_Lex.get(i).compareTo(Copy_Lex.get(j)) > 0)
                   Collections.swap(Copy_Lex, i, j);
           }
           }
               String dummy="";
           for(int i=0;i<Copy_Lex.size();i++) {

               if(i==Copy_Lex.size()-1) {
                   dummy = dummy + Copy_Lex.get(i);
               break;}

                dummy=dummy+Copy_Lex.get(i)+ " ";
           }
               ans[ans_index] = dummy;




         }

    /**
     * Declare necessary variables to describe your Tree
     * Each Node in the Tree represents a person
     * You can declare other classes if necessary
     */

    public int Index_returner(ArrayList<LinkedList<String>> arraylist, String item) {
        for (int i = 0;  arraylist.get(i) != null && i < arraylist.size() ; i++) {
            if (item.equals(arraylist.get(i).get(0)))
                return i;

        }
        return -1;
    }

    public FamilyTree() {

        FamArrList = new ArrayList<LinkedList<String>>();


        for (int i = 0; i < 1000; i++)
            FamArrList.add(null);
    }

    /**
     * @param familyFile
     * @throws Exception
     * @input directory or filename of input file. This file contains the information necessary to build the child
     * parent relation. Throws exception if file is not found
     */

    public void buildFamilyTree(String familyFile) throws Exception {
        String str;

        FileReader fr = new FileReader(familyFile);
        BufferedReader br = new BufferedReader(fr);

        try {

            while ((str = br.readLine()) != null) {
                String[] splited1 = str.split("\\s+");

                ArrayList<String> splitted2 = new ArrayList<>(Arrays.asList(splited1));

                for (int i = 0; i < splitted2.size(); i++)
                    if (splitted2.get(i).equals(""))
                        splitted2.remove(i);

                String[] splited3 = splitted2.toArray(new String[splitted2.size()]);


                String Husband = splited3[0];
                String Wife = splited3[1];

                if (Index_returner(FamArrList, Husband) != -1) {
                    for (int i = 2; i < splited3.length; i++)
                        FamArrList.get(Index_returner(FamArrList, Husband)).add(splited3[i]);
                } else {
                    FamArrList.set(index_FAL, new LinkedList<>());
                    FamArrList.get(index_FAL).add(Husband);

                    for (int i = 2; i < splited3.length; i++)
                        FamArrList.get(index_FAL).add(splited3[i]);

                    index_FAL++;
                }


                if (Index_returner(FamArrList, Wife) != -1) {
                    for (int i = 2; i < splited3.length; i++)
                        FamArrList.get(Index_returner(FamArrList, Wife)).add(splited3[i]);
                } else {
                    FamArrList.set(index_FAL, new LinkedList<>());
                    FamArrList.get(index_FAL).add(Wife);

                    for (int i = 2; i < splited3.length; i++)
                        FamArrList.get(index_FAL).add(splited3[i]);

                    index_FAL++;
                }
            }

        } catch (Exception e) {
            throw e;
        }

        fr.close();

    }

    /**
     * @param queryFile
     * @param outputFile
     * @throws Exception
     * @input directory or filename of Query and Output.
     * queryFile contains the queries about the tree.
     * The output of this query should be written in file outputfile.
     */

    public void evaluate(String queryFile, String outputFile) throws Exception {

        String str;

        FileReader fr = new FileReader(queryFile);
        BufferedReader br = new BufferedReader(fr);

        try {

            while ((str = br.readLine()) != null) {
                String[] splited1 = str.split("\\s+");

                ArrayList<String> splitted2 = new ArrayList<>(Arrays.asList(splited1));

                for (int i = 0; i < splitted2.size(); i++) {
                    if (splitted2.get(i).equals(""))
                        splitted2.remove(i);
                }

                String[] splited3 = splitted2.toArray(new String[splitted2.size()]);

                String Person1 = splited3[0];
                String Person2 = splited3[1];

                ArrayList<String> Ancestry_P1 = new ArrayList();

                ArrayList<String> Ancestry_P2 = new ArrayList();

                Ancestor(Person1,Ancestry_P1);
                Ancestor(Person2,Ancestry_P2);

                ArrayList<String> Copy_P1= new ArrayList<String>();

               Copy_P1=cloneList(Ancestry_P1);


                Copy_P1.retainAll(Ancestry_P2);

                if(Copy_P1.size()==0 && !Ancestry_P2.contains(Person1) && !Ancestry_P1.contains(Person2))
                ans[ans_index]="unrelated";

                else if(Ancestry_P2.contains(Person1) ) {
                    ans[ans_index] = Person2 + " is a descendant of " + Person1;
                }
                else if(Ancestry_P1.contains(Person2) ) {
                    ans[ans_index] = Person1 + " is a descendant of " + Person2;
                }
                else
                {

                    ArrayList<String> Copy_Lex= new ArrayList<String>();

                    Copy_Lex=cloneList(Ancestry_P1);

                    Copy_Lex.retainAll(Ancestry_P2);

                    ArrayList<Pair<String,Integer>> CommAncPairs= new ArrayList<Pair<String, Integer>>(Copy_Lex.size());

                    for(int i=0;i<Copy_Lex.size();i++)
                    {
                        int[] weights= new int[2];
                        int starting_parent_index=Index_returner(FamArrList,Copy_Lex.get(i));
                        person=Person1;

                        weights[0]=  path_weight_finder(Person1,Copy_Lex.get(i),0,starting_parent_index);

                       // weights[0]=y;
                        q.clear();
                        person=Person2;

                         weights[1]=  path_weight_finder(Person2,Copy_Lex.get(i),0,starting_parent_index);
                        //weights[1]=y;
                         q.clear();
                        Pair<String, Integer> e = new Pair<>(Copy_Lex.get(i), weights[0]+weights[1]);
                        CommAncPairs.add(e);

                    }

                    Pair<String,Integer> temp = new Pair<>("", 0);
                    for (int i = 0; i < Copy_Lex.size(); i++) {
                        for (int j = i ; j < Copy_Lex.size(); j++) {

                            if (CommAncPairs.get(j).value < CommAncPairs.get(i).value) {
                                Collections.swap(CommAncPairs, i, j);
                            }
                        }
                    }

                    Copy_Lex.clear();

                    for(int i=0;i<CommAncPairs.size();i++)
                    {
                        if(CommAncPairs.get(0).value==CommAncPairs.get(i).value)
                            Copy_Lex.add(CommAncPairs.get(i).key);
                    }

                    Lex_Order(Copy_Lex);
                }
            ans_index++;
            }

        } catch (Exception e) {
            throw e;
        }

        fr.close();

        FileWriter fw= new FileWriter(outputFile);

       for( int i=0;i<ans.length;i++) {

           for(int j=0;ans[i]!=null && j<ans[i].length();j++)
           {

               fw.write(ans[i].charAt(j));
           }
           fw.write('\n');
       }
       fw.close();

    }
}
