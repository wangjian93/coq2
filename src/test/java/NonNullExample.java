import lombok.NonNull;

public class NonNullExample extends Something {

    private String name;

    public NonNullExample(Person person) {
        super("Hello");
        if (person == null) {
            throw new NullPointerException("person");
        }
        this.name = person.getName();
    }
}