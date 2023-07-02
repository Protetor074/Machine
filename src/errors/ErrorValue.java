package errors;

public class ErrorValue extends Exception {

    public ErrorValue( String parameterName){
        super("Błąd długości " + parameterName);
    }
}
