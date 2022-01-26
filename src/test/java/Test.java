import com.ivo.common.utils.ExcelUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author wj
 * @version 1.0
 */
public class Test {

    public static void main(String[] args) throws IOException {
        File file = new File("/Users/wangjian/Downloads/LCM入库费用.xlsx");  //文件位置
        List list = ExcelUtil.readExcelFirstSheet(file);
        System.out.println(list.size());
    }
}
