package muzhevsky.org.core.enums;

public enum ProjectStatus {
    ONVERIFICATION("on verification"), ACTIVE("active"), CANCELED("canceled"),
    DECLINEDBYMODERATION("declined by moderation"), SUCCESSFUL("successful");
    private String value;
    ProjectStatus(String value){
        this.value = value;
    }
    public String get() { return value;}
}
