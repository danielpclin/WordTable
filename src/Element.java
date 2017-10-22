/**
 * Created by danielpclin on 2017/2/6.
 */
public class Element {
    private String name;
    private String content;
    private String num;
    private int repeat;

    public Element() {
        this.name = "";
        this.content = "";
        this.num = "";
        this.repeat = 0;
    }

    public Element(String name, String content, String num, int repeat) {
        this.name = name;
        this.content = content;
        this.num = num;
        this.repeat = repeat;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRepeat() {
        return this.repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
}
