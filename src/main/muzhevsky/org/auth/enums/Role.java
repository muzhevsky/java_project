package muzhevsky.org.auth.enums;

public enum Role {
    USER("user"), COMPANY("company"), ADMIN("admin");
    private String value;
    Role(String value){
        this.value = value;
    }

    public String get(){
        return value;
    }
}
