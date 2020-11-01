import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        HelloClassLoader classLoader = new HelloClassLoader();
        try {
            Class<?> clazz = classLoader.findClass("Hello");
            Method helloMethod = clazz.getDeclaredMethod("hello");
            helloMethod.invoke(clazz.newInstance());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("src/main/java/Hello.xlass");
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            if (Objects.isNull(is)) {
                throw new ClassNotFoundException();
            }

            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            byte[] bytes = out.toByteArray();

            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }

            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassFormatError();
        }
    }
}
