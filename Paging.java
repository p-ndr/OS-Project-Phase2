//package Phase2;

import java.util.*;

public class Paging {

    private static String deepToString(Object[] array){
        String res = "[";

        for (int i = 0; i < array.length; i++) {
            if (i!=array.length-1){
                res += array[i] + "" + " ";
            }else {
                res += array[i] + "" + "]";
            }
        }

        return res;
    }

    private static String deepToString(Page[] array) {
        String res = "[";

        for (int i = 0; i < array.length; i++) {
            if (i != array.length - 1) {
                res += array[i].getPage() + "" + " ";
            } else {
                res += array[i].getPage() + "" + "]";
            }
        }

        return res;
    }

    private static int[] parse(String[] s){
        int[] res = new int[s.length];
        for (int i = 0; i < s.length; i++) {
            res[i] = Integer.parseInt(s[i]);
        }
        return res;
    }

    private static boolean contains(ArrayList<Page> list, int n){
        for (int i = 0; i < list.size(); i++) {
            if (n == list.get(i).getPage()){
                return true;
            }
        }
        return false;
    }

    private static void updateList(ArrayList<Page> list, Page[] objects){
        list.clear();
        for (int i = 0; i < objects.length; i++) {
            list.add(objects[i]);
        }
    }

    private static Page[] cast(Object[] objects){
        Page[] pages = new Page[objects.length];
        for (int i = 0; i < objects.length; i++) {
            pages[i] = (Page) objects[i];
        }
        return pages;
    }

    private static int LRU(Page[] pages){
        int res = 0;

        int min = Integer.MAX_VALUE;

        for (int i = 0; i < pages.length; i++) {
            if (pages[i].getUsed()<min){
                min = pages[i].getUsed();
                res = i;
            }
        }

        return res;
    }

    private static boolean isInteger(String[] s){
        for (int i = 0; i < s.length; i++) {
            try {
                int num = Integer.parseInt(s[i]);
            }catch (NumberFormatException e){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        int pageNumber = read.nextInt();
        read.nextLine();
        String ref = read.nextLine();
        String par = read.nextLine();
        
        String s1 = ref;
        String[] arr = s1.split(" ");
        boolean isInt = isInteger(arr);
        if (!isInt){
            System.out.println("error");
            return;
        }

        int[] reference = parse(ref.split(" "));
        int n = reference.length;
        String[] param = par.split(" ");
        int frame = Integer.parseInt(param[0]);
        String method = param[1];
        String data1 = "page fault";
        String data2 = "total number of page faults is ";
        int headIndex = 0;

        if (method.equals("FIFO")){

            int pageFaults = 0;
            ArrayList<String> output = new ArrayList<>( 25);
            Queue<Integer> memory = new LinkedList<>();

            for (int i = 0; i < n; i++) {
                if (memory.size()<frame){
                    if (!memory.contains(reference[i])){
                        memory.add(reference[i]);
                        pageFaults++;
                        Object[] temp = memory.toArray();
                        String s = deepToString(temp);
                        output.add(reference[i]+ "" + " " + s + " " + data1);
                        headIndex = 0;
                    }else {
                        Object[] temp = memory.toArray();
                        String s = deepToString(temp);
                        output.add(reference[i]+ "" + " " + s);
                    }
                }else {
                    if (!memory.contains(reference[i])){
                        if (headIndex==frame){
                            headIndex = 0;
                        }
                        Object[] objects = memory.toArray();
                        objects[headIndex] = reference[i];
                        headIndex++;
                        memory.clear();
                        for (int j = 0; j < objects.length; j++) {
                            memory.add((Integer) objects[j]);
                        }
                        pageFaults++;
                        Object[] temp = memory.toArray();
                        String s = deepToString(temp);
                        output.add(reference[i]+ "" + " " + s + " " + data1);
                    }else {
                        Object[] temp = memory.toArray();
                        String s = deepToString(temp);
                        output.add(reference[i]+ "" + " " + s);
                    }
                }
            }

            Object[] res = output.toArray();
            for (int i = 0; i < res.length; i++) {
                System.out.println(res[i]);
            }
            System.out.println(data2+pageFaults+"");

        } else if (method.equals("LRU")){

            ArrayList<Page> pageSet = new ArrayList<>();
            ArrayList<String> output = new ArrayList<>();
            int pageFaults = 0;
            int time = 0;

            Page[] pages = new Page[pageNumber];
            for (int i = 0; i < pages.length; i++) {
                pages[i] = new Page(reference[i],0);
            }

            for (int i = 0; i < pageNumber; i++) {
                if (pageSet.size()<frame){
                    if (!contains(pageSet, pages[i].getPage())) {
                        pageFaults++;
                        pages[i].setUsed(time);
                        time++;
                        pageSet.add(pages[i]);
                        Object[] objects = pageSet.toArray();
                        Page[] temp = cast(objects);
                        String s = deepToString(temp);
                        output.add(pages[i].getPage() + "" + " " + s + " " + data1);
                    }else {
                        Object[] objects = pageSet.toArray();
                        Page[] temp = cast(objects);
                        pages[i].setUsed(time);
                        for (int j = 0; j < pageSet.size(); j++) {
                            if (pages[i].getPage()==temp[j].getPage()){
                                temp[j].setUsed(time);
                            }
                        }
                        time++;
                        updateList(pageSet,temp);
                        String s = deepToString(temp);
                        output.add(pages[i].getPage()+ "" + " " + s);
                    }
                }else {
                    if (!contains(pageSet, pages[i].getPage())){
                        pageFaults++;
                        Object[] objects = pageSet.toArray();
                        Page[] temp = cast(objects);
                        int min = LRU(temp);
                        Page p = pages[i];
                        pages[i].setUsed(time);
                        p.setUsed(time);
                        time++;
                        temp[min] = p;
                        updateList(pageSet,temp);
                        String s = deepToString(temp);
                        output.add(pages[i].getPage() + "" + " " + s + " " + data1);
                    }else {
                        Object[] objects = pageSet.toArray();
                        Page[] temp = cast(objects);
                        pages[i].setUsed(time);
                        for (int j = 0; j < pageSet.size(); j++) {
                            if (pages[i].getPage()==temp[j].getPage()){
                                temp[j].setUsed(time);
                            }
                        }
                        time++;
                        updateList(pageSet,temp);
                        String s = deepToString(temp);
                        output.add(pages[i].getPage()+ "" + " " + s);
                    }
                }
            }

            Object[] objects = output.toArray();
            for (int i = 0; i < objects.length; i++) {
                System.out.println(objects[i]);
            }
            System.out.println(data2 + pageFaults);
        }else {
            System.out.println("error");
        }
    }
}

class Page{

    private int used;
    private int page;

    public Page(){}

    public Page(int page, int used){
        this.used = used;
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }
}
