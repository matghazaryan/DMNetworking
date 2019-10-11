package networkexample.md.com.networkexample.model;

import java.util.HashMap;

public class Test {

    private String title;

    private long date;

    private String logo;

    private HashMap<String, HashMap<String, Test2>> list;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public HashMap<String, HashMap<String, Test2>> getList() {
        return list;
    }

    public void setList(HashMap<String, HashMap<String, Test2>> list) {
        this.list = list;
    }
}
