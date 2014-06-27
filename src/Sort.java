import java.util.*;

public class Sort {
    public static void main(String[] args) {
        Random r = new Random(System.currentTimeMillis());
        int[] a = new int[10000000];
        for(int i = 0; i < 10000000; i++)
            a[i] = r.nextInt();
        long c1 = System.currentTimeMillis();
        sorting_fast_no(a);
        long c2 = System.currentTimeMillis();
        System.out.println(c2-c1);
        c1 = System.currentTimeMillis();
        pthreeSort(a);
        c2 = System.currentTimeMillis();
        System.out.println(c2-c1);
    }

    private static void sorting(int[] list) {
        int length = list.length;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (list[j] > list[j + 1]) {
                    int temp = list[j];
                    list[j] = list[j + 1];
                    list[j + 1] = temp;
                }
            }
        }
    }

    private static void sorting_fast(int[] list, int low, int high) {
        if (low < high) {
            int middle = getMiddle(list, low, high);
            sorting_fast(list, low, middle - 1);
            sorting_fast(list, middle + 1, high);

        }
    }

    public static int getMiddle(int[] list, int low, int high) {
        int tmp = list[low];
        while (low < high) {
            while (low < high && list[high] >= tmp) {
                high--;
            }
            list[low] = list[high];
            while (low < high && list[low] < tmp) {
                low++;
            }
            list[high] = list[low];
        }
        list[low] = tmp;
        return low;
    }

    private static void sorting_fast_no(int[] list) {
        if (list == null || list.length == 0) {
            return;
        }
        Stack<Integer> stack = new Stack<Integer>();
        int start = 0;
        int end = list.length - 1;
        stack.push(start);
        stack.push(end);
        while (!stack.empty()) {
            end = stack.pop();
            start = stack.pop();

            int mid = getMiddle(list, start, end);
            if (start < mid - 1) {
                stack.push(start);
                stack.push(mid - 1);
            }
            if (end > mid + 1) {
                stack.push(mid + 1);
                stack.push(end);
            }

        }
    }

    private static void sorting_merge(int[] list, int low, int high, int[] temp) {
        if (low < high) {
            int mid = (low + high) / 2;
            sorting_merge(list, low, mid, temp);
            sorting_merge(list, mid + 1, high, temp);
            merge(list, low, mid, high, temp);
        }
    }

    private static void sorting_merge_no(int[] list, int[] temp) {
        if (list == null || list.length == 1)
            return;
        int lmin, lmax, rmin, rmax = 0;
        for (int i = 1; i < list.length; i *= 2) {
            for (lmin = 0; lmin < list.length - i; lmin = rmax + 1) {
                lmax = lmin + i - 1;
                rmin = lmax + 1;
                rmax = rmin + i - 1;
                if (rmax > list.length - 1)
                    rmax = list.length - 1;
                merge(list, lmin, lmax, rmax, temp);
            }
        }
    }

    private static void merge(int[] list, int low, int mid, int high, int[] temp) {
        int i = low, j = mid + 1, k = low;
        while (i <= mid && j <= high) {
            if (list[i] < list[j]) {
                temp[k] = list[i];
                i++;
            } else {
                temp[k] = list[j];
                j++;
            }
            k++;
        }

        while (i <= mid) {
            temp[k] = list[i];
            i++;
            k++;
        }
        while (j <= high) {
            temp[k] = list[j];
            j++;
            k++;
        }
        for (int m = low; m <= high; m++) {
            list[m] = temp[m];
        }
    }

    private static void heapsort(int[] list) {
        int length = list.length;
        for (int i = length / 2 - 1; i >= 0; i--) {
            heapadjust(list, i, length);
        }
        for (int i = length - 1; i > 0; i--) {
            int temp = list[0];
            list[0] = list[i];
            list[i] = temp;
            heapadjust(list, 0, i);
        }
    }

    private static void heapadjust(int[] list, int i, int length) {
        int nchild = 0;
        int temp;
        for (temp = list[i]; 2 * i + 1 < length; i = nchild) {
            nchild = 2 * i + 1;
            if (nchild < length - 1 && list[nchild + 1] > list[nchild])
                nchild++;
            if (temp < list[nchild]) {
                list[i] = list[nchild];
            } else {
                break;
            }
            list[nchild] = temp;
        }
    }

    private static int[] pthreeSort(int[] source) {
        int curElemIndex = 0;
        int originRunSize = (int) Math.sqrt(source.length);
        List<List<Integer>> runs = new ArrayList<List<Integer>>(originRunSize);
        List<Integer> tails = new ArrayList<Integer>(originRunSize);
        List<Integer> runSize = new ArrayList<Integer>(originRunSize);
        while (curElemIndex < source.length) {
            int k = pthreeBinarySearch(source[curElemIndex], tails);
            List<Integer> temp = null;
            if (k == -1) {
                temp = new ArrayList<Integer>();
                temp.add(source[curElemIndex]);
                runs.add(temp);
                tails.add(source[curElemIndex]);
                runSize.add(1);
                curElemIndex++;
            } else do {
                temp = runs.get(k);
                temp.add(source[curElemIndex]);
                tails.set(k, source[curElemIndex]);
                runSize.set(k, runSize.get(k) + 1);
                curElemIndex++;
            } while (curElemIndex < source.length && source[curElemIndex - 1] <= source[curElemIndex]);
        }
        return unbalancedMerge(runs, runSize, source);
    }

    private static int pthreeBinarySearch(int element, List<Integer> tails) {
        int begin = 0;
        int end = tails.size() - 1;
        int mid = begin + (end - begin) / 2;
        if (end == -1)
            return -1;
        while (begin < end) {
            if (tails.get(mid).compareTo(element) <= 0) {
                end = mid;
                mid = begin + (end - begin) / 2;
            } else {
                begin = mid + 1;
                mid = begin + (end - begin) / 2;
            }
        }
        if (tails.get(begin) > element)
            return -1;
        else
            return begin;
    }

    private static int[] unbalancedMerge(List<List<Integer>> runs, List<Integer> runSize, int[] elem1) {
        class RunSizeRefs {
            public int runIndex;
            public int runSize;
            public RunSizeRefs(int runIndex, int runSize) {
                this.runIndex = runIndex;
                this.runSize = runSize;
            }
        }
        class ElemsRuns {
            public int elemArr;
            public int elemIndex;
            public int runSize;
            public ElemsRuns(int elemArr, int elemIndex, int runSize) {
                this.elemArr = elemArr;
                this.elemIndex = elemIndex;
                this.runSize = runSize;
            }
        }

        List<RunSizeRefs> runSizeRefs = new ArrayList<RunSizeRefs>(runs.size());
        int[] elem2 = new int[elem1.length];
        int[][] elem = new int[2][];
        elem[0] = elem1;
        elem[1] = elem2;
        List<ElemsRuns> elemsRuns = new LinkedList<ElemsRuns>();
        int nextEmptyArrayLoc = 0;

        for (int i = 0; i < runSize.size(); i++)
            runSizeRefs.add(new RunSizeRefs(i, (runSize.get(i)).intValue()));
        Collections.sort(runSizeRefs, new Comparator<RunSizeRefs>() {
            @Override
            public int compare(RunSizeRefs o1, RunSizeRefs o2) {
                if (o1.runSize <= o2.runSize)
                    return -1;
                else
                    return 1;
            }
        });
        for (int i = 0; i < runSizeRefs.size(); i++) {
            RunSizeRefs temp = runSizeRefs.get(i);
            List<Integer> runTemp = runs.get(temp.runIndex);
            for(int j = 0; j < temp.runSize; j++){
                elem[0][j+nextEmptyArrayLoc] = runTemp.get(j);
            }
            elemsRuns.add(new ElemsRuns(0, nextEmptyArrayLoc, temp.runSize));
            nextEmptyArrayLoc += temp.runSize;
        }
        ListIterator<ElemsRuns> curRunIt = elemsRuns.listIterator();
        ElemsRuns curRun = null;
        ElemsRuns nextRun = null;
        curRun = curRunIt.next();
        while(elemsRuns.size() > 1){
            if(curRunIt.hasNext()){
                nextRun = curRunIt.next();
                if(curRun.runSize+nextRun.runSize > (elemsRuns.get(0).runSize + (elemsRuns.get(1)).runSize)){
                    curRunIt = elemsRuns.listIterator();
                    curRun = curRunIt.next();
                    nextRun = curRunIt.next();
                }
            } else{
                curRunIt = elemsRuns.listIterator();
                curRun = curRunIt.next();
                nextRun = curRunIt.next();
            }
            if(curRun.elemArr == 0){
                pthreeMerge(elem, curRun.elemIndex, curRun.runSize + curRun.elemIndex, curRun.elemArr, nextRun.elemIndex, nextRun.runSize + nextRun.elemIndex, nextRun.elemArr, curRun.elemIndex, curRun.elemArr + 1);
                curRun.elemArr = 1;
            }else{
                pthreeMerge(elem, curRun.elemIndex, curRun.runSize + curRun.elemIndex, curRun.elemArr, nextRun.elemIndex, nextRun.runSize + nextRun.elemIndex, nextRun.elemArr, curRun.elemIndex, curRun.elemArr - 1);
                curRun.elemArr = 0;
            }
            curRun.runSize += nextRun.runSize;
            curRunIt.remove();
            if(curRunIt.hasNext())
                curRun = curRunIt.next();
        }
        if(curRun.elemArr == 0)
            return elem1;
        else
            return elem2;
    }

    private static void pthreeMerge(int[][] elem,int run1Index, int run1End, int run1Arr, int run2Index, int run2End, int run2Arr, int targetIndex, int targetArr){
        while(run1Index < run1End && run2Index < run2End){
            if(elem[run1Arr][run1Index] < elem[run2Arr][run2Index]){
                elem[targetArr][targetIndex] = elem[run1Arr][run1Index];
                run1Index++;
            }else{
                elem[targetArr][targetIndex] = elem[run2Arr][run2Index];
                run2Index++;
            }
            targetIndex++;
        }
        while(run1Index < run1End){
            elem[targetArr][targetIndex] = elem[run1Arr][run1Index];
            targetIndex++;
            run1Index++;
        }
        while(run2Index < run2End){
            elem[targetArr][targetIndex] = elem[run2Arr][run2Index];
            targetIndex++;
            run2Index++;
        }
    }

}
