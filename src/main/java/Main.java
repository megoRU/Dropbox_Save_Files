import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class Main {

  private static final String ACCESS_TOKEN = "nhCokcZyCr0AAAAAAAAAoCukW17BwUGYDSGvWYf8ahtNStY23UFzGj-82eGf9EOl";
  private static final String DIRECTORY = "D:\\Test\\";

  public static void main(String[] args) {

    DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/javaFileCopy").build();
    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
    System.out.println("Копирует файлы и отправляет в Dropbox!");
    long timeStart = System.currentTimeMillis();
    try {
      Files.walk(Paths.get(DIRECTORY))
          .filter(Files::isRegularFile)
          .map(Path::toFile)
          .collect(Collectors.toList())
          .forEach(file -> {
            try {
              InputStream inputStream2 = new FileInputStream(file.getPath());
              client.files().uploadBuilder("/" + file.getName()).uploadAndFinish(inputStream2);
            } catch (Exception e) {
              e.printStackTrace();
            }
          });
    } catch (Exception e) {
      e.printStackTrace();
    }
    long timeEnd = System.currentTimeMillis();
    long result = (timeEnd - timeStart) / 1000;

    System.out.println("Программа удачно все скопировала!" + "\n" + "Программа завершила свою работу за: " + result + " секунд(ы)");
  }
}
