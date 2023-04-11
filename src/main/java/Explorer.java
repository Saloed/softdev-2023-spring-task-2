import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Explorer {
    private final List<String> arrayForExplore;

    public Explorer(List<String> arrayForExplore){
        this.arrayForExplore = arrayForExplore;
    }

    public List<Long> makingLongArrayList(){
        List<Long> longArrayList = new ArrayList<Long>();
        for (int i=0; i < arrayForExplore.size();i++) {
            File curFile = new File(arrayForExplore.get(i));
            longArrayList.add(curFile.length());
        }
        return longArrayList;
    }
}
