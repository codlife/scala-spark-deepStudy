import java.io.IOException;

/**
 * Created by wjf on 2016/8/9.
 */
public class TestExternalEXE {
    public static void main(String[] args) throws IOException {
        String path="D:\\wechat\\WeChat.exe";
        Runtime runtime=Runtime.getRuntime();
        Process process =runtime.exec(path);
    }
}
