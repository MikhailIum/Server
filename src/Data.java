import java.io.Serializable;
import java.nio.ByteBuffer;

public class Data implements Serializable {
    String a;
    int b;

    String buffer;

    public Data(String a, int b){
        this.a = a;
        this.b = b;
    }


}
