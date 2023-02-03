public class Card {
    private int value;

    private String type;

    private String title;

    public boolean isHit = false;

    public Card(int value, String type, String title) {
        this.value = value;
        this.type = type;
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Card hit() {
        this.isHit = true;

        return this;
    }
}
