package errors;

public class ErrorPosition extends Exception {
    public ErrorPosition(String parameterName, int x, int y){
        super("     Element " + parameterName + " znajduje się na nieistniejących koordynatach (" + x + "," + y + ")     ");
    }
}
