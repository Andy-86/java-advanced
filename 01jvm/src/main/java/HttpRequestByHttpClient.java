import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.Closeable;
import java.io.IOException;

/**
 * 作业2:写一段代码，使用 HttpClient 或 OkHttp 访问 http://localhost:8801 ，代码提交到 Github。
 *
 * @author zhongjinhui
 */
public class HttpRequestByHttpClient {

    public static void main(String[] args) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet("http://localhost:8088/api/hello");
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            System.out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(response);
            close(client);
        }
    }

    private static void close(Closeable io) {
        if (io == null) {
            return;
        }
        try {
            io.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
